package frc.team4028.robot.sensors;

import frc.team4028.robot.Constants;
import frc.team4028.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;

// This class encapsulates interactions with the NavX Sensor
//	setup path to libraries using these instructions:
//	http://www.pdocs.kauailabs.com/navx-mxp/software/roborio-libraries/java/
//
//=====> For Changes see <TBD>
//-------------------------------------------------------------
//	Rev		By			D/T				Description
//	0		TomB		26-May-2018		Initial Version
//-------------------------------------------------------------
public class NavXGyro 
{
	//=====================================================================================
	// define class level working variables
	//=====================================================================================
	private AHRS _navXSensor;
	
	//=====================================================================================
	//Define Singleton Pattern
	//=====================================================================================
	private static NavXGyro _instance = new NavXGyro();
	
	public static NavXGyro getInstance() {
		return _instance;
	}
	
	private NavXGyro() 
	{
	     try 
	     {          
	     	_navXSensor = new AHRS(RobotMap.NAVX_PORT); // Communication via RoboRIO MXP (SPI) 
	     } 
	     catch (RuntimeException ex) 
	     {
	         DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
	     }
	}
	
	//=====================================================================================
	// Property Accessors
	//=====================================================================================
	public double getYaw() { 
		return _navXSensor.getYaw();
	}
	
	public void zeroYaw() { 
		_navXSensor.zeroYaw(); 
	}
	
	public double getRoll() {
		return _navXSensor.getPitch();
	}
	
	public boolean isPitchPastThreshhold() {
		return getRoll() >= Constants.MAX_PITCH_POSITIVE || getRoll() <= Constants.MAX_PITCH_NEGATIVE;
	}
}