package eu.gressly.android.zahnuhr;
/**
 * @author  phi (phi@gressly.eu)
 * @version alpha
 * @date    16. 12. 2013
 * 
 * @purpose Implements the actual state (in sequence).
 *          Implement as singleton, so starting twice, will only show one
 *          activity.
 *          This is very important, because when you turn the tablet (phone),
 *          the method "onCreate()" is called again. 
 *          Without a singleton "implementation" like this, the runnable
 *          would restart every turn.
 */
import android.util.Log;
import eu.gressly.android.zahnuhr.stati.PutzAlter;
import eu.gressly.android.zahnuhr.stati.PutzSchritt;
import eu.gressly.android.zahnuhr.stati.PutzSequenz;
import eu.gressly.util.callback.Updater;

public class StateImplementation implements StateCallback {
    private static final String TAG = "StateImplementation";
    
	private Updater         updater        ;
	private SlideShowRunner slideShowRunner;
	private BackwardsTimer  timerOverall   ;
	private PutzSequenz     sequenz = null ; // must be set before switching to slideShwo
	
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
	public synchronized void setUpdater(Updater u) {
      this.updater = u;
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

//	@Override
//	public void setRemainingSecondsActState(float secs) {
//		timerActStep.setRemainingSeconds(secs);
//	}

	@Override
	public PutzSchritt getActPutzSchritt() {
		return sequenz.getActPos();
	}

	@Override
	public synchronized void update() {
		// delegate
		if(null == updater) {
			Log.e(TAG, "Fehlerin StateImplementation.update(): updater ist null!");
			return;
		}
		this.updater.update();
	}

	public void start(Updater up) {
		this.setUpdater(up);
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

} // end class StateImplementation
