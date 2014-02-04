package eu.gressly.android.zahnuhr;
import android.util.Log;
/**
 * @author  phi (phi@gressly.eu)
 * @version alpha
 * @date    16. 12. 2013
 * 
 * @purpose A runner who calls every 200ms an "update()" Method, so a
 *          Progressbar can be repainted.
 */
import eu.gressly.util.callback.Updater;

/**
 * 
 * @author phi@gressly.eu
 * @version Dez. 2013
 * 
 * calls back the view to refresh its content about every 1/4 second
 */
public class SlideShowRunner implements Runnable {
    private static final String TAG = "SlideShowRunner";
    
    private static final int REDRAW_MILLISECS = 100;
    
	private boolean running          ;
	private Thread  thisThread = null;
	private Updater callbackState    ;
    
	// Singleton Design Pattern
	private static  SlideShowRunner singleton;
	
	private SlideShowRunner(Updater up) {
		this.callbackState = up;
		Log.i(TAG, "Starting GUI-Thread in SlideShowRunner");
	}
	
	public static SlideShowRunner getInstance(Updater up) {
		if(null == singleton) {
		   SlideShowRunner.singleton = new SlideShowRunner(up);
		}
		return singleton;
	}

	@Override
	public void run() {
		running = true;
		Log.i(TAG, "Starting runnable.");
		while (running) {
			try {
				// refresh rate of the sliders.
				Thread.sleep(REDRAW_MILLISECS);
				//System.out.println("SSR: running;");
				if(running) {callbackState.update();}
			} catch (InterruptedException ie) {
				running = false;
			}
		}
	}

	void start() {
		stop();
		Log.i(TAG, "SlideShowRunner.start()");
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
