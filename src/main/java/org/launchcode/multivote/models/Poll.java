package org.launchcode.multivote.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Poll {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 1)
    private String name;

    @NotNull
    private String votingSystem;

    public Poll() {}

    public Poll(String name, String votingSystem)
    {
        this.name = name;
        this.votingSystem = votingSystem;
    }

/*
    @NotNull
    private ArrayList<String> candidates;
*/
    public String getVotingSystem() {
        return votingSystem;
    }

    public void setVotingSystem(String votingSystem) {
        this.votingSystem = votingSystem;
    }
/*
    public List<String> getCandidates() {
        return candidates;
    }

    public void setCandidates(ArrayList<String> candidates) {
        this.candidates = candidates;
    }
*/
    public int getId() {
        return id;
    }
}
