package frc.team4028.robot.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import frc.team4028.robot.interfaces.ILogDataWriter;

// implements logic to log data to text file
public class DataLogger 
{
	//=====================================================================================
	// define class level working variables
	//=====================================================================================
	private PrintWriter _writer;
    
    private String _logFilePathName;
    private boolean _isLoggingEnabled;
    private Date _loggingStartedDT;
    private Date _lastScanDT;
    private boolean _isHeadersWrittenAlready;

	private final List<ILogDataWriter> _dataLogSources;
	
	//=====================================================================================
	// NOTE: cannot use singleton pattern because constructor takes params
	//=====================================================================================
	
    // constructor, open a new timestamped log file in the target directory
    public DataLogger(String parentFolder, String fileSuffix) throws IOException {
    	SimpleDateFormat outputFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
		outputFormatter.setTimeZone(TimeZone.getTimeZone("US/Eastern")); 
		String newDateString = outputFormatter.format(new Date());
    	
    	// build the new filename
    	String fileName = newDateString + "_" + fileSuffix + ".tsv";
    	// build the full file path name
    	_logFilePathName = parentFolder + File.separator + fileName;
    	
        _writer = new PrintWriter(new BufferedWriter(new FileWriter(_logFilePathName, true)));
        
        _dataLogSources = new ArrayList<>();
    }
    
	//============================================================================================
	// Methods follow
	//============================================================================================
	// Adds class instance to the dashboard  writers collection 
	public void register(ILogDataWriter dataLogSource) 
	{
		_dataLogSources.add(dataLogSource);
	}
	
	public void register(List<ILogDataWriter> logDataWriters)
	{
		for(ILogDataWriter logDataWriter : logDataWriters)
		{
			register(logDataWriter);
		}
	}

    // Write a string to the file
    public void WriteHeaderLine(String textToLog) {              
        _writer.print("StartDeltaMS" + "\t" + "LastScanDeltaMS" + "\t" + textToLog);
        _writer.flush();
        
    	// init these values
        _loggingStartedDT = new Date();
        _lastScanDT = new Date();
    }

    // Write a structured data object to the log file
    public void WriteDataLine(LogDataBE dataToLog) {
    	if(!_isHeadersWrittenAlready) {
    		WriteHeaderLine(dataToLog.BuildTSVHeader());
    		_isHeadersWrittenAlready = true;
    	}
    	
    	TimeUnit timeUnit = TimeUnit.MILLISECONDS;
       	Date now = new Date();
       	
    	long startDeltaDiffInMSecs = now.getTime() - _loggingStartedDT.getTime();
        long startDeltaTimestamp = timeUnit.convert(startDeltaDiffInMSecs, TimeUnit.MILLISECONDS);
        
    	long lastScanDeltaDiffInMillies = now.getTime() - _lastScanDT.getTime();
        long lastScanDeltaTimestamp = timeUnit.convert(lastScanDeltaDiffInMillies,TimeUnit.MILLISECONDS);
        
        _writer.print(startDeltaTimestamp + "\t" + lastScanDeltaTimestamp + "\t" + dataToLog.BuildTSVData());
        _writer.flush();
        
		// save last scan dt so we can calc delta on next scan
        _lastScanDT = new Date();
    }
        
    public void close() {
    	_writer.close(); // close the file
    }
    
	//============================================================================================
	// Property Accessors follow
	//============================================================================================
	public boolean IsLoggingEnabled() {
		return _isLoggingEnabled;
	}
	
	public String getLogFilePathName() {
		return _logFilePathName;
	}
}