package com.example.lisamazzini.train_app;

import java.io.IOException;
import java.util.Arrays;

import org.jsoup.*;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JsoupTrainDetails implements IScraper{
	
//	private final static String ARRIVED = "arrivato";
	private final static String NOT_DEPARTED_YET = "partito";
	private final static String ON_TIME = "orario";
	private final static String LATE = "ritardo";
	private final static String IN_ADVANCE = "anticipo";
	private final static String IS_MOVING = "viaggia";
	
	private final String baseurl;
	private final String action;
	private String trainCategory;
	private String trainNumber;
	private int delay;
	private String condition;
	private boolean isMoving;
	private String lastSeenStation;
	private String lastSeenTime;
	private Document doc;
	
	public JsoupTrainDetails(String trainNumber) {
		this.trainNumber = trainNumber;
		this.baseurl = "http://mobile.viaggiatreno.it/vt_pax_internet/mobile";
		this.action = "/numero";
		this.trainCategory = "";
		this.delay = 0;
		this.condition = "";
		this.isMoving = false;
		this.lastSeenStation = "";
		this.lastSeenTime = "";
	}
	
/*	public static void main(String[] args) {
		JsoupTrainDetails j = new JsoupTrainDetails("2064");
			try {
				long startTime = System.currentTimeMillis();
//				long startTime = System.nanoTime();
				j.computeResult();
				long endTime = System.currentTimeMillis();
//				long endTime = System.nanoTime();
				System.out.println((endTime - startTime) + "...." + j.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}*/
	
	/**
	 * This method opens visits the main train number research result page, which contains
	 * the DEPARTURE, CURRENT and FINAL station of that train.
	 * The train number used for the research is the one inserted by the user.
	 * It also checks if the train number inserted is a valid one, if not DO SOMETHING (WHAT???)
	 * @throws java.io.IOException
	 * 
	 */
	public Train computeResult() throws IOException{
		goToMainResultPage();
		computeTrainStatus();

        return new Train(this.trainCategory, this.trainNumber, this.condition, this.lastSeenStation, this.lastSeenTime, this.isMoving, this.delay);
	}
	
	/**
	 * This method open the connection and retrieves the html page for the searched train
	 * @throws java.io.IOException
	 */
	private void goToMainResultPage() throws IOException {
		// now we visit the home page for the specified search and we enter the
		// values in da form
		Connection.Response res = Jsoup.connect(baseurl + action)
				.data("numeroTreno", trainNumber)
				.method(Method.POST)
				.execute();

		this.doc = res.parse();
	}

	/**
	 * This method checks if the page remained the same after the the research.
	 * If so, it means the train inserted was wrong OR THERE MAY BE MULTIPLE TRAINS (TODO)
	 * @param trainDetails
	 * @return
	 */
	private boolean rightTrainNumber(String trainDetails) {
		if (trainDetails.equals("Cerca treno per numero")) {
			// TODO exception in case of wrong train number insertion
			// TODO train number may be of more than only one train
			return false;
		}
		return true;
	}
	
	/**
	 * This method sets the TRAIN DELAY, CONDITION, ID and CATEGORY values 
	 * to the specified ones (into the html code). 
	 * It works with the non-detailed train number result page.
	 * 
	 * 
	 */
	private void computeTrainStatus() {
		
		String trainDetails = doc.select("h1").first().ownText();
		if (!rightTrainNumber(trainDetails)) {
			// TODO check wrongTrainNumberException
		}
		Elements divsArray = this.doc.select("strong");
		try {
			String[] conditionArray = divsArray.get(divsArray.size() - 1).ownText().split(" ");
			
			this.computeCondition(conditionArray);
//			System.out.println(trainDetails);
			this.trainCategory = trainDetails.split(" ")[0];
			this.trainNumber = trainDetails.split(" ")[1];
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO
			
		}
	}
	
	/**
	 * This method calls other funtions in order to set the whole train condition.
	 * 5 and 6 are the indexes of the string that are useful to determinate all the other things.
	 * Don't touch them, nor any other index.
	 * 
	 * @param conditionArray
	 */
	private void computeCondition(String[] conditionArray) {
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
		if (conditionArray[2].equals(IS_MOVING)) {
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
		if (conditionArray[conditionIndex].equals(ON_TIME)) {
			this.setStatus(conditionArray, ON_TIME, 0);
		} else if (conditionArray[conditionIndex].equals(NOT_DEPARTED_YET)) {
			this.setStatus(conditionArray, NOT_DEPARTED_YET, 0);
		} else if (conditionArray[conditionIndex + 3].equals(LATE)) {
			this.setStatus(conditionArray, LATE, Integer.parseInt(conditionArray[conditionIndex]));
		} else {
			this.setStatus(conditionArray, IN_ADVANCE, Integer.parseInt("-" + conditionArray[conditionIndex]));
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
	 * This method return only the delay of the searched train
	 * @return delay
	 */
	public int getDelay() {
		return this.delay;
	}

    public String getTrainCategory() {
        return this.trainCategory;
    }
	
	/* (non-Javadoc) Prints out the train ID CATEGORY DELAY CONDITION ISMOVING LASTSEEN
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return 	" || Id: " + this.trainNumber + 
				" || Category: " + this.trainCategory + 
				" || Delay: " + this.delay + 
				" || Condition: " + this.condition + 
				" || IsMoving: " + this.isMoving +
				" || LastSeen: " + this.lastSeenStation + " " + lastSeenTime;
	}
}
