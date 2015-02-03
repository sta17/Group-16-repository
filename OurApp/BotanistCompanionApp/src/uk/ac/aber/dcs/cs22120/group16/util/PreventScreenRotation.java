package uk.ac.aber.dcs.cs22120.group16.util;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @author http://stackoverflow.com/questions/3723823/i-want-my-android-application-to-be-only-run-in-portrait-mode
 * @author Steven(Sta17)
 * 
 * from site, modified by me slightly
 * 
 */
public class PreventScreenRotation {

	public Activity acti;
	
	public PreventScreenRotation(Activity acti){
		this.acti = acti;
		initActivityScreenOrientPortrait();
	}
	
	private void initActivityScreenOrientPortrait() {
		// Avoid screen rotations (use the manifests android:screenOrientation
		// setting)
		// Set this to nosensor or potrait

		// Set window fullscreen
		acti.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		DisplayMetrics metrics = new DisplayMetrics();
		acti.getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);

		// Test if it is VISUAL in portrait mode by simply checking it's size
		boolean bIsVisualPortrait = (metrics.heightPixels >= metrics.widthPixels);

		if (!bIsVisualPortrait) {
			// Swap the orientation to match the VISUAL portrait mode
			if (acti.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				acti.setRequestedOrientation(
						ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			} else {
				acti.setRequestedOrientation(
						ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
		} else {
			acti.setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		}
}
}
