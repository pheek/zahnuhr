package eu.gressly.android.zahnuhr;

/**
 * @author  phi (phi@gressly.eu)
 * @version alpha
 * @date    16. 12. 2013
 * 
 * @purpose A runner who calls every 200ms an "update()" Method, so a
 *          Progressbar can be repainted.
 */

import android.util.Log;
import eu.gressly.util.callback.AbstractCallback;
import eu.gressly.util.callback.Updateable;

/**
 * 
 * @author phi@gressly.eu
 * @version Dez. 2013
 * 
 * calls back the view to refresh its content about every 1/4 second
 */
public class SlideShowRunner extends AbstractCallback implements Runnable {

	private static final int REDRAW_MILLISECS = 100;

	private static final String TAG = "SlideShowRunner-Class";

	private boolean running          ;
	private Thread  thisThread = null;

	// Singleton Design Pattern
	private static  SlideShowRunner singleton;

	private SlideShowRunner(Updateable up) {
		this.addUpdateable(up);
	}


	public static SlideShowRunner getInstance(Updateable up) {
		if(null == singleton) {
			SlideShowRunner.singleton = new SlideShowRunner(up);
		}
		return singleton;
	}


	@Override
	public void run() {
		running = true;
		while (running) {
			try {
				// refresh rate of the sliders.
				Thread.sleep(REDRAW_MILLISECS);
				if(running) {
					Log.d(TAG, "updateAll from SlideShowRunner!");
					this.updateAll();
				}
			} catch (InterruptedException ie) {
				running = false;
			}
		}
	}


	void start() {
		stop();
		if (null != thisThread) {
			thisThread.interrupt();
			thisThread = null;
		}
		thisThread = new Thread(this);
		thisThread.start();
		//running = true;
	}


	public boolean isRunning() {
		return running;
	}


	public void stop() {
		running = false;
	}

} // end class SlideShowRunner