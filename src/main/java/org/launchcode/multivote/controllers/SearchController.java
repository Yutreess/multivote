package org.launchcode.multivote.controllers;

import org.launchcode.multivote.models.Poll;
import org.launchcode.multivote.models.data.CandidateDao;
import org.launchcode.multivote.models.data.PollDao;
import org.launchcode.multivote.models.data.UserDao;
import org.launchcode.multivote.models.forms.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private CandidateDao candidateDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PollDao pollDao;

    @RequestMapping(value="", method = RequestMethod.GET)
    public String index(Model model)
    {
        model.addAttribute("title", "Search Polls");
        model.addAttribute("searchForm", new SearchForm());
        return "search-form";
    }


    @RequestMapping(value="", method = RequestMethod.POST)
    public String returnResults(Model model,
                                @ModelAttribute SearchForm searchForm)
    {
        String term = searchForm.getTerm();
        Iterable<Poll> allPolls = pollDao.findAll();
        ArrayList<Poll> searchResults = new ArrayList<>();

        for (Poll poll : allPolls)
        {
            if (poll.getName().contains(term))
            {
                searchResults.add(poll);
            }
        }

        model.addAttribute("title", "Search Polls");
        model.addAttribute("results", searchResults);

        return "search-form";
    }

}
