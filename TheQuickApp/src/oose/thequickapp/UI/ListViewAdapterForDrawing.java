package oose.thequickapp.UI;

import java.util.ArrayList;

import oose.thequickapp.model.AppList;
import oose.thequickapp.model.PInfo;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * This class provides the adapter for the list view on power-on
 * user interface which shows the results of apps.
 * 
 * @author Cong Feng
 *
 */
public class ListViewAdapterForDrawing extends BaseAdapter {
	Context context;
	ArrayList<PInfo> installedApps;
	
	/**
	 * Creates the adapter by the context.
	 * @param context the context of activity where the adapter is constructed
	 */
	public ListViewAdapterForDrawing(Context context){
		this.context = context;
		this.installedApps = new ArrayList<PInfo>();
	}
	
	/**
	 * Sets the list to show on the list view.
	 * @param installedApps the information of installed apps
	 */
	public void setList(ArrayList<PInfo> installedApps){
		this.installedApps = installedApps;
	}
	
	/**
	 * Clears the list to show on the list view.
	 */
	public void clearList(){
		this.installedApps = new ArrayList<PInfo>();
	}
	
	@Override
	public int getCount() {
		return installedApps.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView iv = new ImageView(context);
		int id = AppList.getInstance().getAppsToShow().get(position).getId();
		iv.setImageDrawable(installedApps.get(id).icon);
		//iv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		return iv;
	}

}
