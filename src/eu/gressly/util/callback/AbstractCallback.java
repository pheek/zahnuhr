package eu.gressly.util.callback;

import java.util.ArrayList;
import java.util.List;


/**
 * Implements the Callback
 * @version 0.1 (Mar 26, 2015)
 * @author Philipp Gressly Freimann 
 *         (philipp.gressly@santis.ch)
 */
public class AbstractCallback implements Callback {

	private List<Updateable> updaters = null;
  @Override
  public void addUpdateable(Updateable updater) {
  	if(null == updaters) {
  		updaters = new ArrayList<Updateable>();
  	}
  	if(! updaters.contains(updater)) {
  		updaters.add(updater);	
  	}
  }

  @Override
  public void updateAll() {
  	if(null == updaters) {
  		return; // nothing to do
  	}
  	List<Updateable> clonedList = new ArrayList<Updateable>(updaters);
  	for(Updateable u: clonedList) {
  		u.update();
  	}
  }

} // end of class AbstractCallback