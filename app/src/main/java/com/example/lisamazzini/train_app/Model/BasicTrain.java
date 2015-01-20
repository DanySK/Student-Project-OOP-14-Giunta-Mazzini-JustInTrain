package com.example.lisamazzini.train_app.Model;

public class BasicTrain {

    private final String category;
    private final String number;
    private final int delay;

    public BasicTrain(String category, String number, int delay) {
        this.category = category;
        this.number = number;
        this.delay = delay;
    }

    public String getCategory() {
        return category;
    }

    public String getNumber() {
        return number;
    }

    public int getDelay() {
        return delay;
    }
}