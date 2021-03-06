package eu.gressly.android.zahnuhr.stati;
/**
 * @author  phi (phi@gressly.eu)
 * @version alpha
 * @date    16. 12. 2013
 * 
 * @purpose Info about one "Slide" in the Slideshow.
 * 
 */

public class PutzSchritt {
	private int    drawableID; // which image to paint
	private int    stringID  ; // which string to write
	private float  seconds   ; // seconds of this step


	public PutzSchritt(int drawableID, int stringID, int seconds) {
		this.drawableID = drawableID;
		this.stringID   = stringID  ;
		this.seconds    = seconds   ; 
	}


	public int getDrawableID() {
		return this.drawableID;
	}


	public int getStringID() {
		return this.stringID;
	}



	public float getSeconds() {
		return this.seconds;
	}

} // end class PutzSchritt