package oose.thequickapp.model;

import java.util.ArrayList;

public class FeedBack {
	/** the id of an app that the user clicked on*/
	private int correctAppId = -1;
	/** the id of a top app that the user did not click on*/
	private int maxIncorrectAppId = -1;
	
	/**
	 * @param position the position in the list view the user clicks on
	 */
	public FeedBack(int position){		//changed
		int correctAppId = AppList.getInstance().getAppsToShow().get(position).getId();	//added
		this.correctAppId = correctAppId;
		findMaxIncorrectYid();
	}
	
	/**
	 * get the correct app id
	 * @return correct app id
	 */
	public int getCorrectAppId(){
		return correctAppId;
	}
	
	/**
	 * get the top incorrect app id
	 * @return incorrect app id
	 */
	public int getInCorrectAppId(){
		return maxIncorrectAppId;
	}
	
	/**
	 * find the top incorrect app id by correct app id
	 */
	private void findMaxIncorrectYid(){
		ArrayList<App> topApps = AppList.getInstance().getAppsToShow();
		for(App app : topApps){
			if(app.getId()==correctAppId) { continue;}
			maxIncorrectAppId = app.getId();
			return;
		}
	}
}
