package com.example.lisamazzini.train_app.Controller;

import android.util.Log;

import com.example.lisamazzini.train_app.Model.Fermate;
import com.example.lisamazzini.train_app.Model.NewTrain;
import com.example.lisamazzini.train_app.Utilities;

import java.util.*;

public class StationListController {

    private final String trainNumber;
    private String trainCode;

    public StationListController(String trainNumber){
        this.trainNumber = trainNumber;
    }

    public TrainDataRequest getNumberRequest(){
        return new TrainDataRequest(this.trainNumber);
    }

    public void setCode(String code){
        this.trainCode = code;
    }

    public TrainRequest getNumberAndCodeRequest(){
        return new TrainRequest(this.trainNumber, this.trainCode);
    }

    public String[] computeData(String data){
        return Utilities.splitString(data);
    }

    public String[] computeChoices(String[] first, String[] second){
        final String[] result = new String[2];
        result[0] = "Treno " + first[0] + " in partenza da " + first[2];
        result[1] = "Treno " + second[0] + " in partenza da " + second[2];
        return result;
    }

    public String getProgress(NewTrain train){
        Long delta = 0L;
        Long intermediateDelta = 0L;
        List<Fermate> visited = new LinkedList<>();

        for(Fermate f : train.getFermate()){
            if(f.getActualFermataType() != 0){
                visited.add(f);
            }
        }

        if(visited.size() < 5) {
            for (int i = visited.size() - 2; i >= 0; i--) {
                intermediateDelta = visited.get(i + 1).getRitardo() - visited.get(i).getRitardo();
                Log.d(" A intermediate delta", "" + intermediateDelta);
                delta += intermediateDelta;
            }
        }else{
            for (int i = visited.size()-2; i >= visited.size()-6; i--) {
                intermediateDelta = visited.get(i + 1).getRitardo() - visited.get(i).getRitardo();
                Log.d(" B intermediate delta", "" + intermediateDelta);
                delta += intermediateDelta;
            }
        }

        Log.d("delta", "" + delta);

        if(delta > 2L){
            return "In rallentamento";
        }
        if(delta < -2L){
            return "In recupero";
        }

        return "Andamento costante";
    }

}