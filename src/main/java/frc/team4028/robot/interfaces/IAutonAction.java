package frc.team4028.robot.interfaces;

public interface IAutonAction 
{
	public abstract void start();
	
	public abstract void update();
	
	public abstract void done();
	
	public abstract boolean isFinished();
}
