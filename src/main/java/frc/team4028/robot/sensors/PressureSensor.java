package frc.team4028.robot.sensors;

import frc.team4028.robot.RobotMap;
import frc.team4028.robot.util.GeneralUtilities;
import frc.team4028.robot.util.LogDataBE;
import frc.team4028.robot.interfaces.IDashboardWriter;
import frc.team4028.robot.interfaces.ILogDataWriter;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//This class implements all functionality for the onboard pressure sensor
//=====> For Changes see <TBD>
//-------------------------------------------------------------
//	Rev		By			D/T				Description
//	0		TomB		26-May-2018		Initial Version
//-------------------------------------------------------------
public class PressureSensor implements IDashboardWriter, ILogDataWriter
{
	//=====================================================================================
	// define class level working variables
	//=====================================================================================
	private AnalogInput _pressureSensor;
	
	//=====================================================================================
	// Define Singleton Pattern
	//=====================================================================================
	private static PressureSensor _instance = new PressureSensor();
	
	public static PressureSensor getInstance() {
		return _instance;
	}
	
	// private constructor for singleton pattern
	private PressureSensor() {
		_pressureSensor = new AnalogInput(RobotMap.STORED_PRESSURE_SENSOR_AIO_PORT);
	}
	
	//=====================================================================================
	// Property Accessors
	//=====================================================================================
	public double getPressure() {
		double voltage = _pressureSensor.getAverageVoltage();
		double pressure = 250*(voltage/4.9) -25;
		return GeneralUtilities.roundDouble(pressure, 2);
	}
	
	//=====================================================================================
	// Utility Methods
	//=====================================================================================
	@Override
	public void updateLogData(LogDataBE logData) 
	{
		logData.AddData("Stored Pressure (PSI)", String.valueOf(getPressure()));
	}

	@Override
	public void outputToDashboard() 
	{
		SmartDashboard.putNumber("Stored Pressure (PSI)", getPressure());
	}
}
