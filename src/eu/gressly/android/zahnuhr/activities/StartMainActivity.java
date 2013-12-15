package eu.gressly.android.zahnuhr.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import eu.gressly.android.zahnuhr.R;
import eu.gressly.android.zahnuhr.stati.PutzAlter;
import eu.gressly.android.zahnuhr.util.AllActivities;
import eu.gressly.android.zahnuhr.util.StartClickListener;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class StartMainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AllActivities.registerActivity(this);
        hideTitleAndNotificationbar();

        setStartButonHandler();
        
    }

	private void hideTitleAndNotificationbar() {
		//Hide title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Hide notification-bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_start_zahnuhr);
	}

	private void setStartButonHandler() {
		Button startButton = (Button) findViewById(R.id.button_start_kinder);
        StartClickListener scl = new StartClickListener(PutzAlter.KINDER);
        scl.setActivity(this);
        startButton.setOnClickListener(scl);
        
        // TODO: Behandle jugendliche anders
        startButton = (Button) findViewById(R.id.button_start_jugendliche);
        scl = new StartClickListener(PutzAlter.JUGENDLICHE);
        scl.setActivity(this);
        startButton.setOnClickListener(scl);
	}

 

}
