package com.example.baby;

public class ChildModel {
    private String name,DOB,gender,bloodgroup;
    private int  phone;
    public ChildModel( String name, String gender,  String DOB, String bloodgroup,int phone) {
        this.name = name;
        this.gender = gender;
        this.bloodgroup = bloodgroup;
        this.DOB = DOB;
        this.phone = phone;
    }

    public ChildModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBlood_group(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public int getPhone() {
//        int phone = DatabaseHelper.parentPhone();
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ChildModel{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", bloodgroup='" + bloodgroup + '\'' +
                ", DOB=" + DOB +
                ", phone=" + phone +
                '}';
    }
}
