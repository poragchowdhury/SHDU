
public class Event {
	public String device_name;
	public String type;
	public String mean;
	public String std;
	public String action;
	public String power;
	
	/*Sensor data*/
	public String event_data;
	public int min;
	public int frequency;
	
	public String toString() {
		return "name:" + device_name + ",type:" + type + ",mean:" + mean + ",std:" + std + ",action:" + action + ",power:" + power; 
	}
	
	public Event(String data, String device, int min) {
		this.event_data = data;
		this.device_name = device;
		this.min = min;
		this.frequency = 1;
	}
	
	public Event(String d, String t, String m, String s, String a, String p) {
		this.device_name = d;
		this.type = t;
		this.mean = m;
		this.std = s;
		this.action = a;
		this.power = p;
	}
}
