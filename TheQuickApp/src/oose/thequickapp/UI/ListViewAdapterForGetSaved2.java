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
public class ListViewAdapterForGetSaved2 extends ArrayAdapter<String> {
	private final Context context;
	/**
	 * values reperesents the app names
	 */
	private final String[] relationName;
	/**
	 * images reperesents the app icons
	 */
	private final String[] relationIcon;
	
	
	private final Drawable[] appIcon;
	

	public ListViewAdapterForGetSaved2(Context context, int resource, String[] relationName, String[] relationIcon, Drawable[] appIcon) {
		super(context, resource, relationName);
		this.context = context;
		this.relationName = relationName;
		this.relationIcon = relationIcon;
		this.appIcon = appIcon;
		
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.element_in_saved, parent,false);
		TextView textView = (TextView) rowView.findViewById(R.id.label1);
		ImageView imageView1 = (ImageView) rowView.findViewById(R.id.logo1);
//		ImageView imageView2 = (ImageView) rowView.findViewById(R.id.logo2);
		ImageView imageView3 = (ImageView) rowView.findViewById(R.id.logo3);
		textView.setText(relationName[position]);
		if(relationIcon[position].equals("Grid")){
		imageView1.setImageResource(R.drawable.grid);
		}else{
			imageView1.setImageResource(R.drawable.sound);
		}
//		imageView2.setImageResource(R.drawable.link);
		imageView3.setImageDrawable(appIcon[position]);
		return rowView;
	}
	
	



}

