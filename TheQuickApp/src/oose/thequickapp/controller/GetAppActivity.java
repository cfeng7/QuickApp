package oose.thequickapp.controller;

import java.util.ArrayList;
import java.util.List;

import oose.thequickapp.R;
import oose.thequickapp.UI.GridSettingModel;
import oose.thequickapp.UI.ListViewAdapterForGetApp;
import oose.thequickapp.model.PInfo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;



/**
 * GetAppActivity is the second step of the set up part. It shows the list of the apps
 * installed in the cell phone and allow user to select which app to be opened.
 * @author Cong Feng
 *
 */
public class GetAppActivity extends Activity {
	/**
	 * The list of app information installed on the cell phone
	 */
	ArrayList<PInfo> appList;

	/**
	 *  The Layout of the app info
	 */

	GridSettingModel gridModel;

	/**
	 * The message to be displayed when confirm button is pressed
	 */
	String confirmMessage;

	/**
	 * Represents if the selected app can be opened or not
	 */
	boolean isConfirmable;

	ImageView selectedAppView;
	Button confirmButton;
	PInfo selectedApp;
	PackageInfoGenerator packageInfoGenerator = new PackageInfoGenerator(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_app);
		Intent i = getIntent();
		if(i.getStringExtra("FromWhich").equals("GridSetActivity")){
			gridModel = (GridSettingModel) i.getSerializableExtra("TheModel");
			confirmButton = (Button) findViewById(R.id.button1);
			selectedAppView = (ImageView) findViewById(R.id.imageView1);
			populateListView();
			confirmButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {				
					if(isConfirmable){
						gridModel.setSavedNames(selectedApp.appname);
						Intent openNaming = new Intent(GetAppActivity.this, NamingActivity.class);
						openNaming.putExtra("TheModel2", gridModel);
						openNaming.putExtra("FromWhich", 1);
						startActivity(openNaming);
					}else{
						Toast.makeText(GetAppActivity.this, "The app cannot be opened! Please choose another app!", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}else{
			confirmButton = (Button) findViewById(R.id.button1);
			selectedAppView = (ImageView) findViewById(R.id.imageView1);
			//this.selectedAppView.set
			selectedAppView.setPadding(2,2,2,2);
			populateListView();
			final double threshold = i.getDoubleExtra("threshold", 0);
			System.err.println(threshold);

			confirmButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {				
					if(isConfirmable){
						Intent openNaming = new Intent(GetAppActivity.this, NamingActivity.class);
						openNaming.putExtra("threshold", threshold);
						openNaming.putExtra("appName", selectedApp.getAppname());
						openNaming.putExtra("FromWhich", 2);
						startActivity(openNaming);
					}else{
						Toast.makeText(GetAppActivity.this, "The app cannot be opened! Please choose another app!", Toast.LENGTH_SHORT).show();
					}
				}
			});

		}
	}
	/**
	 * This method get the applist, set up the ListView and the adapter
	 */
	private void populateListView() {
		appList = packageInfoGenerator.getInstalledApps(false);
		 ListView lv = (ListView) findViewById(R.id.TheListView);
		List<String> appNames = new ArrayList<String>();
		List<String> appLocations = new ArrayList<String>();
		List<String> appVersions = new ArrayList<String>();
		List<Drawable> appIcons = new ArrayList<Drawable>();
		for(PInfo pi : appList){
			appNames.add(pi.getAppname());
			appIcons.add(pi.getIcon());
			appLocations.add("<font color='#006600'>Locations: </font>"+"<font color='#000000'>"+pi.location+"</font>");
			appVersions.add("<font color='#006600'>Version: </font>"+"<font color='#000000'>"+pi.versionName+"</font>");
		}
		ListViewAdapterForGetApp adapter = new ListViewAdapterForGetApp(this, R.layout.app_name_icon, appNames.toArray(new String[appNames.size()]),
				appLocations.toArray(new String[appLocations.size()]),appVersions.toArray(new String[appVersions.size()]),appIcons.toArray(new Drawable[appIcons.size()]));
		lv.setAdapter(adapter);
		//adapter..setBackgroundResource(R.drawable.bgm2);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
                if (((ListView)parent).getTag() != null){                          
                 //   ((View)((ListView)parent).getTag()).setBackgroundDrawable(null);
                    ((View)((ListView)parent).getTag()).setBackgroundResource(R.drawable.bgm1); 
                   
               }
                
				  ((ListView)parent).setTag(view);
                  //view.setBackgroundColor(Color.RED); 
				  view.setBackgroundResource(R.drawable.bgm2);
                 
				if(appList.get(position).toOpen!=null){
					isConfirmable = true;
					selectedAppView.setImageDrawable(appList.get(position).getIcon());
					selectedApp = appList.get(position);

				}else{
					isConfirmable = false;
					selectedAppView.setImageDrawable(getResources().getDrawable(R.drawable.error));
					selectedApp = null;
				}
			}
		});
	}


}
