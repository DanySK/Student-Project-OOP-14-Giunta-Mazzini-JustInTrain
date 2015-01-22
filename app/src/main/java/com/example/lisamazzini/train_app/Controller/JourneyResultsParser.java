package com.example.lisamazzini.train_app.Controller;

import com.example.lisamazzini.train_app.JsoupTrainDetails;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.JourneyTrain;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JourneyResultsParser {

    // FIELDS FOR REQUEST
    private final String day;
    private final String month;
    private final String year;
    private final String departureStation;
    private final String arrivalStation;
    private int timeSlotSelector;
    private int currentTimeSlot;

    // FIELDS FOR PARSING
    private Document allResultsDoc;
    private Document detailLinkResult;

    // FIELDS FOR RESULTS
    private int journeyID = 0;
    private String category;
    private String trainNumber;
    private int delay;
    private String departureTime;
    private String arrivalTime;
    private List<JourneyTrain> journeyTrainsList;


    public JourneyResultsParser(String departureStation, String arrivalStation,
                                int timeSlotSelector, int currentTimeSlot,
                                String day, String month, String year) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.currentTimeSlot = currentTimeSlot;
        this.timeSlotSelector = timeSlotSelector;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public void computeResult() {

    }

    private void goToMainResultPage() throws IOException {
        Connection.Response response = Jsoup
                .connect(Constants.BASE_URL +  Constants.EXT_URL + Constants.ACTION_PLANNED)
                .data("partenza", departureStation, "arrivo", arrivalStation,
                       "giorno", day, "mese", month, "anno", year, "fascia",
                       Integer.toString(timeSlotSelector))
                .method(Method.POST)
                .execute();
        Map<String, String> cookies = response.cookies();
        this.allResultsDoc = response.parse();

    }

    private void iterateAllResults(Map<String, String> cookies) throws IOException {
        Elements divs = this.allResultsDoc.select("div.bloccorisultato");
        if (divs.text().split(" ")[0].equals("Partenza:")) {
            for(Element el : divs){
                this.clickOnDetailsLink(el, cookies);
            }
        }
    }

    private void clickOnDetailsLink(Element el, Map<String, String> cookies) throws IOException {
        String detailLink = el.select("a").first().attr("href");
        this.detailLinkResult = Jsoup.connect(Constants.BASE_URL + detailLink).cookies(cookies).execute().parse();
        String[] s = this.detailLinkResult.select("div.bloccorisultato").get(0).text().split(" ");
        s[0] = "";
        computeJourneyDetails(s);
        this.journeyID++;
//        computeIndex(s, 0);
//        this.id++;
    }


    // TODO spiezza in due come ivan drago con rocky
    private void computeJourneyDetails(String[] journeyTrainDetails) throws IOException {

        int currentPosition = 0;
        // holds the wanted value in the array,
        // which is usually before certain "checkpoints" held in currentPosition variable
        int utilityIndex = currentPosition;

        int nextPartenzaIndex = 0;
        int lastPartenzaIndex = 0;

        String departureExchange;
        String arrivalExchange;

        // iteration overall the string[] of the journey train details
        for (currentPosition = 0; currentPosition < journeyTrainDetails.length; currentPosition++) {

            // here i search for the next train arrival (it can be an "exchange" or absolute arrival)
            if (journeyTrainDetails[currentPosition].equals("Cambio") ||
                journeyTrainDetails[currentPosition].equals("Arrivo:")) {

                utilityIndex = currentPosition - 3; // now it contains the departure time
                this.departureTime = journeyTrainDetails[utilityIndex];
                this.trainNumber = journeyTrainDetails[utilityIndex + 2];
                departureExchange = "";
                arrivalExchange = "";

                // here i save the departure location (which is may be made of multiple words)
                //  TODO trova la maniera di concatenare più efficiente che c'è, come furia cavallo del west
                for (int i = lastPartenzaIndex + 1; i < utilityIndex; i++) {
                    departureExchange = departureExchange.concat(journeyTrainDetails[i]).concat(" ");
                }

                // here i search for the next departure (if any is present, in case of exchanges)
                for (int i = currentPosition + 4; i < journeyTrainDetails.length; i++) {
                    if (journeyTrainDetails[i].equals("Partenza:")) {
                        nextPartenzaIndex = i;
                        break;
                    }
                }

                // here i assign the "exchange" or absolute arrival (they are differernt cause of different indexes : "Cambio a:" insted of "Arrivo:")
                if (journeyTrainDetails[currentPosition].equals("Cambio")) {
                    // here i save the arrival location (which is may be made of multiple words)
                    for (int i = currentPosition + 2; i < nextPartenzaIndex - 1; i++) {
                        //  TODO trova la maniera di concatenare più efficiente che c'è, come furia cavallo del west
                        arrivalExchange = arrivalExchange.concat(journeyTrainDetails[i].concat(" "));
                        this.arrivalTime = journeyTrainDetails[nextPartenzaIndex - 1]; // now it contains the arrival time
                    }
                } else if (journeyTrainDetails[currentPosition].equals("Arrivo:")) {
                    for (int i = currentPosition + 1; i < journeyTrainDetails.length - 1; i++) {
                        //  TODO trova la maniera di concatenare più efficiente che c'è, come furia cavallo del west
                        arrivalExchange = arrivalExchange.concat(journeyTrainDetails[i].concat(" "));
                        this.arrivalTime = journeyTrainDetails[journeyTrainDetails.length - 1];
                    }
                }

                // get ready for next round
                lastPartenzaIndex = nextPartenzaIndex;

                computeDelay();

                this.journeyTrainsList.add(new JourneyTrain.JourneyTrainBuilder(
                                            this.category, this.trainNumber,
                                            this.delay, this.journeyID, this.duration,
                                            this.departureStation, this.arrivalStation,
                                            this.departureTime, this.arrivalTime)
                                            .build());

            }
        }
    }


    private void computeDelay() throws IOException {
        // TODO cambia con parser refrattorizzato
        JsoupTrainDetails j = new JsoupTrainDetails(this.trainNumber);
        j.computeResult();
        this.category = j.getTrainCategory();
        this.delay = j.getDelay();
    }
}
