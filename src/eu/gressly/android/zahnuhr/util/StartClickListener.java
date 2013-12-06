package eu.gressly.android.zahnuhr.util;

import eu.gressly.android.zahnuhr.SlideShowActivity;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class StartClickListener implements OnClickListener {
    Activity myActivity;
    
    public void setActivity(Activity a) {
    	this.myActivity = a;
    }
	@Override
	public void onClick(View v) {
		Intent slideShowView = new Intent(myActivity.getApplicationContext(), SlideShowActivity.class);
        myActivity.startActivity(slideShowView);	
	}

}
