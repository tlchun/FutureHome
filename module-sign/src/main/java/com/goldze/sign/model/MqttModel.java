package com.goldze.sign.model;

import java.io.Serializable;

public class MqttModel implements Serializable {
   private int id;
   private String username;
   private String password;
   private String showPassword;
   private String salt;
   private boolean isSuperuser;
   private boolean created;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShowPassword() {
        return showPassword;
    }

    public void setShowPassword(String showPassword) {
        this.showPassword = showPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isSuperuser() {
        return isSuperuser;
    }

    public void setSuperuser(boolean superuser) {
        isSuperuser = superuser;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "MqttModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", showPassword='" + showPassword + '\'' +
                ", salt='" + salt + '\'' +
                ", isSuperuser=" + isSuperuser +
                ", created=" + created +
                '}';
    }
}
