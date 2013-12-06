package eu.gressly.android.zahnuhr.util;

import eu.gressly.android.zahnuhr.R;
import eu.gressly.android.zahnuhr.SlideShowActivity;	
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PauseResumeButtonListener implements OnClickListener {
  SlideShowActivity slideShowActivity;
  
  public PauseResumeButtonListener(SlideShowActivity slideShowActivity) {
	  this.slideShowActivity = slideShowActivity;
  }

@Override
public void onClick(View v) {
	Button pauseResumeButton = (Button) slideShowActivity.findViewById(R.id.button_pause_resume);
  if(slideShowActivity.isPaused()) {
	 pauseResumeButton.setText(slideShowActivity.getResources().getText(R.string.pause));
	 slideShowActivity.resume();
  } else {
		 pauseResumeButton.setText(slideShowActivity.getResources().getText(R.string.resume));
		 slideShowActivity.pause();
  }
}
  
  
}
