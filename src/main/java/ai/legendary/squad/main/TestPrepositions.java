/**
 * 
 */
package ai.legendary.squad.main;

import java.util.List;

import edu.stanford.nlp.trees.Tree;

/**
 * @author Shrikant M
 *
 */
public class TestPrepositions {
	static boolean check = false;
	static StringBuilder prep = new StringBuilder();
	private static int count = 0;

	public static void doTest() {
		String s = "The game was played on February 7, 2016, at Levi's Stadium in the San Francisco Bay Area at Santa Clara, California.";

		GetPrepositionalPhrase phrases = new GetPrepositionalPhrase();
		phrases.getPrepPhrase(phrases.getTree(s));

		List<Tree> list = phrases.treeList;

		for (Tree tree : list) {
			int val = ordinalIndexOf(tree.toString(), "(PP", 2);
			if (val >= 0) {
				System.out.println(getPrepPhrases(tree));

			} else {
				StringBuilder st = new StringBuilder();
				for (Tree tr : tree.getLeaves()) {
					st.append(tr.toString() + " ");
				}
				System.out.println(st.toString());
			}

		}

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

	public static String getPrepPhrases(Tree tree) {

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
				getPrepPhrases(childTree);

			}
		}
		return prep.toString();

	}

}
