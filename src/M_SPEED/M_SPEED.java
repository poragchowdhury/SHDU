package M_SPEED;

import java.awt.*;
import java.util.*;
import java.lang.Character;

public class M_SPEED {
	public Tree tree;
	public ArrayList<String> EpisodeList = new ArrayList<String>();
	public M_SPEED () {
		tree = new Tree();
	}
	public static void main(String[] args) {
		M_SPEED m_speed = new M_SPEED();
		//speed.Read("ABbDCcaBCbdcADaBAdab", 0);
		//m_speed.Read("AdCBb2D3a12B4c7C1A2d8a1b8c3B3A11D4a2b3 ")`
		m_speed.run("AdCBb2D3a12B4c7C1A2d8a1b8c3B3A11D4a2b3");
		int root_freq = 0;
		for(char c : m_speed.tree.root.children.keySet()) 
			root_freq += m_speed.tree.root.children.get(c).frequency;
		System.out.println(root_freq);
		//m_speed.tree.getFreqevents("Adac");
		//m_speed.CalcProb("b", 'c');
	}

	public void run(String seq){
		int max_window_length = 0;
		String window = "";
		HashMap<Character, Integer> timeStorage = new HashMap<>();
		HashMap<Character, Integer> storage = new HashMap<>();
		String episode = "Not found";
		char E;
		int num;
		String str_num = "";
		
		System.out.println("Speed starts!");
		
		for(int i = 0; i < seq.length(); i++){
			char e = seq.charAt(i);
			window += e;
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
			}
			num = (str_num == "") ? 0 : Integer.parseInt(str_num);
				
			// Episode extraction: find the episode
			if(storage.containsKey(E)) {
				
				/*
				int lastIndexOfE = 0;
				for(int j = i; j >= 0; j--) {
					if(seq.charAt(j) == E) {
						lastIndexOfE = j;
						break;
					}
				}
				episode = seq.substring(lastIndexOfE, i-digitCount+1);
				System.out.println("Episode1: " + episode);
				*/
				
				episode = seq.substring(storage.get(E), i-digitCount+1);
				System.out.println("Episode2: " + episode);
				
				
				if (episode.length() > max_window_length)
					max_window_length = episode.length();
				
				window = seq.substring(i - max_window_length, i);
				timeStorage.put(e, timeStorage.getOrDefault(e, 0)+num);
				
				Read(trimDigits(episode), 0);
				System.out.print("All possible contexts : ");
				for(String context: EpisodeList) {
					if(context.length() <= max_window_length) {
						System.out.print(context + ",");
						tree.addEvents(context);
					}
				}
				System.out.println();
				EpisodeList.clear();
				storage.remove(E);
			}
			storage.put(e, i-digitCount);
			System.out.println("Window after episode extraction : " + window);
		}
		
		//System.out.println("timeStorage.get('A') " + timeStorage.get('A'));
		//System.out.println("timeStorage.get('B')" + timeStorage.get('B'));
	}
	
	public String trimDigits(String s) {
		StringBuilder sb = new StringBuilder();
		for(char c : s.toCharArray()) {
			if(Character.isDigit(c))
				continue;
			sb.append(c);
		}
		return sb.toString();
	}

	public void CalcProb(String window, char c){
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
		} else{
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

