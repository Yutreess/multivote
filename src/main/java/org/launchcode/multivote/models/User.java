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
    @GeneratedValue
    private int id;

    @OneToOne//(mappedBy = "user")
    @JoinColumn(name = "poll_id")
    private Poll poll;

    public User() {}

    public User(String name, String password) {
        this.name = name;
        this.password = password;
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

    public int getId() {
        return id;
    }
}
