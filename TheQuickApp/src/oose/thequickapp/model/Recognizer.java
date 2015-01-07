package oose.thequickapp.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import android.content.Context;
import android.os.Environment;

/**
 * This class achieves the functionality to determines whether one sound can
 * match another sound stored in phone.
 * 
 * @author zcy1848
 *
 */
public class Recognizer {
	/**
	 * Determines whether the sound can match another sound stored in phone.
	 * @param filename the file name of the sound
	 * @param context the context of the activity where the class is constructed
	 * @return the app name which is related to the sound matched, null if not found
	 */
	public static String analyze(String filename, Context context){
		float[] f1 = convertFrom(filename);
		Map<String, VoiceInfo> map = VoiceModel.getInstance().getFileMap(context);
		if(map==null||map.isEmpty()) return null;
		String file = null;
		double min = Double.MAX_VALUE;
		for(String s: map.keySet()){
			float[] f2 = convertFrom(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + s);
			DTW dtw = new DTW(f1, f2);
			double tmp = dtw.getDistance();
			if((tmp-min)<0){
				min = tmp;
				file = s;
			}
		}
		if(file==null) return null;
		double dis = map.get(file).getDistance();
		if(min/dis>2) return null;
		else return map.get(file).getPname();
	}
	
	/**
	 * Converts to float array from a sound file.
	 * @param filename the name of the sound file
	 * @return the converted float array, null if not succeeds
	 */
	public static float[] convertFrom(String filename){
		try {
			byte[] data;
			InputStream inputStream = new FileInputStream(filename);
			data = new byte[inputStream.available()];
			inputStream.read(data);
			float[] res = new float[data.length/4];
			for(int i=0;i<res.length;i++){
				long tmp = data[i*4];
				for(int j=1;j<4;j++) tmp = (tmp<<8)+data[i*4+j];
				res[i] = tmp;
			}
			float[] x = new float[res.length/8];
			for(int i=0;i<x.length;i++) x[i] = res[i*8];
			return x;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
