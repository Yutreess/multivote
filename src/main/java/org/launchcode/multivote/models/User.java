package org.launchcode.multivote.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @NotNull
    @Size(min = 1)
    private String name;

    @NotNull
    private String password;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "user")
    //@JoinColumn(name = "owned_poll_id")
    private List<Poll> polls = new ArrayList<>();

    @ManyToMany(mappedBy = "voters")
    private List<Candidate> voteHistory;

    public User() {}

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public List<Candidate> getVoteHistory() {
        return voteHistory;
    }

    public void setVoteHistory(List<Candidate> voteHistory) {
        this.voteHistory = voteHistory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Poll> getPolls() {
        return polls;
    }

    public void setPolls(List<Poll> polls) {
        this.polls = polls;
    }

}
