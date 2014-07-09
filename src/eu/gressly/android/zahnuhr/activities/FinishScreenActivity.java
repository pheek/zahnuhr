package eu.gressly.android.zahnuhr.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import eu.gressly.android.zahnuhr.R;
import eu.gressly.android.zahnuhr.util.AllActivities;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class FinishScreenActivity extends Activity {
	private static final String TAG = "FinishScreenActivity";
	
	static boolean gongedForCurrentShow = false;
	
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
		
		registerOKButton();
	}
	
	@Override
	protected void onResume() { 
		super.onResume();
		gongPlayFinished();
	}
	
	/**
	 * Ende Gong 
   * @see gongPlay
	 */
	synchronized void gongPlayFinished() {
	  if(! gongedForCurrentShow) {
        MediaPlayer mPlayer = MediaPlayer.create(FinishScreenActivity.this, R.raw.gongende);
        FinishScreenActivity.gongedForCurrentShow = true;
	    mPlayer.start();
	  }
	}
	
	public static void allowEndeGong() {
		FinishScreenActivity.gongedForCurrentShow = false;
	}
	
	private void registerOKButton() {
		Button restartButton = (Button) findViewById(R.id.button_ok);
		 if(null == restartButton) {
			 Log.w(TAG, "FEHLER: Resource button_ok nicht gefunden!!!");
		 } else {
			restartButton.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
				  Intent startActivity = new Intent(FinishScreenActivity.this, StartMainActivity.class);
				  startActivity(startActivity);
			    }
			});
		 }
	}

} //end class FinishScreenActivity
