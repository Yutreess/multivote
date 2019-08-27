package org.launchcode.multivote.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @OneToOne(mappedBy = "poll")
    @JoinColumn(name = "user_id")
    private User user;

    public Poll() {}

    public Poll(String name, String votingSystem, User user)
    {
        this.name = name;
        this.votingSystem = votingSystem;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public int getId() {
        return id;
    }
}
