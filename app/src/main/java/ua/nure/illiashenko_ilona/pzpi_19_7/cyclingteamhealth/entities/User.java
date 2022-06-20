package ua.nure.illiashenko_ilona.pzpi_19_7.cyclingteamhealth.entities;

import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable {

    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private int teamId;
    private Date birthDate;
    private double height;
    private double weight;
    private String gender;
    private String status;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" +
                "\"login\":\"" + login + '"' +
                ", \"firstName\":\"" + firstName + '"' +
                ", \"lastName\":\"" + lastName + '"' +
                ", \"email\":\"" + email + '"' +
                ", \"role\":\"" + role + '"' +
                ", \"teamId\":\"" + teamId + '"' +
                ", \"birthDate\":\"" + birthDate + '"' +
                ", \"height\":\"" + height + '"' +
                ", \"weight\":\"" + weight + '"' +
                ", \"gender\":\"" + gender + '"' +
                ", \"status\":\"" + status + '"' +
                '}';
    }
}
