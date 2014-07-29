package eu.gressly.android.zahnuhr.util;
/**
 * @author  phi (phi@gressly.eu)
 * @version alpha
 * @date    16. 12. 2013
 * 
 * @purpose if "paused()", resume the timer, otherwise: pause the timer.
 */

import eu.gressly.android.zahnuhr.activities.SlideShowActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class PauseResumeButtonListener implements OnClickListener {
	private static final String TAG = "PauseResumeButtonListener";
	SlideShowActivity slideShowActivity;

	public PauseResumeButtonListener(SlideShowActivity slideShowActivity) {
		this.slideShowActivity = slideShowActivity;
	}


	/**
	 * Simple switch between "paused" and "active".
	 */
	@Override
	public void onClick(View v) {
		Log.i(TAG, "Pause/Weiter geklickt...");
		
		if(slideShowActivity.isPaused()) {
			slideShowActivity.resume();
		} else {
			slideShowActivity.pause();
		}

		slideShowActivity.setPausedResumeText();
	}

} // end class PauseResumeButton