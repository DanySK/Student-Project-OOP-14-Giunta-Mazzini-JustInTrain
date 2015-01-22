package com.example.lisamazzini.train_app.Controller;

import com.example.lisamazzini.train_app.JsoupTrainDetails;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.JourneyTrain;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private String category;
    private String trainNumber;
    private int delay;
    private int journeyID = 0;
    private String duration;
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

    public static void main(String[] args) {
        JourneyResultsParser j = new JourneyResultsParser("asdf", "asdf", 3, 3, "22", "01", "2015");
        try {
            j.computeResult();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void computeResult() throws IOException, ParseException {
        goToMainResultPage();
    }

    private void goToMainResultPage() throws IOException, ParseException {
        Connection.Response response = Jsoup
                .connect(Constants.BASE_URL +  Constants.EXT_URL + Constants.ACTION_PLANNED)
                .data("partenza", departureStation, "arrivo", arrivalStation,
                       "giorno", day, "mese", month, "anno", year, "fascia",
                       Integer.toString(timeSlotSelector))
                .method(Method.POST)
                .execute();
        Map<String, String> cookies = response.cookies();
        this.allResultsDoc = response.parse();
        if (!checkForErrors(this.allResultsDoc)) {
            System.out.println("documento parsato");
//            iterateAllResults(cookies);
        } else {
            String e = getErrorString(this.allResultsDoc);
            System.out.println(e);
            switch(e) {
                case "localita' non trovata" :
                    break;
                case "Data non valida" :
                    break;
                case "Inserire almeno 3 caratteri" :
                    break;
            }
        }
    }

    private boolean checkForErrors(Document doc) {
        return doc.title().equals("Ricerca Programma Orario");
    }

    // TODO in caso di più errori (ad esempio entrambe le località sbagliate) prevedere cosa fare? salvare in un array di stringhe? salvarne solo uno?
    private String getErrorString(Document doc) {
        return doc.select("span.errore").text();
    }


    private void iterateAllResults(Map<String, String> cookies) throws IOException, ParseException {
        Elements divs = this.allResultsDoc.select("div.bloccorisultato");
        if (divs.text().split(" ")[0].equals("Partenza:")) {
            for(Element el : divs){
                this.clickOnDetailsLink(el, cookies);
            }
        }
    }

    private void clickOnDetailsLink(Element el, Map<String, String> cookies) throws IOException, ParseException {
        String detailLink = el.select("a").first().attr("href");
        this.detailLinkResult = Jsoup.connect(Constants.BASE_URL + detailLink).cookies(cookies).execute().parse();
        String[] s = this.detailLinkResult.select("div.bloccorisultato").get(0).text().split(" ");
        s[0] = "";
        computeJourneyDetails(s);
        this.journeyID++;
    }


    // TODO spiezza in due come ivan drago con rocky
    private void computeJourneyDetails(String[] journeyTrainDetails) throws IOException, ParseException {

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

                computeDuration();
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

    private void computeDuration() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        DateTime arrivalDateTime;
        DateTime departureDateTime;
        arrivalDateTime = new DateTime(simpleDateFormat.parse(this.arrivalTime));
        departureDateTime = new DateTime(simpleDateFormat.parse(this.departureTime));
        int intervalMinutes = Minutes.minutesBetween(arrivalDateTime, departureDateTime).getMinutes();
        int minutes = intervalMinutes % 60;
        int hours = (intervalMinutes - minutes) / 60;
        this.duration = String.format("%02d:%02d", hours, minutes);
    }


    private void computeDelay() throws IOException {
        // TODO cambia con parser refrattorizzato
        JsoupTrainDetails j = new JsoupTrainDetails(this.trainNumber);
        j.computeResult();
        this.category = j.getTrainCategory();
        this.delay = j.getDelay();
    }
}
