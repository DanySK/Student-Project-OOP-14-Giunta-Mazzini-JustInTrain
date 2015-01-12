package com.example.lisamazzini.train_app;

public class Station {
	
	private final 	String stationName;
	private 	  	String expectedArrival;
	private 	  	String scheduledArrival;
	private 		long timeDifference;
	private 		String expectedPlatform;
	private 		String scheduledPlatform;
	private final 	boolean visited;
	
	public Station(final String stationName, String expectedArrival, String scheduledArrival, long timeDifference,
						String scheduledPlatform, String expectedPlatform, boolean visited) {
		
		this.stationName =		stationName;
		this.expectedArrival = 	expectedArrival;
		this.scheduledArrival = scheduledArrival;
		this.timeDifference = 	timeDifference;
		this.scheduledPlatform =scheduledPlatform;
		this.expectedPlatform = expectedPlatform;
		this.visited = 			visited;
	}
	
	public String getName(){
		return this.stationName;
	}
	
	public String getExpectedArrival(){
		return this.expectedArrival;
	}
	
	public String getScheduledArrival(){
		return this.scheduledArrival;
	}
	
	public long getTimeDifference(){
		return this.timeDifference;
	}
	
	public String getExpectedPlatform(){
		return this.expectedPlatform;
	}
	
	public String getScheduledPlatform(){
		return this.scheduledPlatform;
	}
	
	public boolean isVisited(){
		return this.visited;
	}
	
	@Override
	public String toString() {
		return 	" \n|| stationName: " + this.stationName + 
				" \n|| visited: " + this.visited + 
				" \n|| scheduledArrival: " + this.scheduledArrival + 
				" \n|| expectedArrival: " + this.expectedArrival + 
				" \n|| timeDifference: " + this.timeDifference+ " min" + 
				" \n|| scheduledPlatform: " + this.scheduledPlatform + 
				" \n|| expectedPlatform: " + this.expectedPlatform + 
				" \n||||||||||||||||||||||||||||||||||||||||";
	}
}
