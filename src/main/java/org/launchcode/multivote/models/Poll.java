package org.launchcode.multivote.models;

import org.launchcode.multivote.models.data.DateManipulator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min = 1)
    private String name;

    private Date dateCreated = new Date();

    private Date pollClosingTime;

    @NotNull
    private String votingSystem;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "candidates")
    private List<Candidate> candidates;

    public Poll() {}

    public Poll(String name, String votingSystem, User user)
    {
        this.name = name;
        this.votingSystem = votingSystem;
        this.user = user;
    }

    public Date getPollClosingTime() {
        return pollClosingTime;
    }

    // Set Poll Closing time
    public void setPollClosingTime(int daysToClose,
                                   int hoursToClose,
                                   int minutesToClose) {
        DateManipulator dm = new DateManipulator();
        Date closingTime = this.dateCreated;

        if (daysToClose == 0
            && hoursToClose == 0
            && minutesToClose == 0)
        {
            this.pollClosingTime = dm.addHours(this.dateCreated, 1);
        }
        else
        {
            closingTime = dm.addDays(closingTime, daysToClose);
            closingTime = dm.addHours(closingTime, hoursToClose);
            closingTime = dm.addMinutes(closingTime, minutesToClose);
            this.pollClosingTime = closingTime;
        }
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
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

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public int getId() {
        return id;
    }
}
