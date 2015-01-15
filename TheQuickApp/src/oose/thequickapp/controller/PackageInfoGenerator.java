package oose.thequickapp.controller;

import java.util.ArrayList;
import java.util.List;

import oose.thequickapp.model.PInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;


/**
 * PackageInfoGenerator generates package info and have the ability to find the app intent
 * or app Icon given the app's name;
 * @author Group12
 *
 */
public class PackageInfoGenerator {
	private Context context;

	public PackageInfoGenerator(Context context) {
		this.context = context;
	}

	/**
	 * This method returns the package information within an arraylist with type PInfo
	 * @param getSysPackages "false represents no system packages"
	 * @return
	 */
	ArrayList<PInfo> getInstalledApps(boolean getSysPackages) {
		ArrayList<PInfo> res = new ArrayList<PInfo>();        
		List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(0);
		for(int i=0;i<packs.size();i++) {
			PackageInfo p = packs.get(i);
			if ((!getSysPackages) && isSystemPackage(p)) {
				continue ;
			}
			PInfo newInfo = new PInfo();
			newInfo.appname = p.applicationInfo.loadLabel(context.getPackageManager()).toString();
			newInfo.location = p.applicationInfo.sourceDir;
			//newInfo.appname = p.versionName;
			newInfo.pname = p.packageName;
			newInfo.versionName = p.versionName;
			newInfo.versionCode = p.versionCode;
			newInfo.icon = p.applicationInfo.loadIcon(context.getPackageManager());
			newInfo.toOpen = context.getPackageManager().getLaunchIntentForPackage(p.packageName);
			res.add(newInfo);

		}
		return res; 
	}
	private boolean isSystemPackage(PackageInfo pkgInfo) {
		return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true
				: false;
	}
	
	Drawable iconRecongnize(String appName, PackageInfoGenerator packageInfoGenerator){
		Drawable appIcon = null;
		for (PInfo pi : packageInfoGenerator.getInstalledApps(false)){
			if (appName.equals(pi.appname)){
				appIcon = pi.getIcon();
			}
		}
		return appIcon;
	}
	
	Intent intentRecongnize(String appName, PackageInfoGenerator packageInfoGenerator){
		Intent theIntent = null;
		for (PInfo pi : packageInfoGenerator.getInstalledApps(false)){
			if (appName.equals(pi.appname)){
				theIntent = pi.toOpen;
			}
		}
		return theIntent;
	}
}