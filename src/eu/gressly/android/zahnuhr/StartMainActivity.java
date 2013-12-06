package eu.gressly.android.zahnuhr;



import eu.gressly.android.zahnuhr.util.AllActivities;
import eu.gressly.android.zahnuhr.util.StartClickListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

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
		Button startButton = (Button) findViewById(R.id.button_start);
        StartClickListener scl = new StartClickListener();
        scl.setActivity(this);
        startButton.setOnClickListener(scl);
	}

 

}
