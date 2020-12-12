import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SHDU {
	private static Logger log = Logger.getLogger("SHDU");
	public static String [] days = {"sat", "sun", "mon", "tue", "wed", "thu", "fri"};
	public static void appendToLog(StringBuilder log, String e) {
		log.append(e); 
		log.append(",");
	}
	
	public static void appendToLog(StringBuilder log, int e) {
		log.append(e); 
		log.append(",");
	}
	
	public static void setupLogging() throws IOException {
		// %1 = Date, %2 = Source, %3 = Logger, %4 = Level, %5 = Message, &6 = Thrown
		// %1$tF = Date -> Y-m-d
		// %1$tT = Date -> 24 hour format
		// %4$s = Log Type (Info, ...)
		// %2$s = Class and Method Call
		// %5$s%6$s = Message
		// {%1$tT} %2$s %5$s%6$s  
		System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%6$s" + "\n");
		FileHandler fh = new FileHandler("experiment.log", true);
		SimpleFormatter formatter = new SimpleFormatter();

		fh.setFormatter(formatter);
		log.addHandler(fh);
	}
	
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
		appendToLog(strlog, event.intensity);
		strlog.append(event.type);
		
		return strlog.toString();
	}
	
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
		appendToLog(strlog, event.intensity);
		strlog.append(event.type);
		
		return strlog.toString();
	}
	
	public static void main(String[] args) {
		try {
			setupLogging();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Smart Home Device Usage Generator!");
		log.info("*************** Experimental Run Log ***************");
		Preference preference = new Preference();
		
		log.info("# Simulate data");
		for(int min = 0; min < Parameters.getHorizon(); min++) {
			int day_number = min % (60*24);
			String day = days[day_number%7];
			
			ArrayList<Event> events = preference.preference_distribution_by_day.get(day);
			
			for(Event event : events) {
				log.info(simulateEventLog(min, day_number, event));
			}
		}
	}
}
