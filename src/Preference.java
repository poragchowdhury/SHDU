import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Preference {
	public static HashMap<String, Device> deviceMap;
	public static HashMap<String, ArrayList<Event>> preference_distribution_by_day;
	private static Logger log = Logger.getLogger("SHDU");
	public Preference() {
		deviceMap = new HashMap<String, Device>();
		preference_distribution_by_day = new HashMap<>();
		// add all the devices
		readPreferenceDistributions();
	}

	public void readPreferenceDistributions() {
		JSONParser parser = new JSONParser();
		try {
			log.info("# User Preference Distribution");
			Object obj = parser.parse(new FileReader("preference_distribution.json"));
			JSONObject jsonObject = (JSONObject)obj;
			JSONArray device_activities = (JSONArray)jsonObject.get("device_activities");
			Iterator<JSONObject> iterator = device_activities.iterator();
			// Iterate through each device activity distribution 
			while (iterator.hasNext()) {
				JSONObject json_device = iterator.next();
				String device_name = (String) json_device.get("device_name");
				String power_kw = (String) json_device.get("power_kw");
				Device device = deviceMap.get(device_name);
				if(device == null)
					device = new Device(device_name, power_kw);
				else {
					log.info("Device name conflict!");
					return;
				}
				
				JSONArray day_activities = (JSONArray)json_device.get("day_activities");
				// Read all the day activities of this specific device
				Iterator<JSONObject> day_activities_iterator = day_activities.iterator();
				while (day_activities_iterator.hasNext()) {
					JSONObject json_day_activity = day_activities_iterator.next();
					String days = (String) json_day_activity.get("days");
					JSONArray events = (JSONArray)json_day_activity.get("events");
					String [] arrDays = days.split(",");
					log.info(days);
					for(String day : arrDays) {
						ArrayList<Event> list = preference_distribution_by_day.get(day);
						if(list == null)
							list = new ArrayList<>();
						Iterator<JSONObject> events_iterator = events.iterator();
						while (events_iterator.hasNext()) {
							JSONObject json_event = events_iterator.next();
							String evnt = (String) json_event.get("event");
							String mean = (String) json_event.get("mean");
							String std = (String) json_event.get("std");
							String insty = (String) json_event.get("intensity");
							Event ev = new Event(device_name,evnt,mean,std,insty,power_kw);
							list.add(ev);
							log.info(ev.toString());
						}
						preference_distribution_by_day.put(day, list);
					}
				}
				deviceMap.put(device_name, device);				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Read all preferences successfully.");
	}
}
