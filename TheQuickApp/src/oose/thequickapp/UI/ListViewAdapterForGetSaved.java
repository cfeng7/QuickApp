package oose.thequickapp.UI;

import oose.thequickapp.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * This is a custom list view adapter that adapts the app name and icon into a single
 * row in the list view
 * @author Group 12
 *
 */
public class ListViewAdapterForGetSaved extends ArrayAdapter<String> {
	private final Context context;
	private final String[] app_name;
	private final String[] custom_names;
	private final String[] pattern_type;
	

	public ListViewAdapterForGetSaved(Context context, int resource, String[] app_name, String[] custom_names, String[] pattern_type) {
		super(context, resource);
		this.context = context;
		this.app_name = app_name;
		this.custom_names = custom_names;
		this.pattern_type = pattern_type;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.element_in_saved,parent,false);
//		TextView tvRelationName = (TextView) rowView.findViewById(R.id.relationName);
//		TextView tvPatternName = (TextView) rowView.findViewById(R.id.patternTypeName);
//		TextView tvAppName = (TextView) rowView.findViewById(R.id.appName);
//		tvRelationName.setText(custom_names[position]);
//		tvPatternName.setText(pattern_type[position]);
//		tvAppName.setText(app_name[position]);
		return rowView;
	}
	

}
