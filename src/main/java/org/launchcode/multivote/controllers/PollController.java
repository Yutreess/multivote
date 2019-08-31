package org.launchcode.multivote.controllers;

import org.launchcode.multivote.models.Candidate;
import org.launchcode.multivote.models.Poll;
import org.launchcode.multivote.models.data.CandidateDao;
import org.launchcode.multivote.models.data.PollDao;
import org.launchcode.multivote.models.data.UserDao;
import org.launchcode.multivote.models.forms.ApprovalVoteForm;
import org.launchcode.multivote.models.forms.PluralityVoteForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

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
        //model.addAttribute("title", thisPoll.getCandidates());
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
            return "voting-forms/ranked-choice-vote";
        }

        if(thisPoll.getVotingSystem().equals("Approval"))
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
                                       Errors errors)
    {
        if(errors.hasErrors())
        {
            System.out.println("Errors: " + errors.getAllErrors());
            return "voting-forms/plurality-vote";
            //return "redirect:/poll/vote/" + pluralityVoteForm.getPollId();
        }

        int chosenCandidateId = pluralityVoteForm.getChosenCandidateId();
        Poll thisPoll = pollDao.findById(pluralityVoteForm.getPollId()).get();
        ArrayList<Candidate> candidates = new ArrayList<>(thisPoll.getCandidates());

        //TODO: Use Cnadidate ID's instead of names, use names only for display
        for(Candidate candidate : candidates)
        {
            if(candidate.getId() == chosenCandidateId)
            {
                candidate.incrementPluralityVotes();
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
}
