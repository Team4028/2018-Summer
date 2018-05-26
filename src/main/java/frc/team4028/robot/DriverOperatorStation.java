package frc.team4028.robot;

public class DriverOperatorStation 
{
	// singleton pattern
	private static DriverOperatorStation _instance = new DriverOperatorStation();

	public static DriverOperatorStation getInstance() {
		return _instance;
	}
}
