package frc.team4028.robot.auton;

/* Selects, runs and stops auton */
public class AutonExecuter {
	private BaseAuton _autoMode;
	private Thread _autoThread = null;
	
	public void setAutoMode(BaseAuton autoMode) {
		_autoMode = autoMode;
	}
	
	public void start() {
		// Creates a new threat to run auton in
		if (_autoThread == null) {
			_autoThread = new Thread(new Runnable() {
				@Override
				public void run() {
					if (_autoMode != null) {
						_autoMode.run();
					}
				}
			});
			_autoThread.start();
			_autoMode.start();
		}
	}
	
	public void stop() {
		if (_autoMode != null) {
			_autoMode.stop();
		}
		_autoThread = null;
	}
}
