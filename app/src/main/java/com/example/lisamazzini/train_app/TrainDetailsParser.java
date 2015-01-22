package com.example.lisamazzini.train_app;

import com.example.lisamazzini.train_app.Exceptions.DeletedTrainException;
import com.example.lisamazzini.train_app.Exceptions.InvalidTrainNumberException;
import com.example.lisamazzini.train_app.Model.Constants;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by lisamazzini on 22/01/15.
 */
public class TrainDetailsParser {

    private final static String NOT_DEPARTED_YET = "partito";
    private final static String ON_TIME = "orario";
    private final static String LATE = "ritardo";
    private final static String IN_ADVANCE = "anticipo";
    private final static String IS_MOVING = "viaggia";

    private String trainCategory;
    private String trainNumber;
    private int delay;
    private String condition;
    private boolean isMoving;
    private String lastSeenStation;
    private String lastSeenTime;
    private Document doc;

    //          !!!!!!              TO DO           !!!! !!!
    private String birthStation = "";
    private String deathStation = "";
    //          !!!!!!              TO DO               !!!!!

    public TrainDetailsParser(String trainNumber) {
        this.trainNumber = trainNumber;
        this.trainCategory = "";
        this.delay = 0;
        this.condition = "";
        this.isMoving = false;
        this.lastSeenStation = "";
        this.lastSeenTime = "";
    }

    public static void main(String[] args){

        TrainDetailsParser t = new TrainDetailsParser("31");
        try {
            Train tre = t.computeResult();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * This is the only public method and it starts all the computation
     *
     * @return the train parsed from the site
     */
    public Train computeResult() throws IOException {
        goToMainResultPage();
        //computeTrainStatus();

        return null;
    }

    /**
     * This method executes the connection to the website and gets the html Document
     *
     * @throws IOException
     */
    private void goToMainResultPage() throws IOException {
        Connection.Response response = Jsoup.connect(Constants.BASE_URL + Constants.EXT_URL + Constants.ACTION_NUMBER)
                                            .data("numeroTreno", this.trainNumber)
                                            .method(Connection.Method.POST)
                                            .execute();
        this.doc = response.parse();
        //System.out.println(this.doc.html());
    }

    /**
     * This method starts to parse informations about the train, in particular its category and number
     * It also check if something went wrong by controlling the title of the html page
     */
    private void computeTrainStatus() throws DeletedTrainException, InvalidTrainNumberException {

        String title = doc.select("title").first().ownText();

        if(somethingWentWrong(title)){
            Elements elem = doc.select("span");
            if(elem.isEmpty()){
                //il treno è doppio
            }else{
                String text = elem.first().ownText();
                if(text.equals("numero treno non valido")){
                    throw new InvalidTrainNumberException();
                }
                if(text.equals("Treno Cancellato")){
                    throw new DeletedTrainException();
                }


            }
        }




    }

    private boolean somethingWentWrong(String title){
        return !title.equals("Cerca Treno (per numero)");
    }



}
