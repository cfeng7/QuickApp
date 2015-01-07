package oose.thequickapp.controller;

import java.io.File;
import java.io.IOException;

import oose.thequickapp.R;
import oose.thequickapp.UI.GridSettingModel;
import oose.thequickapp.model.DatabaseOperations;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * NamingActivity is the activity to name a relation and after save button is pressed,
 * the relation will be saved to the database.
 * @author Group12
 *
 */
/**
 * @author ewen
 *
 */
public class NamingActivity extends Activity {

	EditText name;
	String theName;
	String appName;
	Double threshold;
	GridSettingModel gridModel;
	Button saveButton;
	Context ct = this;
	boolean namingDuplicate = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_naming);
		name = (EditText) findViewById(R.id.editText1);
		Intent i = getIntent();
		int a = i.getIntExtra("FromWhich", 0);
		if(a==1){
		gridModel = (GridSettingModel) i.getSerializableExtra("TheModel2");
		saveButton = (Button) findViewById(R.id.saveRelation);
		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DatabaseOperations dop = new DatabaseOperations(ct);
				theName = name.getText().toString();
				try{
				Cursor cr = dop.getInformation(dop);
				cr.moveToFirst();
				do{
					String temp= cr.getString(0);
					if(temp.equals(theName)){
						namingDuplicate = true;
					}
				}while(cr.moveToNext());
				}catch(Exception e){
				}
				saveRelationForGrid();
			}
		});
		}else{
			appName = i.getStringExtra("appName");
			threshold = i.getDoubleExtra("threshold", 0);
			System.err.println(threshold);

			saveButton = (Button) findViewById(R.id.saveRelation);
			saveButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					DatabaseOperations dop = new DatabaseOperations(ct);
					theName = name.getText().toString();
					try{
					Cursor cr = dop.getInformation(dop);
					cr.moveToFirst();
					do{
						String temp= cr.getString(0);
						if(temp.equals(theName)){
							namingDuplicate = true;
						}
					}while(cr.moveToNext());
					}catch(Exception e){
					}
					try {
						saveRelationForSound();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}); 
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.naming, menu);
		return true;
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

	/**
	 * This method saves the name of the the connection between grip pattern and app name.
	 */
	public void saveRelationForGrid() {
		if(namingDuplicate == false){
		DatabaseOperations dbo = new DatabaseOperations(ct);
		dbo.putInformation(dbo, theName, "Grid", gridModel.getSavedNames());
		dbo.putInformationToGridDB(dbo, theName, gridModel.getSavedPattern());
		startActivity(new Intent(NamingActivity.this, MainActivity.class));
		Toast.makeText(NamingActivity.this, "Relation saved!", Toast.LENGTH_SHORT).show();
		}else{
			namingDuplicate = false;
			Toast.makeText(NamingActivity.this, "The name has already exist", Toast.LENGTH_SHORT).show();	
		}
	}
	
	/**
	 * This method saves the name of the the connection between grip sound and app name.
	 */
	public void saveRelationForSound() throws IOException{
		if(namingDuplicate == false){
			DatabaseOperations dbo = new DatabaseOperations(ct);
			dbo.putInformation(dbo, theName, "Sound", appName);
			dbo.putInformationToSoundDB(dbo, theName, theName + ".pcm", threshold);
			startActivity(new Intent(NamingActivity.this, MainActivity.class));
			Toast.makeText(NamingActivity.this, "Relation saved!", Toast.LENGTH_SHORT).show();
			File from = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/re.pcm");
			File to = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+ theName + ".pcm");
//			to.createNewFile();
			from.renameTo(to);
			}else{
				namingDuplicate = false;
				Toast.makeText(NamingActivity.this, "The name has already exist", Toast.LENGTH_SHORT).show();	
			}
	}
}
