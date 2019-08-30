package org.launchcode.multivote.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min = 1)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    private Poll poll;

    @NotNull
    private String votingSystem;

    // Allowing a null property in cas ethe voting system isn't appropriate
    private int numPluralityVotes;

    /*
    // Ranked Choice Vote counters
    @NotNull
    private int numOneVotes;

    @NotNull
    private int numTwoVotes;

    @NotNull
    private int numThreeVotes;

    @NotNull
    private int numFourVotes;

    @NotNull
    private int numFiveVotes;

    @NotNull
    private int numSixVotes;

    @NotNull
    private int numSevenVotes;

    @NotNull
    private int numEightVotes;

    @NotNull
    private int numNineVotes;

    @NotNull
    private int numTenVotes;
    */

    public Candidate() {}

    public Candidate(String name)
    {
        this.name = name;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public String getVotingSystem() {
        return votingSystem;
    }

    public void setVotingSystem(String votingSystem) {
        this.votingSystem = votingSystem;
    }

    // Plurality Counting
    public void incrementVotes()
    {
        this.numPluralityVotes++;
    }

    public int getNumPluralityVotes() {
        return numPluralityVotes;
    }

    public void setNumPluralityVotes(int numPluralityVotes) {
        this.numPluralityVotes = numPluralityVotes;
    }

    public int getId() {
        return id;
    }
}
