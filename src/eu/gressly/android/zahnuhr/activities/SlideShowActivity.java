package eu.gressly.android.zahnuhr.activities;
/**
 * @author  phi (phi@gressly.eu)
 * @version alpha
 * @date    16. 12. 2013
 * 
 * @purpose Contains the images (slide show) of teeth.
 *          is updated (repainted) when the Callback calls "update()".
 */
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import eu.gressly.android.zahnuhr.R;
import eu.gressly.android.zahnuhr.StateCallback;
import eu.gressly.android.zahnuhr.StateImplementation;
import eu.gressly.android.zahnuhr.stati.PutzSchritt;
import eu.gressly.android.zahnuhr.util.AllActivities;
import eu.gressly.android.zahnuhr.util.PauseResumeButtonListener;
import eu.gressly.util.callback.Updater;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class SlideShowActivity extends Activity implements Updater {
	private static final String TAG = "SlideShowActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// avoid screen dimming:
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		// besser gleich in AllActivities eine Methode: 
		AllActivities.registerActivity(this);

		// Hide title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Hide notification-bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentViewAndStartProgress();
	}

	@Override
	public void onBackPressed() {
    	StateCallback sc = StateImplementation.getInstance();
    	sc.stop();  
    	super.onBackPressed();
	}
	
	// to repaint after a screen turn (rotation)
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		Log.i(TAG, "onConfigurationchanged()");
		super.onConfigurationChanged(newConfig);	
		
		setContentViewAndStartProgress();
		// sofort neu zeichnen, und nicht auf den Repaint-Thread warten:
		this.update();
		setPausedResumeText();
	}
	
	
	void setContentViewAndStartProgress() {
		this.setContentView(R.layout.activity_slide_show);
		registerPauseButtonListener();
		// register this for callback
		StateCallback sc = StateImplementation.getInstance();
		sc.setUpdater(this);
	}
	
	
	private void registerPauseButtonListener() {
		Button pauseResumeButton = (Button) findViewById(R.id.button_pause_resume);
		PauseResumeButtonListener prbl = new PauseResumeButtonListener(this);
		pauseResumeButton.setOnClickListener(prbl);
	}

	
	private void setProgress(int bar_rid, float actualSeconds, float maxSeconds) {
		ProgressBar actualProgressBar = (ProgressBar) findViewById(bar_rid);
		if(null == actualProgressBar) {
			Log.e(TAG, "set Progress has a NullPointer!!!!");
			return;
		}
		actualProgressBar.setProgress((int) (actualSeconds * 100.0 / maxSeconds));
	}

	
	public void pause() {
		StateImplementation.getInstance().pause();
	}

	
	public void resume() {
		StateImplementation.getInstance().resume();
	}

	
	public boolean isPaused() {
		return StateImplementation.getInstance().isPaused();
	}

	
	@Override
	public void update() {
		this.neuZeichnen();
	}
	
	
	private boolean isGonging = false;
	private synchronized void neuZeichnen() {
		StateCallback state = StateImplementation.getInstance();
		if (state.isGlobalTimeOver()) {
			state.stop();
			gotoViewFinished();
			return;
		}

		if (state.getRemainingSecondsActState() <= 0) {
			state.nextInSequence();
		}
		
		drawAndText(state.getActPutzSchritt());
		paintingProgressBars();

		if(state.isGongTime() && !isGonging) {
			isGonging = true;
			blackScreen();
			gongPlay();
		} 
		if(!state.isGongTime()) {
			isGonging = false;
		}
		
	}
	
	
	/**
	 * Mit Musik geht alles besser.
	 */
	synchronized void gongPlay() {
		MediaPlayer mPlayer = MediaPlayer.create(SlideShowActivity.this, R.raw.gong);
		mPlayer.start();	
	}

	
	
	private void paintingProgressBars() {
		StateCallback state = StateImplementation.getInstance();
		// Nur laufen, wenn nicht "gonging"
		if(isGonging) {
			// Solange der Gong läuft, ist der "Bar" auf null (0)
			setProgress(R.id.progressBar_teilSchritt, 0, state.getActPutzSchritt().getSeconds());
		} else {			
		  // Zeit etwas manipulieren, denn der Gong benötigt ja auch seine Zeit.
		  // Die Maximalzeit sollte dennoch nicht überschritten werden.
		  // Ob am Anfang, oder am Ende des Gonges die Seite gewechselt wird, spielt somit
	      // keine Rolle.
		  setProgress(R.id.progressBar_teilSchritt, state.getActPutzSchritt().getSeconds() - StateImplementation.GONG_LEN
				- state.getRemainingSecondsActState(), state.getActPutzSchritt().getSeconds() - StateImplementation.GONG_LEN);
		}

		// Nur pro bild wechseln
		setProgress(R.id.progressBar_overallProcess, state.getTotalSecs()
				- state.getRemainingSecondsFromStartPositionActState(), state.getTotalSecs());
	}

	
	private void gotoViewFinished() {
		StateImplementation.getInstance().stop();
		Intent finishedView = new Intent(getApplicationContext(), FinishScreenActivity.class);
    gongPlayFinished();
		startActivity(finishedView);
	}

	/**
	 * Ende Gong 
   * @see gongPlay
	 */
	synchronized void gongPlayFinished() {
		try {
			MediaPlayer mPlayer = MediaPlayer.create(SlideShowActivity.this, R.raw.gongende);
			mPlayer.start();
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private void drawAndText(final PutzSchritt paintedSchritt) {
		// from stackoverflow:
		if (null == paintedSchritt) {
			Log.i(TAG, "DEBUG SlideShowActivity drawAndText(): paintenSchritt == NULL !");
			return;
		}

		SlideShowActivity.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if(!isGonging) {
			    	ImageView img = (ImageView) findViewById(R.id.startImage);
				    img.setImageResource(paintedSchritt.getDrawableID());
				    
				    TextView txt = (TextView) findViewById(R.id.textView_wo);
				    txt.setText(getResources().getText(paintedSchritt.getStringID()));
				}
			}
		});
	}
	
	
	/**
	 * Hier wird ein anderes Bild eingefügt, damit der Wechsel zwischen den Bildern klar wird.
	 * Dies geschieht zusätzlich zum "Gong".
	 */
	void blackScreen() {
		SlideShowActivity.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView) findViewById(R.id.startImage);
				img.setImageResource(R.drawable.blackout);
				
				StateCallback state = StateImplementation.getInstance();
			    TextView txt = (TextView) findViewById(R.id.textView_wo);
			    txt.setText(getResources().getText(state.getActPutzSchritt().getStringID()));
			}
		});
	}
	
	
	public void setPausedResumeText() {
		Button pauseResumeButton = (Button) findViewById(R.id.button_pause_resume);
		CharSequence label;
		
		if (isPaused()) {
			label = getResources().getText(R.string.resume);
			pauseResumeButton.setBackground(getResources().getDrawable(R.drawable.bordershape2));
		} else {
			label = getResources().getText(R.string.pause);
			pauseResumeButton.setBackground(getResources().getDrawable(R.drawable.bordershape));
		}
		pauseResumeButton.setText(label);
	}

	
} // end class: SlideShowActivity
