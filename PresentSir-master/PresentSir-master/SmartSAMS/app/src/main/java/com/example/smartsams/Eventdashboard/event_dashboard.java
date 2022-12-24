package com.example.smartsams.Eventdashboard;


public class event_dashboard {

    int image;
    String title , description, date;

    public event_dashboard(int image, String title, String description, String date) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


    public String getDate() {
        return date;
    }
}
