package org.launchcode.multivote.controllers;

import org.launchcode.multivote.models.User;
import org.launchcode.multivote.models.data.UserDao;
import org.launchcode.multivote.models.forms.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.*;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserDao userDao;

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
        User thisUser = userDao.findById(userId).get();
        model.addAttribute("title", "MultiVote User");
        model.addAttribute("username", thisUser.getName());
        return "user-home.html";
    }


}
