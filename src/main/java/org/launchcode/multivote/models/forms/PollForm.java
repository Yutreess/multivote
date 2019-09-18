package org.launchcode.multivote.models.forms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;

public class PollForm {

    private ArrayList<String> allVotingSystems;

    @NotNull
    private int userId;

    @NotNull
    @Positive
    private int minutesToPollClose;

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    private String votingSystem;

    @Size(min = 1)
    @NotNull
    private ArrayList<String> candidates;

    public PollForm () {}

    public PollForm (ArrayList<String> votingSystems)
    {
        this.allVotingSystems = votingSystems;
    }

    public int getMinutesToPollClose() {
        return minutesToPollClose;
    }

    public void setMinutesToPollClose(int minutesToPollClose) {
        this.minutesToPollClose = minutesToPollClose;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVotingSystem() {
        return votingSystem;
    }

    public void setVotingSystem(String votingSystem) {
        this.votingSystem = votingSystem;
    }

    public ArrayList<String> getCandidates() {
        return candidates;
    }

    public void setCandidates(ArrayList<String> candidates) {
        this.candidates = candidates;
    }

    public ArrayList<String> getAllVotingSystems() {
        return allVotingSystems;
    }


}
