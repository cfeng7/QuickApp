package oose.thequickapp.model;

/**
 * Application class model
 * @author ewen
 *
 */
public class App {
	/** the unique index of an application */
	private int id;
	/** the score of an application given a certain pattern*/
	private double score;
	
	/**
	 * Construct an application given an id.
	 * @param id
	 */
	public App(int id){
		this.id = id;
	}
		
	/**
	 * get the score
	 * @return score
	 */
	public double getScore() {
		return score;
	}
	
	/**
	 * set the score
	 * @param score
	 */
	public void setScore(double score) {
		this.score = score;
	}
	
	/**
	 * get the id
	 * @return id
	 */
	public int getId() {
		return id;
	}
}
