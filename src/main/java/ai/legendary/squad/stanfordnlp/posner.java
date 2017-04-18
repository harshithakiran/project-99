package ai.legendary.squad.stanfordnlp;

/**
 * @author harshithakiran
 *
 */


import java.util.List;
//NER
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.sequences.DocumentReaderAndWriter;
import edu.stanford.nlp.util.Triple;

//POS
import java.io.IOException;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
	
//POS
public class posner{
	public static void posgetter(String[] args) throws IOException, ClassNotFoundException
	{		
		System.out.println("Project-99");
		 
		//initialize the tagger
		MaxentTagger tagger=new MaxentTagger("taggers/bidirectional-distsim-wsj-0-18.tagger");		 
		String sample = ("Pharaohs were the god kings of ancient Egypt, Who were the god kings of ancient Egypt?"); //The sample string
		String tagged = tagger.tagString(sample);							//TAGGED STRING 

		System.out.println(tagged);											//OUTPUT 
	}


	//NER
	  public static void nerget(String[] args) throws Exception {

	    String serializedClassifier = "classifiers/english.all.3class.distsim.crf.ser.gz";

	    if (args.length > 0) {
	      serializedClassifier = args[0];
	    }

	    AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
	    if (args.length > 1) {

	      /*  (1) how to run NER on a String, 
	          (2) how to get the entities in the String with character offsets, and
	          (3) how to run NER on a whole file (without loading it into a String). */

	      String fileContents = IOUtils.slurpFile(args[1]);
	      List<List<CoreLabel>> out = classifier.classify(fileContents);
	      for (List<CoreLabel> sentence : out) {
	        for (CoreLabel word : sentence) {
	          System.out.print(word.word() + '/' + word.get(CoreAnnotations.AnswerAnnotation.class) + ' ');
	        }
	        System.out.println();
	      }

	      System.out.println("---");						
	      out = classifier.classifyFile(args[1]);
	      for (List<CoreLabel> sentence : out) {
	        for (CoreLabel word : sentence) {
	          System.out.print(word.word() + '/' + word.get(CoreAnnotations.AnswerAnnotation.class) + ' ');
	        }
	        System.out.println();
	      }

	      System.out.println("---");				
	      List<Triple<String, Integer, Integer>> list = classifier.classifyToCharacterOffsets(fileContents);
	      for (Triple<String, Integer, Integer> item : list) {
	        System.out.println(item.first() + ": " + fileContents.substring(item.second(), item.third()));
	      }
	      System.out.println("---");
	      System.out.println("entity labelings");
	      DocumentReaderAndWriter<CoreLabel> readerAndWriter = classifier.makePlainTextReaderAndWriter();
	      classifier.classifyAndWriteAnswersKBest(args[1], 10, readerAndWriter);

	      System.out.println("---");
	      System.out.println("Per-token marginalized probabilities");
	      classifier.printProbs(args[1], readerAndWriter);

	    	} else {

	      String[] example = {"Pharaohs were the god kings of ancient Egypt, Who were the god kings of ancient Egypt?" };
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
	    	  
	        // TSV (tab-separated column) file.: The first column gives entities, the second their classes, and the third the remaining text in a document
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
	      System.out.println("---");

	      for (String str : example) {
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
	    }}
}


