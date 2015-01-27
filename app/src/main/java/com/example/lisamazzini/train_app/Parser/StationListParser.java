package com.example.lisamazzini.train_app.Parser;

import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.Station;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lisamazzini on 23/01/15.
 */
public class StationListParser {

    private List<Station> stationList = new LinkedList<>();
    private Document doc = new Document("");
    private final String trainNumber;
    private String stationName;
    private boolean visited;

    private String scheduledTime;
    private String expectedTime;
    private long timeDifference;

    private String scheduledPlatform;
    private String expectedPlatform;

    /**
     * The constructor has the train number as input
     *
     * @param trainNumber
     */
    public StationListParser(String trainNumber){
        this.trainNumber=trainNumber;
    }

    /**
     * This method is the one which will be called outside the class in order to compute all the
     * information about the stations;
     *
     * @throws java.text.ParseException
     * @throws java.io.IOException
     */
    public void computeResult() throws ParseException, IOException {
        goToMainDetailPage();
        computeStation(Constants.ALREADY_VISITED);
        computeStation(Constants.NOT_VISITED_YET);
    }


    public List<Station> getStationList(){
        return this.stationList;
    }

    public String getProgress(){
        return this.convertProgress(computeProgress());
    }


    /**
     * This method generates the Document with the HTML of the internet page; here we need to also compute the
     * platform of the first station because (for no reason at all) it is specified only in the first page
     * but not in the second one. Then, it connects to the page with all the details about the stations.
     *
     * @throws java.io.IOException
     */
    private void goToMainDetailPage() throws IOException {
        Connection.Response res = Jsoup.connect(Constants.BASE_URL + Constants.EXT_URL + Constants.ACTION_NUMBER)
                .data("numeroTreno", trainNumber)
                .method(Connection.Method.POST)
                .execute();

        Document docProv = res.parse();
        computeFirstStationPlatforms(docProv);

        Element link = docProv.select("a").first();
        String linkHref = link.attr("href");
        this.doc = Jsoup.connect(Constants.BASE_URL + linkHref).get();
    }


    /**
     * This method compute all the stations which are in the details page; it scrapes all the information
     * and creates the stationList, adding one by one all the stations.
     *
     * @param stationStatus
     * @throws java.text.ParseException
     */
    private void computeStation(String stationStatus) throws ParseException {
        //filter the station, search for visited, or not
        Elements divs = this.doc.select("div." + stationStatus);
        //parse train for train (each div is a different train)
        for (Element e : divs) {
            this.stationName = e.select("h2").text();

            if (stationStatus.equals(Constants.ALREADY_VISITED)) {
                this.visited = true;
            } else {
                this.visited = false;
            }

            //2 strongs in each div are the arrival and departure times.
            //then it may possibly be another strong for the platform (only if the station is visited)
            Elements trainStationDetails = e.select("strong");
            //first and second strong are the arrivals
            this.scheduledTime = trainStationDetails.get(0).text();
            this.expectedTime = trainStationDetails.get(1).text();

            //if both times are present, calculate the differential
            if (!this.scheduledTime.equals("") && !this.expectedTime.equals("")) {
                this.timeDifference = computeDelay();
            }


            if ((!stationStatus.equals(Constants.ALREADY_VISITED) && !e.equals(divs.get(0))) || stationStatus.equals(Constants.NOT_VISITED_YET)) {
                //divText contains the text of the div (the scheduledPlatform if the station is visited)
                String[] divText = e.text().split(" ");
                this.scheduledPlatform = "";
                this.expectedPlatform = "";
                computePlatform(divText);
            }

            //create and add the station to the stationList
            this.stationList.add(new Station(this.stationName, this.expectedTime,
                    this.scheduledTime, this.timeDifference, this.scheduledPlatform,
                    this.expectedPlatform, this.visited));

            System.out.println(stationList.get(stationList.size() - 1).toString());
        }
    }

    /**
    * This method computes the platform for the first station, which is in the first search page.
    *
    * @param doc
    */
    private void computeFirstStationPlatforms(Document doc) {
        Elements divs = doc.select("div.corpocentrale");
        String[] divText = divs.get(0).text().split(" ");

        this.scheduledPlatform = "";
        this.expectedPlatform = "";
        computePlatform(divText);
    }


    /**
     * This method searches the platform in the String array, and assign the results in the fields
     *
     * @param divText
     */
    private void computePlatform(String[] divText){
        int i = 0;
        int j = 0;

        //search for the word "previsto" and take the result until he finds the word "binario" for platform like "2 ovest"
        for (i = 7; i < divText.length; i++) {
            if (divText[i].equals("Previsto:")) {
                for (j = i+1; !divText[j].equals("Binario"); j++) {
                    this.scheduledPlatform = this.scheduledPlatform.concat(divText[j].concat(" "));
                }
                break;
            }
        }

        //search for the word "reale" and take the platform after it
        for (i = j+1; i < divText.length; i++) {
            if (divText[i].equals("Reale:")) {
                for (j = i+1; j != divText.length; j++) {
                    this.expectedPlatform += divText[j] + " ";
                }
                break;
            }
        }
    }

    /**
     * This method computes the delay of the train in a particular station
     * @return long representing the train's delay in minutes
     * @throws java.text.ParseException
     */
    private long computeDelay() throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date arrival = format.parse(this.scheduledTime);
        Date departure = format.parse(this.expectedTime);
        return (departure.getTime() - arrival.getTime()) / (60 * 1000) % 60;
    }

    /**
     * This method computes the progress of the train by calculating the variation of the minutes
     * of delay in the last 5 or less visited stations
     *
     * @return the variation
     */
    private long computeProgress(){

        final List<Station> visitedStations = new LinkedList<>();
        long progress = 0;
        long intermediate;

        if(this.stationList == null){
            throw new NullPointerException();
        }

        for(Station s : stationList){
            if(s.isVisited()){
                visitedStations.add(s);
            }
        }

        if(visitedStations.size() >= 5) {
            for (int i = visitedStations.size() - 6; i < visitedStations.size() - 1; i++) {
                intermediate = visitedStations.get(i + 1).getTimeDifference() - visitedStations.get(i).getTimeDifference();
                progress += intermediate;
            }
        }else{
            for(int i = 0 ; i<visitedStations.size()-1; i++) {
                intermediate = visitedStations.get(i + 1).getTimeDifference() - visitedStations.get(i).getTimeDifference();
                progress += intermediate;
            }
        }

        return progress;
    }

    /**
     * This method convert the variation expressed in long in a String that describes the progress
     *
     * @param progress long
     * @return the String describing the progress
     */
    private String convertProgress(long progress){

        if(progress < -2){              //magic fuckin number
            return "In recupero";
        }else if(progress > 2){
            return "In rallentamento";
        }else{
            return "Costante";
        }
    }

}
