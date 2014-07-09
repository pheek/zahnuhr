package eu.gressly.android.zahnuhr;
/**
 * @autor phi@gressly.eu
 * @version 2. Dez. 2013 V1.0
 * 
 * A simple second based timer.
 */

public class BackwardsTimer {
	private long endTimePoint;
	private long pauseSince; // 0: running

	public void setRemainingSeconds(float secs) {
		long now          = System.currentTimeMillis();
		this.endTimePoint = (long) (now + 1000.0 * secs);
	}
	
	public float getRemainingSeconds() {
		if(isPaused()) {
			return secondsFromLong(endTimePoint - pauseSince);
		}
		long now      = System.currentTimeMillis();
		long diff     = this.endTimePoint - now;
		float  diffSecs = secondsFromLong(diff);
		return diffSecs;
	}

	private float secondsFromLong(long millis) {
		long abs  = Math.abs(millis);
		float  secs = (float) (((float)abs / 1000.0)); 
		return secs * ((int) Math.signum(millis));
	}

	public void pause() {
		this.pauseSince = System.currentTimeMillis();
	}

	public void resume() {
		long pauseLengthMillis = System.currentTimeMillis() - pauseSince;
		this.endTimePoint = this.endTimePoint + pauseLengthMillis;
		this.pauseSince = 0;
	}

	public boolean isPaused() {
		return 0 != this.pauseSince;
	}

} // end class ZahnTimer