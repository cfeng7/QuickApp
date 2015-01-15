package oose.thequickapp.model;

import java.io.Serializable;

/**
 * This class describe the position that is
 * touched on the board of the screen. A series
 * of positions will form a pattern to be matched.
 * 
 * @author Group12
 *
 */
public class Position implements Serializable {

	private int x;
	private int y;

	/**
	 * Creates a new position at the specified coordinates.
	 * @param x the X-coordinate on the board
	 * @param y the Y-coordinate on the board
	 */
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Retrieves the X-coordinate of the position
	 * @return the X-coordinate of the position
	 */
	public int getX(){
		return this.x;
	}
	
	/**
	 * Retrieves the Y-coordinate of the position
	 * @return the Y-coordinate of the position
	 */
	public int getY(){
		return this.y;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int hashCode() {
		return this.x+this.y*17;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o==null) return false;
		Position p = (Position)o;
		return this.x==p.x&&this.y==p.y;
	}
}
