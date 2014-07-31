package eu.gressly.android.zahnuhr.util;
/**
 * @author  phi (phi@gressly.eu)
 * @version alpha
 * @date    16. 12. 2013
 * 
 * @purpose if "paused()", resume the timer, otherwise: pause the timer.
 */

import android.view.View;
import android.view.View.OnClickListener;
import eu.gressly.android.zahnuhr.activities.SlideShowActivity;

public class PauseResumeButtonListener implements OnClickListener {
	SlideShowActivity slideShowActivity;

	public PauseResumeButtonListener(SlideShowActivity slideShowActivity) {
		this.slideShowActivity = slideShowActivity;
	}


	/**
	 * Simple switch between "paused" and "active".
	 */
	@Override
	public void onClick(View v) {
		if(slideShowActivity.isPaused()) {
			slideShowActivity.resume();
		} else {
			slideShowActivity.pause();
		}

		slideShowActivity.setPausedResumeText();
	}

} // end class PauseResumeButton