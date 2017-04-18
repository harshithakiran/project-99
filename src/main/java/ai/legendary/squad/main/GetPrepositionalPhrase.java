package ai.legendary.squad.main;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.shiftreduce.ShiftReduceParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.Tree;

public class GetPrepositionalPhrase {

	String modelPath = "edu/stanford/nlp/models/srparser/englishSR.ser.gz";
	String taggerPath = "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger";
	boolean check = false;
	List<Tree> treeList = new ArrayList<Tree>();

	MaxentTagger tagger;
	ShiftReduceParser model;

	public GetPrepositionalPhrase() {
		tagger = new MaxentTagger(taggerPath);
		model = ShiftReduceParser.loadModel(modelPath);

	}

	public Tree getTree(String inputSentence) {
		Tree tree = null;
		treeList = new ArrayList<Tree>();
		DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(inputSentence));
		for (List<HasWord> sentence : tokenizer) {
			List<TaggedWord> tagged = tagger.tagSentence(sentence);
			tree = model.apply(tagged);
		}
		return tree;
	}

	public void getPrepPhrase(Tree tree) {
		String s = tree.toString();
		if (s.startsWith("(PP")) {
			treeList.add(tree);
		}
		int numChild = tree.numChildren();
		if (numChild == 0) {
		} else {
			for (int i = 0; i < numChild; i++) {
				Tree childTree = tree.getChild(i);
				getPrepPhrase(childTree);

			}
		}

	}

	public int ordinalIndexOf(String str, String substr, int n) {
		int pos = str.indexOf(substr);
		while (--n > 0 && pos != -1)
			pos = str.indexOf(substr, pos + 1);
		return pos;
	}

	public String getStringFromTree(String tr) {
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

}
