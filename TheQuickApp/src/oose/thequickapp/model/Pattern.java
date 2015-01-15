package oose.thequickapp.model;

/**
 * Pattern interface, to represent a gesture that a user performed.
 * @author niuxiang
 *
 */
public interface Pattern {
	/**
	 * transform a pattern into an array of double values
	 * @return an array of double values
	 */
	public abstract double[] getDoubleValues();
}
