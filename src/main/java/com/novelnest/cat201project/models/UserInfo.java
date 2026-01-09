package com.novelnest.cat201project.models;

import java.time.LocalDateTime;

public class UserInfo {

    private int  id = 0;
    private String name = null;
    private String password = null;
    private LocalDateTime created = null;
    private boolean admin = false;

    public UserInfo() {}

    public UserInfo(int id, String name, String password, LocalDateTime created, boolean admin) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.created = created;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public LocalDateTime getCreated() {
        return created;
    }
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public boolean isAdmin() {
        return admin;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
