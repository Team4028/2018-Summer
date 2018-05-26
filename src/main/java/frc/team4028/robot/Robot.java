/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.team4028.robot;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import static java.util.Arrays.asList; 

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.team4028.robot.auton.AutonExecuter;
import frc.team4028.robot.sensors.PressureSensor;
import frc.team4028.robot.subsystems.Chassis;
import frc.team4028.robot.util.DataLogger;
import frc.team4028.robot.util.LogDataBE;
import frc.team4028.robot.util.Looper;
import frc.team4028.robot.util.MovingAverage;

// =========================================================
// This is the main class for the Robot
//	It contains all startup, init & periodic entry points
//=========================================================
public class Robot extends IterativeRobot
{
	private static final String ROBOT_NAME = "2018 SUMMER";
	
	// Subsystems
	private Chassis _chassis = Chassis.getInstance();
	
	// Main looper
	private Looper _enabledLooper = new Looper();
	
	// Sensors
	private PressureSensor _pressureSensor = PressureSensor.getInstance();
	
	// Other
	private DriverOperatorStation _dos = DriverOperatorStation.getInstance();
	private AutonExecuter _autonExecuter = null;
	private Dashboard _dashboard = Dashboard.getInstance();
	private DataLogger _dataLogger = null;
	
	// Class level working variables
	String _buildMsg = "?";
	String _fmsDebugMsg = "?";
 	long _lastDashboardWriteTimeMSec;
 	long _lastScanEndTimeInMSec;
 	MovingAverage _scanTimeSamples;
 	long _autonStartTime;
 	
 	// ================================================================
 	// Robot-wide initialization code should go here
 	// called once, each time the robot powers up or the roborio is reset
	 // ================================================================
	 @Override
	public void robotInit() 
	{
		// register each class that has data to write to the dashboard
		_dashboard.register(asList(_chassis, _pressureSensor));
		
		// register each class that has data to write to the data logger
		_dataLogger.register(asList(_chassis, _pressureSensor));
	}

	//region Autonomous Mode
	/** Called once, each time the robot enters autonomous mode. */
	@Override
	public void autonomousInit() 
	{
	}

	/** Called each loop (approx every 20mS) in autonomous mode */
	@Override
	public void autonomousPeriodic() 
	{
	}
	//endregion

	//region Teleop Mode
	/** Called once, each time the robot enters teleop mode. */
	@Override
	public void teleopInit() 
	{		
	}
	
	/** Called each loop (approx every 20mS) in telop mode */
	@Override
	public void teleopPeriodic() {
	}
	//#endregion

	//region Disabled Mode

	// ================================================================
	// called once, each time the robot enters disabled mode from
    // either a different mode or from power-on
	// ================================================================
	@Override
	public void disabledInit() {
		if (_autonExecuter != null) {
			_autonExecuter.stop();
		}
		_autonExecuter = null;
		
		_enabledLooper.stop();
		
		if (_dataLogger != null) {
			_dataLogger.close();
			_dataLogger = null;
		}
		
		stopAll();
	}

	// ================================================================
	// called each loop (approx every 20mS) in disabled mode
	// Note: outputs (ex: Motors, PWM, PCM etc are disabled
	//			but you can perform internal "reset state" actions
	// ================================================================
	@Override
	public void disabledPeriodic() 
	{
		stopAll();
		
		if (_dataLogger != null) {
			_dataLogger.close();
			_dataLogger = null;
		}
		
		_dashboard.updateAllData();
	}

	//endregion

	//region Test Mode
	
	@Override
	public void testInit() 
	{ 

	}
	
	/** This function is called periodically during test mode. */
	@Override
	public void testPeriodic() 
	{
	}
	
	//endregion
	
	//region Utility Methods

	/** Methods for Stopping All Motors on Every Subsystem (Every Subsystem w/ Motors needs a method here) */
	private void stopAll() {
		// add one entry for each subsystem
	}
	
	/** Method to Push Data to ShuffleBoard */
	private void outputAllToDashboard() {
		// limit spamming
    	long scanCycleDeltaInMSecs = new Date().getTime() - _lastScanEndTimeInMSec;
    	// add scan time sample to calc scan time rolling average
    	_scanTimeSamples.add(new BigDecimal(scanCycleDeltaInMSecs));
    	
    	if((new Date().getTime() - _lastDashboardWriteTimeMSec) > 100) {
    		// each subsystem should add a call to a outputToSmartDashboard method
    		// to push its data out to the dashboard

    		// add one entry for each subsystem
    		_dashboard.updateAllData();
    	
    		// write the overall robot dashboard info
	    	SmartDashboard.putString("Robot Build", _buildMsg);
	    	SmartDashboard.putString("FMS Debug Msg", _fmsDebugMsg);
	    	
	    	BigDecimal movingAvg = _scanTimeSamples.getAverage();
	    	DecimalFormat df = new DecimalFormat("####");
	    	SmartDashboard.putString("Scan Time (2 sec roll avg)", df.format(movingAvg) + " mSec");
    		// snapshot last time
    		_lastDashboardWriteTimeMSec = new Date().getTime();
    	}
    	
    	// snapshot when this scan ended
    	_lastScanEndTimeInMSec = new Date().getTime();
	}
	
	/** Method for Logging Data to the USB Stick plugged into the RoboRio */
	private void logAllData() { 
		// always call this 1st to calc drive metrics
    	if(_dataLogger != null) {    	
	    	// create a new, empty logging class
        	LogDataBE logData = new LogDataBE();
	    	
	    	// ask each subsystem that exists to add its data
	    	_chassis.updateLogData(logData);
	    	
	    	_dataLogger.WriteDataLine(logData);
    	}
	}

	//endregion
}
