import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.JSONArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class House {
	public JSONObject devices;
	public static int SMALL_HOUSE = 0;
	public static int MEDIUM_HOUSE = 1;
	public static int LARGE_HOUSE = 2;
	public HashMap<String, HashMap<String, ArrayList<String>>> preferenceMap = new HashMap<>(); //<device, <sensing_prob, [rules]>>
	public HashMap<String, HashMap<String,Double>> sensing_prop_sensor_map = new HashMap<>(); //<sensing_prop, <sensor, currentvalue>>
	public HashMap<String, Double> min_sensor_property = new HashMap<>(); //<device+sensing_prob, min_val>
	public HashMap<String, Double> max_sensor_property = new HashMap<>(); //<device+sensing_prob, max_val>
	public HashMap<String, Integer> active_preference_for_device = new HashMap<>(); //<device, action>
	public HashMap<String, String> current_device_action = new HashMap<>(); // <device, current_action>
	
	public House () {
		this.devices = convertDevices(readDevices(), 1);
		readPreferences();
	}
	
	public String getLogString() {
		StringBuilder sb = new StringBuilder();
		sb.append("time " + Observer.cur_hour_of_day+":"+Observer.cur_min_of_hour);
		sb.append("," + Observer.cur_day_of_week);
		sb.append("," + Observer.cur_day_number);		
		for(String key : sensing_prop_sensor_map.keySet()) {
			for(String key2 : sensing_prop_sensor_map.get(key).keySet())
				sb.append("," + key + " " + key2 + " " + Utilities.round(sensing_prop_sensor_map.get(key).get(key2),2));
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
					int target_min = getTargetTimeInMin(targets[3]);
					int current_min = Observer.getCurrentTimeInMin(); 
					if(devices.getJSONObject(device).get("subtype").equals("light") && 
							(targets[2].equals("at") && current_min == target_min))
					{
						SHDU.log.info(Observer.getCurrentTime() + "," + device + "," + targets[1]);
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
							// New action applied and change device status
							SHDU.log.info(Observer.getCurrentTime() + "," + device + "," + applied_device_action);
							active_preference_for_device.put(device, cur_pref_id);
							current_device_action.put(device, applied_device_action);
						}
						return;
					}
					// check if both actions (current device action and preference rule action) are same
					else if(active_preference == cur_pref_id){
						SHDU.log.info(Observer.getCurrentTime() + "," + device + "," + "off");
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
							SHDU.log.info(Observer.getCurrentTime() + "," + device + "," + applied_device_action);
							active_preference_for_device.put(device, cur_pref_id);
							current_device_action.put(device, applied_device_action);
						}
						return;
					}
					else if(active_preference == cur_pref_id){
						SHDU.log.info(Observer.getCurrentTime() + "," + device + "," + "off");
						active_preference_for_device.put(device, -1);
						current_device_action.put(device, "off");
					}
				}
			}
			
			// Doesn't satisfy the preference condition : the device should remain at the current status
			// what is this condition for?
			/*else if(!current_device_action.equals("off")){
						SHDU.log.info(Observer.getCurrentTime() + "," + device + "," + "off");
						current_device_status.put(device, "off");
					}
			 * 
			*/
			sensor_pref_id += sensor_offset;
		}
		
	}

	
	public void changeCurrentStateByDelta(boolean print) {
		for(String device : current_device_action.keySet()) {
			String cur_action = current_device_action.get(device);
			JSONObject dev = (JSONObject) devices.get(device);
			if(dev.getString("subtype").equals("light")) {
				
			}
			else if(dev.getString("type").equals("actuator")) {
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
		JSONArray sensors = (JSONArray) device.get("sensors");
		for(int j = 0; j < sensors.length(); j++) {
			String sensor = sensors.get(j).toString();
			if(sensing_prop_sensor_map.get(sensor_property).containsKey(sensor)) {
				return sensing_prop_sensor_map.get(sensor_property).get(sensor);
			}
		}
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
                if(sensor_property.equals(e.get("property")) && (delta > 0 && delta_needed > 0) || (delta < 0 && delta_needed < 0) || (delta == 0 && delta_needed == 0))
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
	

	
	public static String [] getParsedActiveRule (String rule) {
		String [] parsedRule = new String [3];
		
		int cur_index = 2;
		int start_index = 2;
		while(rule.charAt(cur_index) != ' ')
			cur_index++;
		
		parsedRule[0] = rule.substring(start_index, cur_index);
		cur_index++;
		
		start_index = cur_index;
		while(rule.charAt(cur_index) != ' ')
			cur_index++;
		parsedRule[1] = rule.substring(start_index, cur_index);
		cur_index++;
		
		parsedRule[2] = rule.substring(cur_index);
		return parsedRule;
	}
	
	public void readPreferences() {
		JSONParser parser = new JSONParser();
		try {
			String content = Utilities.readFile(Parameters.getPreferencesPath());
			JSONObject jsonObject = new JSONObject(content.trim());
			JSONArray rules = (JSONArray)jsonObject.get("rules");
			Iterator<Object> iterator = rules.iterator();
			// Iterate through each device activity distribution 
			while (iterator.hasNext()) {
				String rule = iterator.next().toString();
				String [] parsedRule = getParsedActiveRule(rule);
				if(rule.charAt(0) == '#') {
					// skip
				}
				else if(rule.charAt(0) == '1') {
					// Active rule
					HashMap<String, ArrayList<String>> devRules = preferenceMap.get(parsedRule[0]);
					if(devRules == null)
						devRules = new HashMap<>();
					
					ArrayList<String> effectRules = devRules.get(parsedRule[1]);
					if(effectRules == null)
						effectRules = new ArrayList<>();
					
					effectRules.add(parsedRule[2]);
					devRules.put(parsedRule[1], effectRules);
					preferenceMap.put(parsedRule[0], devRules);
				}
				else if(rule.charAt(0) == '0') {
					// Save the passive rule
					String [] condition = parsedRule[2].split(" ");
					if(condition[0].equals("leq"))
						max_sensor_property.put(parsedRule[0]+parsedRule[1], Double.parseDouble(condition[1]));
					else if(condition[0].equals("geq"))
						min_sensor_property.put(parsedRule[0]+parsedRule[1], Double.parseDouble(condition[1]));
				}
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
    
	private JSONObject convertDevices(JSONArray devices, int granularity) {
		for(int i = 0; i < devices.length(); i++) {
			Iterator<?> d_keys = devices.getJSONObject(i).keys();
			while(d_keys.hasNext()) {
				String d_name = (String) d_keys.next();
				
				//HashMap<String, HashMap<String, Double>> actionEffects = new HashMap<>();
				
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
		return (JSONObject) devices.get(SMALL_HOUSE);
	}
}
