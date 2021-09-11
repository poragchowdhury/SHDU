package SPADE;
import java.io.File;         
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.ArrayList;

public class Spade {
	public static void main(String[] args) throws FileNotFoundException {
		/*
		File myFile = new File("canvas.csv");
		Scanner scnr = new Scanner(myFile);

		String sequence = "";
		String line = scnr.nextLine();
		String[] attribute = line.split(","); // 23 sensors

		for (int i = 5; i < attribute.length - 2; i++) {
			if (attribute[i].equals("ON") || attribute[i].equals("PRESENT") || (!attribute[i].equals("0.0") && isDouble(attribute[i])))
				sequence += (char)(60 + i);
		}

		while (scnr.hasNextLine()) {
			String[] previousAttr = line.split(",");
			line = scnr.nextLine();
			attribute = line.split(","); // attributes are what I called the sensor data points

			for (int i = 5; i < attribute.length - 2; i++) {
				if (previousAttr[i].equals("ON") || previousAttr[i].equals("PRESENT") || (!previousAttr[i].equals("0.0") && isDouble(previousAttr[i]))) {
					if (attribute[i].equals("OFF") || attribute[i].equals("ABSENT") || attribute[i].equals("0.0")) {
						sequence += (char)(60 + i + 32);
					}
				}
				else if (previousAttr[i].equals("OFF") || previousAttr[i].equals("ABSENT") || previousAttr[i].equals("0.0")) {
					if (attribute[i].equals("ON") || attribute[i].equals("PRESENT") || (!attribute[i].equals("0.0") && isDouble(attribute[i]))) {
						sequence += (char)(60 + i);
					}
				}
			}
		}
		*/
		String sequence = "ABCDaAaABCDaAaABCDaAaABCDaAa";
		test(sequence);
	}

	public static void test(String seq) {
		Tree tree = new Tree();
		int totalAttemptCount = 0, correctAttemptCount = 0;
		char predicted_event = ' ';
		double maxProb = -100;
		String window = "";
		for(int i = 0; i < seq.length(); i++) {
			// The actual event at current timestep
			char V_OFF = seq.charAt(i); 
			
			// Make prediction from the tree
			maxProb = -100;
			for (char event : tree.root.children.keySet()) {
				if(window.equals("") || event != V_OFF)	{
					double prob = tree.getProbability(window, event);
					if (prob > maxProb) {
						predicted_event = event;
						maxProb = prob;
					}
				}
			}
			// Checking the prediction
			if(predicted_event != ' ') {
				if(V_OFF == predicted_event)
					correctAttemptCount++;
				totalAttemptCount++;
			}
			
			
			// find the opposite of cur_event in sequence
			if (Character.isLowerCase(V_OFF)) {
				// V_OFF is on OFF_STATE
				char V = Character.toUpperCase(V_OFF);
				int last_index_V = i;
				while(last_index_V >= 0) {
					if(seq.charAt(last_index_V) == V)
						break;
					last_index_V--;
				}
				
				if(last_index_V != -1)
					tree.genContext(seq.substring(last_index_V, i+1));
			}
		}
		
		if(totalAttemptCount != 0)
			System.out.println("Accuracy: " + (double) correctAttemptCount / totalAttemptCount * 100 + "%");
		else
			System.out.println("totalAttemptCount = 0");
	}

	public static void sort(TreeNode[] nodes) {
		// NEED TO IMPLEMENT BETTER SORTING - current : bubble sort
	    for (int i = 0; i < nodes.length - 1; i++) {
	    	for (int j = 0; j < nodes.length - i - 1; j++) {
	    		if (nodes[j].probability > nodes[j+1].probability) {
	    			TreeNode temp = nodes[j];
                    nodes[j] = nodes[j+1];
                    nodes[j+1] = temp;
	    		}
	    	}
	    }
	}

	public static ArrayList<String> getEpisodes(String sequence) {
		ArrayList<String> arr = new ArrayList<String>();
		for (int i = 0; i < sequence.length(); i++) {
			char c = sequence.charAt(i);
			if (Character.isUpperCase(c)) {
				String seq = sequence.substring(i);
				int charLoc = seq.indexOf(Character.toLowerCase(c));
				if (charLoc != -1) {
					arr.add(sequence.substring(i,charLoc+i+1));
				}
			}
		}
		return arr;
	}

	public static boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		}
		catch (NumberFormatException ex) {return false;}
	}

	// Calculate Probability
}