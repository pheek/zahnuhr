package eu.gressly.android.zahnuhr.util;

import eu.gressly.android.zahnuhr.StateCallback;
import eu.gressly.android.zahnuhr.StateImplementation;
import eu.gressly.android.zahnuhr.activities.SlideShowActivity;
import eu.gressly.android.zahnuhr.stati.PutzAlter;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class StartClickListener implements OnClickListener {
    Activity myActivity;
    PutzAlter alter;
    
    public StartClickListener(PutzAlter alter) {
    	this.alter = alter;
    }
    
    public void setActivity(Activity a) {
    	this.myActivity = a;
    }
	@Override
	public void onClick(View v) {
		StateCallback sc = StateImplementation.getInstance();
		sc.setAlter(this.alter);
		sc.restart();
		Intent slideShowView = new Intent(myActivity.getApplicationContext(), SlideShowActivity.class);
        myActivity.startActivity(slideShowView);	
	}

}
