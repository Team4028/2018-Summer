package frc.team4028.robot;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;

//This class contains id values for the physical elements of the robot so we can use names 
//	in the code instead of hardcoded constants
public class RobotMap 
{
	// Drivers Station Gamepad USB Ports
	public static final int DRIVER_GAMEPAD_USB_PORT = 0;
	public static final int OPERATOR_GAMEPAD_USB_PORT = 1;
	public static final int ENGINEERING_GAMEPAD_USB_PORT = 2;
	public static final int ENGINEERING_GAMEPAD_B_USB_PORT = 3;
	
	// PCM Can Bus Address
	public static final int PCM_CAN_ADDR = 0;	
	
	// Talons Can Bus Address
	
	// DIO Ports
	
	// Analog Ports	
	public static final int STORED_PRESSURE_SENSOR_AIO_PORT = 0;
	
	// NavX (on Roborio)
	public static final SPI.Port NAVX_PORT = Port.kMXP;
	
	// PWM Ports on RoboRIO
	
	// PCM Ports
	
	// Logging
	// this is where the USB stick is mounted on the RoboRIO filesystem.  
	// You can confirm by logging into the RoboRIO using WinSCP
	public static final String PRIMARY_LOG_FILE_PATH = "/media/sda1/logging";
	public static final String ALTERNATE_LOG_FILE_PATH = "/media/sdb1/logging";
}
