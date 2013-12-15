package eu.gressly.android.zahnuhr;

import eu.gressly.android.zahnuhr.activities.SlideShowActivity;

/**
 * 
 * @author phi@gressly.eu
 * @version Dez. 2013
 * 
 * calls back the view to refresh its content about every 1/4 second
 */
public class SlideShowRunner implements Runnable {

	SlideShowActivity slideShow        ;
	boolean           running          ;
	Thread            thisThread = null;

	public SlideShowRunner(SlideShowActivity slideShowActivity) {
		this.slideShow = slideShowActivity;
		System.out.println("Starting GUI-Thread in SlideShowRunner");
		start();
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
				slideShow.neuZeichnen();
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
		running = true;
	}

	public void stop() {
		running = false;
	}
} // end class SlideShowRunner
