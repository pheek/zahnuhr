package eu.gressly.android.zahnuhr.stati;

public class PutzSchritt {
  public int    drawableID;
  public int    stringID  ;
  public float  seconds   ;
  
  public PutzSchritt(int drawableID, int stringID, int seconds) {
	 this.drawableID = drawableID;
	 this.stringID   = stringID  ;
	 this.seconds    = seconds   ; 
  }
  
}
