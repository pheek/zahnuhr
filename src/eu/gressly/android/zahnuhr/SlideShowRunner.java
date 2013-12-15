package eu.gressly.android.zahnuhr;

import eu.gressly.util.callback.Updater;


/**
 * 
 * @author phi@gressly.eu
 * @version Dez. 2013
 * 
 * calls back the view to refresh its content about every 1/4 second
 */
public class SlideShowRunner implements Runnable {

	private boolean           running          ;
	Thread            thisThread = null;
	Updater           callbackState;
    private static SlideShowRunner   singleton;
	
	private SlideShowRunner(Updater up) {
		this.callbackState = up;
		System.out.println("Starting GUI-Thread in SlideShowRunner");
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
		System.out.println("Starting runnable.");
		while (running) {
			try {
				// refresh rate of the sliders.
				Thread.sleep(250);
				System.out.println("SSR: running;");
				callbackState.update();
			} catch (InterruptedException ie) {
				running = false;
			}
		}
	}

	void start() {
		stop();
		System.out.println("SlideShowRunner.start()");
		if (null != thisThread) {
			thisThread.interrupt();
			thisThread = null;
		}
		thisThread = new Thread(this);
		thisThread.start();
		running = true;
	}
	
	public boolean isRunning() {
		return running;
	}

	public void stop() {
		running = false;
	}
} // end class SlideShowRunner
