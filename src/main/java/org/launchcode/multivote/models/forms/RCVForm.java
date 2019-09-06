package org.launchcode.multivote.models.forms;

import org.launchcode.multivote.models.Candidate;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Map;

public class RCVForm {

    //@AssertTrue
    //private boolean votesAreUnique;

    private String votingSystem;

    @NotNull
    private int pollId;

    @Size(min = 1)
    private ArrayList<Candidate> candidates;

    @NotNull
    private int rank1Candidate;

    private int rank2Candidate;

    private int rank3Candidate;

    private int rank4Candidate;

    private int rank5Candidate;

    private int rank6Candidate;

    private int rank7Candidate;

    private int rank8Candidate;

    private int rank9Candidate;

    private int rank10Candidate;

    public RCVForm() {}

    public RCVForm(String votingSystem, ArrayList<Candidate> candidates)
    {
        this.votingSystem = votingSystem;
        this.candidates = candidates;
    }

    // Make sure candidates don't get more than 1 ranked choice
    public boolean checkVotesAreUnique()
    {
        int[] allVoteChoices = {this.getRank1Candidate(),
                                this.getRank2Candidate(),
                                this.getRank3Candidate(),
                                this.getRank4Candidate(),
                                this.getRank5Candidate(),
                                this.getRank6Candidate(),
                                this.getRank7Candidate(),
                                this.getRank8Candidate(),
                                this.getRank9Candidate(),
                                this.getRank10Candidate()};

        /*
        Loop through the array to check every
        possible check of any 2 unique values
        */
        for (int i = 0; i < allVoteChoices.length; i++)
        {

            for (int j = i + 1; j < allVoteChoices.length; j++)
            {
                /*
                If 2 votes are the same, and aren't the same vote,
                and aren't 0
                */
                if (allVoteChoices[i] == allVoteChoices[j] &&
                    //i != j &&
                    (allVoteChoices[i] != 0 && allVoteChoices[j] != 0))
                {
                    return false;
                }
            }
        }

        return true;
    }

    /*

    public boolean VotesAreUnique() {
        return votesAreUnique;
    }

    public void setVotesAreUnique(boolean votesAreUnique) {
        this.votesAreUnique = checkVotesAreUnique();
    }

    */

    public void setVotingSystem(String votingSystem) {
        this.votingSystem = votingSystem;
    }

    public String getVotingSystem() {
        return votingSystem;
    }

    public void setPollId(int pollId) {
        this.pollId = pollId;
    }

    public int getPollId() {
        return pollId;
    }

    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(ArrayList<Candidate> candidates) {
        this.candidates = candidates;
    }

    public int getRank1Candidate() {
        return rank1Candidate;
    }

    public void setRank1Candidate(int rank1Candidate) {
        this.rank1Candidate = rank1Candidate;
    }

    public int getRank2Candidate() {
        return rank2Candidate;
    }

    public void setRank2Candidate(int rank2Candidate) {
        this.rank2Candidate = rank2Candidate;
    }

    public int getRank3Candidate() {
        return rank3Candidate;
    }

    public void setRank3Candidate(int rank3Candidate) {
        this.rank3Candidate = rank3Candidate;
    }

    public int getRank4Candidate() {
        return rank4Candidate;
    }

    public void setRank4Candidate(int rank4Candidate) {
        this.rank4Candidate = rank4Candidate;
    }

    public int getRank5Candidate() {
        return rank5Candidate;
    }

    public void setRank5Candidate(int rank5Candidate) {
        this.rank5Candidate = rank5Candidate;
    }

    public int getRank6Candidate() {
        return rank6Candidate;
    }

    public void setRank6Candidate(int rank6Candidate) {
        this.rank6Candidate = rank6Candidate;
    }

    public int getRank7Candidate() {
        return rank7Candidate;
    }

    public void setRank7Candidate(int rank7Candidate) {
        this.rank7Candidate = rank7Candidate;
    }

    public int getRank8Candidate() {
        return rank8Candidate;
    }

    public void setRank8Candidate(int rank8Candidate) {
        this.rank8Candidate = rank8Candidate;
    }

    public int getRank9Candidate() {
        return rank9Candidate;
    }

    public void setRank9Candidate(int rank9Candidate) {
        this.rank9Candidate = rank9Candidate;
    }

    public int getRank10Candidate() {
        return rank10Candidate;
    }

    public void setRank10Candidate(int rank10Candidate) {
        this.rank10Candidate = rank10Candidate;
    }
}
