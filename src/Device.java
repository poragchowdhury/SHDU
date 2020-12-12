

import java.util.Iterator;

import org.json.simple.JSONArray;

//import org.json.JSONArray;
//import org.json.JSONObject;

public class Device {
	public String name;
	public double powerKW;
	public double startTS;
	public double endTS;
	public double timeWindow;
	public double runTime;
	public boolean publicUse;
	
	public Device() {}

	public Device(String name, String kw) {
		this.name = name;
		this.powerKW = Double.parseDouble(kw);
	}

}
