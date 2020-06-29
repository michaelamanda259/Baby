package com.example.baby;

public class activities {
    private String date , content;
    private int child_id;

    public activities(String date,String content){
        this.date = date;
        this.content = content;
    }

    public activities(int child_id,String date,String content){
        this.child_id = child_id;
        this.date = date;
        this.content = content;
    }
    public int getId() {
        return child_id;
    }

    public void setId(int child_id) {
        this.child_id = child_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}



