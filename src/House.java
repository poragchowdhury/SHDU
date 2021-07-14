import org.json.JSONObject;

import logparser.LogParser;
import schedulelearning.PreferencePredictor;

import org.json.JSONArray;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeMap;

public class House {
	public static int SEED = 1;
	public JSONObject devices;
	public static String logHeaders;
	public static int[] HOUSE = {0,1,2}; /* small, medium, large */ //TESTING
	public static int EQN = 0;
	public static int AT = 2;
	public static int ON = 4;
	public boolean booSchedulePrediction = false;
	public HashMap<String, HashMap<String, ArrayList<String>>> preferenceMap = new HashMap<>(); //<device, <sensing_prob, [rules]>>
	public HashMap<String, HashMap<String,Double>> sensing_prop_sensor_map = new HashMap<>(); //<sensing_prop, <sensor, currentvalue>>
	public HashMap<String, Double> min_sensor_property = new HashMap<>(); //<device+sensing_prop, min_val>
	public HashMap<String, Double> max_sensor_property = new HashMap<>(); //<device+sensing_prop, max_val>
	public HashMap<String, Integer> active_preference_for_device = new HashMap<>(); //<device, action>
	public HashMap<String, String> current_device_action = new HashMap<>(); // <device, current_action>
	public HashMap<String, Integer> weekDays = new HashMap<>();
	public enum RULE {DEVICE, SENSOR_PROPERTY, PREFERENCE_EQN};
	public HashMap<String, Character> deviceOffEvent = new HashMap<>(); 
	public HashMap<Integer, TreeMap<Integer, HashMap<String, Event>>> histogramData = new HashMap<>();
	public HashMap<Integer, TreeMap<Integer, ArrayList<Event>>> preferrredEvents = new HashMap<>();
	public HashMap<Integer, HashSet<String>> deviceEvents = new HashMap<>();
	public HashMap<Integer, TreeMap<Integer, ArrayList<Event>>> predictedSchedules = new HashMap<>();
	public HashSet<String> cur_devices = new HashSet<>();
	public PreferencePredictor pp;
	public StringBuilder Sequence = new StringBuilder();
	public int unsatisfiedMins = 0;
	
	
	//House() default constructor generates the small house
	public House () {
		this.devices = convertDevices(readDevices(), 1, 0);
		readPreferences();
		
		// build weekdays hashmap
		for(int i = 0; i < 7; i++)
			weekDays.put(SHDU.days[i], i);
		
		pp = new PreferencePredictor();
	}
	
	//House(HSize) constructor generates the size of the house (parameter based on HOUSE[HSize])
	public House (int HSize) {
		this.devices = convertDevices(readDevices(), 1, HSize);
		readPreferences();

		// build weekdays hashmap
		for(int i = 0; i < 7; i++)
			weekDays.put(SHDU.days[i], i);
		
		pp = new PreferencePredictor();
	}
	
	public House (int HSize, String preference) {
		this.devices = convertDevices(readDevices(), 1, HSize);
		readPreferences(preference);
		

		// build weekdays hashmap
		for(int i = 0; i < 7; i++)
			weekDays.put(SHDU.days[i], i);
		
		//pp = new PreferencePredictor();
	}

	public String getLogHeaders() {
		StringBuilder sb = new StringBuilder();
		sb.append(Observer.getCurrentTimeHeader());		
		for(String key : sensing_prop_sensor_map.keySet()) {
			for(String key2 : sensing_prop_sensor_map.get(key).keySet())
				sb.append("," + key+"_"+key2);
		}
		sb.append(",action");
		sb.append(",device");
		logHeaders = sb.toString();
		return sb.toString();
	}
	
	public String getLogString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Observer.getCurrentTime());		
		for(String key : sensing_prop_sensor_map.keySet()) {
			for(String key2 : sensing_prop_sensor_map.get(key).keySet())
				sb.append("," + Utilities.round(sensing_prop_sensor_map.get(key).get(key2),2));
		}
		return sb.toString();
	}

	public String getSensorData() {
		StringBuilder sb = new StringBuilder();
		for(String key : sensing_prop_sensor_map.keySet()) {
			for(String key2 : sensing_prop_sensor_map.get(key).keySet())
				sb.append("," + key + " " + key2 + " " + Utilities.round(sensing_prop_sensor_map.get(key).get(key2),2));
		}
		return sb.toString();
	}

	/*
	public static void main (String [] args) {
		House house = new House();
		JSONObject device = (JSONObject) house.devices.get("Dyson_AM09");
		JSONObject actions = (JSONObject) device.get("actions");
		JSONObject heat = (JSONObject) actions.get("heat");
		JSONArray effects = (JSONArray) heat.get("effects");
		for(int j = 0; j < effects.length(); j++) {
			JSONObject e = effects.getJSONObject(j);
			System.out.println(e.getString("property") + " = " + e.getDouble("delta") / 60.0 * 1);
		}

		JSONObject sensor = (JSONObject) house.devices.get("Kenmore_790_sensor");
		double currentState = sensor.getDouble("current_state");
	}
	 */
	
	public void setDeviceEvents() {
		int std_dev_range = 10;
		TreeMap<Integer, HashMap<String, Event>> day_data = histogramData.get(Observer.cur_day_of_week);
		HashMap<String, Integer> prevMins = new HashMap<>();
		HashMap<String, Event> maxEvents = new HashMap<>();
		deviceEvents.clear();
		HashSet<String> addedDevices = new HashSet<>();
		
		for(String s : cur_devices) {
			prevMins.put(s, -1);
			maxEvents.put(s, null);
		}
		
		for(Integer min : day_data.keySet()) {
			HashMap<String, Event> min_data = day_data.get(min);
			for(String device : min_data.keySet()) {
				Event ev = min_data.get(device);
				int prevMin = prevMins.get(device);
				
				if(prevMin == -1) {
					// first data	
					prevMins.put(device, min);
					maxEvents.put(device, ev);
				}
				else if(Math.abs(prevMin-min) < std_dev_range)
				{
					prevMins.put(device, min);
					if(maxEvents.get(device).frequency < ev.frequency) {
						maxEvents.put(device, ev);
					}
				}
				else {
					if(!addedDevices.contains(device))
						addedDevices.add(device);
				}
				
				if(addedDevices.size() == cur_devices.size())
					break;
			}
			if(addedDevices.size() == cur_devices.size())
				break;
		}
		
		
		for(String device : cur_devices) {
			Event evnt = maxEvents.get(device);
			HashSet<String> set = deviceEvents.get(evnt.min);
			if(set == null)
				set = new HashSet<>();
			set.add(device);
			deviceEvents.put(evnt.min, set);
		}
		
		// reset device actions to off
		for(String device : current_device_action.keySet())
			current_device_action.put(device, "off");

	}

	
	public void simulateMinuteByEvents(boolean log) {
		/* 1. check current min
		 *		1.1 If current min is in the deviceEvents
		 *			1.1.1 Do prediction and update device event
		 *		1.2 If No continue   
		 */
		int current_min = Observer.getCurrentTimeInMin();
	    HashSet<String> devices = deviceEvents.get(current_min);
	    if(devices != null) {
	    	for(String device : devices) {
	    		String logStr = getLogString();
	    		String action = pp.predictAction(pp.getActionData(logStr), device);
	    		Double duration = pp.predictDuration(pp.getDurationData(logStr), device, action);
	    		// change current_device_action action
	    		current_device_action.put(device, action);
	    		SHDU.log.info(logStr+","+action+","+device);
	    		int new_min = current_min + duration.intValue();
	    		HashSet<String> set = deviceEvents.get(new_min);
	    		if(set == null)
	    			set = new HashSet<>();
	    		set.add(device);
	    		deviceEvents.put(new_min, set);
	    	}
	    	deviceEvents.remove(current_min);
	    }

		// simulate the minute by the current device actions
		changeCurrentStateByDelta(log);
	}
	

	public void simulateMinute(boolean log) {
		/*
		 * 1: Pick a device from preferenceMap
		 * 		a. Pick a sensor property
		 * 			i) Pick a preference condition and check with current environment? 		
		 * 				If Satisfies : 
		 * 					- Apply actuator to satisfy the preference
		 * 					- Log events in the log 
		 * 					- Quit all the rules for this device
		 * 				Otherwise  : Continue checking to the next preferences 
		 * */

		for(String device : preferenceMap.keySet()) {
			HashMap<String, ArrayList<String>> sensors_preferences = preferenceMap.get(device);
			checkAndApplyActions(device, sensors_preferences);
		}

		// simulate the minute by the current device actions
		changeCurrentStateByDelta(log);
	}

	public boolean checkDay(String on, String weekday) {
		String [] days = weekday.split(",");
		if(!on.equals("on"))
			return false;
		for(String day : days)
			if(weekDays.get(day) == Observer.cur_day_of_week)
				return true;
		return false;
	}

	/*
	public void sortEvents() {
		for(int day = 0; day < 7; day++) {
			TreeMap<Integer, HashMap<String, Event>> day_data = histogramData.get(day);
			for(Integer min : day_data.keySet()) {
				HashMap<String, Event> min_data = day_data.get(min);
				for(String device : min_data.keySet()) {
					Event e = min_data.get(device);
					TreeMap<Integer, ArrayList<Event>> day_pref_data = preferrredEvents.get(day);
					if(day_pref_data == null)
						day_pref_data = new TreeMap<>(Collections.reverseOrder());
					ArrayList<Event> list = day_pref_data.get(e.frequency);
					if(list == null)
						list = new ArrayList<Event>();
					list.add(e);
					day_pref_data.put(e.frequency, list);
					preferrredEvents.put(day, day_pref_data);
				}
			}
		}
	}
	*/
	
	/*
	public boolean checkUnsatisfiedMin(String device, HashMap<String, ArrayList<String>> sensors_preferences) {
		int sensor_offset = 1000;
		int sensor_pref_id = 0;
		for(String sensor_property : sensors_preferences.keySet()) {
			ArrayList<String> preferences = sensors_preferences.get(sensor_property);

			Double current_status = findCurrentValue(device, sensor_property);
			int active_preference = active_preference_for_device.get(device);

			if(current_status == null) {
				if(!devices.getJSONObject(device).get("subtype").equals("light"))
				{
					System.out.println("This should not happen!");
					System.exit(0);
				}
			}

			for(int preference_id = 0; preference_id < preferences.size(); preference_id++) {
				int cur_pref_id = sensor_pref_id + preference_id;
				String preference = preferences.get(preference_id);
				String [] targets = preference.split(" ");
				double target_status = Double.parseDouble(targets[1]);

				// Satisfies the preference condition : Need to apply action 
				double delta_needed = 0;  
				
				if(targets.length > 2) {
					// preference condition contains time
					if(targets.length == 6)
						if(!checkDay(targets[ON], targets[ON+1]))
							continue;	// check next preferences				

					int target_min = getTargetTimeInMin(targets[AT+1]);
					int current_min = Observer.getCurrentTimeInMin(); 
					if(devices.getJSONObject(device).get("subtype").equals("light") && 
							(targets[AT].equals("at") && current_min == target_min))
					{
						if(booSchedulePrediction)
							storeEventData(getLogString(), device); 	// store event data for histogram
						SHDU.log.info(getLogString()+ ",BR" + targets[1] + "," + device);
						current_device_action.put(device, targets[1]);
					}
					else if(((targets[2].equals("before") && current_min < target_min) ||
							(targets[2].equals("after") && current_min > target_min) || 
							(targets[2].equals("at") && current_min == target_min)) &&
							Utilities.checkConstraints(targets[0], current_status, target_status)) {

						// check active rule
						delta_needed = target_status - current_status;
						String applied_device_action = takeAction(device, sensor_property, delta_needed);
						// check if is crossing the lower or upper limit

						if(active_preference != cur_pref_id) {
							if(booSchedulePrediction)
								storeEventData(getLogString(), device); 	// store event data for histogram
							// New action applied and change device status
							SHDU.log.info(getLogString()+ "," + applied_device_action + "," + device );
							active_preference_for_device.put(device, cur_pref_id);
							current_device_action.put(device, applied_device_action);
						}
						return;
					}
					// check if both actions (current device action and preference rule action) are same
					else if(active_preference == cur_pref_id){
						if(booSchedulePrediction)
							storeEventData(getLogString(), device); 	// store event data for histogram
						SHDU.log.info(getLogString()+ "," + "off" + "," + device );
						active_preference_for_device.put(device, -1);
						current_device_action.put(device, "off");
					}
				}
				else {
					//max_sensor_property.get(device+sensor_property) >= current_status &&
					//min_sensor_property.get(device+sensor_property) <= current_status
					if(Utilities.checkConstraints(targets[0], current_status, target_status))
					{
						delta_needed = target_status - current_status;
						String applied_device_action = takeAction(device, sensor_property, delta_needed);
						if(active_preference != cur_pref_id) {
							// New action applied and change device status
							if(booSchedulePrediction)
								storeEventData(getLogString(), device); 	// store event data for histogram
							SHDU.log.info(getLogString() + "," + applied_device_action + "," + device);
							active_preference_for_device.put(device, cur_pref_id);
							current_device_action.put(device, applied_device_action);
						}
						return;
					}
					else if(active_preference == cur_pref_id){
						if(booSchedulePrediction)
							storeEventData(getLogString(), device); 	// store event data for histogram
						SHDU.log.info(getLogString()+ "," + "off" + "," + device);
						active_preference_for_device.put(device, -1);
						current_device_action.put(device, "off");
					}
				}
			}
			sensor_pref_id += sensor_offset;
		}

	}
	*/

	public void checkAndApplyActions(String device, HashMap<String, ArrayList<String>> sensors_preferences) {
		int sensor_offset = 1000;
		int sensor_pref_id = 0;
		for(String sensor_property : sensors_preferences.keySet()) {
			ArrayList<String> preferences = sensors_preferences.get(sensor_property);

			Double current_status = findCurrentValue(device, sensor_property);
			int active_preference = active_preference_for_device.get(device);

			if(current_status == null) {
				if(!devices.getJSONObject(device).get("subtype").equals("light"))
				{
					System.out.println("This should not happen!");
					System.exit(0);
				}
			}

			for(int preference_id = 0; preference_id < preferences.size(); preference_id++) {
				int cur_pref_id = sensor_pref_id + preference_id;
				String preference = preferences.get(preference_id);
				String [] targets = preference.split(" ");
				double target_status = Double.parseDouble(targets[1]);

				// Satisfies the preference condition : Need to apply action 
				double delta_needed = 0;  
				
				if(targets.length > 2) {
					// preference condition contains time
					if(targets.length == 6)
						if(!checkDay(targets[ON], targets[ON+1]))
							continue;	// check next preferences				
					
					//initialize target_min and target_between
					int[] target_between = {0,0};
					int target_min =0;
					
					//if not between, fill target_min. else, fill target_between and target_min
					if(!targets[2].equals("between")){
						target_min = getTargetTimeInMin(targets[AT+1]);
					}else{
						target_between = getTargetTimeInMinBetween(targets[AT+1]);
						target_min = target_between[0]; //so that things don't break, still fill target_min with target_between[0]
					}
					
					int current_min = Observer.getCurrentTimeInMin(); 
					if(devices.getJSONObject(device).get("subtype").equals("light") && 
							(targets[AT].equals("at") && current_min == target_min))
					{
						if(booSchedulePrediction)
							storeEventData(getLogString(), device); 	// store event data for histogram
						if(Parameters.getSequenceGenerator())
							Sequence.append(Character.toUpperCase(deviceOffEvent.get(device)));
							//SHDU.log.info(getLogString()+ ",BR" + targets[1] + "," + device);
						current_device_action.put(device, targets[1]);
					}
					else if(((targets[2].equals("before") && current_min < target_min) ||
							(targets[2].equals("after") && current_min > target_min) ||
							(targets[2].equals("at") && current_min == target_min) ||
							(targets[2].equals("between") && current_min > target_between[0] && current_min < target_between[1])) &&
							Utilities.checkConstraints(targets[0], current_status, target_status)) {

						// check active rule
						delta_needed = target_status - current_status;
						String applied_device_action = takeAction(device, sensor_property, delta_needed);
						// check if is crossing the lower or upper limit

						if(active_preference != cur_pref_id) {
							if(booSchedulePrediction)
								storeEventData(getLogString(), device); 	// store event data for histogram
							// New action applied and change device status
							if(Parameters.getSequenceGenerator())
								Sequence.append(Character.toUpperCase(deviceOffEvent.get(device)));
								//SHDU.log.info(getLogString()+ "," + applied_device_action + "," + device );
							active_preference_for_device.put(device, cur_pref_id);
							current_device_action.put(device, applied_device_action);
						}
						return;
					}
					// check if both actions (current device action and preference rule action) are same
					else if(active_preference == cur_pref_id){
						if(booSchedulePrediction)
							storeEventData(getLogString(), device); 	// store event data for histogram
						if(Parameters.getSequenceGenerator())
							Sequence.append(deviceOffEvent.get(device));
							//SHDU.log.info(getLogString()+ "," + "off" + "," + device );
						active_preference_for_device.put(device, -1);
						current_device_action.put(device, "off");
					}
				}
				else {
					//max_sensor_property.get(device+sensor_property) >= current_status &&
					//min_sensor_property.get(device+sensor_property) <= current_status
					if(Utilities.checkConstraints(targets[0], current_status, target_status))
					{
						delta_needed = target_status - current_status;
						String applied_device_action = takeAction(device, sensor_property, delta_needed);
						if(active_preference != cur_pref_id) {
							// New action applied and change device status
							if(booSchedulePrediction)
								storeEventData(getLogString(), device); 	// store event data for histogram
							if(Parameters.getSequenceGenerator())
								Sequence.append(Character.toUpperCase(deviceOffEvent.get(device)));
								//SHDU.log.info(getLogString() + "," + applied_device_action + "," + device);
							active_preference_for_device.put(device, cur_pref_id);
							current_device_action.put(device, applied_device_action);
						}
						return;
					}
					else if(active_preference == cur_pref_id){
						if(booSchedulePrediction)
							storeEventData(getLogString(), device); 	// store event data for histogram
						if(Parameters.getSequenceGenerator())
							Sequence.append(deviceOffEvent.get(device));
							//SHDU.log.info(getLogString()+ "," + "off" + "," + device);
						active_preference_for_device.put(device, -1);
						current_device_action.put(device, "off");
					}
				}
			}
			
			sensor_pref_id += sensor_offset;
		}

	}
	
	public void storeEventData(String event_data, String device) {
		if(!cur_devices.contains(device))
			cur_devices.add(device);
		
		TreeMap<Integer, HashMap<String, Event>> day_data = histogramData.get(Observer.cur_day_of_week);
		if(day_data == null)
			day_data = new TreeMap<>();
		HashMap<String, Event> min_data = day_data.get(Observer.getCurrentTimeInMin());
		if(min_data == null)
			min_data = new HashMap<>();
		Event device_event = min_data.get(device);
		if(device_event == null) {
			device_event = new Event(event_data, device, Observer.getCurrentTimeInMin());
		}
		else {
			device_event.event_data = event_data;
			device_event.frequency++;
		} 
		min_data.put(device, device_event);
		day_data.put(Observer.getCurrentTimeInMin(),min_data);
		histogramData.put(Observer.cur_day_of_week, day_data);
	}


	public void changeCurrentStateByDelta(boolean print) {
		for(String device : preferenceMap.keySet()) {
			String cur_action = current_device_action.get(device);
			JSONObject dev = (JSONObject) devices.get(device);
			if(dev.getString("subtype").equals("light")) {
				//if(!Parameters.getSequenceGenerator())
					SHDU.log.info(getLogString()+ "," + "BR"+current_device_action.get(device) + "," + device );
			}
			else if(dev.getString("type").equals("actuator")) {
				//if(!Parameters.getSequenceGenerator())
					SHDU.log.info(getLogString()+ "," + current_device_action.get(device) + "," + device );
				JSONArray sensors = (JSONArray) dev.get("sensors");
				JSONObject actions = (JSONObject) dev.get("actions");
				JSONObject action = (JSONObject) actions.get(cur_action);
				JSONArray effects = (JSONArray) action.get("effects");

				for(int j = 0; j < effects.length(); j++) {
					JSONObject e = effects.getJSONObject(j);
					double delta = e.getDouble("delta");
					String property = e.getString("property");
					String sensor = getSensor(sensors, property);
					if(sensor == null)
						continue;
					HashMap<String, Double> smap = sensing_prop_sensor_map.get(property);
					double cur_status = smap.get(sensor);
					cur_status += delta;
					smap.put(sensor, cur_status);
					
					// check the sensor's cur_status violating the preference
					
					
					sensing_prop_sensor_map.put(property, smap);
					if(print)
						System.out.println("Updating : " + getLogString());
				}
			}
		}
	}

	public String getSensor(JSONArray device_sensors, String dev_act_property) {
		for(int i = 0; i < device_sensors.length(); i++) {
			String dev_sensor = (String) device_sensors.get(i);
			JSONObject sensor = devices.getJSONObject(dev_sensor);
			JSONArray props = (JSONArray) sensor.get("sensing_properties");
			String prop = (String) props.get(0);
			if(dev_act_property.equals(prop.toString()))
				return dev_sensor;
		}
		return null;
	}

	public Double findCurrentValue(String device_name, String sensor_property) {
		JSONObject device = (JSONObject) devices.get(device_name);
		//try {
			JSONArray sensors = (JSONArray) device.get("sensors");
			for(int j = 0; j < sensors.length(); j++) {
				String sensor = sensors.get(j).toString();
				if(sensing_prop_sensor_map.get(sensor_property).containsKey(sensor)) {
					return sensing_prop_sensor_map.get(sensor_property).get(sensor);
				}
			}
		//}catch(org.json.JSONException e) 
		//{System.out.print(e);}
		return null;
	}

	public String takeAction(String device_name, String sensor_property, double delta_needed) {

		// take the action to change corresponding sensor property to 0 
		JSONObject actions = devices.getJSONObject(device_name).getJSONObject("actions");
		Iterator<?> a_keys = actions.keys();
		while(a_keys.hasNext()) {
			String a_name = (String)a_keys.next();
			JSONObject a = actions.getJSONObject(a_name);
			JSONArray effects = a.getJSONArray("effects");
			for(int j = 0; j < effects.length(); j++) {
				JSONObject e = effects.getJSONObject(j);
				double delta = e.getDouble("delta");
				if(sensor_property.equals(e.get("property")) && 
						(delta > 0 && delta_needed > 0) || (delta < 0 && delta_needed < 0) || (delta == 0 && delta_needed == 0))
					return a_name;
			}
		}
		return "off";
	} 

	public int getTargetTimeInMin(String target) {
		String [] time = target.split(":");
		int hh = Integer.parseInt(time[0]);
		int mm = Integer.parseInt(time[1]);
		return (hh*60)+mm;
	}

	//getTargetTimeInMinBetween takes a string in the form hh:mm-hh:mm and returns an array with start time in arr[0] and end time in arr[1]
	public int[] getTargetTimeInMinBetween(String target) {
		boolean debug=false; //if true: enables print statements to help debug this function.
		int [] range = new int[2];
		String [] helper = new String[2];
		if(debug) System.out.println("Test case 0:    function called");
		
		if(target.contains("-")) {//divide target at the "-"
			 helper = target.split("-");  
			 if(debug) System.out.println("Test case 1.1:  helper = {"+helper[0]+", "+helper[1]+"}");
		}
		else { //if for some reason target is written as just hh:mm (has no "-"), just put it in both helper[0] and helper[1] (I don't think this is supposed to happen, but I included it in case I am wrong.)
			helper[0]=target;
			helper[1]=target;
			if(debug) System.out.println("Test case 1.2:  helper = {"+helper[0]+", "+helper[1]+"}");
		}
		//parse the helper data into range as time
		for(int i=0; i<helper.length; i++) { 
			String [] time= helper[i].split(":");
			int hh = Integer.parseInt(time[0]);
			int mm = Integer.parseInt(time[1]);
			range[i] = (hh*60)+mm;
		} 
		if(debug) System.out.println("Test case 2:    range = {"+ range[0] +", " + range[1] +"}");
		return range; 
	}

	public static String [] getParsedActiveRule (String rule) {
		String [] parsedRule = new String [3];

		int cur_index = 2;
		int start_index = 2;
		while(rule.charAt(cur_index) != ' ')
			cur_index++;

		parsedRule[RULE.DEVICE.ordinal()] = rule.substring(start_index, cur_index);
		cur_index++;

		start_index = cur_index;
		while(rule.charAt(cur_index) != ' ')
			cur_index++;
		parsedRule[RULE.SENSOR_PROPERTY.ordinal()] = rule.substring(start_index, cur_index);
		cur_index++;

		parsedRule[RULE.PREFERENCE_EQN.ordinal()] = rule.substring(cur_index);
		return parsedRule;
	}

	public String sampleRule(String rule) {
		// eq 100 at 18:30_0:05 on mon,tue,wed,thu,fri
		String days = null;
		String start_time = "";
		String between_start_time = "";
		String between_end_time = "";
		String [] targets = rule.split(" ");
		int mean_min = -1;
		int std_min = -1;
		int day_number = 0;

		switch(targets.length) {
		case 2:
			return rule;
		case 6:
			// [5] is days
			if(targets[ON].equals("on"))
				days = targets[ON+1];
			else {
				System.out.println("Rule error");
				return "";
			}
		case 4:
			// Sample time if possible
			if(targets[AT].equals("at") || targets[AT].equals("before") || targets[AT].equals("after") ) {
				start_time = targets[AT+1];
				String [] times = targets[AT+1].split("_");
				if(times.length == 2) {
					// Need to sample start time
					String [] time = times[0].split(":");
					int mean_hour = Integer.parseInt(time[0]);
					mean_min = Integer.parseInt(time[1]);
					mean_min += (mean_hour*60);

					time = times[1].split(":");
					int std_hour = Integer.parseInt(time[0]);
					std_min = Integer.parseInt(time[1]);
					std_min += (std_hour*60);

					Random ran = new Random(SEED); 
					double sampled_min = ran.nextGaussian()*std_min + mean_min;

					if(sampled_min < 0) {
						// go to previous day
						--day_number;
					}
					else if(sampled_min >= 60*24) {
						// go to next day
						day_number++;
					}
					days = moveDays(days, day_number);

					int sampled_hour = (int)(sampled_min / 60) % 24;
					sampled_hour = sampled_hour < 0 ? sampled_hour + 24 : sampled_hour;

					sampled_min = Math.floor(sampled_min % 60);
					if(sampled_min < 0)
						sampled_hour = (sampled_hour-1) < 0 ? (sampled_hour-1)+24 : (sampled_hour-1);

					sampled_min = sampled_min < 0 ? sampled_min + 60 : sampled_min;
					start_time = sampled_hour + ":" + (int)sampled_min;
				}
			}
			else if(targets[AT].equals("between")) {
				
				String [] between = targets[AT+1].split("-");
				between_start_time = between[0];
				between_end_time = between[1];
				for(int i=0;i<between.length;i++) {
					String [] times = between[i].split("_");
					if(times.length == 2) {
						// Need to sample start time
						String [] time = times[0].split(":");
						int mean_hour = Integer.parseInt(time[0]);
						mean_min = Integer.parseInt(time[1]);
						mean_min += (mean_hour*60);
	
						time = times[1].split(":");
						int std_hour = Integer.parseInt(time[0]);
						std_min = Integer.parseInt(time[1]);
						std_min += (std_hour*60);
	
						Random ran = new Random(SEED); 
						double sampled_min = ran.nextGaussian()*std_min + mean_min;
	
						if(sampled_min < 0) {
							// go to previous day
							--day_number;
						}
						else if(sampled_min >= 60*24) {
							// go to next day
							day_number++;
						}
						days = moveDays(days, day_number);
	
						int sampled_hour = (int)(sampled_min / 60) % 24;
						sampled_hour = sampled_hour < 0 ? sampled_hour + 24 : sampled_hour;
	
						sampled_min = Math.floor(sampled_min % 60);
						if(sampled_min < 0)
							sampled_hour = (sampled_hour-1) < 0 ? (sampled_hour-1)+24 : (sampled_hour-1);
	
						sampled_min = sampled_min < 0 ? sampled_min + 60 : sampled_min;
						if(i==0)
							between_start_time = sampled_hour + ":" + (int)sampled_min;
						else
							between_end_time = sampled_hour + ":" + (int)sampled_min;
					}
				}
			}
			else {
				System.out.println("Rule error");
				return "";
			}
		default:
			// nothing to do
		}
		if(!start_time.contentEquals("")) { //if not between condition
			if(targets.length == 6)
				return targets[0] + " " + targets[1] + " " + targets[2] + " " + start_time + " " + targets[4] + " " + days;
	
			return targets[0] + " " + targets[1] + " " + targets[2] + " " + start_time;
		}else { //between condition
			if(targets.length == 6)
				return targets[0] + " " + targets[1] + " " + targets[2] + " " + between_start_time + "-" + between_end_time + " " + targets[4] + " " + days;
			
			return targets[0] + " " + targets[1] + " " + targets[2] + " " + between_start_time + "-" + between_end_time;
		}
	}

	public String moveDays(String days, int day_number) {
		if(day_number == 0 || days == null)
			return days;

		String [] day_arr = days.split(",");
		StringBuilder sb = new StringBuilder();
		for(int idx = 0; idx < day_arr.length; idx++) {
			int day_id = weekDays.get(day_arr[idx]);
			day_id = day_id + day_number;
			if(day_id < 0)
				day_id += 7;
			else if(day_id > 6)
				day_id -= 7;
			if(idx == day_arr.length-1)
				sb.append(SHDU.days[day_id]);
			else
				sb.append(SHDU.days[day_id] + ",");
		}
		return sb.toString();
	}

	public void readPreferences(String preference) {
		try {
			Parameters.setPreferencesPath(preference);
			preferenceMap.clear();
			String content = Utilities.readFile(preference);
			JSONObject jsonObject = new JSONObject(content.trim());
			JSONArray rules = (JSONArray)jsonObject.get("rules");
			Iterator<Object> iterator = rules.iterator();
			// Iterate through each device activity distribution 
			while (iterator.hasNext()) {
				String rule = iterator.next().toString();
				String [] parsedRule = getParsedActiveRule(rule);
				parsedRule[RULE.PREFERENCE_EQN.ordinal()] = sampleRule(parsedRule[RULE.PREFERENCE_EQN.ordinal()]);
				if(rule.charAt(0) == '#') {
					// skip rule
					System.out.println("Skipping " + rule);
				}
				else if(rule.charAt(0) == '1') {
					// Active rule
					HashMap<String, ArrayList<String>> devRules = preferenceMap.get(parsedRule[RULE.DEVICE.ordinal()]);
					if(devRules == null)
						devRules = new HashMap<>();

					ArrayList<String> effectRules = devRules.get(parsedRule[RULE.SENSOR_PROPERTY.ordinal()]);
					if(effectRules == null)
						effectRules = new ArrayList<>();

					effectRules.add(parsedRule[RULE.PREFERENCE_EQN.ordinal()]);
					devRules.put(parsedRule[RULE.SENSOR_PROPERTY.ordinal()], effectRules);

					preferenceMap.put(parsedRule[RULE.DEVICE.ordinal()], devRules);
				}
				else if(rule.charAt(0) == '0') {
					// Save the passive rule
					String [] condition = parsedRule[2].split(" ");
					if(condition[0].equals("leq"))
						max_sensor_property.put(parsedRule[RULE.DEVICE.ordinal()]+parsedRule[RULE.SENSOR_PROPERTY.ordinal()], Double.parseDouble(condition[1]));
					else if(condition[0].equals("geq"))
						min_sensor_property.put(parsedRule[RULE.DEVICE.ordinal()]+parsedRule[RULE.SENSOR_PROPERTY.ordinal()], Double.parseDouble(condition[1]));
				}
			}
			int offset = 0;
			for(String device : preferenceMap.keySet()) {
				deviceOffEvent.put(device, (char)('a'+offset));
				//System.out.println("device" + deviceOffEvent.get(device));
				offset++;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readPreferences() {
		try {
			preferenceMap.clear();
			String content = Utilities.readFile(Parameters.getPreferencesPath());
			JSONObject jsonObject = new JSONObject(content.trim());
			JSONArray rules = (JSONArray)jsonObject.get("rules");
			Iterator<Object> iterator = rules.iterator();
			// Iterate through each device activity distribution 
			while (iterator.hasNext()) {
				String rule = iterator.next().toString();
				String [] parsedRule = getParsedActiveRule(rule);
				parsedRule[RULE.PREFERENCE_EQN.ordinal()] = sampleRule(parsedRule[RULE.PREFERENCE_EQN.ordinal()]);
				if(rule.charAt(0) == '#') {
					// skip rule
				}
				else if(rule.charAt(0) == '1') {
					// Active rule
					HashMap<String, ArrayList<String>> devRules = preferenceMap.get(parsedRule[RULE.DEVICE.ordinal()]);
					if(devRules == null)
						devRules = new HashMap<>();

					ArrayList<String> effectRules = devRules.get(parsedRule[RULE.SENSOR_PROPERTY.ordinal()]);
					if(effectRules == null)
						effectRules = new ArrayList<>();

					effectRules.add(parsedRule[RULE.PREFERENCE_EQN.ordinal()]);
					devRules.put(parsedRule[RULE.SENSOR_PROPERTY.ordinal()], effectRules);

					preferenceMap.put(parsedRule[RULE.DEVICE.ordinal()], devRules);
				}
				else if(rule.charAt(0) == '0') {
					// Save the passive rule
					String [] condition = parsedRule[2].split(" ");
					if(condition[0].equals("leq"))
						max_sensor_property.put(parsedRule[RULE.DEVICE.ordinal()]+parsedRule[RULE.SENSOR_PROPERTY.ordinal()], Double.parseDouble(condition[1]));
					else if(condition[0].equals("geq"))
						min_sensor_property.put(parsedRule[RULE.DEVICE.ordinal()]+parsedRule[RULE.SENSOR_PROPERTY.ordinal()], Double.parseDouble(condition[1]));
				}
			}
			int offset = 0;
			for(String device : preferenceMap.keySet()) {
				deviceOffEvent.put(device, (char)('a'+offset));
				//System.out.println("device" + deviceOffEvent.get(device));
				offset++;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private JSONArray readDevices() {
		try {
			String content = Utilities.readFile(Parameters.getDeviceDictionaryPath());
			JSONArray jArray = new JSONArray(content.trim());
			return jArray;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void resetSensor() {
		for(String sensing_property : sensing_prop_sensor_map.keySet()) {
			HashMap<String, Double> sen_prop_val_map = sensing_prop_sensor_map.get(sensing_property);
			for(String sensor : sen_prop_val_map.keySet()) {
				Double cur_val = devices.getJSONObject(sensor).getDouble("current_state");
				sen_prop_val_map.put(sensor, cur_val);
				sensing_prop_sensor_map.put(sensing_property, sen_prop_val_map);
			}
		}
	}

	private JSONObject convertDevices(JSONArray devices, int granularity, int HSize) {
		for(int i = 0; i < devices.length(); i++) {
			Iterator<?> d_keys = devices.getJSONObject(i).keys();
			while(d_keys.hasNext()) {
				String d_name = (String) d_keys.next();

				JSONObject dev = devices.getJSONObject(i).getJSONObject(d_name);
				if(dev.getString("type").equals("actuator")) {
					current_device_action.put(d_name, "off");
					active_preference_for_device.put(d_name, -1);
					JSONObject actions = dev.getJSONObject("actions");
					Iterator<?> a_keys = actions.keys();
					while(a_keys.hasNext()) {
						String a_name = (String)a_keys.next();

						//HashMap<String, Double> effectMap = new HashMap<>();
						JSONObject a = actions.getJSONObject(a_name);
						JSONArray effects = a.getJSONArray("effects");
						for(int j = 0; j < effects.length(); j++) {
							JSONObject e = effects.getJSONObject(j);
							e.put("delta", e.getDouble("delta") / 60.0 * granularity);
							effects.put(j, e);
							//effectMap.put(e.getString("property"), e.getDouble("delta") / 60.0 * granularity);
						}
						a.put("power_consumed", a.getDouble("power_consumed") / 60.0 * granularity);
						a.put("effects", effects);
						actions.put(a_name, a);
					}
					dev.put("actions", actions);
				}
				else if(dev.getString("type").equals("sensor")) {
					String sensing_property = (String) dev.getJSONArray("sensing_properties").get(0);
					HashMap<String, Double> sen_prop_val_map = sensing_prop_sensor_map.get(sensing_property);
					if(sen_prop_val_map == null)
						sen_prop_val_map = new HashMap<>();
					sen_prop_val_map.put(d_name, dev.getDouble("current_state"));
					sensing_prop_sensor_map.put(sensing_property, sen_prop_val_map);
				}
				devices.put(i, devices.getJSONObject(i).put(d_name, dev));
			}
		}
		return (JSONObject) devices.get(HOUSE[HSize]);
	}
	
	public void simulateTrainingData(String logFileName) throws FileNotFoundException, IOException {
		//String logFileName = "experiment.log";
		try {
			SHDU.setupLogging(logFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Smart Home Device Usage Generator!");
		SHDU.log.info("*************** Experimental Run Log ***************");

		SHDU.log.info("# Simulate data");
		SHDU.log.info(this.getLogHeaders());
		for(int min = 0; min < Parameters.getHorizon(); min++) {
			Observer.updateTime(min);
			this.simulateMinute(false);
			if(min % (24*60) == 0) {
				this.readPreferences(); // sample new preferences for the next day
				this.resetSensor(); // reset all the sensor
			} 
		}
		
		System.out.println("\nFinal sensor status :" + this.getLogString());
		System.out.println(Sequence);
		
		//generatTrainingData(House.logHeaders, logFileName);
		//if(!Parameters.getSequenceGenerator())
			generatARFFData(logFileName);
	}
	
	/*
	 * Function generates the training data
	 *
	 * @param  logHeaders   all the attributes separated by comma 
	 * @return void
	 */
	public void generatARFFData(String logFileName) throws FileNotFoundException, IOException{
		
		LogParser lp = new LogParser(logFileName);
		try {
			FileWriter myWriter = new FileWriter(logFileName +".arff");
			String header = "@relation generateddata\n"
					+ "\r\n"
					+ "@attribute cur_hour_of_day numeric\n"
					+ "@attribute cur_min_of_hour numeric\n"
					+ "@attribute cur_day_of_week numeric\n"
					+ "@attribute charge_iRobot_651_battery numeric\n"
					+ "@attribute charge_Tesla_S_battery numeric\n"
					+ "@attribute water_temp_water_heat_sensor numeric\n"
					+ "@attribute temperature_cool_thermostat_cool numeric\n"
					+ "@attribute bake_Kenmore_790_sensor numeric\n"
					+ "@attribute dish_wash_Kenmore_665_sensor numeric\n"
					+ "@attribute laundry_wash_GE_WSM2420D3WW_wash_sensor numeric\n"
					+ "@attribute laundry_dry_GE_WSM2420D3WW_dry_sensor numeric\n"
					+ "@attribute temperature_heat_thermostat_heat numeric\n"
					+ "@attribute cleanliness_dust_sensor numeric\n"
					+ "@attribute action {cool,off,heat,regular,wash,bake,charge,vacuum,charge_48a,charge_72a}\n"  // charge_48a : small and medium ; charge_72a = large
					+ "@attribute device {Bryant_697CN030B,Dyson_AM09,GE_WSM2420D3WW_dry,GE_WSM2420D3WW_wash,Kenmore_665.13242K900,Kenmore_790.91312013,Rheem_XE40M12ST45U1,Roomba_880,Tesla_S}"
					+ "\n"
					+ "@data";
			myWriter.write(header+"\n\n");
			// Generate training data from timeseries
			for(String device : lp.record.keySet()) {
				TreeMap<Integer, String> record_time_map = lp.record.get(device);
				Integer prev = -1;
				for(Integer mm : record_time_map.keySet()) {
					if(prev == -1) {
						prev = mm;
						continue;
					}
					int duration = mm - prev;
					int week_day = (prev / (60*24)) % 7;
					int day_number = (prev / (60*24));
					int hour = (prev / 60) % 24;
					int min = prev % 60;
					String sensor_action = record_time_map.get(prev);
					myWriter.write(hour+","+min+","+week_day+","+sensor_action+","+device+ "\n");
					prev = mm;
				}
			}
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	/*
	 * Function generates the training data
	 *
	 * @param  logHeaders   all the attributes separated by comma 
	 * @return void
	 */
	public void generatTrainingData(String logHeaders, String logFileName) throws FileNotFoundException, IOException{
		
		LogParser lp = new LogParser(logFileName);
		try {
			FileWriter myWriter = new FileWriter("trainingdata_" + logFileName +".csv");
			myWriter.write(logHeaders+",duration\n");
			// Generate training data from timeseries
			for(String device : lp.record.keySet()) {
				TreeMap<Integer, String> record_time_map = lp.record.get(device);
				Integer prev = -1;
				for(Integer mm : record_time_map.keySet()) {
					if(prev == -1) {
						prev = mm;
						continue;
					}
					int duration = mm - prev;
					int week_day = (prev / (60*24)) % 7;
					int day_number = (prev / (60*24));
					int hour = (prev / 60) % 24;
					int min = prev % 60;
					String sensor_action = record_time_map.get(prev);
					myWriter.write(day_number+","+hour+","+min+","+week_day+","+sensor_action+","+device+","+duration+ "\n");
					prev = mm;
				}
			}
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	public void generateSchedules() throws FileNotFoundException, IOException{
		try {
			SHDU.setupLogging("test.log");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Smart Home Device Schedule Generator!");
		SHDU.log.info("*************** Experimental Run Log ***************");
		
		this.booSchedulePrediction = true;
		
		SHDU.log.info("# Simulate data for 1 month to create histogram");
		SHDU.log.info(this.getLogHeaders());
		int min = 0;
		for(; min < 4*7*24*60; min++) {
			Observer.updateTime(min);
			this.simulateMinute(false);
			if(min % (24*60) == 0) {
				this.readPreferences(); // sample new preferences for the next day
				this.resetSensor(); // reset all the sensor
			} 
		}
		
		// Predict the schedule for the devices
		SHDU.log.info("\n\n# Simulate data for 1 week to use histogram to predict schedule");
		for(; min < 5*7*24*60; min++) {
			if(min % (24*60) == 0) {
				//house.readPreferences(); // sample new preferences for the next day
				this.setDeviceEvents();
				this.resetSensor(); // reset all the sensor
			}
			Observer.updateTime(min);
			this.simulateMinuteByEvents(false);
		}
		
		// generate Schedules for a day and calculate the satisfaction
		System.out.println("\n\nUnsatisfied minutes ?" + " out of total " + 7*24*60 + " mins");
	}

}
