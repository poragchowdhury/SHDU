package logparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.logging.Logger;

public class LogParser {
	public enum LOG_VALS {ACTUALDAY, HOUR, MIN, WEEKDAY, SENSOR_DATA, ACTION, DEVICE};
	public TreeMap<String, TreeMap<Integer, String>> record;

	public LogParser(String logName) throws FileNotFoundException, IOException {
		record = new TreeMap<>();
		try(BufferedReader br = new BufferedReader(new FileReader(logName))) {
			String line = br.readLine();
			line = br.readLine();
			// load the values from the file
			while (line != null) {
				if(line.equals("# Simulate data")) {
					line = br.readLine(); // this line is for skipping header
					break;
				}
				line = br.readLine();
			}

			line = br.readLine();
			while (line != null) {
				String [] vals = parseLogLine(line);
				int actual_day = Integer.parseInt(vals[LOG_VALS.ACTUALDAY.ordinal()]);
				int hh = Integer.parseInt(vals[LOG_VALS.HOUR.ordinal()]);
				int mm = Integer.parseInt(vals[LOG_VALS.MIN.ordinal()]);
				int week_day = Integer.parseInt(vals[LOG_VALS.WEEKDAY.ordinal()]);
				String sensor_data = vals[LOG_VALS.SENSOR_DATA.ordinal()];
				String device = vals[LOG_VALS.DEVICE.ordinal()];
				String action = vals[LOG_VALS.ACTION.ordinal()];
				TreeMap<Integer, String> record_time_map = record.get(device);
				if(record_time_map == null)
					record_time_map = new TreeMap<>();
				int idx = actual_day * 24 * 60 + hh * 60 + mm;
				record_time_map.put(idx, sensor_data+","+action);
				record.put(device, record_time_map);
				line = br.readLine();
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public String [] parseLogLine(String line) {
		String [] results = new String[LOG_VALS.values().length];
		String [] arrLines = line.split(",");
		StringBuilder sb = new StringBuilder();
		for(int count = 0; count < arrLines.length; count++) {
			if(count < LOG_VALS.values().length - 3)
				results[count] = arrLines[count];  
			else if(count < arrLines.length-2) { 
				if(sb.length() == 0)
					sb.append(arrLines[count]);
				else
					sb.append(","+arrLines[count]);
			}
		}
		results[LOG_VALS.values().length-1] = arrLines[arrLines.length-1];
		results[LOG_VALS.values().length-2] = arrLines[arrLines.length-2];
		results[LOG_VALS.values().length-3] = sb.toString();
		return results;
	}
}
