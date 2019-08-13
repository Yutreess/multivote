package org.launchcode.multivote.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping(value="")
    public String index()
    {
        return "index";
    }

    // TODO #1: Refactor for use with ORM and Spring Data
    // TODO #2: Validate Usernames & Passwords to a specific criteria
    @RequestMapping(value="sign-up")
    public String signUp()
    {
        return "sign-up";
    }

}
