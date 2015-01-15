package oose.thequickapp.model;

/**
 * This class stores the information of voice.
 * 
 * @author ewen
 *
 */
public class VoiceInfo {
	private String pname;
	private double distance;
	/**
	 * Creates the class to store information of voice.
	 * @param pname the package name which the voice is related to
	 * @param distance the distance of the voice
	 */
	public VoiceInfo(String pname, double distance) {
		this.pname = pname;
		this.distance = distance;
		
	}
	/**
	 * Retrieves the distance of a specific voice.
	 * @return the distance of the voice
	 */
	public double getDistance() {
		return distance;
	}
	/**
	 * Retrieves the package name of a specific voice.
	 * @return the package name of the voice
	 */
	public String getPname() {
		return pname;
	}
}
