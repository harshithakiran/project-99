package ai.legendary.squad.stanfordnlp;

import java.util.List;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.AnnotationPipeline;
import edu.stanford.nlp.util.Triple;

/*
 * Stanford NER is the Named Entity Recognizer. 
 * 
 * The documentation and downloads are available here: https://nlp.stanford.edu/software/CRF-NER.shtml 
 * 
 * The models for the NER are located in: "stanford-english-corenlp-2016-10-31-models.jar" 
 * for some reason, I had to specify an "in .jar path" to get the classloader to find them. 
 * 
 * The .jar comes with many different models. Here's the basics: 
 * The "3 model" classifiers tag 3 classes (LOCATION, PERSON, ORGANIZATION)
 * The "4 model" classifiers tag 4 classes (LOCATION, PERSON, ORGANIZATION, MISCELLANEOUS )
 * The "7 model" classifiers tag 7 classes (LOCATION, PERSON, ORGANIZATION, MONEY, PERCENT, DATE, TIME) 
 * 
 *  Each model has been categorized to either take advantage of case sensitivity. The models marked "caseless"
 *  do a better job of recognizing Named Entities that weren't properly capitalized ("He went to china.") 
 *  
 *  All of the models were trained using Conditional Random Fields (CRFClassifier). 
 *  
 *  Finally, there is a version of each model that consider "distributional similarity" in their training. 
 *  Those tagged with "distsim" provide some performance gain at the cost of increasing their size and runtime. 
 *  
 */

public class NERTest {
	private AnnotationPipeline pipeline; 
	AbstractSequenceClassifier<CoreLabel> classifier;
	
	public NERTest() {
		String InJarPath = "edu/stanford//nlp//models//ner";
		//"english.all.3class.caseless.distsim.crf.ser.gz";
		String serializedClassifier = InJarPath + "//english.muc.7class.caseless.distsim.crf.ser.gz" ;
		classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
		
	}

	
	public void runTest() {
		
	  String[] example = {"On January 1st, Bob Smith ate a pizza at Stanford University, which in late 1984 was located in California." };
	  for (String str : example) {
		  System.out.println(classifier.classifyToString(str));
	  }
	  System.out.println("---");
	
	  for (String str : example) {
		  // This one puts in spaces and newlines between tokens, so just print not println.
		  System.out.print(classifier.classifyToString(str, "slashTags", false));
	  }
	  System.out.println("---");
	
	  for (String str : example) {
		  // This one is best for dealing with the output as a TSV (tab-separated column) file.
		  // The first column gives entities, the second their classes, and the third the remaining text in a document
		  System.out.print(classifier.classifyToString(str, "tabbedEntities", false));
	  }
	  System.out.println("---");
	
	  for (String str : example) {
		  System.out.println(classifier.classifyWithInlineXML(str));
	  }
	  System.out.println("---");
	
	  for (String str : example) {
		  System.out.println(classifier.classifyToString(str, "xml", true));
	  }
	  
	  
	  System.out.println("--- TSV");
	
	  for (String str : example) {
		  //System.out.print(classifier.classifyToString(str, "tsv", false));
		  System.out.print(classifier.classifyToString(str, "tsv", false));
	  }
	  System.out.println("---");
	
	  // This gets out entities with character offsets
	  int j = 0;
	  for (String str : example) {
		  j++;
		  List<Triple<String,Integer,Integer>> triples = classifier.classifyToCharacterOffsets(str);
		  for (Triple<String,Integer,Integer> trip : triples) {
			  System.out.printf("%s over character offsets [%d, %d) in sentence %d.%n",
					  trip.first(), trip.second(), trip.third, j);
		  }
	  }
	  System.out.println("---");
	
	  // This prints out all the details of what is stored for each token
	  int i=0;
	  for (String str : example) {
		  for (List<CoreLabel> lcl : classifier.classify(str)) {
			  for (CoreLabel cl : lcl) {
				  System.out.print(i++ + ": ");
				  System.out.println(cl.toShorterString());
			  }
		  }
	  }
	
	  System.out.println("---");

    }

}