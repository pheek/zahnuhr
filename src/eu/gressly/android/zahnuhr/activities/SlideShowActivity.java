package eu.gressly.android.zahnuhr.activities;
/**
 * @author  phi (phi@gressly.eu)
 * @version alpha
 * @date    16. 12. 2013
 * 
 * @purpose Contains the images (slide show) of teeth.
 *          is updated (repainted) when the Callback calls "update()".
 */
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
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
	}

	@Override
	protected void onResume(){
		super.onResume();
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
		gongPlay();
		StateCallback stateCallback = StateImplementation.getInstance();
		stateCallback.setUpdater(this);
	}
	
	
	private void registerPauseButtonListener() {
		Button pauseResumeButton = (Button) findViewById(R.id.button_pause_resume);
		PauseResumeButtonListener prbl = new PauseResumeButtonListener(this);
		pauseResumeButton.setOnClickListener(prbl);
	}

	
	private void setProgress(int bar_rid, float actualSeconds, float maxSeconds) {
		ProgressBar actualProgressBar = (ProgressBar) findViewById(bar_rid);
		if(null == actualProgressBar) {
			Log.e(TAG, "Debug SlideShowActivity.setProgress(): set Progress has a NullPointer!!!!");
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
	
	
	private synchronized void neuZeichnen() {
		StateCallback state = StateImplementation.getInstance();
		if (state.isGlobalTimeOver()) {
			state.stop();
			gotoViewFinished();
			return;
		}

		if (state.getRemainingSecondsActState() <= 0) {
			state.nextInSequence();
			gongPlay();
		}
		
		drawAndText(state.getActPutzSchritt());
		paintingProgressBars();

	}
		
	/**
	 * Mit Musik geht alles besser.
	 */
	//private static boolean isGonging = false;
	MediaPlayer mPlayer = null;
	synchronized void gongPlay() {
		if(null == mPlayer) { // lazy instantiation
			mPlayer = MediaPlayer.create(SlideShowActivity.this, R.raw.gong);			
		}
		StateCallback state = StateImplementation.getInstance();
		Log.i(TAG, "Gong time " + state.isGongTime());
		if(state.isGongTime()) {
			if(!mPlayer.isPlaying()) {
				blackScreen();
				if(! isPaused()) {
					mPlayer.start();
				}
			}
		}
	}
	
	private void paintingProgressBars() {
		StateCallback state = StateImplementation.getInstance();
		// Nur laufen, wenn nicht "gonging"
		if(null != mPlayer && mPlayer.isPlaying()) {
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
		FinishScreenActivity.allowEndeGong();
		Intent finishedView = new Intent(getApplicationContext(), FinishScreenActivity.class);
		startActivity(finishedView);
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
				if(null != mPlayer && !mPlayer.isPlaying()) {
					ImageView img = (ImageView) findViewById(R.id.startImage);
					if(! isPaused()) {
						img.setImageResource(paintedSchritt.getDrawableID());
					}
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
		if (isPaused()) {
			relabel(R.string.resume, R.drawable.bordershape2);
		} else {
			relabel(R.string.pause,  R.drawable.bordershape);
		}
	}

	private void relabel(int stringResource, int drawableResource) {
		Button pauseResumeButton = (Button) findViewById(R.id.button_pause_resume);
		CharSequence label = getResources().getText(stringResource);
		setBackgroundVersionIndependant(drawableResource, pauseResumeButton);
		pauseResumeButton.setText(label);
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private void setBackgroundVersionIndependant(int drawableResource,
			Button pauseResumeButton) {

		int sdk = android.os.Build.VERSION.SDK_INT;
		if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			pauseResumeButton.setBackgroundDrawable(getResources().getDrawable(drawableResource));
		} else {
			pauseResumeButton.setBackground(getResources().getDrawable(drawableResource));
		}
	}
} // end class: SlideShowActivity