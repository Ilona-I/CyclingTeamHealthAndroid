package ua.nure.illiashenko_ilona.pzpi_19_7.cyclingteamhealth.entities;

import java.io.Serializable;

public class Chat implements Serializable {

    private int id;
    private String type;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
