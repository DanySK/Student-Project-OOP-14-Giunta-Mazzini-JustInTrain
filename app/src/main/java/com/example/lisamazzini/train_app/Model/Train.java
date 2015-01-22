package com.example.lisamazzini.train_app.Model;

import com.example.lisamazzini.train_app.Exceptions.FieldNotBuiltException;
import com.example.lisamazzini.train_app.Model.Station;

import java.util.List;

public class Train extends BasicTrain {

    private final boolean isMoving;
    private final String birthStation;
    private final String deathStation;
    private final String lastSeenStation;
    private final String lastSeenTime;
    private final List<Station> stationList;
    private final String progress;

    public boolean isMoving() {
        return isMoving;
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

    public String getProgress(){
        return this.progress;
    }

    public List<Station> getStationList() throws FieldNotBuiltException {
        if (this.stationList == null) {
            throw new FieldNotBuiltException();
        }
        return this.stationList;
    }

    private Train(TrainBuilder builder) {
        super(builder.category, builder.number, builder.delay);
        this.isMoving = builder.isMoving;
        this.birthStation = builder.birthStation;
        this.deathStation = builder.deathStation;
        this.lastSeenStation = builder.lastSeenStation;
        this.lastSeenTime = builder.lastSeenTime;
        this.stationList = builder.stationList;
        this.progress = builder.progress;
    }


    public static class TrainBuilder {
        private final String category;
        private final String number;
        private final boolean isMoving;
        private final int delay;
        private final String birthStation;
        private final String deathStation;
        private final String lastSeenStation;
        private final String lastSeenTime;
        private List<Station> stationList;
        private String progress;

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

        public TrainBuilder withProgress(String progress){
            this.progress = progress;
            return this;
        }

        public Train build() {
            return new Train(this);
        }
    }
}