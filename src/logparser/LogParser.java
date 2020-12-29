package logparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.logging.Logger;

public class LogParser {
	public static int total_days = 60;
	public static int total_mins = total_days * 24 * 60;
	public enum LOG_VALS {ACTUALDAY, HOUR, MIN, WEEKDAY, DEVICE, INTENSITY};
	public static TreeMap<String, TreeMap<Integer, Integer>> record;

	public LogParser(String logName) throws FileNotFoundException, IOException {
		record = new TreeMap<>();
		try(BufferedReader br = new BufferedReader(new FileReader(logName))) {
		    String line = br.readLine();
		    line = br.readLine();
		    // load the values from the file
		    while (line != null) {
		    	if(line.equals("# Simulate data"))
		    		break;
		    	line = br.readLine();
		    }
		    
		    line = br.readLine();
		    while (line != null) {
		    	String [] vals = line.split(",");
		    	int actual_day = Integer.parseInt(vals[LOG_VALS.ACTUALDAY.ordinal()]);
		    	int hh = Integer.parseInt(vals[LOG_VALS.HOUR.ordinal()]);
		    	int mm = Integer.parseInt(vals[LOG_VALS.MIN.ordinal()]);
		    	int week_day = Integer.parseInt(vals[LOG_VALS.WEEKDAY.ordinal()]);
		    	String device = vals[LOG_VALS.DEVICE.ordinal()];
		    	int intensity = Integer.parseInt(vals[LOG_VALS.INTENSITY.ordinal()]);
		    	TreeMap<Integer, Integer> record_time_map = record.get(device);
		    	if(record_time_map == null)
		    		record_time_map = new TreeMap<>();
		    	
		    	if(actual_day % 7 == week_day) {
		    		// hh : mm in the same day
		    		int idx = actual_day * 24 * 60 + hh * 60 + mm;
		    		record_time_map.put(idx, intensity);
		    		record.put(device, record_time_map);
		    	}
		    	else if(actual_day % 7 - 1 == week_day) {
		    		// hh : mm in the previous day
		    		int idx = (actual_day - 1) * 24 * 60 + hh * 60 + mm;
		    		record_time_map.put(idx, intensity);
		    		record.put(device, record_time_map);
		    	}
		    	else if((actual_day % 7 + 1)% 7 == week_day) {
		    		// hh : mm in the next day
		    		int idx = (actual_day + 1) * 24 * 60 + hh * 60 + mm;
		    		record_time_map.put(idx, intensity);
		    		record.put(device, record_time_map);
		    	}
		    	line = br.readLine();
		    }
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException{
    	LogParser lp = new LogParser("exp2_test.log");
    	// Generate training data from timeseries
    	for(String device : record.keySet()) {
    		TreeMap<Integer, Integer> record_time_map = record.get(device);
	    	Integer prev = -1;
	    	for(Integer mm : record_time_map.keySet()) {
	    		if(prev == -1) {
	    			prev = mm;
	    			continue;
	    		}
	    		int duration = mm - prev;
	    		int week_day = (prev / (60*24)) % 7;
	    		int hour = (prev / 60) % 24;
	    		int min = prev % 60;
	    		int intensity = record_time_map.get(prev); 
	    		System.out.println(week_day+","+hour+","+min+","+intensity+","+duration);
	    		prev = mm;
	    	}
    	}
    }
}
