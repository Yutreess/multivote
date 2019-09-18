package org.launchcode.multivote.controllers;

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
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;

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
                            Errors errors,
                            HttpServletResponse response)
    {
        String formUsername = loginForm.getName();
        String formPassword = loginForm.getPassword();
        User user = userDao.findByName(formUsername);

        if (user != null)
        {
            if (user.getPassword().equals(formPassword))
            {
                Cookie userSession = new Cookie("username", user.getName());
                userSession.setPath("/");
                response.addCookie(userSession);
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

    //Logout User
    @RequestMapping(value = "logout")
    public String logoutUser(Model model,
                             HttpServletRequest request,
                             HttpServletResponse response)
    {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies)
        {
            if(cookie.getName().equals("username"))
            {
                Cookie userSession = new Cookie("username", null);
                userSession.setPath("/");
                userSession.setMaxAge(0);
                response.addCookie(userSession);

                return "redirect:/";
            }
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
                           @PathVariable int userId,
                           HttpServletResponse request)
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
                                Errors errors,
                                HttpServletResponse request)
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
        newPoll.setPollClosingTime(pollForm.getMinutesToPollClose());

        pollDao.save(newPoll);


        return "redirect:/user-home/" + pollForm.getUserId();
    }

}
