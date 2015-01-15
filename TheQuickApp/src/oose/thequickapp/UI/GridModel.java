package oose.thequickapp.UI;

import java.util.HashSet;
import java.util.Set;

import oose.thequickapp.model.Position;

/**
 * This is the model used by 4*4 board. It stores the information
 * about which positions have been touched and the set pattern by
 * the user.
 * 
 * @author Cong Feng
 *
 */
public class GridModel {
	private Set<Position> positions;
	private Set<Position> res;
	


	/**
	 * This constructs the model.
	 */
	public GridModel(){
		positions = new HashSet<Position>();
//		Position pa = new Position(0, 1);
//		Position pb = new Position(1, 1);
//		Position pc = new Position(2, 1);
//		Position pd = new Position(3, 1);
		res = new HashSet<Position>();
//		res.add(pa); res.add(pb);
//		res.add(pc); res.add(pd);
	}
	
	/**
	 * Retrieves whether a position has been touched.
	 * @param x the x coordinate of the position
	 * @param y the y coordinate of the position
	 * @return whether the position has been touched
	 */
	public boolean check(int x, int y){
		return this.positions.contains(new Position(x, y));
	}
	
	/**
	 * Adds a position to the set of touched positions
	 * @param x the x coordinate of the position
	 * @param y the y coordinate of the position
	 */
	public void add(int x, int y){
		this.positions.add(new Position(x, y));
	}
	
	/**
	 * Clears the state of the touched positions
	 */
	public void clear(){
		this.positions.clear();
	}
	
	/**
	 * Determines whether the touched positions match the pattern
	 * set by user.
	 * @return
	 */
	public boolean isMatch(){
		if(this.positions.size()!=this.res.size()) return false;
		for(Position p: this.positions)
			if(!this.res.contains(p))
				return false;
		return true;
	}
	
	public void setRes(Set<Position> res) {
		this.res = res;
	}
}
