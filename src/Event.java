
public class Event {
	public String device_name;
	public String type;
	public String mean;
	public String std;
	public String intensity;
	public String power;
	
	public String toString() {
		return "name:" + device_name + ",type:" + type + ",mean:" + mean + ",std:" + std + ",intensity:" + intensity + ",power:" + power; 
	}
	
	public Event(String d, String t, String m, String s, String i, String p) {
		this.device_name = d;
		this.type = t;
		this.mean = m;
		this.std = s;
		this.intensity = i;
		this.power = p;
	}
}
