package ai.legendary.squad.stanfordnlp;

import java.io.StringReader;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.shiftreduce.ShiftReduceParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.Tree;

public class ShiftReduceDemo {
	String modelPath = "edu/stanford/nlp/models/srparser/englishSR.ser.gz";
	String taggerPath = "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger";

	public ShiftReduceDemo(String inputSentence) {
		MaxentTagger tagger = new MaxentTagger(taggerPath);
	    ShiftReduceParser model = ShiftReduceParser.loadModel(modelPath);
	    DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(inputSentence));
	    for (List<HasWord> sentence : tokenizer) {
	    	List<TaggedWord> tagged = tagger.tagSentence(sentence);
	    	Tree tree = model.apply(tagged);
	    	for (int i=0; i< tree.subTreeList().size(); i++) {
	    		System.out.println(i + " " + tree.subTreeList().get(i) + " ");
	    	}
	      
	    	for (int i=0; i< tree.subTreeList().size(); i++) {
	    		if (! tree.subTreeList().get(i).toString().startsWith("("))
	    			System.out.print(tree.subTreeList().get(i) + " ");
	            if (tree.subTreeList().get(i).toString().startsWith("(S "))
	            	System.out.println("");
	    	}
	    	System.out.println("");
	    	System.out.println("pennString: " + tree.pennString());

	   }
	  }
	}
