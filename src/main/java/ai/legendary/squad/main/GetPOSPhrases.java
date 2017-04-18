/** @author harshithakiran */



package ai.legendary.squad.main;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.parser.shiftreduce.ShiftReduceParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.Tree;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import ai.legendary.squad.main.ConstituentPhraseHelper;

public class GetPOSPhrases {
	
	String npprint = null;
	String modelPath = "edu/stanford/nlp/models/srparser/englishSR.ser.gz";
	String taggerPath = "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger";
	private LexicalizedParser parser;
	boolean check = false;
	List<Tree> preptreelist = new ArrayList<Tree>();
	List<Tree> verbtreelist = new ArrayList<Tree>();
	List<Tree> nountreelist = new ArrayList<Tree>();
	String prepstr; 
	
	MaxentTagger tagger;
	ShiftReduceParser model;
	//parser = LexicalizedParser.loadModel();

	public GetPOSPhrases() {		//constructor
		tagger = new MaxentTagger(taggerPath);				//maxtent is inbuilt class of sf t detect pos tags
		model = ShiftReduceParser.loadModel(modelPath);		// shift reduce used for dependency parsing
	}
	
	public Tree getTree(String inputSentence) {		//get dependency parse tree for a given input sample sentence
		Tree tree = null;
		
		DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(inputSentence));
		for (List<HasWord> sentence : tokenizer) {
			List<TaggedWord> tagged = tagger.tagSentence(sentence);
			tree = model.apply(tagged);
		}
		return tree;
	}
	
	
	
	//in tree, search for particular POS subtree, returns that subtree
	public void getposphrase(Tree tree){		
		
		String s = tree.toString();
		if (s.startsWith("(PP")) {				//if subtree is Preposition 
			preptreelist.add(tree);
			
			//String preptree = preptreelist.toString();
			System.out.println("pp");
			//System.out.println("prep phrase subtree" + preptreelist);
		}
		if(s.startsWith("(VP")){				//if subtree is verb
			verbtreelist.add(tree);
			System.out.println("vp");
			//System.out.println("vrb phrase subtree" + verbtreelist);
		}
		if(s.startsWith("(NP")){				//if subtree is noun
			
			nountreelist.add(tree);
			//System.out.println("noun phrase subtree" + nountreelist);						//nountreelist is of type list<Tree>
			
			for (int j =0; j< nountreelist.size(); j++) {
                 npprint = nountreelist.get(j).getLeaves().toString();	
            }
			System.out.println("np:" + npprint);
		}
		
		int numChild = tree.numChildren();
		if (numChild == 0) {}		//subtree has no children(leaf nodes)
		else {
			for (int i = 0; i < numChild; i++) {
				Tree childTree = tree.getChild(i);		//putting the children in another 'TREE' stanfnlp class
				getposphrase(childTree);
				
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