package eu.gressly.android.zahnuhr.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import eu.gressly.android.zahnuhr.R;
import eu.gressly.android.zahnuhr.StateCallback;
import eu.gressly.android.zahnuhr.StateImplementation;
import eu.gressly.android.zahnuhr.util.AllActivities;
import eu.gressly.android.zahnuhr.util.StartClickListener;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class FinishScreenActivity extends Activity {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("DEBUG: Creating FinishScreenActivity");
		super.onCreate(savedInstanceState);
		
		AllActivities.registerActivity(this);
		
		//Hide title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Hide notification-bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
             
		setContentView(R.layout.activity_fertig);
		
		registerRestartZahnputzButton();
		
		registerQuitButton();
	}

	private void registerQuitButton() {
		Button quitButton = (Button) findViewById(R.id.button_quit);
		if(null == quitButton) {
			System.out.println("FEHLER: Resource button_quit nicht gefunden");
		} else {
			quitButton.setOnClickListener( new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					for(Activity a : AllActivities.getAllRegisteredActivities()) {
						System.out.println("Closing Activity: " + a);
						a.finish();
					}					
				}
				
			});
		}
		
	}
	private void registerRestartZahnputzButton() {
		Button restartButton = (Button) findViewById(R.id.button_restart);
		 if(null == restartButton) {
			 System.out.println("FEHLER: Resource button_restart nicht gefunden!!!");
		 } else {
			StateCallback sc = StateImplementation.getInstance();
	        StartClickListener scl = new StartClickListener(sc.getAlter());
	        scl.setActivity(this);
	        restartButton.setOnClickListener(scl);
		 }
	}


}
