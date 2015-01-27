package com.example.lisamazzini.train_app.Parser;

import com.example.lisamazzini.train_app.Exceptions.DeletedTrainException;
import com.example.lisamazzini.train_app.Exceptions.DoubleTrainNumberException;
import com.example.lisamazzini.train_app.Exceptions.InvalidTrainNumberException;
import com.example.lisamazzini.train_app.Model.Train;
import com.example.lisamazzini.train_app.Model.Constants;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by lisamazzini on 22/01/15.
 */
public class TrainDetailsParser {

    private String trainCategory;
    private String trainNumber;
    private int delay;
    private String condition;
    private boolean isMoving;
    private String lastSeenStation;
    private String lastSeenTime;
    private Document doc;

    private String birthStation;
    private String deathStation;

    public TrainDetailsParser(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    /**
     * This is the only public method and it starts all the computation
     *
     * @return the train parsed from the site
     */
    public Train computeResult() throws IOException, InvalidTrainNumberException, DeletedTrainException, DoubleTrainNumberException {
        goToMainResultPage();
        computeTrainDetails();
        computeCondition();
        computeBaDStation();
        return new com.example.lisamazzini.train_app.Model.Train.TrainBuilder(this.trainCategory, this.trainNumber,
                this.isMoving, this.delay, this.birthStation, this.deathStation, this.lastSeenStation, this.lastSeenTime)
                .build();
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
     * This method parse from the page the station which the train starts from and the one in which stops.
     */
    private void computeBaDStation()  {
        Elements stations = this.doc.select("h2");
        this.birthStation = stations.get(0).toString();
        this.deathStation = stations.get(1).toString();

    }

    /**
     * This method starts to parse informations about the train, in particular its category and number
     * It also check if something went wrong by controlling the title of the html page
     *
     * @throws DoubleTrainNumberException, if there are more trains with the same number
     * @throws InvalidTrainNumberException, if there's no train with the number
     * @throws DeletedTrainException, if the train was deleted
     */
    private void computeTrainDetails() throws DoubleTrainNumberException, InvalidTrainNumberException, DeletedTrainException {

        String title = doc.select("title").first().ownText();

        if(somethingWentWrong(title)){
            solveIt();
        }
        String trainDetails = doc.select("h1").first().ownText();

        this.trainCategory = trainDetails.split(" ")[0];
        this.trainNumber = trainDetails.split(" ")[1];
        }


    /**
     * This method calls other functions in order to set the whole train condition.
     * 5 and 6 are the indexes of the string that are useful to determinate all the other things.
     * Don't touch them, nor any other index.
     *
     *
     */
    private void computeCondition() {
        Elements divsArray = this.doc.select("strong");
        String[] conditionArray = divsArray.get(divsArray.size() - 1).ownText().split(" ");
        if (this.isMoving(conditionArray)) {
            this.doDirtyJob(conditionArray, 4);
        } else {
            this.doDirtyJob(conditionArray, 5);
        }
    }

    /**
     * This method says if the train is moving or not.
     * It also sets the value of isMoving field
     *
     * @param conditionArray
     * @return true if train is moving, otherwise false
     */
    private boolean isMoving(String[] conditionArray ) {
        if (conditionArray[2].equals(Constants.IS_MOVING)) {
            this.isMoving = true;
            return true;
        }
        return false;
    }

    /**
     * This method calls setStatus with the right values, take directly from the string.
     * It looks for whether the train is late, on time etc...
     * As usually, don't touch any index
     *
     * @param conditionArray
     * @param conditionIndex
     */
    private void doDirtyJob(String[] conditionArray, int conditionIndex) {
        if (conditionArray[conditionIndex].equals(Constants.ON_TIME)) {
            this.setStatus(conditionArray, Constants.ON_TIME, 0);
        } else if (conditionArray[conditionIndex].equals(Constants.NOT_DEPARTED_YET)) {
            this.setStatus(conditionArray, Constants.NOT_DEPARTED_YET, 0);
        } else if (conditionArray[conditionIndex + 3].equals(Constants.LATE)) {
            this.setStatus(conditionArray, Constants.LATE, Integer.parseInt(conditionArray[conditionIndex]));
        } else {
            this.setStatus(conditionArray, Constants.IN_ADVANCE, Integer.parseInt("-" + conditionArray[conditionIndex]));
        }

    }

    /**
     * This method actually sets all the values.
     * It also sets the last seen station and time if the train is moving.
     *
     * @param conditionArray
     * @param condition
     * @param delay
     */
    private void setStatus(String[] conditionArray, String condition, int delay) {
        this.condition = condition;
        this.delay = delay;

        if (isMoving == true) {
            for (int i = 11; i < conditionArray.length - 3; i++) {
                //TODO use concat?
                this.lastSeenStation = this.lastSeenStation.concat(conditionArray[i].concat(" "));
            }
            this.lastSeenTime = conditionArray[conditionArray.length - 1];
        }
    }


    /**
     * Check if after the connection it's still in the same page, through the title
     *
     * @param title
     * @return true if it's the same page, false otherwise
     */
    private boolean somethingWentWrong(String title){
        return title.equals("Cerca Treno (per numero)");
    }

    /**
     *  This method checks what has gone wrong with the search, in fact it throws an exception which can be:
     *
     * @throws DoubleTrainNumberException, if there is more than one train with the same number
     * @throws InvalidTrainNumberException, if there's no train with this number
     * @throws DeletedTrainException, if the train was deleted
     */

    private void solveIt() throws DoubleTrainNumberException, InvalidTrainNumberException, DeletedTrainException {
        Elements elem = doc.select("span");
        if(elem.isEmpty()){
            Elements options = doc.select("option");
            String[] stations = new String[options.size()];
            int i = 0;
            for(Element e : options){
                stations[i] = e.ownText();
                i++;
            }
            System.out.println( "stat" + Arrays.toString(stations));
            throw new DoubleTrainNumberException("" + Arrays.toString(stations));
        }else{
            String text = elem.first().ownText();
            System.out.println("a");
            if(text.equals("numero treno non valido")){
                System.out.println("b");

                throw new InvalidTrainNumberException();
            }
            if(text.equals("Treno Cancellato")){
                System.out.println("c");

                throw new DeletedTrainException();
            }
        }
    }


    public int getDelay() {
        return this.delay;
    }

    public String getTrainCategory() {
        return this.trainCategory;
    }



}
