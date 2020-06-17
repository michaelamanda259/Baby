package com.example.baby;
/*
CREATE TABLE IF NOT EXISTS " + CHILD +"
(child_id	INTEGER PRIMARY KEY AUTOINCREMENT, child_name TEXT, gender TEXT, DOB NUMERIC, blood_group TEXT,"+ PHONE +" INTEGER ," +
"FOREIGN KEY("+ PHONE +") REFERENCES " + PARENT +"("+ PHONE +") ON DELETE CASCADE)";
        db.execSQL(childTable);

CREATE TABLE IF NOT EXISTS  " + DAIPER +
"(diaper_id INTEGER PRIMARY KEY AUTOINCREMENT,change_time NUMERIC,date NUMERIC,status TEXT, child_id INTEGER, " +
"FOREIGN KEY("+CHILD_ID+") REFERENCES " + CHILD + "("+CHILD_ID+") ON DELETE CASCADE)";
        db.execSQL(diaper);

CREATE TABLE IF NOT EXISTS " + FEEDING +
"(feeding_id INTEGER PRIMARY KEY AUTOINCREMENT, child_id INTEGER, start_time NUMERIC, end_time NUMERIC, date NUMERIC, " +
"FOREIGN KEY("+CHILD_ID+") REFERENCES " + CHILD + "("+CHILD_ID+") ON DELETE CASCADE)";
        db.execSQL(feeding);

CREATE TABLE IF NOT EXISTS " + MEDICATION +
"(medication_id INTEGER PRIMARY KEY AUTOINCREMENT, date NUMERIC,dosage INTEGER,name TEXT, "+
"FOREIGN KEY("+CHILD_ID+") REFERENCES " + CHILD + "("+CHILD_ID+") ON DELETE CASCADE)";
        db.execSQL(medication);

CREATE TABLE IF NOT EXISTS " + NOTES +
"(note_id INTEGER PRIMARY KEY AUTOINCREMENT, text TEXT NOT NULL, phone INTEGER," +
"FOREIGN KEY("+ PHONE +") REFERENCES " + PARENT +"("+ PHONE +") ON DELETE CASCADE)";
        db.execSQL(notes);

CREATE TABLE IF NOT EXISTS  " + PUMPING +
"(pumping_id INTEGER PRIMARY KEY AUTOINCREMENT, total_quantity INTEGER, date NUMERIC, " +
"start_time NUMERIC, end_time NUMERIC, left_quantity INTEGER, right_quantity INTEGER, phone INTEGER," +
"FOREIGN KEY("+ PHONE +") REFERENCES " + PARENT +"("+ PHONE +") ON DELETE CASCADE)";
        db.execSQL(pumping);

CREATE TABLE IF NOT EXISTS " + SLEEP +
"(sleep_id INTEGER PRIMARY KEY AUTOINCREMENT, date NUMERIC,start_time NUMERIC,end_time NUMERIC, child_id INTEGER," +
"FOREIGN KEY("+ CHILD_ID +") REFERENCES " + CHILD + "("+CHILD_ID+") ON DELETE CASCADE)";
        db.execSQL(sleep);
 */
public class ChildModel {
    /*
    (child_id	INTEGER PRIMARY KEY AUTOINCREMENT,
    child_name TEXT, gender TEXT,
    DOB NUMERIC,
    blood_group TEXT,
    "+ PHONE +" INTEGER ," + "FOREIGN KEY("+ PHONE +") REFERENCES " + PARENT +"("+ PHONE +") ON DELETE CASCADE)";
*/
    private String name,gender,blood_group;
    private int child_id, DOB,phone;

    public ChildModel(String name, String gender, String blood_group, int DOB /*,int phone*/) {
        this.name = name;
        this.gender = gender;
        this.blood_group = blood_group;
        this.DOB = DOB;
       // this.phone = phone;
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

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public int getChild_id() {
        return child_id;
    }

    public void setChild_id(int child_id) {
        this.child_id = child_id;
    }

    public int getDOB() {
        return DOB;
    }

    public void setDOB(int DOB) {
        this.DOB = DOB;
    }

    public int getPhone() {
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
                ", blood_group='" + blood_group + '\'' +
                ", child_id=" + child_id +
                ", DOB=" + DOB +
                ", phone=" + phone +
                '}';
    }
}
