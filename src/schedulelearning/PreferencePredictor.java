package schedulelearning;

import java.util.ArrayList;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class PreferencePredictor {
	private Classifier durationPredictor;
	private Classifier intensityPredictor;
	public static String [] days = {"sat", "sun", "mon", "tue", "wed", "thu", "fri"};
	protected Instances duration_data;
	protected Instances intensity_data;

	public PreferencePredictor() {

		try {
			//this.durationPredictor = (Classifier) SerializationHelper.read(new FileInputStream(durationModel));
			loadDurationModel();
			loadIntensityModel();
			System.out.println("Model loaded successfully!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main (String [] args) {
		PreferencePredictor pp = new PreferencePredictor();
		System.out.println(days[5] + " 23:38" + " actual intensity " + 0 + " actual duration " + 1095);
		System.out.println(days[5] + " 23:38" + " predicted intensity " + pp.predictIntensity(5, 23, 38) + " predicted duration " + pp.predictDuration(5, 23, 38) + "\n\n");
		
		System.out.println(days[6] + " 23:29" + " actual intensity " + 0 + " actual duration " + 1060);
		System.out.println(days[6] + " 23:29" + " predicted intensity " + pp.predictIntensity(6, 23, 29) + " predicted duration " + pp.predictDuration(6, 23, 29) + "\n\n");
		
		System.out.println(days[1] + " 16:56" + " actual intensity " + 100 + " actual duration " + 436);
		System.out.println(days[1] + " 16:56" + " predicted intensity " + pp.predictIntensity(1, 16, 56) + " predicted duration " + pp.predictDuration(1, 16, 56) + "\n\n");

		//5,22,11,50,96
		System.out.println(days[5] + " 22:11" + " actual intensity " + 50 + " actual duration " + 96);
		System.out.println(days[5] + " 22:11" + " predicted intensity " + pp.predictIntensity(5, 22, 11) + " predicted duration " + pp.predictDuration(5, 22, 11) + "\n\n");

		//2,17,25,100,282
		System.out.println(days[2] + " 17:25" + " actual intensity " + 100 + " actual duration " + 282);
		System.out.println(days[2] + " 17:25" + " predicted intensity " + pp.predictIntensity(2, 17, 25) + " predicted duration " + pp.predictDuration(2, 17, 25) + "\n\n");

		//6,22,40,50,60
		System.out.println(days[6] + " 22:40" + " actual intensity " + 50 + " actual duration " + 60);
		System.out.println(days[6] + " 22:40" + " predicted intensity " + pp.predictIntensity(6, 22, 40) + " predicted duration " + pp.predictDuration(2, 17, 25) + "\n\n");

		
	}
	public void loadIntensityModel(){

		Attribute week_day = new Attribute("week_day");
		Attribute hour = new Attribute("hour");
		Attribute min = new Attribute("min");
		Attribute intensity = new Attribute("intensity");

		ArrayList<Attribute> attributes = new ArrayList<>();
		attributes.add(week_day);
		attributes.add(hour);
		attributes.add(min);
		attributes.add(intensity);

		this.intensity_data = new Instances("Dataset", attributes, 0);
		this.intensity_data.setClassIndex(this.intensity_data.numAttributes() - 1);


		try {
			intensityPredictor = (Classifier) SerializationHelper.read("exp1_intensity_prediction.model");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void loadDurationModel(){

		Attribute week_day = new Attribute("week_day");
		Attribute hour = new Attribute("hour");
		Attribute min = new Attribute("min");
		Attribute duration = new Attribute("duration");

		ArrayList<Attribute> attributes = new ArrayList<>();
		attributes.add(week_day);
		attributes.add(hour);
		attributes.add(min);
		attributes.add(duration);

		this.duration_data = new Instances("Dataset", attributes, 0);
		this.duration_data.setClassIndex(this.duration_data.numAttributes() - 1);


		try {
			durationPredictor = (Classifier) SerializationHelper.read("exp1_duration_prediction.model");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public double predictIntensity(int week_day, int hour, int min) {
		double intensity = 0;
		try {
			Instance myIns = getIntensityInstance(week_day, hour, min);
			if (myIns == null)
				System.out.println("*****************My Ins Null : ");

			intensity = this.intensityPredictor.classifyInstance(myIns);


		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :" + e.toString());
		}

		return intensity;

	}
	
	public double predictDuration(int week_day, int hour, int min) {
		double duration = 0;
		try {
			Instance myIns = getDurationInstance(week_day, hour, min);
			if (myIns == null)
				System.out.println("*****************My Ins Null : ");

			duration = this.durationPredictor.classifyInstance(myIns);


		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :" + e.toString());
		}

		return duration;

	}


	public Instance getDurationInstance(int week_day, int hh, int mm){

		Instance testInstance = null;

		double[] values = new double[duration_data.numAttributes()];

		try {
			// Day_date
			values[0] = week_day;
			// Month_date
			values[1] = hh;
			// day
			values[2] = mm;
			// Hour

			values[3] = 0;

			this.duration_data.clear();
			this.duration_data.add(new DenseInstance(1.0, values));
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

	public Instance getIntensityInstance(int week_day, int hh, int mm){

		Instance testInstance = null;

		double[] values = new double[intensity_data.numAttributes()];

		try {
			// Day_date
			values[0] = week_day;
			// Month_date
			values[1] = hh;
			// day
			values[2] = mm;
			// Hour

			values[3] = 0;

			this.intensity_data.clear();
			this.intensity_data.add(new DenseInstance(1.0, values));
			//testInstance = data.lastInstance();
			testInstance = intensity_data.firstInstance();

			//System.out.println("Data to string : " + myIns.toString());

			//System.out.println("!!!!!!!!!!!!!!!!!!!!!Predicted clearing price : " + clearingPrice);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :" + e.toString());
		}
		return testInstance;
	}

}
