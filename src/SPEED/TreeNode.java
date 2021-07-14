package SPEED;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class TreeNode {
	public Character event;
	public int frequency;
	public HashMap<Character, TreeNode> children;
	
	public TreeNode(char c) {
		event = c;
		frequency = 0;
		children = new HashMap<>();
	}

	public ArrayList<Character> getChildren(Character c){
		Set<Character> keySet = children.keySet();
		ArrayList<Character> NewList = new ArrayList<Character>(keySet);
		return NewList;
		/*if(c == ' '){
			System.out.println("The current children of root is/are " + NewList);
		} else {
			System.out.println("The current children of " + c + " is/are " + NewList);
		}*/
	}


	public void getFreq(TreeNode c){
		if (event != ' ') {
			System.out.println("The Frequency of " + c.ToString() + " is " + c.frequency);
		}
	}

	public String ToString(){
		if (event == (' ')){
			return "root";
		} else {
			return event.toString();
		}
	}

}
