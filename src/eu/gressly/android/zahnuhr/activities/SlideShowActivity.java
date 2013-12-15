package eu.gressly.android.zahnuhr.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import eu.gressly.android.zahnuhr.PutzSchritt;
import eu.gressly.android.zahnuhr.PutzStatus;
import eu.gressly.android.zahnuhr.R;
import eu.gressly.android.zahnuhr.SlideShowRunner;
import eu.gressly.android.zahnuhr.util.AllActivities;
import eu.gressly.android.zahnuhr.util.PauseResumeButtonListener;
import eu.gressly.android.zahnuhr.util.ZahnTimer;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class SlideShowActivity extends Activity {
   
	ZahnTimer ztOverall;
	ZahnTimer ztLocal;
	
	SlideShowRunner slideShowRunner;
	PutzStatus putzStatus;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AllActivities.registerActivity(this);
		
		//Hide title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Hide notification-bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        this.setContentView(R.layout.activity_slide_show);
		ztOverall = new ZahnTimer();
		ztLocal   = new ZahnTimer();
		
		// any value > 0 is ok. otherwise it stopps before starting and reading the hot data times.
		ztOverall.setRemainingSeconds(100);
		ztLocal  .setRemainingSeconds(100);
		
		putzStatus = new PutzStatus();   
	    
	    registerPauseButtonListener();

        startProgress();
        
	}
	
	private void registerPauseButtonListener() {
		Button pauseResumeButton = (Button) findViewById(R.id.button_pause_resume);
        PauseResumeButtonListener prbl = new PauseResumeButtonListener(this);
        pauseResumeButton.setOnClickListener(prbl);
	}
	
	void setProgress(int bar_rid, float actualSeconds, float maxSeconds) {
		 ProgressBar pbOverAll = (ProgressBar) findViewById(bar_rid);
		 pbOverAll.setProgress( (int) (actualSeconds * 100.0 / maxSeconds));
		 //pbOverAll.setBackgroundColor(Color.MAGENTA);
	}
	
	void startProgress() {
		slideShowRunner = new SlideShowRunner(this);
	}
	
	public void pause() {
		ztLocal   . pause();
		ztOverall . pause();
	}
	
	public void resume() {
		ztLocal   . resume();
		ztOverall . resume();
	}
	
	public boolean isPaused() {
		return ztLocal.isPaused() && ztOverall.isPaused();
	}
	
	//TODO
	// starting: dies muss gezügelt werden, sobald der "SlideShowRunner" gezügelt wird.
	PutzSchritt paintedSchritt = null;
	boolean     justStarted    = true;

	public void neuZeichnen() {
		if(ztOverall.getRemainingSeconds() <= 0.0) {
		   gotoViewFinished();
		   return;
		}
		if(justStarted) {
			justStarted = false;
			ztOverall.setRemainingSeconds(putzStatus.totalSecs);
			paintedSchritt = putzStatus.getActPos();
			if(null != paintedSchritt) {
				System.out.println("DEBUG: neuZeichnen(): null==paintedSchritt");
				ztLocal  .setRemainingSeconds(paintedSchritt.seconds);
				drawAndText(paintedSchritt);
			} else {
				gotoViewFinished();
				return;
			}
		}
		if(ztLocal.getRemainingSeconds() <= 0) {
		    System.out.println("DEBUG: neuZeichnen(): remainingSeconds <= 0");
			paintedSchritt = putzStatus.nextPos();
			if(null == paintedSchritt) {
				// finished
				gotoViewFinished();
				return;
			}
			ztLocal  .setRemainingSeconds(paintedSchritt.seconds);
			drawAndText(paintedSchritt);
		}

		paintingProgressBars();
	}

	private void paintingProgressBars() {
		setProgress(R.id.progressBar_teilSchritt,   
				    paintedSchritt.seconds - ztLocal.getRemainingSeconds(), 
				    paintedSchritt.seconds);
		setProgress(R.id.progressBar_overallProcess, 
				    putzStatus.totalSecs - ztOverall.getRemainingSeconds(), putzStatus.totalSecs);
	}
	

	private void gotoViewFinished() {
		//switch to other view
		System.out.println("DEBUG: Go to other view (finished). stopping slideShowrunner!");
		slideShowRunner.stop();
		Intent finishedView = new Intent(getApplicationContext(), FinishScreenActivity.class);
        startActivity(finishedView);	
	}

	private void drawAndText(final PutzSchritt paintedSchritt) {
		// from stackoverflow:
		if(null == paintedSchritt) {
			System.out.println("DEBUG SlideShowActivity drawAndText();");
			return;
		}
		
	    SlideShowActivity.this.runOnUiThread(new Runnable() {
	    	@Override 
	    	public void run() {
	    		System.out.println("DEBUG runOnUiThread...");
	    		ImageView img = (ImageView) findViewById(R.id.startImage);
	    		System.out.println("... found drawableID: " + paintedSchritt.drawableID);
	    		img.setImageResource(paintedSchritt.drawableID);	
	    		
	    		System.out.println("DEBUG: drawAndText: replace Text...");
	    		TextView txt = (TextView) findViewById(R.id.textView_wo);
	    		txt.setText(getResources().getText(paintedSchritt.stringID));		
	    	}
	    });
	}



}
