package com.trip.expensemanager.beans;

public class UserBean {

    private long id;

    private String username;

    private String prefferedName;

    private String synced;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPrefferedName() {
        return prefferedName;
    }

    public void setPrefferedName(String prefferedName) {
        this.prefferedName = prefferedName;
    }


    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

}
