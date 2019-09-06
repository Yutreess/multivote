package org.launchcode.multivote.models.forms;

import org.launchcode.multivote.models.Candidate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

public class ApprovalVoteForm {

    private String votingSystem;

    @NotNull
    private int pollId;

    //@NotNull
    @Size(min = 1)
    private ArrayList<Candidate> candidates;

    @NotNull
    @Size(min = 1)
    private ArrayList<Integer> chosenCandidateIds;

    public ApprovalVoteForm () {}

    public ApprovalVoteForm (String votingSystem, ArrayList<Candidate> candidates)
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

    public int getPollId() {
        return pollId;
    }

    public void setPollId(int pollId) {
        this.pollId = pollId;
    }

    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(ArrayList<Candidate> candidates) {
        this.candidates = candidates;
    }

    public ArrayList<Integer> getChosenCandidateIds() {
        return chosenCandidateIds;
    }

    public void setChosenCandidateIds(ArrayList<Integer> chosenCandidateIds) {
        this.chosenCandidateIds = chosenCandidateIds;
    }


}
