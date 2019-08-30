package org.launchcode.multivote.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.launchcode.multivote.models.Candidate;
import org.launchcode.multivote.models.Poll;
import org.launchcode.multivote.models.User;
import org.launchcode.multivote.models.data.CandidateDao;
import org.launchcode.multivote.models.data.PollDao;
import org.launchcode.multivote.models.data.UserDao;
import org.launchcode.multivote.models.forms.LoginForm;
import org.launchcode.multivote.models.forms.PluralityVoteForm;
import org.launchcode.multivote.models.forms.PollForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private CandidateDao candidateDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PollDao pollDao;

    @RequestMapping(value="")
    public String index(Model model)
    {
        model.addAttribute("title", "MultiVote");
        //model.addAttribute("username", "DieLinke");
        return "index";
    }


    // Display Sign-up Form
    // TODO #1: Validate Usernames & Passwords to a specific criteria
    @RequestMapping(value="sign-up", method = RequestMethod.GET)
    public String signUpForm(Model model)
    {
        model.addAttribute(new User());
        model.addAttribute("title", "Sign Up");

        return "sign-up";
    }

    // Add new user to database
    @RequestMapping(value="sign-up", method = RequestMethod.POST)
    public String processSignUpForm(Model model,
                                    @ModelAttribute @Valid User user,
                                    Errors errors)
    {
        if (errors.hasErrors())
        {
            model.addAttribute("title", "Sign Up");
            return "sign-up";
        }

        userDao.save(user);
        return "redirect:/user-home/" + user.getId();
    }

    // Display Login form
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginForm(Model model)
    {
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("title", "Login");

        return "login";
    }

    // Log User in
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String logInUser(Model model,
                            @ModelAttribute @Valid LoginForm loginForm,
                            Errors errors)
    {
        String formUsername = loginForm.getName();
        String formPassword = loginForm.getPassword();
        User user = userDao.findByName(formUsername);

        if (user != null)
        {
            if (user.getPassword().equals(formPassword))
            {
                return "redirect:/user-home/" + user.getId();
            }

            else
            {
                loginForm.setPasswordsMatch(false);
                model.addAttribute("title", "Login");

                return "login";
            }
        }

        if (errors.hasErrors())
        {
            model.addAttribute("title", "Login");

            return "login";
        }

        return "redirect:/";
    }

    // FAQ Page
    @RequestMapping(value = "faq")
    public String faq(Model model)
    {
        model.addAttribute("title", "FAQ");
        return "faq";
    }


    // User home page
    @RequestMapping(value = "user-home/{userId}", method = RequestMethod.GET)
    public String userHome(Model model,
                           @PathVariable int userId)
    {
        //ArrayList<Poll> userPolls = pollDao.findAllByUser(userId);
        User thisUser = userDao.findById(userId).get();
        model.addAttribute("title", "MultiVote User");
        model.addAttribute("user", thisUser);
        return "user-home";
    }

    // Display Create Poll Form
    @RequestMapping(value = "new-poll/{userId}", method = RequestMethod.GET)
    public String newPoll(Model model,
                          @PathVariable int userId)
    {
        User thisUser = userDao.findById(userId).get();
        ArrayList<String> allVotingSystems = new ArrayList<>();
        allVotingSystems.add("Plurality");
        allVotingSystems.add("Ranked Choice");
        allVotingSystems.add("Approval");

        model.addAttribute("newPoll",new PollForm(allVotingSystems));
        model.addAttribute("title", "Create Poll");
        model.addAttribute("user", thisUser);

        return "new-poll";
    }

    // Process Create Poll Form
    @RequestMapping(value = "new-poll", method = RequestMethod.POST)
    public String createNewPoll(Model model,
                                //@RequestParam("candidates") ArrayList<String> newCandidates,
                                @ModelAttribute @Valid PollForm pollForm,
                                Errors errors)
    {
        if (errors.hasErrors())
        {
            return "redirect:/new-poll/" + pollForm.getUserId();
        }

        ArrayList<Candidate> candidateList = new ArrayList<>();
        User thisUser = userDao.findById(pollForm.getUserId()).get();
        String newPollName = pollForm.getName();
        String newPollVotingSystem = pollForm.getVotingSystem();

        Poll newPoll = new Poll(newPollName, newPollVotingSystem, thisUser);
        newPoll.setUser(thisUser);

        for(String candidateName : pollForm.getCandidates())
        {
            if(!candidateName.equals(""))
            {
                Candidate newCandidate = new Candidate(candidateName);
                newCandidate.setPoll(newPoll);
                newCandidate.setVotingSystem(newPollVotingSystem);
                candidateDao.save(newCandidate);

                candidateList.add(newCandidate);
            }
        }
        newPoll.setCandidates(candidateList);

        pollDao.save(newPoll);


        return "redirect:/user-home/" + pollForm.getUserId();
    }

    // Main Poll page
    @RequestMapping(value = "poll/{pollId}")
    public String poll(Model model,
                       @PathVariable int pollId)
    {
        Poll thisPoll = pollDao.findById(pollId).get();
        model.addAttribute("poll", thisPoll);
        //model.addAttribute("title", thisPoll.getCandidates());
        return "poll";
    }

    // Vote Form
    @RequestMapping(value = "poll/vote/{pollId}", method = RequestMethod.GET)
    public String voteForms(Model model,
                            @PathVariable int pollId)
    {
        Poll thisPoll = pollDao.findById(pollId).get();
        model.addAttribute("poll", thisPoll);

        if(thisPoll.getVotingSystem().equals("Ranked Choice"))
        {
            return "voting-forms/ranked-choice-vote";
        }

        if(thisPoll.getVotingSystem().equals("Approval"))
        {
            return "voting-forms/approval-vote";
        }

        else
        {
            ArrayList<Candidate> candidates = new ArrayList<>(thisPoll.getCandidates());

            //candidates.addAll(thisPoll.getCandidates());
            PluralityVoteForm pluralityVoteForm = new PluralityVoteForm(thisPoll.getVotingSystem(), candidates);
            pluralityVoteForm.setPollId(thisPoll.getId());
            model.addAttribute("pluralityVoteForm", pluralityVoteForm);
            return "voting-forms/plurality-vote";
        }
    }

    // Process Plurality Votes
    @RequestMapping(value = "poll/vote", method = RequestMethod.POST)
    public String processPluralityVote(Model model,
                              @ModelAttribute("pluralityVoteForm") @Valid PluralityVoteForm pluralityVoteForm,
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
                candidate.incrementVotes();
                candidateDao.save(candidate);
            }
        }

        return "redirect:/poll/" + pluralityVoteForm.getPollId();
    }

}
