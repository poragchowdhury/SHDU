package schedulelearning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class PreferencePredictor {
	public Classifier durationPredictor;
	public Classifier actionPredictor;
	public static String [] days = {"sat", "sun", "mon", "tue", "wed", "thu", "fri"};
	protected Instances duration_data;
	protected Instances action_data;
	public ArrayList<Attribute> action_attributes;
	public ArrayList<Attribute> duration_attributes;
	public FastVector<String> all_devices;
	public FastVector<String> all_actions;
	public void loadAllNominals() {
		all_actions = new FastVector<String>(11);
		all_actions.addElement("BR100");
		all_actions.addElement("BR0");
		all_actions.addElement("heat");
		all_actions.addElement("off");
		all_actions.addElement("regular");
		all_actions.addElement("wash");
		all_actions.addElement("bake");
		all_actions.addElement("BR50");
		all_actions.addElement("charge");
		all_actions.addElement("vacuum");
		all_actions.addElement("charge_48a");
		
		all_devices = new FastVector<String>(11);
		all_devices.addElement("Dinning_room_light");
		all_devices.addElement("Dyson_AM09");
		all_devices.addElement("GE_WSM2420D3WW_dry");
		all_devices.addElement("GE_WSM2420D3WW_wash");
		all_devices.addElement("Kenmore_665.13242K900");
		all_devices.addElement("Kenmore_790.91312013");
		all_devices.addElement("Kitchen_light");
		all_devices.addElement("Living_room_light");
		all_devices.addElement("Rheem_XE40M12ST45U1");
		all_devices.addElement("Roomba_880");
		all_devices.addElement("Tesla_S");

	}
	public PreferencePredictor() {
		loadAllNominals();
		try {
			//this.durationPredictor = (Classifier) SerializationHelper.read(new FileInputStream(durationModel));
			loadActionPredictionModel();
			loadDurationModel();
			System.out.println("Model loaded successfully!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public double [] getActionData(String eventData) {
		String [] arrData = eventData.split(",");
		double [] vals = new double[arrData.length-1+2];
		Arrays.fill(vals, 0);
		for(int i = 1; i < arrData.length; i++)
			vals[i-1] = Double.parseDouble(arrData[i]);
		return vals;
	}
	
	public double [] getDurationData(String eventData) {
		String [] arrData = eventData.split(",");
		double [] vals = new double[arrData.length-1+3];
		Arrays.fill(vals, 0);
		for(int i = 1; i < arrData.length; i++)
			vals[i-1] = Double.parseDouble(arrData[i]);
		return vals;
	}
	
	public static void main (String [] args) {
		PreferencePredictor pp = new PreferencePredictor();
		double [] vals_action;
		double [] vals_duration;
		String predicted_action;
		String actual_action;
		double predicted_duration;
		double actual_duration;
		String device;
		// Sample 1: 4,44,3,90.68,54.18,62.13,20.78,0,60,60,0,22.17,54.08,off,Dyson_AM09,17
		vals_action = new double[] {4,44,3,90.68,54.18,62.13,20.78,0,60,60,0,22.17,54.08,0.0,0.0};
		vals_duration = new double[] {4,44,3,90.68,54.18,62.13,20.78,0,60,60,0,22.17,54.08,0.0,0.0,0.0};
		device = "Dyson_AM09";
		predicted_action = pp.predictAction(vals_action,device);
		System.out.println("Test sample 1: 4,44,3,90.68,54.18,62.13,20.78,0,60,60,0,22.17,54.08,off,Dyson_AM09,17");
		System.out.println("Actual action off, Predicted action " + predicted_action);
		predicted_duration = pp.predictDuration(vals_duration,device,predicted_action);
		System.out.println("Actual duration 17, Predicted duration " + predicted_duration + "\n\n");
				
		
		// Sample 2: 3,59,6,90.68,54.18,62.13,20.34,0,60,60,0,16.02,54.08,off,GE_WSM2420D3WW_wash,1375
		vals_action = new double[] {3,59,6,90.68,54.18,62.13,20.34,0,60,60,0,16.02,54.08,0.0,0.0};
		vals_duration = new double[] {3,59,6,90.68,54.18,62.13,20.34,0,60,60,0,16.02,54.08,0.0,0.0,1375};
		predicted_action = pp.predictAction(vals_action,"GE_WSM2420D3WW_wash");
		System.out.println("Test sample 2: 3,59,6,90.68,54.18,62.13,20.34,0,60,60,0,16.02,54.08,off,GE_WSM2420D3WW_wash,1375");
		System.out.println("Actual action off, Predicted action " + predicted_action);
		predicted_duration = pp.predictDuration(vals_duration,"GE_WSM2420D3WW_wash",predicted_action);
		System.out.println("Actual duration 1375, Predicted duration " + predicted_duration + "\n\n");
		
		//6,58,1,90.68,54.18,61.93,22.1,60,60,60,60,22.1,54.08,off,Kenmore_790.91312013,1391
		vals_action = new double[] {6,58,1,90.68,54.18,61.93,22.1,60,60,60,60,22.1,54.08,0.0,0.0};
		vals_duration = new double[] {6,58,1,90.68,54.18,61.93,22.1,60,60,60,60,22.1,54.08,0.0,0.0,0.0};
		device = "Kenmore_790.91312013";
		actual_action = "off";
		actual_duration = 1391;
		predicted_action = pp.predictAction(vals_action,device);
		System.out.println("Test sample 3: 6,58,1,90.68,54.18,61.93,22.1,60,60,60,60,22.1,54.08,off,Kenmore_790.91312013,1391");
		System.out.println("Actual action " + actual_action + ", Predicted action " + predicted_action);
		predicted_duration = pp.predictDuration(vals_duration,device,predicted_action);
		System.out.println("Actual duration "+actual_duration+", Predicted duration " + predicted_duration + "\n\n");
		
		String sample = "0,1,2,70,30,50,18,0,0,0,0,18,0,charge_48a,Tesla_S,107";
		vals_action = new double[] {0,1,2,70,30,50,18,0,0,0,0,18,0,0,0};
		vals_duration = new double[] {0,1,2,70,30,50,18,0,0,0,0,18,0,0,0,0};
		device = "Tesla_S";
		actual_action = "charge_48a";
		actual_duration = 107;
		predicted_action = pp.predictAction(vals_action,device);
		System.out.println(sample);
		System.out.println("Actual action " + actual_action + ", Predicted action " + predicted_action);
		predicted_duration = pp.predictDuration(vals_duration,device,predicted_action);
		System.out.println("Actual duration "+actual_duration+", Predicted duration " + predicted_duration + "\n\n");
		
	}
	public void loadActionPredictionModel(){
		Attribute hour = new Attribute("cur_hour_of_day");
		Attribute min = new Attribute("cur_min_of_hour");
		Attribute week_day = new Attribute("cur_day_of_week");
		Attribute charge_iRobot_651_battery = new Attribute("charge_iRobot_651_battery");
		Attribute charge_Tesla_S_battery = new Attribute("charge_Tesla_S_battery");
		Attribute water_temp_water_heat_sensor = new Attribute("water_temp_water_heat_sensor");
		Attribute temperature_cool_thermostat_cool = new Attribute("temperature_cool_thermostat_cool");
		Attribute bake_Kenmore_790_sensor = new Attribute("bake_Kenmore_790_sensor");
		Attribute dish_wash_Kenmore_665_sensor = new Attribute("dish_wash_Kenmore_665_sensor");
		Attribute laundry_wash_GE_WSM2420D3WW_wash_sensor = new Attribute("laundry_wash_GE_WSM2420D3WW_wash_sensor");
		Attribute laundry_dry_GE_WSM2420D3WW_dry_sensor = new Attribute("laundry_dry_GE_WSM2420D3WW_dry_sensor");
		Attribute temperature_heat_thermostat_heat = new Attribute("temperature_heat_thermostat_heat");
		Attribute cleanliness_dust_sensor = new Attribute("cleanliness_dust_sensor");
		Attribute action = new Attribute("action", all_actions);
		Attribute device = new Attribute("device", all_devices);
		
		action_attributes = new ArrayList<>();
		action_attributes.add(hour);
		action_attributes.add(min);
		action_attributes.add(week_day);
		action_attributes.add(charge_iRobot_651_battery);
		action_attributes.add(charge_Tesla_S_battery);
		action_attributes.add(water_temp_water_heat_sensor);
		action_attributes.add(temperature_cool_thermostat_cool);
		action_attributes.add(bake_Kenmore_790_sensor);
		action_attributes.add(dish_wash_Kenmore_665_sensor);
		action_attributes.add(laundry_wash_GE_WSM2420D3WW_wash_sensor);
		action_attributes.add(laundry_dry_GE_WSM2420D3WW_dry_sensor);
		action_attributes.add(temperature_heat_thermostat_heat);
		action_attributes.add(cleanliness_dust_sensor);
		action_attributes.add(action);
		action_attributes.add(device);
        
		/*
		//cur_hour_of_day
        //cur_min_of_hour
        //cur_day_of_week
        //charge_iRobot_651_battery
        //charge_Tesla_S_battery
        //water_temp_water_heat_sensor
        //temperature_cool_thermostat_cool
        //bake_Kenmore_790_sensor
        //dish_wash_Kenmore_665_sensor
        laundry_wash_GE_WSM2420D3WW_wash_sensor
        laundry_dry_GE_WSM2420D3WW_dry_sensor
        temperature_heat_thermostat_heat
        cleanliness_dust_sensor
        action
        device
        */

		this.action_data = new Instances("Dataset", action_attributes, 0);
		this.action_data.setClassIndex(this.action_data.numAttributes() - 2);


		try {
			actionPredictor = (Classifier) SerializationHelper.read("REPTREE_all_features_action_prediction.model");//random_forest_all_features_action_prediction.model");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void loadDurationModel(){

		Attribute hour = new Attribute("cur_hour_of_day");
		Attribute min = new Attribute("cur_min_of_hour");
		Attribute week_day = new Attribute("cur_day_of_week");
		Attribute charge_iRobot_651_battery = new Attribute("charge_iRobot_651_battery");
		Attribute charge_Tesla_S_battery = new Attribute("charge_Tesla_S_battery");
		Attribute water_temp_water_heat_sensor = new Attribute("water_temp_water_heat_sensor");
		Attribute temperature_cool_thermostat_cool = new Attribute("temperature_cool_thermostat_cool");
		Attribute bake_Kenmore_790_sensor = new Attribute("bake_Kenmore_790_sensor");
		Attribute dish_wash_Kenmore_665_sensor = new Attribute("dish_wash_Kenmore_665_sensor");
		Attribute laundry_wash_GE_WSM2420D3WW_wash_sensor = new Attribute("laundry_wash_GE_WSM2420D3WW_wash_sensor");
		Attribute laundry_dry_GE_WSM2420D3WW_dry_sensor = new Attribute("laundry_dry_GE_WSM2420D3WW_dry_sensor");
		Attribute temperature_heat_thermostat_heat = new Attribute("temperature_heat_thermostat_heat");
		Attribute cleanliness_dust_sensor = new Attribute("cleanliness_dust_sensor");
		Attribute action = new Attribute("action", all_actions);
		Attribute device = new Attribute("device", all_devices);
		Attribute duration = new Attribute("duration");
		
		duration_attributes = new ArrayList<>();
		duration_attributes.add(hour);
		duration_attributes.add(min);
		duration_attributes.add(week_day);
		duration_attributes.add(charge_iRobot_651_battery);
		duration_attributes.add(charge_Tesla_S_battery);
		duration_attributes.add(water_temp_water_heat_sensor);
		duration_attributes.add(temperature_cool_thermostat_cool);
		duration_attributes.add(bake_Kenmore_790_sensor);
		duration_attributes.add(dish_wash_Kenmore_665_sensor);
		duration_attributes.add(laundry_wash_GE_WSM2420D3WW_wash_sensor);
		duration_attributes.add(laundry_dry_GE_WSM2420D3WW_dry_sensor);
		duration_attributes.add(temperature_heat_thermostat_heat);
		duration_attributes.add(cleanliness_dust_sensor);
		duration_attributes.add(action);
		duration_attributes.add(device);
		duration_attributes.add(duration);

		this.duration_data = new Instances("Dataset", duration_attributes, 0);
		this.duration_data.setClassIndex(this.duration_data.numAttributes() - 1);

		try {
			durationPredictor = (Classifier) SerializationHelper.read("random_forest_all_features.model");//multilayer_perceptor_all_features.model");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String predictAction(double [] vals, String device) {
		double action = 0;
		try {
			Instance myIns = getActionInstance(vals, device);
			if (myIns == null)
				System.out.println("*****************My Ins Null : ");

			action = this.actionPredictor.classifyInstance(myIns);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :" + e.toString());
		}

		return all_actions.get((int)action);

	}
	
	public double predictDuration(double [] vals, String device, String action) {
		double duration = 0;
		try {
			Instance myIns = getDurationInstance(vals, device, action);
			if (myIns == null)
				System.out.println("*****************My Ins Null : ");

			duration = this.durationPredictor.classifyInstance(myIns);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :" + e.toString());
		}
		return duration;
	}


	public Instance getDurationInstance(double [] vals, String device, String action){

		Instance testInstance = null;

		try {
			// Day_date
			this.duration_data.clear();
			Instance d = new DenseInstance(vals.length);
			for(int i = 0; i < vals.length; i++) {
				if(i == vals.length-2)
					d.setValue(duration_attributes.get(i), device);
				if(i == vals.length-3)
					d.setValue(duration_attributes.get(i), action);
				else
					d.setValue(duration_attributes.get(i), vals[i]);
			}
			this.duration_data.add(d);
			//testInstance = data.lastInstance();
			testInstance = duration_data.firstInstance();

			//System.out.println("Data to string : " + myIns.toString());

			//System.out.println("!!!!!!!!!!!!!!!!!!!!!Predicted clearing price : " + clearingPrice);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :" + e.toString());
		}
		return testInstance;
	}

	public Instance getActionInstance(double [] vals, String device){

		Instance testInstance = null;

		try {
			this.action_data.clear();
			Instance d = new DenseInstance(vals.length);
			for(int i = 0; i < vals.length; i++) {
				if(i == vals.length-1)
					d.setValue(action_attributes.get(i), device);
				else
					d.setValue(action_attributes.get(i), vals[i]);
			}
			this.action_data.add(d);
			//testInstance = data.lastInstance();
			testInstance = action_data.firstInstance();

			//System.out.println("Data to string : " + myIns.toString());

			//System.out.println("!!!!!!!!!!!!!!!!!!!!!Predicted clearing price : " + clearingPrice);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :" + e.toString());
		}
		return testInstance;
	}

}
