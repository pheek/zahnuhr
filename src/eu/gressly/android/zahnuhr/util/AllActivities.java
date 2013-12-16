package eu.gressly.android.zahnuhr.util;

import java.util.ArrayList;
import java.util.List;

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
	
   public static PutzAlter SEQUENZ;

	private static ArrayList<Activity> registeredActivities;

	public static void registerActivity(Activity a) {
		if (null == registeredActivities) {
			registeredActivities = new ArrayList<Activity>();
		}
// while registering a new "SlideActivity", unregister the old one.
		SlideShowActivity oldActivity = AllActivities.getNewestSlideActivity();
		if(null != oldActivity) {
			//TODO: Test, if ok, without finishing the old one.
			// In other words: What happens, when we omit the following line?
			oldActivity.finish();
			AllActivities.unregister(oldActivity);
		}	
		
		registeredActivities.add(a);
	}

	public static List<Activity> getAllRegisteredActivities() {
		return registeredActivities;
	}

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

	public static void unregister(SlideShowActivity toRemove) {
		if(null == AllActivities.registeredActivities) {
		   return;
		}
		AllActivities.registeredActivities.remove(toRemove);
	}

} // end class AllActivities
