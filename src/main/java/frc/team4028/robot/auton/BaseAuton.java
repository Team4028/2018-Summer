package frc.team4028.robot.auton;

import frc.team4028.robot.interfaces.IAutonAction;

import edu.wpi.first.wpilibj.Timer;

public abstract class BaseAuton 
{
	protected boolean _active = false;
	protected double _startTime;
	
	/** This contains all the runAction methods in the auton */
	public abstract void routine();
	
	public void run() 
	{
		_active = true;
		routine();
	}
	
	public void start() 
	{
		_startTime = Timer.getFPGATimestamp();
	}
	
	public void stop() 
	{
		_active = false;
	}
	
	/** Runs an action until isFinished() returns true */
	public void runAction(IAutonAction action) 
	{
		action.start();
		while (_active && !action.isFinished()) 
		{
			action.update();
		}
		action.done();
	}
}
