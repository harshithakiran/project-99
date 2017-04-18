/** author: Harshitha **/

//Scenario 1: Rearrange question, question word phrase followed by preposiiton phrase 

package ai.legendary.squad.main;

import java.util.*;

import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;

public class Qrephrasing {
		@SuppressWarnings("unused")
		public static void Quesrephrase(String ques) {
		
			List<String> wd = new ArrayList<String>();								//list for words
			List<Sentence> sampl_sent = new ArrayList<Sentence>();						//list for sentences
			List<String> postags = new ArrayList<String>();							//list- pos tags of sentence
			List<String> rephrasedSentence = new ArrayList<String>();					//List for new (re-phrased) sentence
	    	Document doc = new Document(ques);
	    	
	    	@SuppressWarnings("unused")
			String PrPhrase = null;
	    	String VrbPhrase = null;
	    	String NPhrase = null;
	    	String Qpart = null;
	    	for (Sentence sent : doc.sentences()) { 		
	    		sampl_sent.add(sent);	
	    		wd.addAll(sent.words());
	    		System.out.println("Sentence:" +sent);
	    		System.out.println("Words:" + sent.words());
	    		System.out.println("POS tags: " + sent.posTags());
	    	}
	    	boolean WORD = false;
	    	
	    	//getting all POS phrases
	    	CheckPOSPhrases getphobject = new CheckPOSPhrases();
	    
	    	List<String> PP = getphobject.prepphrase();
	    	List<String> VP = getphobject.verbphrase();
	    	List<String> NP = getphobject.nounphrase();
	    	
	    	for(int pr =0; pr< PP.size(); pr++){
	    		 PrPhrase = PP.get(pr);	}
	    	for(int vrb =0; vrb< VP.size(); vrb++){
	    		 VrbPhrase = PP.get(vrb);	}
	    	for(int nouns =0; nouns< PP.size(); nouns++){
	    		 NPhrase = PP.get(nouns);	}
	    
	    	for (int i = 0; i < sampl_sent.size()-1; i++) {
	    		StringBuilder sb = new StringBuilder();
	    		postags.addAll(sampl_sent.get(i).posTags());
	    			
	    		for(int p = 0; p<postags.size(); p++){
	    			String pos = postags.get(p);								//converting postags list into string
	    			//detecting question word
	    			if(pos.equalsIgnoreCase("WDT")){
	    				WORD = true;
	    			}
	    			if(WORD){
	    				if (pos.startsWith(".")) {
	    					sb.append(wd.get(p) + " ");	
	    					rephrasedSentence.add(sb.toString());
	    					sb = new StringBuilder();
	    				}
	    				else {
	    				sb.append(wd.get(p) + " ");
                    	}	
	    				
	    				// rewording part---> still working on it. 
	    	      	    	 System.out.println("Q-part of sentence: " + s+ " ");
	    					 System.out.println("prep phrase : " + PP);
	    					 System.out.println("Rephrased sentence:	" + PrPhrase);
	 	 		    		
	    	        	    	}
	    				
	    			}
	    			
	    			}
	    		}
	    		
	    	}
		
		


	    		
		