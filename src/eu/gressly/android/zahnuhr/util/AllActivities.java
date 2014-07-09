package eu.gressly.android.zahnuhr.util;

import java.util.ArrayList;

import eu.gressly.android.zahnuhr.activities.SlideShowActivity;
import eu.gressly.android.zahnuhr.stati.PutzAlter;

import android.app.Activity;

/**
 * 
 * @author phi@gress.ly
 * @version 1.0 Dez. 2013
 * 
 *          Collects all registered activities. This is used, if you want to
 *          close all activities after the last step.
 * 
 *          The "newestSlideShowActivity" is used to calculate the orientation
 *          of the actual layout.
 * 
 */
public class AllActivities {
	public final static String TAG="AllActivities";
	
    public static PutzAlter SEQUENZ;

	private static ArrayList<Activity> registeredActivities;

	public static void registerActivity(Activity a) {
		// lazy instantiation
		if (null == registeredActivities) {
			registeredActivities = new ArrayList<Activity>();
		}
		
		removeOtherActivitiesAfterTerminatingOrRelaunch(a);
		
		registeredActivities.add(a);
	}

	private static void removeOtherActivitiesAfterTerminatingOrRelaunch(Activity a) {
		@SuppressWarnings("unchecked")
		ArrayList<Activity> clone = (ArrayList<Activity>) registeredActivities.clone();
		for(Activity ac : clone) {
			if(a != ac) {
				registeredActivities.remove(ac);
		    	ac.finish();
			}
		}
	
//		if(a instanceof FinishScreenActivity) {
//			// remove all SlideShowActivities
//			@SuppressWarnings("unchecked")
//			ArrayList<Activity> clone = (ArrayList<Activity>) registeredActivities.clone();
//			for(Activity ac : clone) {
//				if(ac instanceof SlideShowActivity) {
//				  registeredActivities.remove(ac);
//				  ac.finish();
//				}
//			}
//		}
//		if(a instanceof StartMainActivity) {
//			@SuppressWarnings("unchecked")
//			ArrayList<Activity> clone = (ArrayList<Activity>) registeredActivities.clone();
//			for(Activity ac: clone) {
//				if(ac != a) {
//					registeredActivities.remove(ac);
//					ac.finish();
//				}
//			}
//		}
	}

//	public static List<Activity> getAllRegisteredActivities() {
//		return registeredActivities;
//	}
//
	public static SlideShowActivity getNewestSlideActivity() {
		if(null == registeredActivities) {
			return null;
		}
		
		SlideShowActivity newest = null;		
		for (Activity a : registeredActivities) {
			if (a instanceof SlideShowActivity) {
				newest = (SlideShowActivity) a;
			}
		}
		return newest;
	}
	

//	public static void unregister(SlideShowActivity toRemove) {
//		if(null == AllActivities.registeredActivities) {
//		   return;
//		}
//		AllActivities.registeredActivities.remove(toRemove);
//	}

} // end class AllActivities
