package weka;

import java.util.Random;

import weka.ModelGenerator;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.trees.RandomForest;
import weka.core.Debug;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

public class Test {

    public static final String TRAINDATASETPATH = "/datasets/large/traindata_action"; // do this for medium and small also
    public static final String TESTDATASETPATH = "/datasets/large/testdata_action"; // do this for medium and small also
    public static final String MODElPATH = "rf.model";

    public static void main(String[] args) throws Exception {
        for(int i = 1; i <= 3; i++) {
	        ModelGenerator mg = new ModelGenerator();
	
	        Instances trainingdataset = mg.loadDataset(TRAINDATASETPATH+i+".arff");
	        Instances testingdataset = mg.loadDataset(TESTDATASETPATH+i+".arff");
	
	        Filter filter = new Normalize();
	
	        trainingdataset.randomize(new Debug.Random(1));
	        
	        //Normalize dataset
	        filter.setInputFormat(trainingdataset);
	        Instances datasetnor1 = Filter.useFilter(trainingdataset, filter);
	        Instances datasetnor2 = Filter.useFilter(testingdataset, filter);
	
	        Instances traindataset = new Instances(datasetnor1);
	        Instances testdataset = new Instances(datasetnor2);
	
	        // build classifier with train dataset             
	        RandomForest rf = (RandomForest) mg.buildClassifier(traindataset);
	        
	        //Evaluation eval = new Evaluation(traindataset);
	        //System.out.println("2 fold cross validation...");
	        //eval.crossValidateModel(rf, traindataset, 2, new Random(1));
	        //System.out.println("Estimated Accuracy: "+Double.toString(eval.pctCorrect()));
	        // Evaluate classifier with test dataset
	        
	        System.out.println("Evaluation with test data...");
	        String evalsummary = mg.evaluateModel(rf, traindataset, testdataset);
	        System.out.println("Evaluation: " + evalsummary);
	
	        //Save model 
	        mg.saveModel(rf, MODElPATH);
        }
    }

}