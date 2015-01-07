package oose.thequickapp.UI;

import oose.thequickapp.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This is a custom list view adapter that adapts the app name and icon into a single
 * row in the list view
 * @author Group 12
 *
 */
public class ListViewAdapterForGetApp extends ArrayAdapter<String> {
	private final Context context;
	/**
	 * values reperesents the app names
	 */
	private final String[] values;
	/**
	 * images reperesents the app icons
	 */
	private final Drawable[] images;
	

	public ListViewAdapterForGetApp(Context context, int resource, String[] values, Drawable[] images) {
		super(context, resource, values);
		this.context = context;
		this.values = values;
		this.images = images;
		
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.app_name_icon, parent,false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values[position]);
		imageView.setImageDrawable(images[position]);
		return rowView;
	}
	
	



}
