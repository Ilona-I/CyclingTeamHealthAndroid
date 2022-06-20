package ua.nure.illiashenko_ilona.pzpi_19_7.cyclingteamhealth.entities;

import java.io.Serializable;
import java.sql.Timestamp;

public class TrainingGoals implements Serializable {

    private int id;
    private int teamId;
    private int pulse;
    private int speed;
    private Timestamp startDateTime;
    private Timestamp endDateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id +
                "\", \"teamId\":\"" + teamId +
                "\", \"pulse\":\"" + pulse +
                "\", \"speed\":\"" + speed +
                "\", \"startDateTime\":\"" + startDateTime +
                "\", \"endDateTime\":\"" + endDateTime +
                "\"}";
    }
}
