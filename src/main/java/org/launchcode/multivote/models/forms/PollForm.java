package org.launchcode.multivote.models.forms;

import org.launchcode.multivote.models.Poll;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public class PollForm {

    // Rendering
    private ArrayList<String> allVotingSystems;

    // Processing
    @NotNull
    private String name;

    @NotNull
    private String votingSystem;
/*
    @NotNull
    private ArrayList<String> candidates;
*/

    public PollForm () {}

    public PollForm (ArrayList<String> votingSystems)
    {
        this.allVotingSystems = votingSystems;
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

    public void setVotingSytem(String votingSystem) {
        this.votingSystem = votingSystem;
    }

/*
    public ArrayList<String> getCandidates() {
        return candidates;
    }
*/
/*
    public void setCandidates(ArrayList<String> candidates) {
        this.candidates = candidates;
    }
*/

    public ArrayList<String> getAllVotingSystems() {
        return allVotingSystems;
    }
}
