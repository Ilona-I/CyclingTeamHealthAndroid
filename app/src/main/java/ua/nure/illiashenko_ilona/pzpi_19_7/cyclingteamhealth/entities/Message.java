package ua.nure.illiashenko_ilona.pzpi_19_7.cyclingteamhealth.entities;

import java.io.Serializable;
import java.sql.Timestamp;

public class Message implements Serializable {

    private int id;
    private int chatId;
    private String sender;
    private String text;
    private Timestamp dateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id +
                "\", \"chatId\":\"" + chatId +
                "\", \"sender\":\"" + sender +
                "\", \"text\":\"" + text +
                "\", \"dateTime\":\"" + dateTime +
                "\"}";
    }
}
