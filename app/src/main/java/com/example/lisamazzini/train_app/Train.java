package com.example.lisamazzini.train_app;

import java.util.List;

public class Train {
    private final String condition;
    private final boolean isMoving;
    private final String lastSeenStation;
    private final String lastSeenTime;
    private final String category;
    private final String number;
    private final int delay;
    private final List<Station> stationList;
    private boolean pinned;

    public Train(String category, String number, String condition, String lastSeenStation, String lastSeenTime, boolean isMoving, int delay) {
        this.category = category;
        this.number = number;
        this.delay = delay;
        this.isMoving = isMoving;
        this.condition = condition;
        this.lastSeenStation = lastSeenStation;
        this.lastSeenTime = lastSeenTime;
        this.pinned = false;
        this.stationList = null; 			// LA MORTE NERA
    }

    public Train(Train train, List<Station> stationList) {
        this.category = train.getCategory();
        this.number = train.getNumber();
        this.delay = train.getDelay();
        this.stationList = stationList;
        this.isMoving = train.isMoving();
        this.condition = train.getCondition();
        this.lastSeenStation = train.getLastSeenStation();
        this.lastSeenTime = train.getLastSeenTime();
        this.pinned = train.isPinned();
    }

    public String getCategory(){
        return this.category;
    }

    public String getNumber(){
        return this.number;
    }

    public int getDelay(){
        return this.delay;
    }

    public boolean isPinned(){
        return this.pinned;
    }

    public List<Station> getStationList(){
        return this.stationList;
    }

    public String getCondition(){
        return this.condition;
    }

    public String getLastSeenStation(){
        return this.lastSeenStation;
    }

    public String getLastSeenTime(){
        return this.lastSeenTime;
    }

    public boolean isMoving(){
        return this.isMoving;
    }

    public void pin(){
        this.pinned = true;
    }
}
