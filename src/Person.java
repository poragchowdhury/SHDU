import java.util.HashSet;
import java.util.Random;

public class Person {
	public String id;
	public Person() {}
	public Person(String id) {
		this.id = id;
	}
	
	public double [] simulateUsage(Preference preference, HashSet<String> deviceOnUse) {
		double [] usage = new double[24];
		Random ran = new Random(); 
		/*
		for(Device device : preference.devices) {
			// generating integer 
			if(device.publicUse)
				continue;
			double startTS2 = device.endTS-device.runTime;
			if(startTS2 < 0.0)
				startTS2 += 24;
			
			double meanSTS = device.startTS + (startTS2 - device.startTS)/2;
			double stdSTS = (startTS2 - device.startTS)/2;
			// pick a random sample from a normal distribution
	        double sampledSTS = ran.nextGaussian()*stdSTS+meanSTS;
	        
	        // check the logic here
	        if(sampledSTS < 0)
	        	sampledSTS += 24;
	        sampledSTS %= 24;
	        for(double start = sampledSTS; start < (sampledSTS+device.runTime); start++)
	        	usage[(int)start%24] += device.powerKW;
	        
		}
		*/
		return usage;
	}
}
