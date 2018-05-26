package frc.team4028.robot;

import java.util.ArrayList;
import java.util.List;

import frc.team4028.robot.auton.BaseAuton;
import frc.team4028.robot.auton.modes.DoNothing;
import frc.team4028.robot.interfaces.IDashboardWriter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *  This class contains code to interface with the Dashboard on the Driver's Station
 *	We read values from 
 *		- Sendable Choosers to control Auton
 *		- FMS Data for real time game data
 *  We write values to
 *		- provide real-time info to the drive team
 */
public class Dashboard 
{
	//=====================================================================================
	// define values used in sendable choosers
	//=====================================================================================
	private enum AUTON_MODE 
	{
		UNDEFINED,
		DO_NOTHING
	}
	
	private enum STARTING_SIDE 
	{
		LEFT,
		RIGHT
	}
	
	//=====================================================================================
	// define class level working variables
	//=====================================================================================
	private SendableChooser<AUTON_MODE> _autonModeChooser = new SendableChooser<>();
	private SendableChooser<STARTING_SIDE> _autonStartingSideChooser = new SendableChooser<>();
	
	private final List<IDashboardWriter> _dashboardWriters;
	
	private boolean _isStartingLeft = true;
	
	//=====================================================================================
	//Define Singleton Pattern
	//=====================================================================================
	private static Dashboard _instance = new Dashboard();
	
	public static Dashboard getInstance() 
	{
		return _instance;
	}
	
	private Dashboard() 
	{
		// configure the sendable choosers
		_autonModeChooser.addDefault("Do Nothing", AUTON_MODE.DO_NOTHING);
		SmartDashboard.putData("AUTON MODE: ", _autonModeChooser);
		
		_autonStartingSideChooser.addDefault("LEFT", STARTING_SIDE.LEFT);
		_autonStartingSideChooser.addObject("RIGHT", STARTING_SIDE.RIGHT);
		SmartDashboard.putData("AUTON STARTING SIDE: ", _autonStartingSideChooser);
		
		_dashboardWriters = new ArrayList<>();
	}
	
	//=====================================================================================
	// Property Accessors
	//=====================================================================================
	public boolean isGameDataReceived() 
	{
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if (gameData.length() > 0) {
			//_isSwitchLeft = (gameData.charAt(0) == 'L');
			//_isScaleLeft = (gameData.charAt(1) == 'L');
			DriverStation.reportWarning("GAMEDATA: "+ gameData, false);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isBlueAlliance() 
	{
		return DriverStation.getInstance().getAlliance() == Alliance.Blue;
	}
	

	
	/** Returns the autonBase object associated with the auton selected on the dashboard */
	public BaseAuton getSelectedAuton() 
	{
		_isStartingLeft = (_autonStartingSideChooser.getSelected() == STARTING_SIDE.LEFT);
		
		switch(_autonModeChooser.getSelected()) 
		{
			case DO_NOTHING:
				return new DoNothing();
				
			default:
				return new DoNothing();
		}
	}
	
	//=====================================================================================
	// Methods
	//=====================================================================================
	// Adds class instance to the dashboard  writers collection 
	public void register(IDashboardWriter dashboardWriter) 
	{
		_dashboardWriters.add(dashboardWriter);
	}
	
	public void register(List<IDashboardWriter> dashboardWriters)
	{
		for(IDashboardWriter dashboardWriter : dashboardWriters)
		{
			register(dashboardWriter);
		}
	}

	public void updateAllData() 
	{
		// loop over each registered dashboard writer
		for(IDashboardWriter writer : _dashboardWriters)
		{
			writer.outputToDashboard();
		}
		
		SmartDashboard.putString("AUTON SELECTED", _autonModeChooser.getSelected().toString());
	}
	
	// This prints once during robotInit 
	public void printStartupMessage() 
	{
		boolean isFMSAttached = DriverStation.getInstance().isFMSAttached();
		
		DriverStation.reportWarning(">>>>> Is FMS Attached : [" + isFMSAttached + "] <<<<<<", false);
	}
	

}
