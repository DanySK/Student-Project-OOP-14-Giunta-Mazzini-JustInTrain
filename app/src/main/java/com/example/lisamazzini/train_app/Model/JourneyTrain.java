package com.example.lisamazzini.train_app.Model;

import com.example.lisamazzini.train_app.Exceptions.FieldNotBuiltException;

public class JourneyTrain extends BasicTrain {

    private final int journeyID;
    private final String duration;
    private final String departureStation;
    private final String arrivalStation;
    private final String departureTime;
    private final String arrivalTime;
    private final String departurePlatform;
    private final String arrivalPlatform;

    public int getJourneyID() {
        return journeyID;
    }

    public String getDuration() {
        return duration;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDeparturePlatform() throws FieldNotBuiltException {
        if (this.departurePlatform == null) {
            throw new FieldNotBuiltException();
        }
        return departurePlatform;
    }

    public String getArrivalPlatform() throws FieldNotBuiltException {
        if (this.arrivalPlatform == null) {
            throw new FieldNotBuiltException();
        }
        return arrivalPlatform;
    }

    private JourneyTrain(JourneyTrainBuilder builder) {
        super(builder.category, builder.number, builder.delay);
        this.journeyID = builder.journeyID;
        this.duration = builder.duration;
        this.departureStation = builder.departureStation;
        this.arrivalStation = builder.arrivalStation;
        this.departureTime = builder.departureTime;
        this.arrivalTime = builder.arrivalTime;
        this.departurePlatform = builder.departurePlatform;
        this.arrivalPlatform = builder.arrivalPlatform;
    }

    public static class JourneyTrainBuilder {
        private final String category; // fai enum
        private final String number;
        private final int delay;
        private final int journeyID;
        private final String duration;
        private final String departureStation;
        private final String arrivalStation;
        private final String departureTime;
        private final String arrivalTime;
        private String departurePlatform;
        private String arrivalPlatform;

        public JourneyTrainBuilder(String category, String number,
                                   int delay, int journeyID, String duration,
                                   String departureStation, String arrivalStation,
                                   String departureTime, String arrivalTime) {
            this.category = category;
            this.number = number;
            this.delay = delay;
            this.journeyID = journeyID;
            this.duration = duration;
            this.departureStation = departureStation;
            this.arrivalStation = arrivalStation;
            this.departureTime = departureTime;
            this.arrivalTime = arrivalTime;
        }

        public JourneyTrainBuilder withDeparturePlatform(String platform) {
            this.departurePlatform = platform;
            return this;
        }

        public JourneyTrainBuilder withArrivalPlatform(String platform) {
            this.arrivalPlatform = platform;
            return this;
        }

        public JourneyTrain build() {
            return new JourneyTrain(this);
        }
    }
}