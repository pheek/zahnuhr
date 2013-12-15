package eu.gressly.android.zahnuhr.util;

import java.util.ArrayList;
import java.util.List;

import eu.gressly.android.zahnuhr.activities.SlideShowActivity;

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

	private static ArrayList<Activity> registeredActivities;

	public static void registerActivity(Activity a) {
		if (null == registeredActivities) {
			registeredActivities = new ArrayList<Activity>();
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

} // end class AllActivities
