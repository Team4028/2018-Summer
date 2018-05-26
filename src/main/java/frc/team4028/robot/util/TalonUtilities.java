package frc.team4028.robot.util;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

// this class contains general utility methods for TalonSRX Motor Controllers
public class TalonUtilities 
{
    public static void setPIDFGains(TalonSRX talon, double[] gains) 
    {
    	talon.config_kP(0, gains[0], 0);
		talon.config_kI(0, gains[1], 0);
		talon.config_kD(0, gains[2], 0);
		talon.config_kF(0, gains[3], 0);
    }
    
    public static void setMotionMagicConstants(TalonSRX talon, int[] constants) 
    {
    	talon.configMotionCruiseVelocity(constants[0], 0);
    	talon.configMotionAcceleration(constants[1], 0);
    }
}
