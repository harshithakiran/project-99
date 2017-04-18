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
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/*
Pattern pattern = Pattern.compile("(((\s\w+)|(\,\s)))+");
        Matcher matcher = pattern.matcher(target); 
        while (matcher.find()) {
            
            matches[i] = matcher.group(); 
            //System.out.println(target.substring(matcher.start(), matcher.end()));
            i++;
*/            
            
            
            
public class PhrasesinaSentence {
	//String text  = "There's a UNESCO World Heritage site in the Rhine Gorge between the Koblenz and what?";
	public List<CoreMap> getSentences(String text){
		
        Vector<String> AllPhraseWords = new Vector<String> ();
        
        String str = " ";
		
	    Properties props = new Properties();
	    props.put("annotators","tokenize, ssplit, pos, parse");
	    StanfordCoreNLP stanford = new StanfordCoreNLP(props);
	    
	    Annotation document = new Annotation(text);
	    stanford.annotate(document);

	    List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
	    
	    System.out.println("returned sentence: " + sentences);
	    
	    return sentences;
	}
	
	//creating saperate functions 
	
	//adverbial phrases
	public String getADVphrase(List<CoreMap> sentences){
		
        String ADVphraseLabel ="@ADVP";
        Vector<String> ADVWords = new Vector<String> ();
        String tempADVPhrase="";
        TregexPattern ADVPattern = TregexPattern.compile(ADVphraseLabel);
        
        System.out.println("Different phrases in the sentence are: ");
	    
		for (CoreMap sentence : sentences) {
			
	        Tree sentenceTree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
	        TregexMatcher ADVmatcher = ADVPattern.matcher(sentenceTree);
			if(ADVmatcher.find()){
	        	Tree thePhraseTree = ADVmatcher.getMatch();
	        	//String tempADVPhrase=""; 
	        	for (int j =0; j< thePhraseTree.getLeaves().size(); j++) {
	        		tempADVPhrase = tempADVPhrase + thePhraseTree.getLeaves().get(j).toString() + " ";
	        	}
	        	//ADVWords.add(tempPhrase);
	        	System.out.println("Adverb phrase: " + tempADVPhrase);
	        }        
		}
		return tempADVPhrase;
	}
}

	//adjective phrases
	/*public String getADJphrase(CoreMap sentences){
		
		String ADVphraseLabel ="@ADVP";
        Vector<String> ADVWords = new Vector<String> ();
        String tempADVPhrase="";
        TregexPattern ADVPattern = TregexPattern.compile(ADVphraseLabel);
		
		for (CoreMap sentence : sentences) {

	        Tree sentenceTree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
	        
	        TregexMatcher ADJmatcher = ADJPattern.matcher(sentenceTree);
	        System.out.println("Different phrases in the sentence are: ");
	        
	        if(ADJmatcher.find()){
	        	Tree thePhraseTree = ADJmatcher.getMatch();
	        	//String tempADVPhrase=""; 
	        	for (int j =0; j< thePhraseTree.getLeaves().size(); j++) {
	        		tempADJPhrase = tempADJPhrase + thePhraseTree.getLeaves().get(j).toString() + " ";
	        	}
	        	//ADVWords.add(tempPhrase);
	        	System.out.println("Adjective phrase: " + tempADJPhrase);
	        }
	        
	}
	return tempADJPhrase;
}

	
	//noun phrases
	public String getNphrase(){
		for (CoreMap sentence : sentences) {

	        Tree sentenceTree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
	        
	        TregexMatcher Nmatcher = NPattern.matcher(sentenceTree);
	        System.out.println("Different phrases in the sentence are: ");
	        
	        if(Nmatcher.find()){
	        	Tree thePhraseTree = Nmatcher.getMatch();
	        	//String tempNPhrase=""; 
	        	for (int j =0; j< thePhraseTree.getLeaves().size(); j++) {
	        		tempNPhrase = tempNPhrase + thePhraseTree.getLeaves().get(j).toString() + " ";
	        	}
	        	//NWords.add(tempPhrase);
	        	System.out.println("Noun phrase: " + tempNPhrase);
	        }
	       }
	       return tempNPhrase;

}

	public String getVphrase(){
		for (CoreMap sentence : sentences) {

	        Tree sentenceTree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
	        
	        TregexMatcher Vmatcher = VPattern.matcher(sentenceTree);
	        System.out.println("Different phrases in the sentence are: ");
	        
	        if(Vmatcher.find()){
	        	Tree thePhraseTree = Vmatcher.getMatch();
	        	//String tempVPhrase=""; 
	        	for (int j =0; j< thePhraseTree.getLeaves().size(); j++) {
	        		tempVPhrase = tempVPhrase + thePhraseTree.getLeaves().get(j).toString() + " ";
	        	}
	        	//ADVWords.add(tempPhrase);
	        	System.out.println("Verb phrase: " + tempVPhrase);

}

	public String getPrepphrase(){
		for (CoreMap sentence : sentences) {

	        Tree sentenceTree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
	        
	        TregexMatcher ADJmatcher = ADJPattern.matcher(sentenceTree);
	        System.out.println("Different phrases in the sentence are: ");
	        
	        if(ADVmatcher.find()){
	        	Tree thePhraseTree = ADVmatcher.getMatch();
	        	//String tempADVPhrase=""; 
	        	for (int j =0; j< thePhraseTree.getLeaves().size(); j++) {
	        		tempADVPhrase = tempADVPhrase + thePhraseTree.getLeaves().get(j).toString() + " ";
	        	}
	        	//ADVWords.add(tempPhrase);
	        	System.out.println("Adjective phrase: " + tempADJPhrase);

}
	public String getCCphrase(){
		for (CoreMap sentence : sentences) {

	        Tree sentenceTree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
	        
	        TregexMatcher ADJmatcher = ADJPattern.matcher(sentenceTree);
	        System.out.println("Different phrases in the sentence are: ");
	        
	        if(ADVmatcher.find()){
	        	Tree thePhraseTree = ADVmatcher.getMatch();
	        	//String tempADVPhrase=""; 
	        	for (int j =0; j< thePhraseTree.getLeaves().size(); j++) {
	        		tempADVPhrase = tempADVPhrase + thePhraseTree.getLeaves().get(j).toString() + " ";
	        	}
	        	//ADVWords.add(tempPhrase);
	        	System.out.println("Adjective phrase: " + tempADJPhrase);

}
*/
