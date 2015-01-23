package com.example.lisamazzini.train_app.Older;

import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupPlannedJourney {
	private final static int N_TIME_SLOT = 5;
//	private final static Date EARLY_MORNING = new SimpleDateFormat("HH:mm").parse("06:00"); // < 6:00
//	private final static Date MORNING = new SimpleDateFormat("HH:mm").parse("13:00"); // 6:00 < x < 13:00
//	private final static Date AFTERNOON = new SimpleDateFormat("HH:mm").parse("18:00");//13:00 < x < 18:00
//	private final static Date EVENING = new SimpleDateFormat("HH:mm").parse("22:00");//18:00 < x < 22:00
//	private final static Date NIGHT = new SimpleDateFormat("HH:mm").parse("00:00"); //x > 22:00
//	
//	
	
	private final String baseurl = "http://mobile.viaggiatreno.it";
	private final String baseurl2 = "/vt_pax_internet/mobile";
	private final String action = "/programmato";
	private  String departureStation = "";
	private String arrivalStation = "";
	private final String day = "17";
	private final String month = "12"; 
	private final String year = "2014";
    private boolean isFirstNotArrived = false;
    private int id = 0;
	private String trainNumber = "";
	private List<Journey> exchangeList = new ArrayList<>();

	private String departureTime = "";
	private String arrivalTime = "";
	
	private int timeSlot = -1;
	private int radio = 0;
	private List<Journey>journeysList = new LinkedList<>();
    private String category = "";
	private int delay = 0;
	private  Document doc1; 
	private  Document doc2;
	
	/*public static void main(String[] args) throws IOException, ParseException{
		JsoupPlannedJourney j = new JsoupPlannedJourney();
		j.selectTimeSlot();
//		System.out.println(j.timeSlot);
//		try {
			j.goToMainResultPage(j.timeSlot);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}*/
	
	
	public void computeResult(Integer radioBut, String departure, String arrival) {
		try {
//            selectTimeSlot();
            System.out.println("entrato in compute result");
            this.departureStation = departure;
            this.arrivalStation = arrival;
            this.radio = radioBut;
			goToMainResultPage(radioBut);
		} catch (IOException e) {
			e.printStackTrace();
        }
    }

    public void computeResult(Integer radioBut, int timeSlot, String departure, String arrival) {
        try {
//            selectTimeSlot();
            System.out.println("entrato in compute result");
            this.departureStation = departure;
            this.arrivalStation = arrival;
            this.timeSlot = timeSlot;
            this.radio = radioBut;
            goToMainResultPage(radioBut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
//	private void selectTimeSlot() throws ParseException {
//        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
//        Date earlyNight = time.parse("00:00");
//        Date earlyMorning = time.parse("06:00");
//        Date morning = time.parse("13:00");
//        Date afternoon = time.parse("18:00");
//        Date evening = time.parse("22:00");
//        Date lateNight = time.parse("23:59");
////    	String systemTime = time.format(Calendar.getInstance().getTime().toString());
//        Date actualTime = time.parse(time.format(Calendar.getInstance().getTime()));
////    	Date actualTime = time.parse("04:00");
//        if (isBetween(new DateTime(earlyNight), new DateTime(earlyMorning), new DateTime(actualTime))) {
//            this.timeSlot = 1;
//        } else if (isBetween(new DateTime(earlyMorning), new DateTime(morning), new DateTime(actualTime))) {
//            this.timeSlot = 2;
//        } else if (isBetween(new DateTime(morning), new DateTime(afternoon), new DateTime(actualTime))) {
//            this.timeSlot = 3;
//        } else if (isBetween(new DateTime(afternoon), new DateTime(evening), new DateTime(actualTime))) {
//            this.timeSlot = 4;
//        } else if (isBetween(new DateTime(evening), new DateTime(lateNight), new DateTime(actualTime))) {
//            this.timeSlot = 5;
//        }
//    }
//	private boolean isBetween(DateTime initialTime, DateTime finalTime, DateTime actualTime) {
//		int min1 = Minutes.minutesBetween(initialTime, actualTime).getMinutes();
//		int min2 = Minutes.minutesBetween(actualTime, finalTime).getMinutes();
//		System.out.println(initialTime + " " + finalTime + " " + actualTime);
//		System.out.println(min1 + "\t\t\t" + min2);
//		return (Minutes.minutesBetween(initialTime, actualTime).getMinutes()) >= 0 &&
//				(Minutes.minutesBetween(actualTime, finalTime).getMinutes()) > 0;
//		return min1 >= 0 && min2 > 0;
//	}
	
	/**
	 * This method connects to main result page for each time slot (beginning from a specific one
	 * and itering all of them in a cyclic manner).
	 * It builds the arrayList with lists
	 * The for loop selects the radiobuttons from the start point to the end and then from the beginning
	 * to the start point ex. 3 -> 4 -> 5 -> 1 -> 2
	 * @throws java.io.IOException
	 */
	private void goToMainResultPage(int startPoint) throws IOException{
		int radioButSelector = startPoint;
//		for (int i = 0; i < 5; i++) {
//			this.journeysList.add(new LinkedList<Journey>());
//		}
		
//		for (int i = 0; i < N_TIME_SLOT; i++){
			Connection.Response res = Jsoup.connect(baseurl + baseurl2 + action)
					.data("partenza", departureStation, "arrivo", arrivalStation, "giorno", day, "mese",
                            month, "anno", year, "fascia", Integer.toString(radioButSelector))
					.method(Method.POST).execute();
			Map<String, String> cookies = res.cookies();
			this.doc1 = res.parse();
			iterateResults(cookies, startPoint, radioButSelector);
//			if (radioButSelector == N_TIME_SLOT) {
//				radioButSelector = 0;
//			}
//			radioButSelector++;
//		}
	}
		
	
	/**
	 * This method parses each result of the main result page.
	 * It also checks for expections in case there is not result in da page.
	 * @param cookies
	 * @throws java.io.IOException
	 */
	private void iterateResults(Map<String, String> cookies, int startPoint, int timeSlot) throws IOException {
		Elements divs = this.doc1.select("div.bloccorisultato");
		if (divs.text().split(" ")[0].equals("Partenza:")) {
			for(Element e : divs){
				this.clickOnDetailsLink(e, cookies);	//ora il doc2 contiene i risultati dei dettagli
				this.buildJourney(startPoint, timeSlot);
	//			System.out.println(toString());
				this.exchangeList.removeAll(exchangeList);
			}
		}
	}
	
	
	/**
	 * This method connects to the link of each result, searching for times and the number train.
	 * It computes the delay
	 * @param e
	 * @param cookies
	 * @throws java.io.IOException
	 */
	private void clickOnDetailsLink(Element e, Map<String, String> cookies) throws IOException {
		Element detailLink = e.select("a").first();
		String linkHref = detailLink.attr("href");
		this.doc2 = Jsoup.connect(baseurl + linkHref).cookies(cookies).execute().parse();
		String[] s = this.doc2.select("div.bloccorisultato").get(0).text().split(" ");
		s[0] = "";
		computeIndex(s, 0);
        this.id++;
	}


	/**
	 *This is a utility method for searching all the information about every journey
	 *it fills the journeylist with also multiple journey if there is any exchange
	 * @param resultArray
	 * @return
	 * @throws java.io.IOException
	 */
	private void computeIndex(String[] resultArray, int startPoint) throws IOException {
		int x = 0;
		int nextPartenza = 0;
		int lastPartenza = 0;
		String departureExchange = "";
		String arrivalExchange = "";
		for(int j = startPoint; j < resultArray.length; j++){
			if (resultArray[j].equals("Cambio") || resultArray[j].equals("Arrivo:")) {
				x = j - 3;
				this.departureTime = resultArray[x]; 
				this.trainNumber = resultArray[x + 2];
				departureExchange = "";
				arrivalExchange = "";
				for (int i = lastPartenza+1; i < x; i++) {
					departureExchange = departureExchange.concat(resultArray[i]).concat(" ");
				}
				for (int i = j+4; i < resultArray.length; i++) {
					if (resultArray[i].equals("Partenza:")) {
						nextPartenza = i;
						break;
					}
				}
				if(resultArray[j].equals("Cambio")){
					for (int i = j+2; i < nextPartenza-1; i++) {
						arrivalExchange = arrivalExchange.concat(resultArray[i].concat(" "));
						this.arrivalTime = resultArray[nextPartenza - 1];
					}
				} else if (resultArray[j].equals("Arrivo:")) {
					for (int i = j+1; i < resultArray.length-1; i++) {
						arrivalExchange = arrivalExchange.concat(resultArray[i].concat(" "));
						this.arrivalTime = resultArray[resultArray.length-1];
					}
				}
				computeDelay();

                if (this.timeSlot != -1) {
                    this.isFirstNotArrived = true;
                }

				this.exchangeList.add(new Journey(isFirstNotArrived, id, trainNumber, category, departureExchange, departureTime, arrivalExchange, arrivalTime, delay));
				lastPartenza = nextPartenza;
			}
		}
	}


	/**
	 * This method takes the train number and obtains the delay of that train from
	 * the train number search class.
	 * @throws java.io.IOException
	 */
	private void computeDelay() throws IOException {
		JsoupTrainDetails j = new JsoupTrainDetails(this.trainNumber);
		j.computeResult();
		this.delay = j.getDelay();
        this.category = j.getTrainCategory();
	}
	
	
	/**
	 * This method builds a new journey with the data just parsed
	 * @param startPoint
	 * @param timeSlot
	 */
	private void buildJourney(int startPoint, int timeSlot) {
        for (Journey j : this.exchangeList) {
            this.journeysList.add(j);
        }
//		System.out.println(this.journeysList.get(timeSlot - 1).get(journeysList.get(timeSlot - 1).size() - 1).toString());
	}

    public ListJourney getJourneysList() {

        ListJourney lj = new ListJourney(this.journeysList, this.radio);
       return lj;
//        return this.journeysList;
    }
}