package eu.gressly.util.callback;

/**
 * @author  phi (phi@gressly.eu)
 * @version alpha
 * @date    16. 12. 2013
 * 
 * @purpose Same as java.util.Observer/Observable, but here only a single updater can be
 *          registered. Advantage: There is no "hasChanged()" method. As soon the Callback
 *          is updated (via "update()"), its registerd Updater is updated too.
 * @see     Updater
 */
 
public interface Callback extends Updater {	
	void setUpdater(Updater u);

	@Override
	void update();
} // end interface Callback