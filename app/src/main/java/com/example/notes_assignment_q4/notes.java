package com.example.notes_assignment_q4;

//creating a model class for notes to save the data
public class notes {
    //creating variables
    private String title, content,time;

    public notes(String title, String content, String time) {
        this.title = title;
        this.content = content;
        this.time = time;
    }

    //getters and setters for all variables

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
