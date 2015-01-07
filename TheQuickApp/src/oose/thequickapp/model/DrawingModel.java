package oose.thequickapp.model;

/**
 * This class records the location information when user draws on the palette
 * on the power-on user interface.
 * 
 * @author zcy1848
 *
 */
public class DrawingModel {
	public final int N = 20;
	private double[][] b;
	
	/**
	 * This creates a model for the palette on the power-on interface
	 */
	public DrawingModel(){
		b = new double[N][N];
	}
	
	/**
	 * Retrieve the location information on a specific location
	 * @param x	the X-coordinate of the location
	 * @param y the Y-coordinate of the location
	 * @return the location information
	 */
	public double get(int x, int y){
		return b[x][y];
	}
	
	/**
	 * Retrieve all the location information on the palette
	 * @return location information array of the palette
	 */
	public double[][] getArray(){
		return this.b;
	}
	
	/**Set a specific location to be have drawn
	 * @param x the X-coordinate of the location
	 * @param y the Y-coordinate of the location
	 */
	public void set(int x, int y){
		b[x][y] = 1.0;
	}
	
	/**
	 * Clear the location information in the model
	 */
	public void clear(){
		b = new double[N][N];
	}
}
