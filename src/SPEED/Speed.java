package SPEED;

import java.util.*;


public class Speed {
	public Tree tree;
	public ArrayList<String> EpisodeList = new ArrayList<String>();
	public Speed () {
		tree = new Tree();
	}
	public static void main(String[] args) {
		Speed speed = new Speed();
		//speed.Read("ABbDCcaBCbdcADaBAdab", 0);

		String training = "ABCDEFGHIJKLMNOPQRSTabcdfghijkStabcdfghijktabcdfghijktabcdfghijkTabcdfghijktabcdfghijkTabcdeFGHIjktabcdefghjktabcdefghjkrtvabcdefghjklmnopRtabcdefghjkLMNOPtwabcdefgHjktabcdefgjktabcdefgijktabcdefgIjktabcdefghjktabcdefghjktabcdEFGHjkqtuabcdghijkQtabcdghijktabcdeghijktabcdEGHIjktabcdefghJktabcdefghijktabcdefghijktabcdefghIJktabcdefghktaBCdEFghijKstvabcdeghijkSTabcdEfghijktabcdFGHIJktabcdefghjkprtabcdefghjkmpRtwabcdefghjklMnoPtabcdefghjkLNOtuabcdefghjktabcdefghJktabcdefghjktuabcdEFGHjknqtabcdeghijkNQtwabcdeGHIjktabcdefghjkqtabcdefghijkQtabcdefghIJktwabcdefghjktvABCdEFghijKsTabcdfghijkStabcdfghijkTabcdeFGHIjktabcdefghJktabcdefghijkmptvabcdefghIjklMnPrtabcdefghjkLNRtuvabcdefghjktabcdefghijktabcdefghIjktabcdefghJkotabcdEFGHjknOqtabcdeghijkNQtuvwabcdeGHIjktabcdefghjktabcdefghJktABCDEFghijKtvabcdfghijkstabcdfghijkSTabcdfGHIJktabcdefghjklmtvabcdefghjklmnprtabcdefghjkLMNoPRtvabcdefghjkOtuabcdefghjktabcdefghjktabcdefghjktabcdefghjktuabcdEFGHjkqtabcdfghijkQtvwabcdeFGHijktabcdefghIJktabcdefghjktabcdefghijktabcdefghIJktabcdefghiktwabcdEFGHIktABCDghijKstvwabcdfghijkStabcdfghijkTabcdfghijkabcdeFGHIjktabcdefghjktvabcdefghjktabcdefghjkmprtabcdefghjkMPRtvwabcdefghjklntabcdefghjkLNotabcdefghijkOtvabcdefghijktabcdefghijktabcdefghIjktuabcdefghjktabcdEFGHjkqtabcdeghijkQtvwabcdeghijktabcdeghijktabcdeghijktabcdeghijktabcdEghijktabcdGHIJktabcdefghijktabcdefghIjktwabcdefghjktabcdefghjktabcdefghjklotvabcdefghjkLOtABCDEFghijKstvabcdfghijkStabcdfghijkTabcdfghijkabcdfghijkabcdFGHIjktabcdefghJktabcdefghijklmtvabcdefghIjkLMnoprtabcdefghjkNOPRtabcdefghijktvabcdefghijktabcdefghIjktabcdefghijktabcdefghIjktabcdefghjktuabcdefghJkqtabcdEFGHijkQtvwabcdghijktabcdeGHIjktabcdefghjktabcdefghijktabcdefghIJktabcdefghkpqtvABCDEFghijkPQstabcdghijkSTabcdghijkabcdGHIjktabcdefghJktabcdefghjklmnoptwabcdefghjkLMNOPrtabcdefghjkRtuvabcdefghjktabcdefghjktabcdefghjktabcdefghjktabcdEFGHjktuabcdeghijktwabcdeghijktabcdefGHIjktabcdefghjktabcdefghijktabcdefghijktabcdefghijktabcdefghijktabcdefghIJkptabcdefghjkPtwABCDEFghijKstvabcdghijkStabcdghijkTabcdfghijktabcdfGHIJktabcdefghktvabcdefghjklmoprtabcdefghjkLMnOPRtvabcdefghJkNtuabcdefghjktabcdefghjktabcdefghjktuabcdefghiJktabcdefghIjktvabcdEFGHjknqtabcdJkNQtvwabcdefghjktuabcdefghjktabcdefghJktABCdEFghijKstvabcdghijkSTabcdefGHIjktabcdefghjktabcdefghjklmnoprtvabcdefghjkLMNOpRtabcdefghJkPtvabcdefGHijktuabcdefijktabcdefijktabcdefIJktabcdefghjktabcdefghijktvwabcdefghIJktabcdEFGHjktuvabcdefghjktabcdefghjknqtabcdEFGHijkNQtwabcdfghijktabcdFghijktabcdeGhijktabcdefgHIjktabcdefghjktabcdefghJktabcdefghjktabcdefghjktAbCdEFghijktvabcdghijkTabcdGHIjkrtabcdefghJkRtabcdefghjkmtvabcdefghjkMptabcdefghjkPtwabcdefghjkotabcdefghjkOtabcdefghjklntuabcdefghjkLNtabcdefghJknqtuvabcdEFGHjkNQtabcdghijktwabcdGHIjktabcdefghijktabcdefghijktabcdefghiJktvwabcdefghIktABCDEFghijkstvwabcdfghijkSTabcdfghijkabcdFGHIJktabcdefghjklmnoptvabcdefghjkLMNOPrtabcdefghJkRtvabcdefghijktabcdefghIJktuabcdefghjktabcdefghjktabcdefghjktabcdEFGHjkqtuabcdfghijkQtvwabcdFGHIjktabcdefghJktabcdefghjktabcdefghJktabcdefghjktabcdefghJktabcdefghijktvABCDEFghijkstabcdfghijkStabcdfghijktabcdfghijktabcdfghijkTabcdfghijkabcdFGHIjktabcdefghJktvabcdefghjklmnoprtabcdefghjkLMNOPRtabcdefghjktvabcdefghjktuabcdefghjktabcdefghjktabcdefghjktabcdefghijktabcdefghIjktabcdefghjktuabcdefghjktabcdeFGHjkqtabcdEghijkQtuwabcdefGHIjktabcdefghJktabcdefghijktabcdefghIjktabcdefghJktABCDEFghijKstvabcdfghijkSTabcdfghijkabcdFGHIjktabcdefghJktabcdefghijkltvabcdefghIjkLmnoprtabcdefghjkMNOPRtvabcdefghjktuabcdefghjktabcdefghjktabcdefghijktuabcdeFGHIjknqtabcdEghijkNQtwabcdfGHIJktabcdefghktabcdefghijktvabcdefghIJktaBCDEFghijkstvwabcdfghijkSTabcdeFGHIjktabcdefghjktabcdefghjklmnoprtabcdefghjkLMNOPRtabcdefghjktuvabcdefghjktabcdefghjktabcdefghJktvabcdefgHjktuabcdefghjkltabcdeFGHjkLqtabcdEjkQtvabcdfghijktabcdefghijktabcdEFGHIjktabcdefghjktabcdefghijktabcdefghijktabcdefghijktabcdefghiJktabcdefghIktAbcdefghijKtvaBCDEFghijkstabcdfghijkSTabcdeFGHIjktabcdefghiJktvwabcdefghIklmprtabcdefghjkLMPRtabcdefghJktwabcdefghjktabcdefghijkntabcdefghIjkNtabcdefghijkotuabcdefghIjkOtabcdEFGHJkqtabcdfghijkQtwabcdFGHIjkotabcdefghijkOtabcdefghiJktwabcdefghIktvabcdefghjktaBCDEFghijkstvabcdfghijkSTabcdfghijkabcdeFGHIjktabcdefghJktabcdefghjklmntvabcdefghJkLMNoprtabcdefghjkOPRtvabcdefghjktabcdefghjktabcdefghijktabcdefghIjktuabcdefghijktabcdefghIjktuabcdEFGHjkqtabcdghijkQtwabcdfghijktabcdeFghijktabcdEghijktabcdfGHIjktabcdefghijktabcdefghiJktabcdefghIktabcdefghktabcdefghjktvwABCDEFGHijKstabcdfghijkStabcdfghijkTabcdfghijkabcdeFGHIjktabcdefghJktabcdefghjklmnoprtabcdefghjkLMNOPRtabcdefghjktvwabcdefghjktabcdefghjktabcdefghjktabcdefghjktabcdefghjktuabcdefghjktabcdeFGHjkntabcdEijkNtwabcdIJktabcdefghjktabcdefghjktabcdefghjktabcdefghJktvaBCDEFghijksTwabcdfghijkSabcdefgHIJktabcdefghjklmptabcdefghjkLMPtabcdefghjklrtvabcdefghjkLRtabcdefghjknotuabcdefghjkNOtabcdefghjktabcdefghjktabcdefghjktuabcdEFGHjknqtabcdfghijkNQtwabcdeFGHIJktabcdefghjktabcdefghjktabcdefghJktABCDefGHktwabcdEFijkstabcdfghijkSTabcdfghijkabcdfghijktabcdeFGHIJktabcdefghjklmnprtabcdefghjkLMNPRtabcdefghjktuvabcdefghijktabcdefghIjktabcdefghjkotuabcdefghjkOtabcdefghjktabcdEFGHjknqtabcdfghijkNQtvabcdFGHIjktabcdefghJkntabcdefghikNtvabcdefghiktabcdefghIktaBCDEFghijKstvabcdghijkStabcdfghijkTabcdfghijkabcdFGHIjktabcdefghJktabcdefghjklmnoptvabcdefghjkLMNOPrtabcdefghjkRtvabcdefghjktabcdefghjktabcdefghjktabcdefghjktabcdEFGHjkqtuvabcdghijkQtwabcdGHIJktabcdefghjktabcdefghiJktabcdefghIjktabcdefghJktvABCDEFghijKstabcdfghijkSTabcdFGHIJktabcdefgjklmnoprtabcdefghjkLMNOPRtuabcdefghjktvabcdefghjktabcdefghjktabcdefgHjknqtuvabcdEFGjkNQtabcdghijktwabcdeGHIJktabcdefghijktabcdefghIJktabcdefghktaBCDEFghijkstvwabcdfghijkSTabcdFGHIJktabcdefghjklmprtabcdefghJkLMPRtvwabcdefghijktabcdefghijknotabcdefghIJkNOtabcdefghiktuabcdEFGHIjkqtabcdeghijkQtwabcdEGHIJktabcdefghkqtabcdefghijkQtabcdefghiJktabcdefGHIktABCdEFghijKstvwabcdghijkSTabcdGHIJktabcdefghijkrtvabcdefghIjklmprtabcdefghjkLMPRtvabcdefghjktabcdefghjktvwabcdefghJkntabcdefghjklNotabcdefghjkLOtuabcdEFGHjkqtabcdeghijkQtvwabcdEghijktuabcdghijktabcdghijktabcdGHIjktabcdefghijktabcdefghiJktabcdefghIktABCdefghijKtvwabcDEFghijkstabcdfghijkSTabcdFghijkabcdefGHIJktabcdefghjklmnoprtabcdefghjkLMNOPRtvwabcdefghjktabcdefghjktabcdefghJkotabcdefghjkOtabcdefghjklqtuabcdEFGHijkLQtvwabcdfghijktabcdeFGHIJktabcdefghijktabcdefghIJktabcdefghijkt";
		//String testing  = "ABCDEFGHIJKLMNOPQRSTabcdfghijkStabcdfghijktabcdfghijktabcdfghijkTabcdfghijktabcdfghijkTabcdeFGHIjktabcdefghjktabcdefghjkrtvabcdefghjklmnopRtabcdefghjkLMNOPtwabcdefgHjktabcdefgjktabcdefgijktabcdefgIjktabcdefghjktabcdefghjktabcdEFGHjkqtuabcdghijkQtabcdghijktabcdeghijktabcdEGHIjktabcdefghJktabcdefghijktabcdefghijktabcdefghIJktabcdefghktaBCdEFghijKstvabcdeghijkSTabcdEfghijktabcdFGHIJktabcdefghjkprtabcdefghjkmpRtwabcdefghjklMnoPtabcdefghjkLNOtuabcdefghjktabcdefghJktabcdefghjktuabcdEFGHjknqtabcdeghijkNQtwabcdeGHIjktabcdefghjkqtabcdefghijkQtabcdefghIJktwabcdefghjktvABCdEFghijKsTabcdfghijkStabcdfghijkTabcdeFGHIjktabcdefghJktabcdefghijkmptvabcdefghIjklMnPrtabcdefghjkLNRtuvabcdefghjktabcdefghijktabcdefghIjktabcdefghJkotabcdEFGHjknOqtabcdeghijkNQtuvwabcdeGHIjktabcdefghjktabcdefghJktABCDEFghijKtvabcdfghijkstabcdfghijkSTabcdfGHIJktabcdefghjklmtvabcdefghjklmnprtabcdefghjkLMNoPRtvabcdefghjkOtuabcdefghjktabcdefghjktabcdefghjktabcdefghjktuabcdEFGHjkqtabcdfghijkQtvwabcdeFGHijktabcdefghIJktabcdefghjktabcdefghijktabcdefghIJktabcdefghiktwabcdEFGHIktABCDghijKstvwabcdfghijkStabcdfghijkTabcdfghijkabcdeFGHIjktabcdefghjktvabcdefghjktabcdefghjkmprtabcdefghjkMPRtvwabcdefghjklntabcdefghjkLNotabcdefghijkOtvabcdefghijktabcdefghijktabcdefghIjktuabcdefghjktabcdEFGHjkqtabcdeghijkQtvwabcdeghijktabcdeghijktabcdeghijktabcdeghijktabcdEghijktabcdGHIJktabcdefghijktabcdefghIjktwabcdefghjktabcdefghjktabcdefghjklotvabcdefghjkLOtABCDEFghijKstvabcdfghijkStabcdfghijkTabcdfghijkabcdfghijkabcdFGHIjktabcdefghJktabcdefghijklmtvabcdefghIjkLMnoprtabcdefghjkNOPRtabcdefghijktvabcdefghijktabcdefghIjktabcdefghijktabcdefghIjktabcdefghjktuabcdefghJkqtabcdEFGHijkQtvwabcdghijktabcdeGHIjktabcdefghjktabcdefghijktabcdefghIJktabcdefghkpqtvABCDEFghijkPQstabcdghijkSTabcdghijkabcdGHIjktabcdefghJktabcdefghjklmnoptwabcdefghjkLMNOPrtabcdefghjkRtuvabcdefghjktabcdefghjktabcdefghjktabcdefghjktabcdEFGHjktuabcdeghijktwabcdeghijktabcdefGHIjktabcdefghjktabcdefghijktabcdefghijktabcdefghijktabcdefghijktabcdefghIJkptabcdefghjkPtwABCDEFghijKstvabcdghijkStabcdghijkTabcdfghijktabcdfGHIJktabcdefghktvabcdefghjklmoprtabcdefghjkLMnOPRtvabcdefghJkNtuabcdefghjktabcdefghjktabcdefghjktuabcdefghiJktabcdefghIjktvabcdEFGHjknqtabcdJkNQtvwabcdefghjktuabcdefghjktabcdefghJktABCdEFghijKstvabcdghijkSTabcdefGHIjktabcdefghjktabcdefghjklmnoprtvabcdefghjkLMNOpRtabcdefghJkPtvabcdefGHijktuabcdefijktabcdefijktabcdefIJktabcdefghjktabcdefghijktvwabcdefghIJktabcdEFGHjktuvabcdefghjktabcdefghjknqtabcdEFGHijkNQtwabcdfghijktabcdFghijktabcdeGhijktabcdefgHIjktabcdefghjktabcdefghJktabcdefghjktabcdefghjktAbCdEFghijktvabcdghijkTabcdGHIjkrtabcdefghJkRtabcdefghjkmtvabcdefghjkMptabcdefghjkPtwabcdefghjkotabcdefghjkOtabcdefghjklntuabcdefghjkLNtabcdefghJknqtuvabcdEFGHjkNQtabcdghijktwabcdGHIjktabcdefghijktabcdefghijktabcdefghiJktvwabcdefghIktABCDEFghijkstvwabcdfghijkSTabcdfghijkabcdFGHIJktabcdefghjklmnoptvabcdefghjkLMNOPrtabcdefghJkRtvabcdefghijktabcdefghIJktuabcdefghjktabcdefghjktabcdefghjktabcdEFGHjkqtuabcdfghijkQtvwabcdFGHIjktabcdefghJktabcdefghjktabcdefghJktabcdefghjktabcdefghJktabcdefghijktvABCDEFghijkstabcdfghijkStabcdfghijktabcdfghijktabcdfghijkTabcdfghijkabcdFGHIjktabcdefghJktvabcdefghjklmnoprtabcdefghjkLMNOPRtabcdefghjktvabcdefghjktuabcdefghjktabcdefghjktabcdefghjktabcdefghijktabcdefghIjktabcdefghjktuabcdefghjktabcdeFGHjkqtabcdEghijkQtuwabcdefGHIjktabcdefghJktabcdefghijktabcdefghIjktabcdefghJktABCDEFghijKstvabcdfghijkSTabcdfghijkabcdFGHIjktabcdefghJktabcdefghijkltvabcdefghIjkLmnoprtabcdefghjkMNOPRtvabcdefghjktuabcdefghjktabcdefghjktabcdefghijktuabcdeFGHIjknqtabcdEghijkNQtwabcdfGHIJktabcdefghktabcdefghijktvabcdefghIJktaBCDEFghijkstvwabcdfghijkSTabcdeFGHIjktabcdefghjktabcdefghjklmnoprtabcdefghjkLMNOPRtabcdefghjktuvabcdefghjktabcdefghjktabcdefghJktvabcdefgHjktuabcdefghjkltabcdeFGHjkLqtabcdEjkQtvabcdfghijktabcdefghijktabcdEFGHIjktabcdefghjktabcdefghijktabcdefghijktabcdefghijktabcdefghiJktabcdefghIktAbcdefghijKtvaBCDEFghijkstabcdfghijkSTabcdeFGHIjktabcdefghiJktvwabcdefghIklmprtabcdefghjkLMPRtabcdefghJktwabcdefghjktabcdefghijkntabcdefghIjkNtabcdefghijkotuabcdefghIjkOtabcdEFGHJkqtabcdfghijkQtwabcdFGHIjkotabcdefghijkOtabcdefghiJktwabcdefghIktvabcdefghjktaBCDEFghijkstvabcdfghijkSTabcdfghijkabcdeFGHIjktabcdefghJktabcdefghjklmntvabcdefghJkLMNoprtabcdefghjkOPRtvabcdefghjktabcdefghjktabcdefghijktabcdefghIjktuabcdefghijktabcdefghIjktuabcdEFGHjkqtabcdghijkQtwabcdfghijktabcdeFghijktabcdEghijktabcdfGHIjktabcdefghijktabcdefghiJktabcdefghIktabcdefghktabcdefghjktvwABCDEFGHijKstabcdfghijkStabcdfghijkTabcdfghijkabcdeFGHIjktabcdefghJktabcdefghjklmnoprtabcdefghjkLMNOPRtabcdefghjktvwabcdefghjktabcdefghjktabcdefghjktabcdefghjktabcdefghjktuabcdefghjktabcdeFGHjkntabcdEijkNtwabcdIJktabcdefghjktabcdefghjktabcdefghjktabcdefghJktvaBCDEFghijksTwabcdfghijkSabcdefgHIJktabcdefghjklmptabcdefghjkLMPtabcdefghjklrtvabcdefghjkLRtabcdefghjknotuabcdefghjkNOtabcdefghjktabcdefghjktabcdefghjktuabcdEFGHjknqtabcdfghijkNQtwabcdeFGHIJktabcdefghjktabcdefghjktabcdefghJktABCDefGHktwabcdEFijkstabcdfghijkSTabcdfghijkabcdfghijktabcdeFGHIJktabcdefghjklmnprtabcdefghjkLMNPRtabcdefghjktuvabcdefghijktabcdefghIjktabcdefghjkotuabcdefghjkOtabcdefghjktabcdEFGHjknqtabcdfghijkNQtvabcdFGHIjktabcdefghJkntabcdefghikNtvabcdefghiktabcdefghIktaBCDEFghijKstvabcdghijkStabcdfghijkTabcdfghijkabcdFGHIjktabcdefghJktabcdefghjklmnoptvabcdefghjkLMNOPrtabcdefghjkRtvabcdefghjktabcdefghjktabcdefghjktabcdefghjktabcdEFGHjkqtuvabcdghijkQtwabcdGHIJktabcdefghjktabcdefghiJktabcdefghIjktabcdefghJktvABCDEFghijKstabcdfghijkSTabcdFGHIJktabcdefgjklmnoprtabcdefghjkLMNOPRtuabcdefghjktvabcdefghjktabcdefghjktabcdefgHjknqtuvabcdEFGjkNQtabcdghijktwabcdeGHIJktabcdefghijktabcdefghIJktabcdefghktaBCDEFghijkstvwabcdfghijkSTabcdFGHIJktabcdefghjklmprtabcdefghJkLMPRtvwabcdefghijktabcdefghijknotabcdefghIJkNOtabcdefghiktuabcdEFGHIjkqtabcdeghijkQtwabcdEGHIJktabcdefghkqtabcdefghijkQtabcdefghiJktabcdefGHIktABCdEFghijKstvwabcdghijkSTabcdGHIJktabcdefghijkrtvabcdefghIjklmprtabcdefghjkLMPRtvabcdefghjktabcdefghjktvwabcdefghJkntabcdefghjklNotabcdefghjkLOtuabcdEFGHjkqtabcdeghijkQtvwabcdEghijktuabcdghijktabcdghijktabcdGHIjktabcdefghijktabcdefghiJktabcdefghIktABCdefghijKtvwabcDEFghijkstabcdfghijkSTabcdFghijkabcdefGHIJktabcdefghjklmnoprtabcdefghjkLMNOPRtvwabcdefghjktabcdefghjktabcdefghJkotabcdefghjkOtabcdefghjklqtuabcdEFGHijkLQtvwabcdfghijktabcdeFGHIJktabcdefghijktabcdefghIJktabcdefghijkt";
		// Get the sequence from SHDU
		speed.run(training, true);
		//speed.run(testing, true);

		//speed.tree.getFreqevents("Adac");
		//System.out.println(speed.getProbability(speed.tree,"Ada", 'b'));
		//System.out.println(speed.getProbability(speed.tree,"Ada", 'c'));
		//System.out.println(speed.getProbability(speed.tree, "AB", 'D'));
		//speed.CalcProb("Ada", 'c');
		//speed.Prediction(sequence);
	}


	public void run(String seq, boolean test){
		int max_episode_length = 1;
		String Episode = "Not found";
		char E;
		String Window = "";
		//System.out.println("Speed starts!");
		char predictedEvent = ' ';
		double maxProb = -1;
		double successCount = 0;
		double totalAttemps = 0;
		for(Character e: seq.toCharArray()) {
			//System.out.println("******Actual event " + e + " Predicted Event " + predictedEvent);
			/*if(e == ',' || Window.length() > 15){
				Window = "";
				max_episode_length = 0;

			}*/
			if (e == predictedEvent)
				successCount++;
			totalAttemps++;
			//System.out.println("Episode: " + Episode);
			Window += e;
			if (e >= 'A' && e <= 'Z')
				E = Character.toLowerCase(e); //if it is true ,display upper case
			else
				E = Character.toUpperCase(e); //if it is true ,display lower case

			// Episode extraction: find the episode
			for (int i = 0; i < Window.length(); i++) {
				char[] WindList = Window.toCharArray();
				if (WindList[i] == E) {
					Episode = Window.substring(i, Window.length());
					if (Episode.length() > max_episode_length)
						max_episode_length = Episode.length();

					Window = Window.substring(Window.length() - max_episode_length, Window.length());
					//System.out.println(Window);
					//System.out.println(Episode);
					Read(Episode, 0);
					//System.out.print("All possible contexts : ");
					for (String context : EpisodeList) {
						if (context.length() <= max_episode_length) {
							//System.out.print(context + ",");
							tree.addEvents(context);
						}
					}
					//System.out.println();
					EpisodeList.clear();
					break;
				}
			}

			//System.out.println("Window after episode extraction : " + Window);

			// Update root frequency
			int root_freq = 0;
			for (char c : tree.root.children.keySet())
				root_freq += tree.root.children.get(c).frequency;
			tree.root.frequency = root_freq;


			// use this window and the decision tree to iterate over all the possible events
			// pick the event which has the highest probability value
			if (test == true) {
				predictedEvent = ' ';
				maxProb = -1;
				for (char event : tree.root.children.keySet()) {
					double prob = getProbability(tree, Window, event);
					if (prob > maxProb) {
						predictedEvent = event;
						maxProb = prob;
					}
				}
			}
		}
		if(test == true) {
			System.out.println("Accuracy of speed " + (successCount / totalAttemps)*100 + "%");
		}
	}

	public double getProbability(Tree tree, String window, char event) {
		double prob = 1.0;
		Deque<TreeNode> queue = new LinkedList<>();
		queue.add(tree.root);
		for(char window_event : window.toCharArray()) {
			TreeNode n = queue.peekLast().children.get(window_event);
			if(n!=null)
				queue.add(queue.peekLast().children.get(window_event));
			else
				break;
		}

		while(!queue.isEmpty()) {
			TreeNode cur_node = queue.poll();
			double parent_freq = (double) cur_node.frequency;
			double event_freq = (double) ((cur_node.children.get(event) == null) ? 0 : cur_node.children.get(event).frequency);
			double event_prob = (event_freq / parent_freq);
			// get all children freq of current node and substract from event freq
			double all_child_freq = 0.0;
			for(char child : cur_node.children.keySet())
				all_child_freq += cur_node.children.get(child).frequency;

			double null_freq = parent_freq - all_child_freq;
			double null_prob = null_freq/parent_freq;
			prob = event_prob + (null_prob*prob);
		}
		return prob;
	}

	public void CalcProb(String window, char c){
		TreeNode cur_node = tree.root;
		double all_child_freq = 0;
		char[] WindList = window.toCharArray();
		double generalp = tree.root.children.get(c).frequency;
		double Calc = generalp/tree.root.frequency;
		double nullc = cur_node.children.get(WindList[0]).frequency;
		for(char child : cur_node.children.keySet())
			all_child_freq += cur_node.children.get(child).frequency;
		nullc = nullc/all_child_freq;
		Calc = nullc * Calc;
		cur_node = cur_node.children.get(WindList[0]);
		TreeNode p = cur_node.children.get(c);
		double pfreq;
		if (p == null){
			pfreq = 0;
		} else{
			pfreq = p.frequency;
		}
		pfreq = pfreq/cur_node.frequency;
		Calc += pfreq;
		for(int i = 1; i < window.length(); i++) {
			all_child_freq = 0;
			cur_node = cur_node.children.get(WindList[i]);
			nullc = cur_node.frequency;
			for(char child : cur_node.children.keySet())
				all_child_freq += cur_node.children.get(child).frequency;
			nullc = nullc/ all_child_freq;
			Calc = nullc * Calc;
			p = cur_node.children.get(c);
			if (p == null){
				pfreq = 0;
			} else{
				pfreq = p.frequency;
			}
			pfreq = pfreq/cur_node.frequency;
			Calc += pfreq;

		}
		System.out.println(Calc);
	}

	public void Read(String Episode, int i){
		if (i == Episode.length())
			return;
		// consider each substring `S[i, j]`
		for (int j = Episode.length() - 1; j >= i; j--){

			EpisodeList.add(Episode.substring(i,j+1));
			// append the substring to the result and recur with an index of
			// the next character to be processed and the result string
			Read(Episode, j + 1);
		}
		Set<String> set = new HashSet<>(EpisodeList);
		EpisodeList.clear();
		EpisodeList.addAll(set);
	}
}