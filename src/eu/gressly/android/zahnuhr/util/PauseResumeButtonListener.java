package eu.gressly.android.zahnuhr.util;
/**
 * @author  phi (phi@gressly.eu)
 * @version alpha
 * @date    16. 12. 2013
 * 
 * @purpose if "paused()", resume the timer, otherwise: pause the timer.
 */
import eu.gressly.android.zahnuhr.R;
import eu.gressly.android.zahnuhr.activities.SlideShowActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
		Button pauseResumeButton = (Button) slideShowActivity
				.findViewById(R.id.button_pause_resume);
		CharSequence label;
		if (slideShowActivity.isPaused()) {
			label = slideShowActivity.getResources().getText(R.string.pause);
			slideShowActivity.resume();
		} else {
			label = slideShowActivity.getResources().getText(R.string.resume);
			slideShowActivity.pause();
		}
		pauseResumeButton.setText(label);
	}

} // end class PauseResumeButton
