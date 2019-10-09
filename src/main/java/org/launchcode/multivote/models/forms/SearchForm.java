package org.launchcode.multivote.models.forms;

public class SearchForm {

    private String term;

    public SearchForm() {}

    public SearchForm(String term)
    {
        this.term = term;
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
