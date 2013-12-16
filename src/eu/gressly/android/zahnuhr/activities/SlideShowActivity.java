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
import android.os.Bundle;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// besser gleich in AllActivities eine Methode: 
		AllActivities.registerActivity(this);

		// Hide title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Hide notification-bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		this.setContentView(R.layout.activity_slide_show);

		registerPauseButtonListener();
		
		// register this for callback
		StateCallback sc = StateImplementation.getInstance();
		sc.setUpdater(this);

	//	startProgress();
	}

	private void registerPauseButtonListener() {
		Button pauseResumeButton = (Button) findViewById(R.id.button_pause_resume);
		PauseResumeButtonListener prbl = new PauseResumeButtonListener(this);
		pauseResumeButton.setOnClickListener(prbl);
	}

	private void setProgress(int bar_rid, float actualSeconds, float maxSeconds) {
		ProgressBar pbOverAll = (ProgressBar) findViewById(bar_rid);
		pbOverAll.setProgress((int) (actualSeconds * 100.0 / maxSeconds));
		// pbOverAll.setBackgroundColor(Color.MAGENTA);
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
	
	private void neuZeichnen() {
		StateCallback state = StateImplementation.getInstance();
		if (state.getRemainingSecondsOverAll() <= 0.0) {
			state.stop();
			gotoViewFinished();
			return;
		}

		if (state.getRemainingSecondsActState() <= 0) {
			System.out.println("DEBUG: neuZeichnen(): remainingSeconds <= 0");
			
			state.nextInSequence();
			drawAndText(state.getActPutzSchritt());
			
		}
		paintingProgressBars();
	}



	private void paintingProgressBars() {
		StateCallback state = StateImplementation.getInstance();
		setProgress(R.id.progressBar_teilSchritt, state.getActPutzSchritt().getSeconds()
				- state.getRemainingSecondsActState(), state.getActPutzSchritt().getSeconds());
		setProgress(R.id.progressBar_overallProcess, state.getTotalSecs()
				- state.getRemainingSecondsOverAll(),state.getTotalSecs());
	}

	private void gotoViewFinished() {
		// switch to other view
		System.out
				.println("DEBUG: Go to other view (finished). stopping slideShowrunner!");
		StateImplementation.getInstance().stop();
		Intent finishedView = new Intent(getApplicationContext(),
				FinishScreenActivity.class);
		startActivity(finishedView);
	}

	private void drawAndText(final PutzSchritt paintedSchritt) {
		// from stackoverflow:
		if (null == paintedSchritt) {
			System.out.println("DEBUG SlideShowActivity drawAndText();");
			return;
		}

		SlideShowActivity.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				System.out.println("DEBUG runOnUiThread...");
				ImageView img = (ImageView) findViewById(R.id.startImage);
				System.out.println("... found drawableID: "
						+ paintedSchritt.getDrawableID());
				img.setImageResource(paintedSchritt.getDrawableID());

				System.out.println("DEBUG: drawAndText: replace Text...");
				TextView txt = (TextView) findViewById(R.id.textView_wo);
				txt.setText(getResources().getText(paintedSchritt.getStringID()));
			}
		});
	}

} // end class: SlideShowActivity
