package eu.gressly.android.zahnuhr.util;
/**
 * @author  phi (phi@gressly.eu)
 * @version alpha
 * @date    16. 12. 2013
 * 
 * @purpose restarts the "runner" (and all timers) and switches to the
 *          "intent" SlideShowActivity. This should be the only place,
 *          where the sequence is restarted. 
 *          Restarting in "onCreate()" of the "SlideShowActivity" will
 *          restart the timer on all turns of the phone.
 */
import eu.gressly.android.zahnuhr.State;
import eu.gressly.android.zahnuhr.StateImplementation;
import eu.gressly.android.zahnuhr.activities.SlideShowActivity;
import eu.gressly.android.zahnuhr.stati.PutzAlter;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class StartClickListener implements OnClickListener {
	Activity  myActivity;
	PutzAlter alter     ;


	public StartClickListener(PutzAlter alter) {
		this.alter = alter;
	}


	public void setActivity(Activity a) {
		this.myActivity = a;
	}


	@Override
	public void onClick(View v) {
		State sc = StateImplementation.getInstance();
		sc.setAlter(this.alter);
		//sc.restart();
		Intent slideShowView = new Intent(myActivity.getApplicationContext(), SlideShowActivity.class);
		myActivity.startActivity(slideShowView);
		//TODO: Dies sollte erst im SlideShowActivity.setContentViewAnd StartProgress() 
		//      geschehen, doch dort habe ich das "Alter" nicht mehr zur Verfügung...

		sc.restart();
	}

} // end class StartClickListener