package oose.thequickapp.controller;
import java.util.ArrayList;
import java.util.List;

import oose.thequickapp.R;
import oose.thequickapp.UI.ListViewAdapterForGetSaved2;
import oose.thequickapp.model.DatabaseOperations;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ViewSavedActivity is the activity to enable the user to view, edit the saved relations
 * or reset the whole database.
 * @author ewen
 *
 */
public class ViewSavedActivity extends Activity {

	ListView lv;
	Button deleteDatabase;
	Button deleteRelation;
	TextView selectedRelationIcon;
	Context context = this;
	PackageInfoGenerator packageInfoGenerator = new PackageInfoGenerator(this);
	String selectedRelation;
	Integer selectedRelationPosition;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try{
			setContentView(R.layout.activity_view_saved);
			lv = (ListView) findViewById(R.id.listViewForSaved);
			selectedRelationIcon = (TextView) findViewById(R.id.selectedRelationIcon);
			deleteRelation = (Button) findViewById(R.id.deleteRelation);
			selectedRelationIcon.setBackgroundResource(R.drawable.delete);

			List<String> appName = new ArrayList<String>();
			final List<String> customNames = new ArrayList<String>();
			List<String> patternType = new ArrayList<String>();
			final List<Drawable> appIcons = new ArrayList<Drawable>();

			DatabaseOperations dop = new DatabaseOperations(this);
			Cursor cr = dop.getInformation(dop);
			cr.moveToFirst();
			//		cr.moveToLast();
			do{
				customNames.add(cr.getString(0));
				patternType.add(cr.getString(1));
				appName.add(cr.getString(2));
				appIcons.add(packageInfoGenerator.iconRecongnize(cr.getString(2), packageInfoGenerator));
			}while(cr.moveToNext());
			//		ListViewAdapterForGetSaved adapter = new ListViewAdapterForGetSaved(this, R.layout.element_in_saved, appName.toArray(new String[appName.size()]), customNames.toArray(new String[customNames.size()]), patternType.toArray(new String[patternType.size()]));
			//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, customNames.toArray(new String[customNames.size()])); 
			final ListViewAdapterForGetSaved2 adapter = new ListViewAdapterForGetSaved2(this, R.layout.app_name_icon, customNames.toArray(new String[customNames.size()]), patternType.toArray(new String[patternType.size()]) ,appIcons.toArray(new Drawable[appIcons.size()]));
			lv.setAdapter(adapter);	
			lv.setOnItemClickListener(new OnItemClickListener() {

				@TargetApi(Build.VERSION_CODES.JELLY_BEAN) @Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position,
						long arg3) {
					selectedRelation = customNames.get(position);
					selectedRelationPosition = position;
					selectedRelationIcon.setBackground(appIcons.get(position));
					selectedRelationIcon.setText("X");
					selectedRelationIcon.setTextColor(Color.RED);
				}
			});
			
//			Cursor cr1 = dop.getInformationFromGridDB(dop);
//			cr1.moveToFirst();
//			for (int i = 1; i<=16; i++){
//				check.add(cr1.getInt(i));
//			}
//			GridTranslater translater = new GridTranslater();
//			Set<Position> aa = translater.translateToGrid(check);
//			for(Position p : aa){
//				Position p1 = p;
//			}

			

			deleteDatabase = (Button) findViewById(R.id.deleteDatabase);
			deleteDatabase.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder adb2 = new AlertDialog.Builder(ViewSavedActivity.this);
					adb2.setTitle("Reset all?");
					adb2.setMessage("Are you sure you want to delete the database?");
					adb2.setNegativeButton("No", null);
					adb2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							DatabaseOperations dop = new DatabaseOperations(context);
							dop.deleteDatabase(context);
							Toast.makeText(ViewSavedActivity.this, "All the stored data are deleted!", Toast.LENGTH_SHORT).show();
							Intent returnToMain = new Intent(ViewSavedActivity.this, MainActivity.class);
							startActivity(returnToMain);							
						}
					});
					adb2.show();

				}
			});

			deleteRelation.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					AlertDialog.Builder adb = new AlertDialog.Builder(ViewSavedActivity.this);
					adb.setTitle("Delete?");
					adb.setMessage("Are you sure you want to delete relation" + selectedRelation + " ?");
					adb.setNegativeButton("No", null);
					adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							DatabaseOperations dop = new DatabaseOperations(context);
							dop.deleteRows(dop, customNames.get(selectedRelationPosition));
							customNames.remove(selectedRelationPosition);
							appIcons.remove(selectedRelationPosition);
							adapter.notifyDataSetChanged();
							Toast.makeText(ViewSavedActivity.this, "The relation " + selectedRelation + "is deleted!", Toast.LENGTH_SHORT).show();
							Intent returnToMain = new Intent(ViewSavedActivity.this, MainActivity.class);
							startActivity(returnToMain);	
						}
					});
					adb.show();
				}
			});

		}catch(Exception e){
			setContentView(R.layout.activity_no_database_found);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	
	
}
