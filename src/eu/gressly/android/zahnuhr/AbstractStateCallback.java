package eu.gressly.android.zahnuhr;

import eu.gressly.android.zahnuhr.stati.PutzAlter;
import eu.gressly.android.zahnuhr.stati.PutzSchritt;
import eu.gressly.util.callback.AbstractCallback;


/**
 * Wie das Interface, doch es implementiert die "Callback" Methoden
 * @version 0.1 (Mar 26, 2015)
 * @author Philipp Gressly Freimann 
 *         (phi@gressly.eu)
 */
public abstract class AbstractStateCallback extends AbstractCallback implements State {

	@Override
	public abstract boolean isRunning();

	@Override
	public abstract void restart() ;

	@Override
	public abstract void stop();

	@Override
	public abstract void pause();

	@Override
	public abstract boolean isPaused();

	@Override
	public abstract void resume();

	@Override
	public abstract void setRemainingSecondsOverAll(float secs);

	@Override
	public abstract boolean isGlobalTimeOver();

	@Override
	public abstract float getRemainingSecondsFromStartPositionActState();

	@Override
	public abstract float getRemainingSecondsActState();

	@Override
	public abstract PutzSchritt getActPutzSchritt();
	
	@Override
	public abstract void setAlter(PutzAlter alter);
	
	@Override
	public abstract PutzAlter getAlter();
	
	@Override
	public abstract float getTotalSecs();
	
	@Override
	public abstract void nextInSequence();
	
	@Override
	public abstract boolean isGongTime();
	
} // end of class AbstractStateCallbar