package org.launchcode.multivote.controllers;

import org.launchcode.multivote.models.Candidate;
import org.launchcode.multivote.models.Poll;
import org.launchcode.multivote.models.User;
import org.launchcode.multivote.models.data.CandidateDao;
import org.launchcode.multivote.models.data.PollDao;
import org.launchcode.multivote.models.data.UserDao;
import org.launchcode.multivote.models.forms.ApprovalVoteForm;
import org.launchcode.multivote.models.forms.PluralityVoteForm;
import org.launchcode.multivote.models.forms.RCVForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/poll/")
public class PollController {

    @Autowired
    private CandidateDao candidateDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PollDao pollDao;


    // Main Poll page
    @RequestMapping(value = "{pollId}")
    public String poll(Model model,
                       @PathVariable int pollId)
    {
        Poll thisPoll = pollDao.findById(pollId).get();
        model.addAttribute("poll", thisPoll);
        Date dateCreated = thisPoll.getDateCreated().getTime();

        String dateCreatedStr = dateCreated.toString();
        //model.addAttribute("title", thisPoll.getCandidates());
        model.addAttribute("dateCreated", dateCreatedStr);
        return "poll";
    }

    // Vote Forms
    @RequestMapping(value = "vote/{pollId}", method = RequestMethod.GET)
    public String voteForms(Model model,
                            @PathVariable int pollId)
    {
        Poll thisPoll = pollDao.findById(pollId).get();
        ArrayList<Candidate> candidates = new ArrayList<>(thisPoll.getCandidates());

        model.addAttribute("poll", thisPoll);

        if(thisPoll.getVotingSystem().equals("Ranked Choice"))
        {
            RCVForm rankedChoiceForm = new RCVForm(thisPoll.getVotingSystem(), candidates);
            rankedChoiceForm.setPollId(thisPoll.getId());
            model.addAttribute("rankedChoiceForm", rankedChoiceForm);

            return "voting-forms/ranked-choice-vote";
        }

        else if(thisPoll.getVotingSystem().equals("Approval"))
        {
            ApprovalVoteForm approvalVoteForm = new ApprovalVoteForm(thisPoll.getVotingSystem(), candidates);
            approvalVoteForm.setPollId(thisPoll.getId());
            model.addAttribute("approvalVoteForm", approvalVoteForm);
            return "voting-forms/approval-vote";
        }

        else
        {
            PluralityVoteForm pluralityVoteForm = new PluralityVoteForm(thisPoll.getVotingSystem(), candidates);
            pluralityVoteForm.setPollId(thisPoll.getId());
            model.addAttribute("pluralityVoteForm", pluralityVoteForm);
            return "voting-forms/plurality-vote";
        }
    }

    // Process Plurality Votes
    @RequestMapping(value = "vote/plurality", method = RequestMethod.POST)
    public String processPluralityVote(Model model,
                                       @ModelAttribute("pluralityVoteForm") @Valid
                                       PluralityVoteForm pluralityVoteForm,
                                       Errors errors,
                                       @CookieValue(value = "username")
                                       String votersUsername)
    {
        if(errors.hasErrors())
        {
            System.out.println("Errors: " + errors.getAllErrors());
            return "voting-forms/plurality-vote";
            //return "redirect:/poll/vote/" + pluralityVoteForm.getPollId();
        }

        User thisUser = userDao.findByName(votersUsername);
        int chosenCandidateId = pluralityVoteForm.getChosenCandidateId();
        Poll thisPoll = pollDao.findById(pluralityVoteForm.getPollId()).get();
        ArrayList<Candidate> candidates = new ArrayList<>(thisPoll.getCandidates());

        //TODO: Use Cnadidate ID's instead of names, use names only for display
        for(Candidate candidate : candidates)
        {
            if(candidate.getId() == chosenCandidateId)
            {
                candidate.incrementPluralityVotes();
                candidate.addVoter(thisUser);
                candidateDao.save(candidate);
            }
        }

        return "redirect:/poll/" + pluralityVoteForm.getPollId();
    }

    // Process Approval Votes
    @RequestMapping(value = "vote/approval", method = RequestMethod.POST)
    public String processApprovalVote(Model model,
                                      @ModelAttribute("approvalVoteForm") @Valid
                                      ApprovalVoteForm approvalVoteForm,
                                      Errors errors)
    {

        if (errors.hasErrors())
        {
            System.out.println(approvalVoteForm);
            System.out.println("Errors: " + errors.getAllErrors());
            voteForms(model, approvalVoteForm.getPollId());
            return "voting-forms/approval-vote";
        }

        ArrayList<Integer> chosenCandidateIds = approvalVoteForm.getChosenCandidateIds();
        Poll thisPoll = pollDao.findById(approvalVoteForm.getPollId()).get();
        ArrayList<Candidate> candidates = new ArrayList<>(thisPoll.getCandidates());

        for (Candidate candidate : candidates)
        {
            if (chosenCandidateIds.contains(candidate.getId()))
            {
                candidate.incrementApprovalVotes();
                candidateDao.save(candidate);
            }

        }
        System.out.println("Chosen Candidate Ids (form): " + chosenCandidateIds);
        System.out.println("Chosen Candidate Ids (request param): " + chosenCandidateIds);


        return "redirect:/poll/" + approvalVoteForm.getPollId();
    }

    // Ranked Choice Votes
    @RequestMapping(value = "vote/rankedChoice", method = RequestMethod.POST)
    public String countRCVotes(Model model,
                               @ModelAttribute("rankedChoiceForm") @Valid
                               RCVForm rankedChoiceForm,
                               Errors errors)
    {
        if (errors.hasErrors() || !rankedChoiceForm.checkVotesAreUnique())
        {
            System.out.println(rankedChoiceForm);
            System.out.println("Errors: " + errors.getAllErrors());
            voteForms(model, rankedChoiceForm.getPollId());
            return "voting-forms/ranked-choice-vote";
        }

        Poll thisPoll = pollDao.findById(rankedChoiceForm.getPollId()).get();
        ArrayList<Candidate> candidates = new ArrayList<>(thisPoll.getCandidates());

        // Check for all 10 possible votes

        for (Candidate candidate : candidates)
        {

            if (rankedChoiceForm.getRank1Candidate() == candidate.getId())
            {
                candidate.incrementRank1Votes();
                candidateDao.save(candidate);
            }

            else if (rankedChoiceForm.getRank2Candidate() == candidate.getId())
            {
                candidate.incrementRank2Votes();
                candidateDao.save(candidate);
            }

            else if (rankedChoiceForm.getRank3Candidate() == candidate.getId())
            {
                candidate.incrementRank3Votes();
                candidateDao.save(candidate);
            }

            else if (rankedChoiceForm.getRank4Candidate() == candidate.getId())
            {
                candidate.incrementRank4Votes();
                candidateDao.save(candidate);
            }

            else if (rankedChoiceForm.getRank5Candidate() == candidate.getId())
            {
                candidate.incrementRank5Votes();
                candidateDao.save(candidate);
            }

            else if (rankedChoiceForm.getRank6Candidate() == candidate.getId())
            {
                candidate.incrementRank6Votes();
                candidateDao.save(candidate);
            }

            else if (rankedChoiceForm.getRank7Candidate() == candidate.getId())
            {
                candidate.incrementRank7Votes();
                candidateDao.save(candidate);
            }

            else if (rankedChoiceForm.getRank8Candidate() == candidate.getId())
            {
                candidate.incrementRank8Votes();
                candidateDao.save(candidate);
            }

            else if (rankedChoiceForm.getRank9Candidate() == candidate.getId())
            {
                candidate.incrementRank9Votes();
                candidateDao.save(candidate);
            }

            else if (rankedChoiceForm.getRank10Candidate() == candidate.getId())
            {
                candidate.incrementRank10Votes();
                candidateDao.save(candidate);
            }
        }

        return "redirect:/poll/" + rankedChoiceForm.getPollId();
    }

    // Poll List
    @RequestMapping(value = "list")
    public String pollList(Model model)
    {
        Iterable<Poll> allPolls = pollDao.findAll();
        model.addAttribute("allPolls", allPolls);
        return "poll-list";
    }
}
