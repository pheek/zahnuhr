package eu.gressly.android.zahnuhr;
/**
 * @author  phi (phi@gressly.eu)
 * @version 1.0
 * @date    16. 12. 2013
 * 
 * @purpose Implements the actual state (in sequence).
 *          Implement as singleton, so starting twice, will only show one
 *          activity.
 *          This is very important, because when you turn the tablet (phone),
 *          the method "onCreate()" is called again. 
 *          Without a singleton "implementation" like this, the runnable
 *          would restart every turn.
 *          
 *          Notice that this class is Updateable (and gets notifide by the SlideShowRunner)
 *          On the other hand, this Class updates the SlideShowActivity, when ever it gets updated by the SlideShowRunner.
 *          
 */

import android.util.Log;
import eu.gressly.android.zahnuhr.stati.PutzAlter;
import eu.gressly.android.zahnuhr.stati.PutzSchritt;
import eu.gressly.android.zahnuhr.stati.PutzSequenz;
import eu.gressly.util.callback.Updateable;


public class StateImplementation extends AbstractStateCallback implements Updateable {
	private static final String TAG = "StateImplementation";

	private SlideShowRunner slideShowRunner;
	private BackwardsTimer  timerOverall   ;
	private PutzSequenz     sequenz = null ; // must be set before switching to slideShow

	private static StateImplementation singleton;


	private StateImplementation() {
		this.slideShowRunner = SlideShowRunner.getInstance(this);
		this.timerOverall    = new BackwardsTimer();
		this.sequenz         = new PutzSequenz();
		this.sequenz.reset();
	}


	public static StateImplementation getInstance() {
		if(null == singleton) {
			StateImplementation.singleton = new StateImplementation();
		}
		return singleton;
	}


	@Override
	public boolean isRunning() {
		return slideShowRunner.isRunning();
	}


	@Override
	public void restart() {
		if(this.isPaused()) {this.resume();}
		slideShowRunner.stop();
		sequenz.reset();
		resetTimer();
		slideShowRunner.start();
	}


	private void resetTimer() {
		this.setRemainingSecondsOverAll(sequenz.getTotalSecs());
	}

	@Override
	public void stop() {
		slideShowRunner.stop();
		resetTimer();
		sequenz.reset();
	}


	@Override
	public void pause() {
		timerOverall.pause();
	}


	@Override
	public boolean isPaused() {
		return timerOverall.isPaused();
	}


	@Override
	public void resume() {
		timerOverall.resume();
	}


	@Override
	public void setRemainingSecondsOverAll(float secs) {
		timerOverall.setRemainingSeconds(secs);
	}


	@Override
	public float getRemainingSecondsActState() {
		float afterSecs  = sequenz.getTotalSecsAfterActState();
		return timerOverall.getRemainingSeconds() - afterSecs;
	}


	@Override
	public PutzSchritt getActPutzSchritt() {
		return sequenz.getActPos();
	}


	public void start(Updateable up) {
		slideShowRunner.start();
		Log.d(TAG, "Call start() in StateImplementation");
		this.addUpdateable(up);
	}
	

	@Override
	public void update() {
		updateAll();
	}


	@Override
	public void setAlter(PutzAlter alter) {
		this.sequenz.setAlter(alter);
	}


	@Override
	public PutzAlter getAlter() {
		return this.sequenz.getAlter();
	}


	@Override
	public float getTotalSecs() {
		return this.sequenz.getTotalSecs();
	}


	@Override
	public void nextInSequence() {
		sequenz.nextPos();
		//setRemainingSecondsActState(this.sequenz.getActPos().getSeconds());
	}


	@Override
	public boolean isGlobalTimeOver() {
		return this.timerOverall.getRemainingSeconds() <= 0.0f;
	}


	@Override
	public float getRemainingSecondsFromStartPositionActState() {
		return this.sequenz.getTotalSecsAfterActState() + this.sequenz.getActPos().getSeconds();
	}


	public static final float GONG_LEN = 2.5f;
	// ACHTUNG: Dies entspricht genau der Länge des Tons!
	@Override
	public boolean isGongTime() {
		if(! this.isRunning()) {return false;}
		float  actPosSeconds            = this.sequenz.getActPos().getSeconds();
		float  remainingSecondsActState = this.getRemainingSecondsActState(); 
		float  diff = actPosSeconds - remainingSecondsActState;
		return diff  < GONG_LEN;
	}

} // end class StateImplementation