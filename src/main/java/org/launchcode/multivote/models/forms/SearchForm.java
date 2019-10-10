package org.launchcode.multivote.models.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

public class SearchForm {

    private String term;

    @NotNull
    @Size(min = 1, message = "Please select at least one voting system")
    private ArrayList<String> searchedVotingSystems;

    @NotNull
    @Size(min = 1, message = "Please select at least one area to search for the keyword")
    private ArrayList<String> searchTermArea;

    public SearchForm() {}

    public SearchForm(String term)
    {
        this.term = term;
    }

    public ArrayList<String> getSearchTermArea() {
        return searchTermArea;
    }

    public void setSearchTermArea(ArrayList<String> searchTermArea) {
        this.searchTermArea = searchTermArea;
    }

    public ArrayList<String> getSearchedVotingSystems() {
        return searchedVotingSystems;
    }

    public void setSearchedVotingSystems(ArrayList<String> searchedVotingSystems) {
        this.searchedVotingSystems = searchedVotingSystems;
    }

    public String getTerm()
    {
        return term;
    }

    public void setTerm(String term)
    {
        this.term = term;
    }
}
