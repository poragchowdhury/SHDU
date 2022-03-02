import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import M_SPEED.M_SPEED;
import SPEED.Speed;
import SPADE.Spade;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class Experiment {
	public enum HOUSE_SIZE {SMALL, MEDIUM, LARGE}; 

	public static void main (String[] args) throws FileNotFoundException, IOException {
		String [] prefs = new String[]{"inputs/AAMAS/Preferences1.json","inputs/AAMAS/Preferences2.json","inputs/AAMAS/Preferences3.json", "inputs/AAMAS/Preferences4.json",
		"inputs/AAMAS/Preferences5.json","inputs/AAMAS/Preferences6.json","inputs/AAMAS/Preferences7.json","inputs/AAMAS/Preferences8.json","inputs/AAMAS/Preferences9.json",
		"inputs/AAMAS/Preferences10.json","inputs/AAMAS/Preferences11.json","inputs/AAMAS/Preferences12.json","inputs/AAMAS/Preferences13.json","inputs/AAMAS/Preferences14.json",
		"inputs/AAMAS/Preferences15.json","inputs/AAMAS/Preferences16.json","inputs/AAMAS/Preferences17.json","inputs/AAMAS/Preferences18.json","inputs/AAMAS/Preferences19.json",
		"inputs/AAMAS/Preferences20.json"}; 
		for (String pref: prefs) {	
			// declare all variable 
			SHDU shdu = new SHDU();
			M_SPEED m_speed = new M_SPEED();
			Speed speed = new Speed();
			Spade spade = new Spade();
			String trainSeqMSpeed;
			String testSeqMSpeed;
//			String trainSeqSpeed;
//			String testSeqSpeed;
//			String trainSeqSpade;
//			String testSeqSpade;
			String trainSeqSpeedSpade;
			String testSeqSpeedSpade; 
			 
			System.out.println("Training Data for " + pref);
			House.SEED = 1;
			shdu.run(pref);
			trainSeqMSpeed = shdu.house.Sequence.toString();
			trainSeqSpeedSpade = speed.getSequence(shdu.house.Sequence.toString());
			
			
			System.out.println("Testing Data for " + pref);
			House.SEED = 2;
			shdu.run(pref);
			testSeqMSpeed = shdu.house.Sequence.toString();
			testSeqSpeedSpade = speed.getSequence(shdu.house.Sequence.toString());		
			
			// test data for m_speed
			System.out.println("M_speed for " + pref);
			m_speed.run(trainSeqMSpeed, false);
			m_speed.run(testSeqMSpeed, true);
			//System.out.println(trainSeqMSpeed);
			
			try {
				FileWriter fw = new FileWriter("m_speedData.txt", true);
				PrintWriter pw = new PrintWriter(fw);
				pw.println("M_SPEED: " + pref + " " + m_speed.accuracy + "%");		
				pw.close();
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block				
				e.printStackTrace();
			}
			 
			// test data for speed	
			System.out.println("Speed for " + pref);
			speed.run(trainSeqSpeedSpade, false);
			speed.run(testSeqSpeedSpade , true);
			
			try {
				FileWriter fw = new FileWriter("speedData.txt", true);
				PrintWriter pw = new PrintWriter(fw);
				pw.println("SPEED: " + pref + " " + speed.accuracy + "%");
				pw.close();
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			
			// test data for spade
			System.out.println("Spade for " + pref);
			spade.run(trainSeqSpeedSpade, false);
			spade.run(testSeqSpeedSpade , true);
									
			try {
				FileWriter fw = new FileWriter("spadeData.txt", true);
				PrintWriter pw = new PrintWriter(fw);
				pw.println("SPADE: " + pref + " " + spade.accuracy + "%");
				pw.close();
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(pref + " is done");
					
		}
	}
	
	

}
