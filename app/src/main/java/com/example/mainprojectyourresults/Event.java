package com.example.mainprojectyourresults;


public class Event {

    public String id, nameOfTheCompetition, distance, category, result;

    public Event(String id, String nameOfTheCompetition, String distance, String category, String result) {
        this.id = id;
        this.nameOfTheCompetition = nameOfTheCompetition;
        this.distance = distance;
        this.category = category;
        this.result = result;
    }

    public Event()  {
    }
}
