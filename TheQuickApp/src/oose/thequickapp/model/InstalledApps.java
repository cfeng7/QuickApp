package oose.thequickapp.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

/**
 * The class restored the information for all the installed apps
 * in phone and provide way to retrieve information.
 * 
 * @author zcy1848
 *
 */
public class InstalledApps {

	private Context context;
	
	/**
	 * Creates the class through one activity.
	 * @param context The context of a specific activity
	 */
	public InstalledApps(Context context){
		this.context = context;
	}
	
	/**
	 * Retrieves the information of all the information in phone and
	 * store the information.
	 * @param getSysPackages whether gets the information of system packages
	 * @return a list of package information of every app
	 */
	public ArrayList<PInfo> getInstalledApps(boolean getSysPackages){
		ArrayList<PInfo> res = new ArrayList<PInfo>();
		List<PackageInfo> packs = this.context.getPackageManager().getInstalledPackages(0);
		for(int i=0;i<packs.size();i++) {
			PackageInfo p = packs.get(i);
			if(!getSysPackages&&isSystemPackage(p)){
				continue;
			}
			PInfo newInfo = new PInfo();
			newInfo.appname = p.applicationInfo.loadLabel(this.context.getPackageManager()).toString();
			newInfo.pname = p.packageName;
			newInfo.icon = p.applicationInfo.loadIcon(this.context.getPackageManager());
			newInfo.toOpen = this.context.getPackageManager().getLaunchIntentForPackage(p.packageName);
			res.add(newInfo);
		}
		return res;
	}
	
	/**
	 * Determines whether a package is a system package.
	 * @param pkgInfo the information of a specific package
	 * @return whether the package is a system package
	 */
	private boolean isSystemPackage(PackageInfo pkgInfo){
		if((pkgInfo.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)!=0) return true;
		else return false;
	}
	
	
}
