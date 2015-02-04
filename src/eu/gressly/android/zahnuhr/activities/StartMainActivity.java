package eu.gressly.android.zahnuhr.activities;
/**
 * @author  phi (phi@gressly.eu)
 * @version 1
 * @date    16. 12. 2013
 * 
 * @purpose Start main Activity.
 *          important: The button event is responsable, to restart the activity.
 */

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import eu.gressly.android.zahnuhr.R;
import eu.gressly.android.zahnuhr.StateCallback;
import eu.gressly.android.zahnuhr.StateImplementation;
import eu.gressly.android.zahnuhr.stati.PutzAlter;
import eu.gressly.android.zahnuhr.util.AllActivities;
import eu.gressly.android.zahnuhr.util.StartClickListener;
// project specific

public class StartMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AllActivities.registerActivity(this);
		hideTitleAndNotificationbar();
		setContentView(R.layout.activity_start_zahnuhr);
		buttonTexteLos();
		setStartButonHandler();
	}


	// switch to the browser. "Uni" = Link to university of Zurich
	public void onUniClick(View v) {
		//TODO: URL auslagern
		Intent zahnInstitutImBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.zzm.uzh.ch/patienten/downloads/mb-kinder.html"));
		startActivity(zahnInstitutImBrowser);	
	}


	/**
	 * Hier werden die Texte "Kinder ab 10 Jahern" mit dem Text "Los"
	 * verknüpft. Dazwischen kommt ein Doppelpunkt und am Schluss ein Ausrufezeichnen.
	 */
	private void buttonTexteLos() {
		losText(R.id.button_start_kinder     , R.string.kinder     );
		losText(R.id.button_start_jugendliche, R.string.jugendliche);
	}


	/**
	 * Zusammensetzen von "Kinder bis 10 Jahre" + ": " + "Los"
	 */
	private void losText(int rButtonID, int rTextID) {
		Button startButton = (Button) findViewById(rButtonID);
		String losText     = (String) getText(rTextID) + ":\n" + getText(R.string.los_text) + "!";
		if(null != startButton && null != losText) {
			startButton.setText(losText);
		}
	}


	@Override
	protected void onPostResume() {
		StateCallback sc = StateImplementation.getInstance();
		sc.stop();  

		super.onPostResume();
	}


	private void hideTitleAndNotificationbar() {
		//Hide title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//Hide notification-bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}


	private void setStartButonHandler() {
		configStartButtonHandler(R.id.button_start_kinder     , PutzAlter.KINDER     );
		configStartButtonHandler(R.id.button_start_jugendliche, PutzAlter.JUGENDLICHE);
	}


	private void configStartButtonHandler(int rID, PutzAlter pa) {
		Button             startButton =  (Button) findViewById(rID);
		StartClickListener scl         =  new StartClickListener(pa);

		scl.setActivity(this);
		startButton.setOnClickListener(scl);
	}

} // end class StartMainActivity