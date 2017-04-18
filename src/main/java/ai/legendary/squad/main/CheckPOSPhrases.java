/** @author harshithakiran */

package ai.legendary.squad.main;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.trees.Tree;

public class CheckPOSPhrases {
	static boolean check = false;
	static StringBuilder prep = new StringBuilder();
	static StringBuilder verb = new StringBuilder();
	static StringBuilder noun = new StringBuilder();
	private static int count = 0;
	private GetPOSPhrases phrases;
	
	public CheckPOSPhrases(){
	String s = "In the morning, John ate pizza by the car.";
	GetPOSPhrases phrases = new GetPOSPhrases();
	phrases.getposphrase(phrases.getTree(s));
	}
	
	
	List<Tree> preplist = phrases.preptreelist;
	List<Tree> verblist = phrases.verbtreelist;
	List<Tree> nounlist = phrases.nountreelist;
	ArrayList<String> pphrase = new ArrayList<String>();
	ArrayList<String> vphrase = new ArrayList<String>();
	ArrayList<String> nphrase = new ArrayList<String>();
	
		public ArrayList<String> prepphrase(){ 	
		//(PP ---> prep phrase
		for (Tree tree : preplist) {
			int val = ordinalIndexOf(tree.toString(), "(PP", 2);			//index of PP in tree
			if (val >= 0) {
				System.out.println("prepositional phrase part : " + getposphrase(tree));

			} else {
				StringBuilder st = new StringBuilder();
				for (Tree tr : tree.getLeaves()) {
					st.append(tr.toString() + " ");
					
				}
				System.out.println(st.toString());
				pphrase.add(st.toString());
			}

		}return pphrase;
		}
		
		public ArrayList<String> verbphrase(){
		//(VP ---> verb phrase
		for (Tree tree : verblist) {
			int val = ordinalIndexOf(tree.toString(), "(VP", 2);			//index of PP in tree
			if (val >= 0) {
				System.out.println("verb phrase part : " + getposphrase(tree));

			} else {
				StringBuilder st = new StringBuilder();
				for (Tree tr : tree.getLeaves()) {
					st.append(tr.toString() + " ");
					
				}
				System.out.println(st.toString());
				vphrase.add(st.toString());
			}
		}return vphrase;
		}

		public ArrayList<String> nounphrase(){
		//(NP ---> noun phrase
		for (Tree tree : nounlist) {
			int val = ordinalIndexOf(tree.toString(), "(NP", 2);			//index of PP in tree
			if (val >= 0) {
				System.out.println("noun phrase part : " + getposphrase(tree));

			} else {
				StringBuilder st = new StringBuilder();
				for (Tree tr : tree.getLeaves()) {
					st.append(tr.toString() + " ");
					
				}
				System.out.println(st.toString());
				nphrase.add(st.toString());
			}
		}return nphrase;

	}
	
	
	public static int ordinalIndexOf(String str, String substr, int n) {
		int pos = str.indexOf(substr);
		while (--n > 0 && pos != -1)
			pos = str.indexOf(substr, pos + 1);
		return pos;
	}

	public static String getStringFromTree(String tr) {
		StringBuilder sb = new StringBuilder();
		boolean con = false;
		for (int i = 0; i < tr.length(); i++) {

			if (con) {
				if (tr.charAt(i) == ' ')
					con = false;
				else
					sb.append(tr.charAt(i) + "");

			} else {

			}
			if (tr.charAt(i) == ' ') {
				con = true;
			}
		}

		return sb.toString();
	}

	public static String getposphrase(Tree tree) {

		String s = tree.toString();
		if (check) {
			if (!s.startsWith("(PP")) {
				if (tree.isLeaf()) {
					if (!tree.toString().startsWith("-"))
						prep.append(tree.toString() + " ");
				}
			} else if (!tree.isLeaf()) {
				check = false;
			}
			if (!s.startsWith("(VP")) {
				if (tree.isLeaf()) {
					if (!tree.toString().startsWith("-"))
						verb.append(tree.toString() + " ");
				}
			} else if (!tree.isLeaf()) {
				check = false;
			}
			if (!s.startsWith("(NP")) {
				if (tree.isLeaf()) {
					if (!tree.toString().startsWith("-"))
						noun.append(tree.toString() + " ");
				}
			} else if (!tree.isLeaf()) {
				check = false;
			}
		}
		
		
		
		if (s.startsWith("(PP")) {
			if (count == 1)
				return null;
			count++;
			prep.setLength(0);
			check = true;
		}
		int numChild = tree.numChildren();
		if (numChild == 0) {

		} else {
			for (int i = 0; i < numChild; i++) {
				Tree childTree = tree.getChild(i);
				getposphrase(childTree);

			}
		}
		return prep.toString();

	}

}
