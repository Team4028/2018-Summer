package frc.team4028.robot.subsystems;

import frc.team4028.robot.interfaces.IDashboardWriter;
import frc.team4028.robot.interfaces.ILogDataWriter;
import frc.team4028.robot.interfaces.ISubsytem;
import frc.team4028.robot.util.LogDataBE;

public class Chassis implements ISubsytem, IDashboardWriter, ILogDataWriter
{
	// singleton pattern
	private static Chassis _instance = new Chassis();
	
	public static Chassis getInstance() {
		return _instance;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void zeroSensors() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void outputToDashboard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateLogData(LogDataBE logData) {
		// TODO Auto-generated method stub
		
	}
}
