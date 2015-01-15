package oose.thequickapp.model;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * This class simply represent the useful information of an app
 * @author Group 12
 *
 */
public class PInfo {
	public String appname = "";
	public String pname = "";
	public String versionName = "";
	public String location = "";
	public int versionCode = 0;
	public Drawable icon;
	public Intent toOpen;
	public String getAppname() {
		return appname;
	}
	public String getPname() {
		return pname;
	}
	public int getVersionCode() {
		return versionCode;
	}
	public Drawable getIcon() {
		return icon;
	}
}
