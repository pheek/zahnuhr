package eu.gressly.android.zahnuhr;

public class PutzStatus {
   final float totalSecs  ;
         int   actPosIndex;
   
         
   PutzSchritt[] stati =
	   {
		   
		   // debug data:
//		   new PutzSchritt(R.drawable.kf_or, R.string.kf_or, 4),
//		   new PutzSchritt(R.drawable.kf_ol, R.string.kf_ol, 4),
//		   new PutzSchritt(R.drawable.kf_ur, R.string.kf_ur, 4),

		   // hot data:
		   new PutzSchritt(R.drawable.kf_or, R.string.kf_or, 12),
		   new PutzSchritt(R.drawable.kf_ol, R.string.kf_ol, 12),
		   new PutzSchritt(R.drawable.kf_ur, R.string.kf_ur, 12),
		   new PutzSchritt(R.drawable.kf_ul, R.string.kf_ul, 12),
		   
		   new PutzSchritt(R.drawable.sf_r, R.string.sf_r, 10),
		   new PutzSchritt(R.drawable.sf_l, R.string.sf_l, 10),
		   new PutzSchritt(R.drawable.sf_vo, R.string.sf_vo, 8),		   
		   new PutzSchritt(R.drawable.sf_vu, R.string.sf_vu, 8),		   
			   
		   new PutzSchritt(R.drawable.i_or, R.string.i_or, 10),
		   new PutzSchritt(R.drawable.i_ov, R.string.i_ov, 10),
		   new PutzSchritt(R.drawable.i_ol, R.string.i_ol, 10),
		   
		   new PutzSchritt(R.drawable.i_ur, R.string.i_ur, 10),
		   new PutzSchritt(R.drawable.i_uv, R.string.i_uv, 10),
		   new PutzSchritt(R.drawable.i_ul, R.string.i_ul, 10)
	   };
   
   public PutzStatus() {
	   totalSecs          = calcTotalSecs();
	   reset();
   }
   
   public void reset() {
	   actPosIndex = 0;
   }
   
   int calcTotalSecs() {
     int total = 0;
     for(PutzSchritt ps : stati) {
    	 total += ps.seconds;
     }
     return total;  
   }
   
   PutzSchritt getActPos() {
	   if(actPosIndex < stati.length) {
		   return stati[actPosIndex];
	   }
	   return null;
   }
   
   PutzSchritt nextPos() {
	   actPosIndex++;
	   return getActPos();
   }

   
   float getOverallSecondsBeforThisState() {
	 float overall = 0;
	 for(int pos = 0; stati[pos] != getActPos(); pos ++) {
		overall = overall + stati[pos].seconds;
	 }
	 return overall;
   }
   
   
}
