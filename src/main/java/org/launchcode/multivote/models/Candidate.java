package org.launchcode.multivote.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "voters")
    private List<User> voters;

    // Allowing a null property in cas ethe voting system isn't appropriate
    private int numPluralityVotes;

    // For Approval Votes
    private int numApprovalVotes;

    // Ranked Choice Vote counters
    private int rank1Votes;

    private int rank2Votes;

    private int rank3Votes;

    private int rank4Votes;

    private int rank5Votes;

    private int rank6Votes;

    private int rank7Votes;

    private int rank8Votes;

    private int rank9Votes;

    private int rank10Votes;

    public Candidate() {}

    public Candidate(String name)
    {
        this.name = name;
    }

    // Add Voter
    public void addVoter(User voter)
    {
        this.voters.add(voter);
    }

    // Ranked Choice Counting

    public void incrementRank1Votes() {this.rank1Votes++;}
    public void incrementRank2Votes() {this.rank2Votes++;}
    public void incrementRank3Votes() {this.rank3Votes++;}
    public void incrementRank4Votes() {this.rank4Votes++;}
    public void incrementRank5Votes() {this.rank5Votes++;}
    public void incrementRank6Votes() {this.rank6Votes++;}
    public void incrementRank7Votes() {this.rank7Votes++;}
    public void incrementRank8Votes() {this.rank8Votes++;}
    public void incrementRank9Votes() {this.rank9Votes++;}
    public void incrementRank10Votes() {this.rank10Votes++;}

    // Plurality Counting
    public void incrementPluralityVotes()
    {
        this.numPluralityVotes++;
    }

    // Approval Counting
    public void incrementApprovalVotes()
    {
        this.numApprovalVotes++;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

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

    public List<User> getVoters() {
        return voters;
    }

    public void setVoters(List<User> voters) {
        this.voters = voters;
    }

    // Ranked Choice

    public int getRank1Votes() {
        return rank1Votes;
    }

    public void setRank1Votes(int rank1Votes) {
        this.rank1Votes = rank1Votes;
    }

    public int getRank2Votes() {
        return rank2Votes;
    }

    public void setRank2Votes(int rank2Votes) {
        this.rank2Votes = rank2Votes;
    }

    public int getRank3Votes() {
        return rank3Votes;
    }

    public void setRank3Votes(int rank3Votes) {
        this.rank3Votes = rank3Votes;
    }

    public int getRank4Votes() {
        return rank4Votes;
    }

    public void setRank4Votes(int rank4Votes) {
        this.rank4Votes = rank4Votes;
    }

    public int getRank5Votes() {
        return rank5Votes;
    }

    public void setRank5Votes(int rank5Votes) {
        this.rank5Votes = rank5Votes;
    }

    public int getRank6Votes() {
        return rank6Votes;
    }

    public void setRank6Votes(int rank6Votes) {
        this.rank6Votes = rank6Votes;
    }

    public int getRank7Votes() {
        return rank7Votes;
    }

    public void setRank7Votes(int rank7Votes) {
        this.rank7Votes = rank7Votes;
    }

    public int getRank8Votes() {
        return rank8Votes;
    }

    public void setRank8Votes(int rank8Votes) {
        this.rank8Votes = rank8Votes;
    }

    public int getRank9Votes() {
        return rank9Votes;
    }

    public void setRank9Votes(int rank9Votes) {
        this.rank9Votes = rank9Votes;
    }

    public int getRank10Votes() {
        return rank10Votes;
    }

    public void setRank10Votes(int rank10Votes) {
        this.rank10Votes = rank10Votes;
    }

    // Approval
    public int getNumApprovalVotes() {
        return numApprovalVotes;
    }

    public void setNumApprovalVotes(int numApprovalVotes) {
        this.numApprovalVotes = numApprovalVotes;
    }

    // Plurality

    public int getNumPluralityVotes() {
        return numPluralityVotes;
    }

    public void setNumPluralityVotes(int numPluralityVotes) {
        this.numPluralityVotes = numPluralityVotes;
    }

}
