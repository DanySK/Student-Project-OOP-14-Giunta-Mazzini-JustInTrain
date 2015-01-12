package com.example.lisamazzini.train_app;

import java.util.List;

public class Train {

	private final String category;
	private final String id;
	private final int delay;
	private final List<Station> stationList;
	private 	  boolean pinned; 
	
	public Train(String category, String id, int delay, List<Station> stationList) {
		this.category = category;
		this.id = id;
		this.delay = delay;
		this.stationList = stationList;
		this.pinned = false;
	}
	
	public Train(String category, String id, int delay) {
		this.category = category;
		this.id = id;
		this.delay = delay;
		this.pinned = false;
		this.stationList = null; 			// LA MORTE NERA
	}
	
	public Train(Train train, List<Station> stationList) {
		this.category = train.getCategory();
		this.id = train.getId();
		this.delay = train.getDelay();
		this.stationList = stationList;
		this.pinned = train.isPinned();
	}
	
	public String getCategory(){
		return this.category;
	}
	
	public String getId(){
		return this.id;
	}
	
	public int getDelay(){
		return this.delay;
	}
	
	public boolean isPinned(){
		return this.pinned;
	}
	
	public List<Station> getStationList(){
		return this.stationList;
	}
	
	public void pin(){
		this.pinned = true; 
	}
}