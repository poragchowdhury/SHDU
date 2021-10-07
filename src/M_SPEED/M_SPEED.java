package M_SPEED;

import java.awt.*;
import java.util.*;
import java.lang.Character;

public class M_SPEED {
	public Tree tree;
	public ArrayList<String> EpisodeList = new ArrayList<String>();
	public HashSet<String> AllWindow = new HashSet<>();
	
	public M_SPEED () {
		tree = new Tree();
	}// end M_SPEED
	
	public static void main(String[] args) {
		M_SPEED m_speed = new M_SPEED();
		// AdCBb2D3a12B4c7C1A2d8a1b8c3B3A11D4a2b3 is a sequence
		//String training = "ABCDEFGHIJKLMNOPQRSTabcdfghijkStabcdfghijktabcdfghijktabcdfghijkTabcdfghijktabcdfghijkTabcdeFGHIjktabcdefghjktabcdefghjkrtvabcdefghjklmnopRtabcdefghjkLMNOPtwabcdefgHjktabcdefgjktabcdefgijktabcdefgIjktabcdefghjktabcdefghjktabcdEFGHjkqtuabcdghijkQtabcdghijktabcdeghijktabcdEGHIjktabcdefghJktabcdefghijktabcdefghijktabcdefghIJktabcdefghktaBCdEFghijKstvabcdeghijkSTabcdEfghijktabcdFGHIJktabcdefghjkprtabcdefghjkmpRtwabcdefghjklMnoPtabcdefghjkLNOtuabcdefghjktabcdefghJktabcdefghjktuabcdEFGHjknqtabcdeghijkNQtwabcdeGHIjktabcdefghjkqtabcdefghijkQtabcdefghIJktwabcdefghjktvABCdEFghijKsTabcdfghijkStabcdfghijkTabcdeFGHIjktabcdefghJktabcdefghijkmptvabcdefghIjklMnPrtabcdefghjkLNRtuvabcdefghjktabcdefghijktabcdefghIjktabcdefghJkotabcdEFGHjknOqtabcdeghijkNQtuvwabcdeGHIjktabcdefghjktabcdefghJktABCDEFghijKtvabcdfghijkstabcdfghijkSTabcdfGHIJktabcdefghjklmtvabcdefghjklmnprtabcdefghjkLMNoPRtvabcdefghjkOtuabcdefghjktabcdefghjktabcdefghjktabcdefghjktuabcdEFGHjkqtabcdfghijkQtvwabcdeFGHijktabcdefghIJktabcdefghjktabcdefghijktabcdefghIJktabcdefghiktwabcdEFGHIktABCDghijKstvwabcdfghijkStabcdfghijkTabcdfghijkabcdeFGHIjktabcdefghjktvabcdefghjktabcdefghjkmprtabcdefghjkMPRtvwabcdefghjklntabcdefghjkLNotabcdefghijkOtvabcdefghijktabcdefghijktabcdefghIjktuabcdefghjktabcdEFGHjkqtabcdeghijkQtvwabcdeghijktabcdeghijktabcdeghijktabcdeghijktabcdEghijktabcdGHIJktabcdefghijktabcdefghIjktwabcdefghjktabcdefghjktabcdefghjklotvabcdefghjkLOtABCDEFghijKstvabcdfghijkStabcdfghijkTabcdfghijkabcdfghijkabcdFGHIjktabcdefghJktabcdefghijklmtvabcdefghIjkLMnoprtabcdefghjkNOPRtabcdefghijktvabcdefghijktabcdefghIjktabcdefghijktabcdefghIjktabcdefghjktuabcdefghJkqtabcdEFGHijkQtvwabcdghijktabcdeGHIjktabcdefghjktabcdefghijktabcdefghIJktabcdefghkpqtvABCDEFghijkPQstabcdghijkSTabcdghijkabcdGHIjktabcdefghJktabcdefghjklmnoptwabcdefghjkLMNOPrtabcdefghjkRtuvabcdefghjktabcdefghjktabcdefghjktabcdefghjktabcdEFGHjktuabcdeghijktwabcdeghijktabcdefGHIjktabcdefghjktabcdefghijktabcdefghijktabcdefghijktabcdefghijktabcdefghIJkptabcdefghjkPtwABCDEFghijKstvabcdghijkStabcdghijkTabcdfghijktabcdfGHIJktabcdefghktvabcdefghjklmoprtabcdefghjkLMnOPRtvabcdefghJkNtuabcdefghjktabcdefghjktabcdefghjktuabcdefghiJktabcdefghIjktvabcdEFGHjknqtabcdJkNQtvwabcdefghjktuabcdefghjktabcdefghJktABCdEFghijKstvabcdghijkSTabcdefGHIjktabcdefghjktabcdefghjklmnoprtvabcdefghjkLMNOpRtabcdefghJkPtvabcdefGHijktuabcdefijktabcdefijktabcdefIJktabcdefghjktabcdefghijktvwabcdefghIJktabcdEFGHjktuvabcdefghjktabcdefghjknqtabcdEFGHijkNQtwabcdfghijktabcdFghijktabcdeGhijktabcdefgHIjktabcdefghjktabcdefghJktabcdefghjktabcdefghjktAbCdEFghijktvabcdghijkTabcdGHIjkrtabcdefghJkRtabcdefghjkmtvabcdefghjkMptabcdefghjkPtwabcdefghjkotabcdefghjkOtabcdefghjklntuabcdefghjkLNtabcdefghJknqtuvabcdEFGHjkNQtabcdghijktwabcdGHIjktabcdefghijktabcdefghijktabcdefghiJktvwabcdefghIktABCDEFghijkstvwabcdfghijkSTabcdfghijkabcdFGHIJktabcdefghjklmnoptvabcdefghjkLMNOPrtabcdefghJkRtvabcdefghijktabcdefghIJktuabcdefghjktabcdefghjktabcdefghjktabcdEFGHjkqtuabcdfghijkQtvwabcdFGHIjktabcdefghJktabcdefghjktabcdefghJktabcdefghjktabcdefghJktabcdefghijktvABCDEFghijkstabcdfghijkStabcdfghijktabcdfghijktabcdfghijkTabcdfghijkabcdFGHIjktabcdefghJktvabcdefghjklmnoprtabcdefghjkLMNOPRtabcdefghjktvabcdefghjktuabcdefghjktabcdefghjktabcdefghjktabcdefghijktabcdefghIjktabcdefghjktuabcdefghjktabcdeFGHjkqtabcdEghijkQtuwabcdefGHIjktabcdefghJktabcdefghijktabcdefghIjktabcdefghJktABCDEFghijKstvabcdfghijkSTabcdfghijkabcdFGHIjktabcdefghJktabcdefghijkltvabcdefghIjkLmnoprtabcdefghjkMNOPRtvabcdefghjktuabcdefghjktabcdefghjktabcdefghijktuabcdeFGHIjknqtabcdEghijkNQtwabcdfGHIJktabcdefghktabcdefghijktvabcdefghIJktaBCDEFghijkstvwabcdfghijkSTabcdeFGHIjktabcdefghjktabcdefghjklmnoprtabcdefghjkLMNOPRtabcdefghjktuvabcdefghjktabcdefghjktabcdefghJktvabcdefgHjktuabcdefghjkltabcdeFGHjkLqtabcdEjkQtvabcdfghijktabcdefghijktabcdEFGHIjktabcdefghjktabcdefghijktabcdefghijktabcdefghijktabcdefghiJktabcdefghIktAbcdefghijKtvaBCDEFghijkstabcdfghijkSTabcdeFGHIjktabcdefghiJktvwabcdefghIklmprtabcdefghjkLMPRtabcdefghJktwabcdefghjktabcdefghijkntabcdefghIjkNtabcdefghijkotuabcdefghIjkOtabcdEFGHJkqtabcdfghijkQtwabcdFGHIjkotabcdefghijkOtabcdefghiJktwabcdefghIktvabcdefghjktaBCDEFghijkstvabcdfghijkSTabcdfghijkabcdeFGHIjktabcdefghJktabcdefghjklmntvabcdefghJkLMNoprtabcdefghjkOPRtvabcdefghjktabcdefghjktabcdefghijktabcdefghIjktuabcdefghijktabcdefghIjktuabcdEFGHjkqtabcdghijkQtwabcdfghijktabcdeFghijktabcdEghijktabcdfGHIjktabcdefghijktabcdefghiJktabcdefghIktabcdefghktabcdefghjktvwABCDEFGHijKstabcdfghijkStabcdfghijkTabcdfghijkabcdeFGHIjktabcdefghJktabcdefghjklmnoprtabcdefghjkLMNOPRtabcdefghjktvwabcdefghjktabcdefghjktabcdefghjktabcdefghjktabcdefghjktuabcdefghjktabcdeFGHjkntabcdEijkNtwabcdIJktabcdefghjktabcdefghjktabcdefghjktabcdefghJktvaBCDEFghijksTwabcdfghijkSabcdefgHIJktabcdefghjklmptabcdefghjkLMPtabcdefghjklrtvabcdefghjkLRtabcdefghjknotuabcdefghjkNOtabcdefghjktabcdefghjktabcdefghjktuabcdEFGHjknqtabcdfghijkNQtwabcdeFGHIJktabcdefghjktabcdefghjktabcdefghJktABCDefGHktwabcdEFijkstabcdfghijkSTabcdfghijkabcdfghijktabcdeFGHIJktabcdefghjklmnprtabcdefghjkLMNPRtabcdefghjktuvabcdefghijktabcdefghIjktabcdefghjkotuabcdefghjkOtabcdefghjktabcdEFGHjknqtabcdfghijkNQtvabcdFGHIjktabcdefghJkntabcdefghikNtvabcdefghiktabcdefghIktaBCDEFghijKstvabcdghijkStabcdfghijkTabcdfghijkabcdFGHIjktabcdefghJktabcdefghjklmnoptvabcdefghjkLMNOPrtabcdefghjkRtvabcdefghjktabcdefghjktabcdefghjktabcdefghjktabcdEFGHjkqtuvabcdghijkQtwabcdGHIJktabcdefghjktabcdefghiJktabcdefghIjktabcdefghJktvABCDEFghijKstabcdfghijkSTabcdFGHIJktabcdefgjklmnoprtabcdefghjkLMNOPRtuabcdefghjktvabcdefghjktabcdefghjktabcdefgHjknqtuvabcdEFGjkNQtabcdghijktwabcdeGHIJktabcdefghijktabcdefghIJktabcdefghktaBCDEFghijkstvwabcdfghijkSTabcdFGHIJktabcdefghjklmprtabcdefghJkLMPRtvwabcdefghijktabcdefghijknotabcdefghIJkNOtabcdefghiktuabcdEFGHIjkqtabcdeghijkQtwabcdEGHIJktabcdefghkqtabcdefghijkQtabcdefghiJktabcdefGHIktABCdEFghijKstvwabcdghijkSTabcdGHIJktabcdefghijkrtvabcdefghIjklmprtabcdefghjkLMPRtvabcdefghjktabcdefghjktvwabcdefghJkntabcdefghjklNotabcdefghjkLOtuabcdEFGHjkqtabcdeghijkQtvwabcdEghijktuabcdghijktabcdghijktabcdGHIjktabcdefghijktabcdefghiJktabcdefghIktABCdefghijKtvwabcDEFghijkstabcdfghijkSTabcdFghijkabcdefGHIJktabcdefghjklmnoprtabcdefghjkLMNOPRtvwabcdefghjktabcdefghjktabcdefghJkotabcdefghjkOtabcdefghjklqtuabcdEFGHijkLQtvwabcdfghijktabcdeFGHIJktabcdefghijktabcdefghIJktabcdefghijkt";
		//m_speed.run("AdCBb2D3a12B4c7C1A2d8a1b8c3B3A11D4a2b3", false);
		//m_speed.run("AdCBb2D3a12B4c7C1A2dBa1b8", true);
		//m_speed.run("AdCBb2D3a12", true);
		
		//m_speed.run("DEId45e45i266Cc45GHg301G1g1G2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1G2g1G1g1G2g1FG2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1G2g1G2g1G1g1f45G2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1h131G2g1G1g1G2g1G2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1G2Bg1G2g1G1g1G2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1G2g1G1g1G2b112g1G2g1G2g1G2g1G2g1G1g1B16G2b1g1G2g1G2g1G2g1G1g1G2g1G2B17g1b1G2g1G2g1G1g1G2g1G2g1G2g1B17b1G2g1G2g1G1g1G2g1G2g1G2g1G2B18g1b1G1g1G2g1G2g1G2g1G2g1G1g1B17G2b1g1G2g1G2g1G2g1G2g1G1g1G2B17g1b1G2g1G2g1G2g1G1g1G2g1G2g1B17b1G2g1G2g1G2g1G1g1G2g1G2g1B17G2b1g1G2g1G1g1G2g1G2g1G2g1G2B17g1b1G1g1G2g1G2g1G2g1G2g1G2g1B17G1b1g1G2g1G2g1G2g1G2g1G1g1G2B17g1b1G2g1G2g1G2g1G2g1G1g1G2g1B18G2b1g1G2g1G2g1G1g1G2g1G2g1G2B17g1b1G2g1G1g1", false);
		//m_speed.run("DEId45e45i266Cc45GHg301G1g1G2g1G2g1G2g1",true);
//		m_speed.run("DEId45e45i266Cc45GHg301G1g1G2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1G2g1G1g1G2g1G2g1G2g1G2g1G1g1", false);
//		m_speed.run("DEId45e45i266Cc45GHg301G1g1G2g1G2g1G2g1",true);
		String trainningData = "DEHd45e45h266Cc45GFf45g131Bb112B16b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHde45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHde45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHd45e45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHde45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHde45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHd45e45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHde45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHde45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHd45e45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHde45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHde45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHd45e45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1";
		//String testData 	 = "DEHd45e45h266Cc45GFf45g131Bb112B16b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHde45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHde45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHd45e45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHde45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHde45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHd45e45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHde45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHde45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHd45e45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHde45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHde45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1DEHd45e45h266Cc45GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1EHe45h266GFf45g131Bb112B17b1B17b1B17b1B18b1B17b1B17b1B17b1B17b1B17b1B17b1B17b1B18b1B17b1";
		m_speed.run(trainningData, false);
		/*
		int root_freq = 0;
		for(char c : m_speed.tree.root.children.keySet()) 
			root_freq += m_speed.tree.root.children.get(c).frequency;
		System.out.println(root_freq);
		
		//m_speed.highProb();
		 */
		
	} //end main

	public void run(String seq, boolean test) {
		//if (test)
			System.out.println("\n\n\nM_Speed starts!");
		
		int max_window_length = 0;
		String window = "";	// window acts like a history of the sequence
		char predictedEvent = ' ';
		double successCount = 0;
		double totalAttemps = 0;
		double maxProb = -1;
		HashMap<Character, Integer> timeStorage = new HashMap<>();
		HashMap<Character, Integer> storage = new HashMap<>();
		//String episode = "Not found";
		char E;
		int num; // number in a sequence
		String str_num = ""; 
		String episode = null;
		System.out.println(seq.length());
		for(int i = 0; i < seq.length(); i++) {
			char e = seq.charAt(i);
			if (test)
				System.out.println("Testing...");
			else
				System.out.println("Training...");
			
			System.out.println("Seq i " + i + " / " + seq.length() + " Current event " + e + " Current window " + window);
			episode = null;
			if (test) {
				predictedEvent = ' ';
				maxProb = -100;
				for (char event: tree.root.children.keySet()) {
					if (window.equals("") || event != window.charAt(window.length()-1 )) {
						double prob =   getProbability(tree, window, event);
						System.out.println("Event " + event + " Prob " + prob);
						if (prob > maxProb) {
							predictedEvent = event;
							maxProb = prob;
						} //end if 
					} //end if
				} //end for
				
				System.out.println("******Actual event " + e + " Predicted Event " + predictedEvent);
				if (e == predictedEvent) {
					System.out.println("CORRECT!!!");
					successCount++;
				}
				else
					System.out.println("WRONG!!!");
				totalAttemps++;
			} // end if
		
			
			window += e;
			System.out.println("Window after current event " + window);
			System.out.println("Max window length " + max_window_length);
			if(e >= 'A' && e <= 'Z')
				E = Character.toLowerCase(e); //if it is true ,display upper case
			else
				E = Character.toUpperCase(e); //if it is true ,display lower case
			
			str_num = "";
			int digitCount = 0; 
			
			while(i+1 < seq.length() && Character.isDigit(seq.charAt(i+1))) {
				// if there is any number?
				str_num += seq.charAt(i+1);
				i++;
				digitCount++;
			} //end while
			num = (str_num == "") ? 0 : Integer.parseInt(str_num);
			if(num == 0)
				continue;
			
			System.out.println ("The number is:" + num);
				
			// Episode extraction: find the episode
				if(storage.containsKey(E)) {								
					episode = seq.substring(storage.get(E), i-digitCount+1);
					System.out.println("Episode: " + episode);
					
					if (episode.length() > max_window_length)
						max_window_length = episode.length();
					
					window = seq.substring(i - max_window_length, i);
					timeStorage.put(e, timeStorage.getOrDefault(e, 0)+num);
					
					//Read(trimDigits(episode), 0);
					HashSet<String> set = generateAllContext(episode, max_window_length);
					System.out.println ("All possible contexts : " + set );
					for(String context: set) 
							tree.addEvents(context);
				} //end if
				storage.put(e, i-digitCount);
			
					//System.out.println();
					//EpisodeList.clear();
					//storage.remove(E);
				// end if
			
			
			System.out.println("Episode " + episode);
			
			if (test)
				System.out.println("Window after episode extraction: " + window);
			System.out.println("----------------------------------------------");
			
			// Update root frequency
			int root_freq = 0;
			for (char c : tree.root.children.keySet())
				root_freq += tree.root.children.get(c).frequency;
			tree.root.frequency = root_freq;
			
		} //end for		
		
		if(test)
			System.out.println("Accuracy of speed: sucess " + successCount + " total " + totalAttemps + " rate " + (successCount / totalAttemps)*100.0 + "%");
		
	} //end run
	
	public String trimDigits(String s) {
		StringBuilder sb = new StringBuilder();
		for(char c : s.toCharArray()) {
			if(Character.isDigit(c))
				continue;
			sb.append(c);
		} //end for
		return sb.toString();
	} //end trimDigits
	
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
		} //end for

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
		} //end while
		return prob;
	} //end getProbability

	public float CalcProb(String window, char c){
		TreeNode cur_node = tree.root;
		char[] WindList = window.toCharArray();
		float generalp = tree.root.children.get(c).frequency;
		float Calc = generalp/38;
		float nullc = cur_node.children.get(WindList[0]).frequency;
		nullc = 1/nullc;
		Calc = nullc * Calc;
		cur_node = cur_node.children.get(WindList[0]);
		TreeNode p = cur_node.children.get(c);
		float pfreq;
		if (p == null){
			pfreq = 0;
		} 
		else {
			pfreq = p.frequency;
		}
		pfreq = pfreq*nullc;
		Calc += pfreq;
		for(int i = 1; i < window.length(); i++) {
			cur_node = cur_node.children.get(WindList[i]);
			nullc = cur_node.frequency;
			nullc = 1/nullc;
			Calc = nullc * Calc;
			p = cur_node.children.get(c);
			if (p == null){
				pfreq = 0;
			} 
			else{
				pfreq = p.frequency;
			}
			pfreq = pfreq/cur_node.frequency;
			Calc += pfreq;
		} //end for
		System.out.println(Calc);
		return Calc; 
	} //end CalcProb
	
	// final decision making
	
	public float getHighProbabilityEvent(String window) {	
		char maxEvent = ' ';
		float maxProb = -1;
		for(char event : tree.root.children.keySet()) {
			float c_prob = CalcProb(window, event);
			if (c_prob > maxProb) {
				maxProb = c_prob;
				maxEvent = event;
			}
		}
		System.out.println ("max event is:"+ maxProb);
		return maxEvent;
		
	} //end getHighProbabilityEvent
	
	public HashSet<String> generateAllContext(String episode, int maxContextLen){
		HashSet<String> set = new HashSet<>();
		//System.out.println("Start: generateAllContext");
		for(int idx = 0; idx < episode.length(); idx++) {
			int max_len = (episode.length() < idx+maxContextLen) ? episode.length()-idx : maxContextLen; 
			StringBuilder sb = new StringBuilder();
			for(int len = 0; len < max_len; len++) {
				if (Character.isDigit(episode.charAt(idx+len)))
					break;
				sb.append(episode.charAt(idx+len));
				set.add(sb.toString());
			} //end for
		} //end for 
		//System.out.println("End: generateAllContext");
		return set;
	} //end generateAllContext

	public void Read(String Episode, int i) {
		if (i == Episode.length())
			return;
		// consider each substring `S[i, j]`
		for (int j = Episode.length() - 1; j >= i; j--){

			EpisodeList.add(Episode.substring(i,j+1));
			// append the substring to the result and recur with an index of
			// the next character to be processed and the result string
			Read(Episode, j + 1);
		} //end for
		
		Set<String> set = new HashSet<>(EpisodeList);
		EpisodeList.clear();
		EpisodeList.addAll(set);
	} //end Read
	
} //end class

