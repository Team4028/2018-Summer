package frc.team4028.robot.interfaces;

public interface ILoop 
{
	public void onStart(double timestamp);
	
	public void onLoop(double timestamp);
	
	public void onStop(double timestamp);
}
