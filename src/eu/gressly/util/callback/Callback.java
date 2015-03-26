package eu.gressly.util.callback;

/**
 * @author  phi (phi@gressly.eu)
 * @version alpha
 * @date    16. 12. 2013
 * 
 * @purpose Same as java.util.Observer/Observable, but here only a single updater can be
 *          registered. Advantage: There is no "hasChanged()" method. As soon the Callback
 *          is updated (via "update()"), its registerd Updater is updated too.
 * @see     Updateable
 */
 
/*TODO: Let this class be the implementation and the StateImplementation (eu.gressly.android.zahnuhr) 
 *      should only delegate to this class and not implement this interface.
 */
public interface Callback {	
	void addUpdateable(Updateable u);

	public void updateAll();
} // end interface Callback