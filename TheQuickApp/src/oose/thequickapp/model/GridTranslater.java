package oose.thequickapp.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import oose.thequickapp.model.TableData.TableInfo2;

/**
 * @author group12
 * This class connects the grid pattern to the sequence it represents.
 */
public class GridTranslater {
	public List<Integer> translateToDatabase(Set<Position> gridData){
		Integer[] temp = new Integer[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		for(Position p : gridData){
			int location = p.getX()+p.getY()*4;
			temp[location] = 1;
		}
		List<Integer> output =  Arrays.asList(temp);
		return output;
	}
	
	public Set<Position> translateToGrid(List<Integer> databaseData){
		Set<Position> output = new HashSet<Position>();
		Position p;
		int i = 0;
		for(int a : databaseData){
			if(a == 1){
				p = new Position(i-((i/4)*4), i/4);
				output.add(p);
			}
			i=i+1;
		}
		return output;
	}
	
	public String gridTableLocationTranslate(Integer i){
		String output = TableInfo2.L + i.toString();
		output = output + " Integer,";
		return output;
	}
	
	public String gridTableLocationTranslate2(Integer i){
		String output = TableInfo2.L + i.toString() + " ";
		return output;
	}

}
