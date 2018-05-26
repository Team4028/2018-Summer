package frc.team4028.robot.interfaces;

import frc.team4028.robot.util.LogDataBE;

//interface that all classes that want to log data must implement
public interface ILogDataWriter 
{
	public void updateLogData(LogDataBE logData);
}
