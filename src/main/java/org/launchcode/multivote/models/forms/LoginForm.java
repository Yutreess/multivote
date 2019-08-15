package org.launchcode.multivote.models.forms;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginForm {

    @NotNull
    @Size(min = 1, message = "Username cannot be empty")
    private String name;

    @NotNull
    @Size
    private String password;

    @AssertTrue(message = "Incorrect Password or Username")
    private boolean passwordsMatch = true;

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

    public boolean getPasswordsMatch(){
        return passwordsMatch;
    }

    public void setPasswordsMatch(boolean passwordsMatch) {
        this.passwordsMatch = passwordsMatch;
    }
}
