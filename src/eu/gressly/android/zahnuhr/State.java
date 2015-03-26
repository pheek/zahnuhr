package eu.gressly.android.zahnuhr;

/**
 * @author  phi (phi@gressly.eu)
 * @version alpha
 * @date    16. 12. 2013
 * 
 * @purpose Interface of the state. 
 *          To be sure, that all functions are called synchroneous, 
 *          this interface should be implemented as singleton.
 * @see     Updater, Callback
 */
 
import eu.gressly.android.zahnuhr.stati.PutzAlter;
import eu.gressly.android.zahnuhr.stati.PutzSchritt;

public interface State {
	/*
	 * General Timer functions.
	 */
	boolean     isRunning() ;
	void        restart()   ;
	void        stop()      ;
	void        pause()     ;
	boolean     isPaused()  ;
	void        resume()    ;

	/*
	 * State handling functions.
	 */
	void        setRemainingSecondsOverAll(float secs)         ;
	boolean     isGlobalTimeOver()                             ;
	float       getRemainingSecondsFromStartPositionActState() ;
	float       getRemainingSecondsActState()                  ;

	/*
	 * Teeth timer functions.
	 */
	PutzSchritt getActPutzSchritt()       ;
	void        setAlter(PutzAlter alter) ;
	PutzAlter   getAlter()                ;
	float       getTotalSecs()            ;
	void        nextInSequence()          ;
	boolean     isGongTime()              ;

} // end interfaces StateCallback