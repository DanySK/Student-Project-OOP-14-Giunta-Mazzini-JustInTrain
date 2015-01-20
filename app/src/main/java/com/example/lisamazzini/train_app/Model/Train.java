package com.example.lisamazzini.train_app.Model;

import java.util.List;

public class Train {

    private final String category; // fai enum
    private final String number;
    private final boolean isMoving;
    private final int delay;
    private final String birthStation;
    private final String deathStation;
    private final String lastSeenStation;
    private final String lastSeenTime;
    private final List<Station> stationList;

    public String getCategory() {
        return category;
    }

    public String getNumber() {
        return number;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public int getDelay() {
        return delay;
    }

    public String getBirthStation() {
        return birthStation;
    }

    public String getDeathStation() {
        return deathStation;
    }

    public String getLastSeenStation() {
        return lastSeenStation;
    }

    public String getLastSeenTime() {
        return lastSeenTime;
    }

    public List<Station> getStationList() {
        return stationList;
    }

    private Train(TrainBuilder builder) {
        this.category = builder.category;
        this.number = builder.number;
        this.isMoving = builder.isMoving;
        this.delay = builder.delay;
        this.birthStation = builder.birthStation;
        this.deathStation = builder.deathStation;
        this.lastSeenStation = builder.lastSeenStation;
        this.lastSeenTime = builder.lastSeenTime;
        this.stationList = builder.stationList; //se non presenti potrebbe generare nullpointer?
    }


    public static class TrainBuilder {
        private final String category; // fai enum
        private final String number;
        private final boolean isMoving;
        private final int delay;
        private final String birthStation;
        private final String deathStation;
        private final String lastSeenStation;
        private final String lastSeenTime;
        private List<Station> stationList;

        public TrainBuilder(String category, String number,
                            boolean isMoving, int delay,
                            String birthStation, String deathStation,
                            String lastSeenStation, String lastSeenTime) {
            this.category = category;
            this.number = number;
            this.isMoving = isMoving;
            this.delay = delay;
            this.birthStation = birthStation;
            this.deathStation = deathStation;
            this.lastSeenStation = lastSeenStation;
            this.lastSeenTime = lastSeenTime;
        }

        public TrainBuilder withStationList(List<Station> stationList) {
            this.stationList = stationList;
            return this;
        }

        public Train build() {
            return new Train(this);
        }
    }



}
