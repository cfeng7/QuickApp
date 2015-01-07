package oose.thequickapp.model;

import oose.thequickapp.controller.GridActivity;
import android.content.Context;
import android.content.Intent;

/**
 * ServiceModel determines which activity to start in service
 * @author yuyueqi
 *
 */
public class ServiceModel {
	private Intent toStart;
	public ServiceModel(Context context) {
		// TODO Auto-generated constructor stub
		this.toStart = new Intent(context, GridActivity.class);
	}
	public Intent getToStart() {
		return toStart;
	}
	public void setToStart(Intent toStart) {
		this.toStart = toStart;
	}
	

}
