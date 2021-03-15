import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import logparser.LogParser;

public class SHDU {
	public static Logger log = Logger.getLogger("SHDU");
	public static String [] days = {"sat", "sun", "mon", "tue", "wed", "thu", "fri"};
	public enum HOUSE_SIZE {SMALL, MEDIUM, LARGE}
	/*
	 * Function appends string e to log with a comma delimiter
	 */
	public static void appendToLog(StringBuilder log, String e) {
		log.append(e); 
		log.append(",");
	}
	
	/*
	 * Function appends int e to log with a comma delimiter
	 */
	public static void appendToLog(StringBuilder log, int e) {
		log.append(e); 
		log.append(",");
	}
	
	/*
	 * Function sets log string properties
	 */
	public static void setupLogging(String logFileName) throws IOException {
		System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%6$s" + "\n");
		FileHandler fh = new FileHandler(logFileName, true);
		SimpleFormatter formatter = new SimpleFormatter();
		fh.setFormatter(formatter);
		log.addHandler(fh);
	}
	
	/*
	 * Function samples a time from an event object
	 * 
	 * @param min   current minute
	 * @param day_number   number of days  
	 * @param event   Event object containing the device information and distribution
	 */
	public static String simulateEventLog(int min, int day_number, Event event) {
		int actual_day_number = day_number;
		StringBuilder strlog = new StringBuilder();
		
		String [] arrTime = event.mean.split(":");
		int mean_hour = Integer.parseInt(arrTime[0]);
		int mean_min = Integer.parseInt(arrTime[1]);
		mean_min += mean_hour * 60;
		
		arrTime = event.std.split(":");
		int std_hour = Integer.parseInt(arrTime[0]);
		int std_min = Integer.parseInt(arrTime[1]);
		std_min += std_hour * 60;
		
		Random ran = new Random(); 
		double sampled_min = ran.nextGaussian()*std_min + mean_min;
		
		if(sampled_min < 0) {
			// go to previous day
			--day_number;
			if(day_number == -1)
				day_number = 6;
		}
		else if(sampled_min >= 60*24) {
			// go to next day
			day_number = (day_number + 1)%7;
		}
		
		int sampled_hour = (int)(sampled_min / 60) % 24;
		sampled_hour = sampled_hour < 0 ? sampled_hour + 24 : sampled_hour;
		
		sampled_min = Math.floor(sampled_min % 60);
		if(sampled_min < 0)
			sampled_hour = (sampled_hour-1) < 0 ? (sampled_hour-1)+24 : (sampled_hour-1);
		
		sampled_min = sampled_min < 0 ? sampled_min + 60 : sampled_min;
		
		// Log order: DN, HH, MM, DV, PW, EV
		appendToLog(strlog, actual_day_number);
		appendToLog(strlog, day_number%7);
		appendToLog(strlog, sampled_hour);
		appendToLog(strlog, (int)sampled_min);
		appendToLog(strlog, event.device_name);
		appendToLog(strlog, event.action);
		strlog.append(event.type);
		
		return strlog.toString();
	}
	
	/*
	 * Function samples a time from an event object
	 * 
	 * @param day_number   number of days  
	 * @param event   Event object containing the device information
	 * @return void
	 */
	public static String simulateEventLog(int day_number, Event event) {
		int actual_day_number = day_number;
		StringBuilder strlog = new StringBuilder();
		
		String [] arrTime = event.mean.split(":");
		int mean_hour = Integer.parseInt(arrTime[0]);
		int mean_min = Integer.parseInt(arrTime[1]);
		mean_min += mean_hour * 60;
		
		arrTime = event.std.split(":");
		int std_hour = Integer.parseInt(arrTime[0]);
		int std_min = Integer.parseInt(arrTime[1]);
		std_min += std_hour * 60;
		
		Random ran = new Random(); 
		double sampled_min = ran.nextGaussian()*std_min + mean_min;
		
		if(sampled_min < 0) {
			// go to previous day
			--day_number;
			if(day_number == -1)
				day_number = 6;
		}
		else if(sampled_min >= 60*24) {
			// go to next day
			day_number = (day_number + 1)%7;
		}
		
		int sampled_hour = (int)(sampled_min / 60) % 24;
		sampled_hour = sampled_hour < 0 ? sampled_hour + 24 : sampled_hour;
		
		sampled_min = Math.floor(sampled_min % 60);
		if(sampled_min < 0)
			sampled_hour = (sampled_hour-1) < 0 ? (sampled_hour-1)+24 : (sampled_hour-1);
		
		sampled_min = sampled_min < 0 ? sampled_min + 60 : sampled_min;
		
		// Log order: DN, HH, MM, DV, PW, EV
		appendToLog(strlog, actual_day_number);
		appendToLog(strlog, day_number%7);
		appendToLog(strlog, sampled_hour);
		appendToLog(strlog, (int)sampled_min);
		appendToLog(strlog, event.device_name);
		appendToLog(strlog, event.action);
		strlog.append(event.type);
		
		return strlog.toString();
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		for(int i = 1; i <= 1; i++) {
			House house = new House(HOUSE_SIZE.LARGE.ordinal(), "inputs/random/Preferences1.json");
			// call the simulate training data to generate training dataset
			house.simulateTrainingData("trainingdata_large_"+i);
		}
		// call the generateSchedules method to predict the schedules of the devices		
		//house.generateSchedules();
	}
}
