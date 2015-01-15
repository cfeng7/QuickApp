package oose.thequickapp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AppList{
	/** application list singleton*/
	private static AppList appList ;
	/** the list ordered from 1 to n*/
	private static final ArrayList<App> apps = new ArrayList<App>();
	/** the list used to show*/
	private ArrayList<App> appsToShow;
	
	/**
	 * private construction for singleton
	 * add applications into the list
	 * app id from 0 to size-1
	 */
	private AppList(int m){
		if(m<=0) return;
		for(int i=0;i<m;i++){
			int id = i;
			apps.add(new App(id));
		}
	}

	/**
	 * get singleton class
	 * @param m parameters to initialize instance
	 * @return singleton
	 */
	public static AppList getInstance(int m){		
		if(appList==null) appList = new AppList(m);
		return appList;
	}
	
	/**
	 * get singleton class
	 * @return singleton
	 */
	public static AppList getInstance(){
		return appList;
	}
	
	/**
	 * get apps size
	 * @return the number of apps
	 */
	public int getSize() {
		return apps.size();
	}
	
	/**
	 * Given an application index, get an application model.
	 * @param id
	 * @return an application model.
	 */
	public App get(int id){
		return apps.get(id);
	}
	
	/**
	 * get top apps used to show
	 * @return top apps
	 */
	public ArrayList<App> getAppsToShow() {
		return appsToShow;
	}

	/**
	 * set app scores by predicted values
	 * @param predictedY
	 */
	public void setAppScores(double[] predictedY){
		if(predictedY==null || predictedY.length!=apps.size()) return;
		int m = predictedY.length;
		for(int i=0;i<m;i++){
			apps.get(i).setScore(predictedY[i]);
		}
		sortApps();
	}
	
	/**
	 * sort apps by app scores
	 */
	private void sortApps(){
		appsToShow = new ArrayList<App>(apps);
		Collections.sort(appsToShow, new AppComparator());
	}
	
	/**
	 * compare any two application
	 * @author niuxiang
	 */
	private class AppComparator implements Comparator<App>{
		@Override
		public int compare(App app1, App app2) {
			if(app1.getScore()<app2.getScore()) {return 1;}				//changed
			else if(app1.getScore()>app2.getScore()) {return -1;}		//changed
			else {return app1.getId()-app2.getId();}					//changed
		}
	}
}
