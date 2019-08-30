package org.launchcode.multivote.models.forms;

import org.launchcode.multivote.models.Candidate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

public class PluralityVoteForm {

    private String votingSystem;

    @NotNull
    private int pollId;

    //@NotNull
    @Size(min = 1)
    private ArrayList<Candidate> candidates;

    @NotNull
    private int chosenCandidateId;

    public PluralityVoteForm () {}

    public PluralityVoteForm(String votingSystem, ArrayList<Candidate> candidates)
    {
        this.votingSystem = votingSystem;
        this.candidates = candidates;
    }

    public String getVotingSystem() {
        return votingSystem;
    }

    public void setVotingSystem(String votingSystem) {
        this.votingSystem = votingSystem;
    }

    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(ArrayList<Candidate> candidates) {
        this.candidates = candidates;
    }

    public int getChosenCandidateId() {
        return chosenCandidateId;
    }

    public void setChosenCandidateId(int chosenCandidateId) {
        this.chosenCandidateId = chosenCandidateId;
    }

    public int getPollId() {
        return pollId;
    }

    public void setPollId(int pollId) {
        this.pollId = pollId;
    }
}
