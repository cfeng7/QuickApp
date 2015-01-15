package oose.thequickapp.model;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;

/**
 * This class defines a model for the data transmission between
 * file system and database of phone with power-on user
 * interface.
 * 
 * @author ewen
 *
 */
public class VoiceModel {
	private static VoiceModel model;
	
	/**
	 * Get the only one instance of the class.
	 * @return the only one instance of the class
	 */
	public static VoiceModel getInstance(){
		if(model==null) model = new VoiceModel();
		return model;
	}
	
	/**
	 * Retrieves all the information of the voice and related packages.
	 * @param context the context of activity in which the model is constructed
	 * @return all the information of the voice and related packages
	 */
	public Map<String, VoiceInfo> getFileMap(Context context){
		Map<String, VoiceInfo> map = new HashMap<String, VoiceInfo>();
		DatabaseOperations dop = new DatabaseOperations(context);
		Cursor cursor = dop.getInformationFromSoundDB(dop);
		cursor.moveToFirst();
		do{
			String fileName = cursor.getString(1);
			Double threshold = cursor.getDouble(2);
			String Pname = "null";
			
			DatabaseOperations dop1 = new DatabaseOperations(context);
			Cursor cr1 = dop.getInformation(dop1);
			cr1.moveToFirst();
			do{
				if(cr1.getString(0).equals(cursor.getString(0))){
					Pname = cr1.getString(2);
				}
			}while(cr1.moveToNext());

			VoiceInfo vi = new VoiceInfo(Pname, threshold);
			map.put(fileName, vi);
		}while(cursor.moveToNext());
		return map;
	}
}
