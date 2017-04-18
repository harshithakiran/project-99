package ai.legendary.squad.main;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.parser.shiftreduce.ShiftReduceParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.Tree;

 /*
  *  Clause Level

	S - simple declarative clause, i.e. one that is not introduced by a (possible empty) subordinating conjunction or a wh-word and that does not exhibit subject-verb inversion.
	SBAR - Clause introduced by a (possibly empty) subordinating conjunction.
	SBARQ - Direct question introduced by a wh-word or a wh-phrase. Indirect questions and relative clauses should be bracketed as SBAR, not SBARQ.
	SINV - Inverted declarative sentence, i.e. one in which the subject follows the tensed verb or modal.
	SQ - Inverted yes/no question, or main clause of a wh-question, following the wh-phrase in SBARQ.

	Phrase Level

	ADJP - Adjective Phrase.
	ADVP - Adverb Phrase.
	CONJP - Conjunction Phrase.
	FRAG - Fragment.
	INTJ - Interjection. Corresponds approximately to the part-of-speech tag UH.
	LST - List marker. Includes surrounding punctuation.
	NAC - Not a Constituent; used to show the scope of certain prenominal modifiers within an NP.
	NP - Noun Phrase. 
	NX - Used within certain complex NPs to mark the head of the NP. Corresponds very roughly to N-bar level but used quite differently.
	PP - Prepositional Phrase.
	PRN - Parenthetical. 
	PRT - Particle. Category for words that should be tagged RP. 
	QP - Quantifier Phrase (i.e. complex measure/amount phrase); used within NP.
	RRC - Reduced Relative Clause. 
	UCP - Unlike Coordinated Phrase. 
	VP - Vereb Phrase. 
	WHADJP - Wh-adjective Phrase. Adjectival phrase containing a wh-adverb, as in how hot.
	WHAVP - Wh-adverb Phrase. Introduces a clause with an NP gap. May be null (containing the 0 complementizer) or lexical, containing a wh-adverb such as how or why.
	WHNP - Wh-noun Phrase. Introduces a clause with an NP gap. May be null (containing the 0 complementizer) or lexical, containing some wh-word, e.g. who, which book, whose daughter, none of which, or how many leopards.
	WHPP - Wh-prepositional Phrase. Prepositional phrase containing a wh-noun phrase (such as of which or by whose authority) that either introduces a PP gap or is contained by a WHNP.
	X - Unknown, uncertain, or unbracketable. X is often used for bracketing typos and in bracketing the...the-constructions.
	
  */

public class SentenceToClause {

    private String modelPath = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
    private String taggerPath = "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger";

    private MaxentTagger tagger;
    private ShiftReduceParser model;
    private LexicalizedParser parser;
    
    public static final ArrayList<String> ClauseMarkerWordList = new ArrayList<String> (Arrays.asList(
    		"how", "however", "if", "that", "what", "whatever", "when", "whenever", "where", "wherever", "whether", "which", "whichever",
    		"who", "whoever", "whom", "whomever", "whose", "why", "and", "but", "for", "nor", "or", "so", "yet", "than", "but also", "as"));
    public static final ArrayList<String> NoTrailingSpaceCharacters = new ArrayList<String> (Arrays.asList("(", "[", "{", "$", "#")); 
    public static final ArrayList<String> NoLeadingSpaceCharacters = new ArrayList<String> (Arrays.asList(")","]", "}", "%")); 
    
	public SentenceToClause() {
	    tagger = new MaxentTagger(taggerPath);
	   // model = ShiftReduceParser.loadModel(modelPath);
	    parser = LexicalizedParser.loadModel();
	}
	
	public ArrayList<String> process(String inputSentence, boolean keepSubordinator) {
		ArrayList<String> ClausesInSentence = new ArrayList<String>();
		boolean clauseBreakFound = false; 
		String theClause=""; 
		String lastWord=""; 
	
		System.out.println("");
		System.out.println(inputSentence);

		DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(inputSentence));
    
	    for (List<HasWord> sentence : tokenizer) {
	      List<TaggedWord> tagged = tagger.tagSentence(sentence);
	    
	     // Tree tree = model.apply(tagged);
	      Tree tree=parser.apply(tagged);
	      /* Keep for testing / debugging 
	      for (int i=0; i< tree.subTreeList().size(); i++) {
	    		System.out.println(i + " " + tree.subTreeList().get(i) + " ");
	      } */
	      
	      for (int i=4; i< tree.subTreeList().size(); i++) {
	    	  if (tree.subTreeList().get(i).isLeaf()) {
	    		  lastWord = tree.subTreeList().get(i).toString(); 
	    		  if (lastWord.equals("-LRB-")) lastWord = "(";
	    		  if (lastWord.equals("-RRB-")) lastWord = ")";
	    		  if (lastWord.equals("-LSB-")) lastWord = "[";
	    		  if (lastWord.equals("-RSB-")) lastWord = "]";
	    		  if (lastWord.equals("-LCB-")) lastWord = "{";
	    		  if (lastWord.equals("-RCB-")) lastWord = "}";
	    		  
	    		  
	    		  if (NoLeadingSpaceCharacters.contains(lastWord)) theClause = theClause.trim() +lastWord+" "; else
	    		  if (NoTrailingSpaceCharacters.contains(lastWord)) theClause = theClause+ lastWord;  else
	    			  theClause = theClause + lastWord + " ";
	    	  }
	    	  
	    	  if (tree.subTreeList().get(i).toString().startsWith("(S") ) {
	    			  clauseBreakFound = true; 
	    			  /*
	    			  if (keepSubordinator) 
		    			  if ((ClauseMarkerWordList.contains(lastWord)) && (theClause.trim().lastIndexOf(" ")>0))
		    				  theClause = theClause.substring(0, theClause.trim().lastIndexOf(" ") ); 
	    			  */
	    			  if (!ClauseMarkerWordList.contains(theClause.trim())) {
	    				  ClausesInSentence.add(theClause.trim()); 
	    				  //System.out.println(">> " + theClause);
	    				  theClause="";
	    			  }
	    	  } else {
	    		  clauseBreakFound = false;
	    	  }
	      }
    	        
	    }
	    ClausesInSentence.add(theClause.trim());	
	    return ClausesInSentence; 
	  }
	
}