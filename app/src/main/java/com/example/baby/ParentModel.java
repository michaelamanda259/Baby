package com.example.baby;
/*
CREATE TABLE IF NOT EXISTS " + PARENT + "(" + NAME + " text , " + USERNAME + " text , " + EMAIL + " text , " + PHONE + " INTEGER  PRIMARY KEY, " + PASSWORD + " text)";
*/
public class ParentModel {

    private String name, username, email, password;
    private int phoneNumber;

    public ParentModel(String name, String username, String email, int phoneNumber,String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public ParentModel() {
    }

    public ParentModel(String username)
    {
        this.username=username;
    }

    //toString
    @Override
    public String toString() {
        return "ParentModel{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }
}