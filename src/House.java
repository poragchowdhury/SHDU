import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.JSONArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class House {
	public static JSONArray devices;
	public static HashMap<String, HashMap<String, ArrayList<String>>> rulesMap = new HashMap<>();
	public House () {
		devices = convertDevices(readDevices(), 1);
		readPreferences();
	}
	
	public static void main (String [] args) {
		devices = convertDevices(readDevices(), 1);
		JSONObject house = (JSONObject) devices.get(0);
		JSONObject device = (JSONObject) house.get("Dyson_AM09");
		JSONObject actions = (JSONObject) device.get("actions");
		JSONObject heat = (JSONObject) actions.get("heat");
		JSONArray effects = (JSONArray) heat.get("effects");
		for(int j = 0; j < effects.length(); j++) {
			JSONObject e = effects.getJSONObject(j);
			System.out.println(e.getString("property") + " = " + e.getDouble("delta") / 60.0 * 1);
		}
		
		JSONObject sensor = (JSONObject) house.get("Kenmore_790_sensor");
		double currentState = sensor.getDouble("current_state");
		System.out.println(currentState);
		readPreferences();
		System.out.println("HI");
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
	
	public static void readPreferences() {
		JSONParser parser = new JSONParser();
		try {
			String content = Utilities.readFile(Parameters.getPreferencesPath());
			JSONObject jsonObject = new JSONObject(content.trim());
			JSONArray rules = (JSONArray)jsonObject.get("rules");
			Iterator<Object> iterator = rules.iterator();
			// Iterate through each device activity distribution 
			while (iterator.hasNext()) {
				String rule = iterator.next().toString();
				if(rule.charAt(0) == '1') {
					// Active rule
					String [] parsedRule = getParsedActiveRule(rule);
					
					HashMap<String, ArrayList<String>> devRules = rulesMap.get(parsedRule[0]);
					if(devRules == null)
						devRules = new HashMap<>();
					
					ArrayList<String> effectRules = devRules.get(parsedRule[1]);
					if(effectRules == null)
						effectRules = new ArrayList<>();
					
					effectRules.add(parsedRule[2]);
					devRules.put(parsedRule[1], effectRules);
					rulesMap.put(parsedRule[0], devRules);
				}
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

    private static JSONArray readDevices() {
        try {
            String content = Utilities.readFile(Parameters.getDeviceDictionaryPath());
            JSONArray jArray = new JSONArray(content.trim());
            return jArray;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
	private static JSONArray convertDevices(JSONArray devices, int granularity) {
		for(int i = 0; i < devices.length(); i++) {
			Iterator<?> d_keys = devices.getJSONObject(i).keys();
			while(d_keys.hasNext()) {
				String d_name = (String) d_keys.next();
				//HashMap<String, HashMap<String, Double>> actionEffects = new HashMap<>();
				
				JSONObject dev = devices.getJSONObject(i).getJSONObject(d_name);
				if(dev.getString("type").equals("actuator")) {
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
				devices.put(i, devices.getJSONObject(i).put(d_name, dev));
			}
			
		}

		return devices;
	}
}
