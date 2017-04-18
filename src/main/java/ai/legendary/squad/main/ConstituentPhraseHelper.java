package ai.legendary.squad.main;



import java.util.List;
import java.util.Properties;
import java.util.Vector;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.util.CoreMap;

public class ConstituentPhraseHelper {

	public enum PhraseType {NOUN_PHRASE, VERB_PHRASE, ADJECTIVE_PHRASE, ADVERB_PHRASE, PREPOSITIONAL_PHRASE, RECURSIVE_FLAG} 
	public static Vector<String> getPhraseUsingConstituent(String text, PhraseType phraseType){

		String phraseLabel =""; 
        Vector<String> theWords = new Vector<String> ();  
		
	    Properties props = new Properties();
	    props.put("annotators","tokenize, ssplit, pos, parse");
	    StanfordCoreNLP stanford = new StanfordCoreNLP(props);
	    
	    switch (phraseType) {
		    case ADJECTIVE_PHRASE : {
		    	phraseLabel = "@ADJP"; break; 
		    }
		    case ADVERB_PHRASE : {
		    	phraseLabel = "@ADVP"; break; 
		    }
		    case NOUN_PHRASE : {
		    	phraseLabel = "@NP"; break; 
		    }
		    case VERB_PHRASE : {
		    	phraseLabel = "@VP"; break; 
		    }
		    case PREPOSITIONAL_PHRASE : {
		    	phraseLabel = "@PP"; break; 
		    }
	    }
	    
	    
	    TregexPattern npPattern = TregexPattern.compile(phraseLabel);			//@ADJP, @ADVP, @NP, @VP, @PP
	    Annotation document = new Annotation(text);
	    stanford.annotate(document);

	    List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
	    for (CoreMap sentence : sentences) {

	        Tree sentenceTree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
	        TregexMatcher matcher = npPattern.matcher(sentenceTree);			//matching pattern with the tree

	        while (matcher.find()) { 											//if you find the matched pattern
	            Tree thePhraseTree = matcher.getMatch();						//get the matching sub tree
			    String tempPhrase=""; 

	            for (int j =0; j< thePhraseTree.getLeaves().size(); j++) {
	            	tempPhrase = tempPhrase + thePhraseTree.getLeaves().get(j).toString() + " ";
	            }
	            theWords.add(tempPhrase); 
	            //System.out.println(tempPhrase);
	        }
	        
	    }
	    return theWords; 
	}
	
	
	
}

