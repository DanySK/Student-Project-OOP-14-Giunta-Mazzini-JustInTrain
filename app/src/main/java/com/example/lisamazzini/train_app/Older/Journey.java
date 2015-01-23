package com.example.lisamazzini.train_app.Older;

public class Journey {

    private boolean isFirstNotArrived = false;
    private int id = 0;
    private String trainNumber = "";
    private String trainCategory = "";
    private String departureStation = "";
    private String departureTime = "";
    private String arrivalStation = "";
    private String arrivalTime = "";
    private int delay = 0;


    public Journey(boolean isFirstNotArrived, int id, String trainNumber, String trainCategory, String departureStation,
                   String departureTime, String arrivalStation, String arrivalTime,
                   int delay) {
        super();
        this.isFirstNotArrived = isFirstNotArrived;
        this.id = id;
        this.trainNumber = trainNumber;
        this.trainCategory = trainCategory;
        this.departureStation = departureStation;
        this.departureTime = departureTime;
        this.arrivalStation = arrivalStation;
        this.arrivalTime = arrivalTime;
        this.delay = delay;
    }

    public boolean isFirstNotArrived() {
        return isFirstNotArrived;
    }

    public int getId() {
        return id;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public String getTrainCategory() {
        return trainCategory;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public int getDelay() {
        return delay;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.isFirstNotArrived);
        builder.append("\t");
        builder.append(this.id);
        builder.append("\t");
        builder.append(this.trainNumber);
        builder.append("\t");
        builder.append(this.trainCategory);
        builder.append("\t");
        builder.append(this.departureStation);
        builder.append("\t");
        builder.append(this.departureTime);
        builder.append("\t");
        builder.append(this.arrivalStation);
        builder.append("\t");
        builder.append(this.arrivalTime);
        builder.append("\t");
        builder.append(this.delay);
        builder.append("\t");
        return builder.toString();
    }
}