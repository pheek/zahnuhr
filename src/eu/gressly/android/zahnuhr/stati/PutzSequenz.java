package eu.gressly.android.zahnuhr.stati;

import eu.gressly.android.zahnuhr.R;

public class PutzSequenz {
   private float totalSecs  ;
         int   actPosIndex;
   
         private PutzSchritt[] stati; 
         
         private PutzSchritt[] statiJugendliche =
      	   {
      		   
      		   // debug data:
        	   new PutzSchritt(R.drawable.i_uv, R.string.i_uv, 5),
      		   new PutzSchritt(R.drawable.sf_r, R.string.sf_r, 5),
      		   new PutzSchritt(R.drawable.i_or, R.string.i_or, 5)

      	   };
         
   private PutzSchritt[] statiKinder =
	   {
		   
		   // debug data:
		   new PutzSchritt(R.drawable.kf_or, R.string.kf_or, 4),
		   new PutzSchritt(R.drawable.kf_ol, R.string.kf_ol, 4),
		   new PutzSchritt(R.drawable.kf_ur, R.string.kf_ur, 4)

		   // hot data:
//		   new PutzSchritt(R.drawable.kf_or, R.string.kf_or, 12),
//		   new PutzSchritt(R.drawable.kf_ol, R.string.kf_ol, 12),
//		   new PutzSchritt(R.drawable.kf_ur, R.string.kf_ur, 12),
//		   new PutzSchritt(R.drawable.kf_ul, R.string.kf_ul, 12),
//		   
//		   new PutzSchritt(R.drawable.sf_r, R.string.sf_r, 10),
//		   new PutzSchritt(R.drawable.sf_l, R.string.sf_l, 10),
//		   new PutzSchritt(R.drawable.sf_vo, R.string.sf_vo, 8),		   
//		   new PutzSchritt(R.drawable.sf_vu, R.string.sf_vu, 8),		   
//			   
//		   new PutzSchritt(R.drawable.i_or, R.string.i_or, 10),
//		   new PutzSchritt(R.drawable.i_ov, R.string.i_ov, 10),
//		   new PutzSchritt(R.drawable.i_ol, R.string.i_ol, 10),
//		   
//		   new PutzSchritt(R.drawable.i_ur, R.string.i_ur, 10),
//		   new PutzSchritt(R.drawable.i_uv, R.string.i_uv, 10),
//		   new PutzSchritt(R.drawable.i_ul, R.string.i_ul, 10)
	   };
   
   public PutzSequenz() {
	   //default mit setAlter überschreiben.
	   setAlter(PutzAlter.KINDER);
   }
   
   /*
   public PutzSequenz(PutzAlter alter) {
	   setAlter(alter);	   
   }*/
   
   public void setAlter(PutzAlter alter) {
	   switch(alter) {
	   case KINDER: stati = statiKinder; break;
	   case JUGENDLICHE: stati = statiJugendliche; break;
	   }
	   calcTotalSecs();
	   reset(); 
   }
   
   public void reset() {
	   actPosIndex = 0;
   }
   
   private void calcTotalSecs() {
     float total = 0;
     for(PutzSchritt ps : stati) {
    	 total += ps.seconds;
     }
     this.totalSecs =  total;  
   }
   
   public float getTotalSecs() {
	   return this.totalSecs;
   }
   
   public PutzSchritt getActPos() {
	   if(actPosIndex < stati.length) {
		   return stati[actPosIndex];
	   }
	   return null;
   }
   
   public void nextPos() {
	   actPosIndex++;
   }

   
   float getOverallSecondsBeforThisState() {
	 float overall = 0;
	 for(int pos = 0; stati[pos] != getActPos(); pos ++) {
		overall = overall + stati[pos].seconds;
	 }
	 return overall;
   }

public PutzAlter getAlter() {
	if(stati==statiKinder)       return PutzAlter.KINDER;
	if(stati==statiJugendliche)  return PutzAlter.JUGENDLICHE;
	
	// default:
	return PutzAlter.KINDER;
}
   
   
}
