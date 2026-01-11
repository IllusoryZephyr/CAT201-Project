package com.novelnest.cat201project.models;

import java.time.LocalDateTime;

public class MessageInfo {
    private int ownerID = 0;
    private int senderID = 0;
    private LocalDateTime timestamp = null;
    private String message = null;
    private boolean ownerSeen = false;
    private boolean adminSeen = false;

    public MessageInfo() {}

    public void setOwnerID(int ownerID) {this.ownerID = ownerID;}
    public void setSenderID(int senderID) {this.senderID = senderID;}
    public void setTimestamp(LocalDateTime timestamp) {this.timestamp = timestamp;}
    public void setMessage(String message) {this.message = message;}
    public void setOwnerSeen(boolean ownerSeen) {this.ownerSeen = ownerSeen;}
    public void setAdminSeen(boolean adminSeen) {this.adminSeen = adminSeen;}

    public int getOwnerID() {return this.ownerID;}
    public int getSenderID() {return this.senderID;}
    public LocalDateTime getTimestamp() {return this.timestamp;}
    public String getMessage() {return this.message;}
    public boolean getOwnerSeen() {return this.ownerSeen;}
    public boolean getAdminSeen() {return this.adminSeen;}
}