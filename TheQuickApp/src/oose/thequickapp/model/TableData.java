package oose.thequickapp.model;

import android.provider.BaseColumns;

/**
 * It contains the definition informations of tables in the database
 * @author Group12
 *
 */
public class TableData {
	public TableData() {
		// TODO Auto-generated constructor stub
	}
	
	public static abstract class TableInfo1 implements BaseColumns{
		public static final String DATABSE_NAME = "saved_relations";
		public static final String APP_NAME = "app_name";
		public static final String PATTERN_TYPE = "pattern_type";
		public static final String RELATION_NAME = "relation_type";
		public static final String TABLE_NAME = "detailed_info";
	}
	
	public static abstract class TableInfo2 implements BaseColumns{
		public static final String DATABASE_NAME = "saved_relations";
		public static final String TABLE_NAME = "data_for_grid";
		public static final String ID = "id";
		public static final String L = "l";
		public static final String L1 = "l1";
		public static final String L2 = "l2";
		public static final String L3 = "l3";
		public static final String L4 = "l4";
		public static final String L5 = "l5";
		public static final String L6 = "l6";
		public static final String L7 = "l7";
		public static final String L8 = "l8";
		public static final String L9 = "l9";
	}
	
	public static abstract class TableInfo3 implements BaseColumns{
		public static final String DATABASE_NAME = "saved_relations";
		public static final String TABLE_NAME = "data_for_sound";
		public static final String ID = "id";
		public static final String SOUNDFILE = "sound_file";
		public static final String THRESHOLD = "threshold";
	}
	
	
}