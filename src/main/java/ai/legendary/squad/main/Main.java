package ai.legendary.squad.main;

import java.util.List;
import java.util.Vector;

import edu.stanford.nlp.util.CoreMap;

public class Main {

	public static void main(String[] args) {
		
//		TemporalClass tc = new TemporalClass();
//		tc.processTemporal("From next month, we will have meeting on every friday, from 3:00 pm to 4:00 pm");
		
		/*CheckPOSPhrases cposphrase = new CheckPOSPhrases();
		cposphrase.getStringFromTree("There's a UNESCO World Heritage site in the Rhine Gorge between the Koblenz and what?");*/

		//List<CoreMap> phrases = PhrasesinaSentence.getSentences("There's a UNESCO World Heritage site in the Rhine Gorge between the Koblenz and what?");
		//System.out.println("\n from constituent phrase helper:	\n" + phrases);
		
		PhrasesinaSentence phrases = new PhrasesinaSentence();
		phrases.getADVphrase(phrases.getSentences("There's a UNESCO World Heritage site in the Rhine Gorge between the Koblenz and what?"));

		/*Vector<String> phrases = ConstituentPhraseHelper.getPhraseUsingConstituent("The Linguistic Analysis API simplifies complex languages to help you easily parse text", HKConstituentPhraseHelper.PhraseType.VERB_PHRASE);
        System.out.println("\n" + phrases);*/
        
        
		/*	GetPOSPhrases GPOSphrases = new GetPOSPhrases();
		GPOSphrases.getposphrase(GPOSphrases.getTree("John, a linguist, is coming to dinner."));	*/
		
		/*Qrephrasing qr = new Qrephrasing();
		qr.Quesrephrase("What are you doing");*/
		
//		AnswerTypeClassifier ans = new AnswerTypeClassifier();
//		ans.runIllinoisTests();
		

//		NERTest test = new NERTest();
//		test.runTest();		
	
//		ParseTags pTags = new ParseTags();
//		GetAnswers ga = new GetAnswers(pTags);
//
//		// parseAndPrint(pTags);
//		parseAndFindAnswers(pTags, ga);
	}

	// Iterate through all the paragraphs, questions and answers and print their tags
	/*public static void parseAndFindAnswers(ParseTags pt, GetAnswers ga) {
		@SuppressWarnings("unused")
		int count = 0;
		ParseDataSet dt = null;
		List<Boolean> listBool = new ArrayList<Boolean>();
		try {
			dt = new ParseDataSet();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Iterate through all the questions
		List<String> para = dt.getAllParagraphs();
		for (int i = 0; i < para.size(); i++) {
			List<String> question = dt.getQuestionFromParagraph(i);
			for (int j = 0; j < question.size(); j++) {
				String qs = question.get(j);
				if (qs.split(" ", 2)[0].equalsIgnoreCase("when")) {
					ArrayList<String> answerString = new ArrayList<String>();
					count++;
					List<String> answers = dt.getAnswersFromQuestion(i, j);
					StringBuilder sb = new StringBuilder();
					for (String a : answers) {
						answerString.add(a);
						sb.append(a);
						sb.append(" ,");
					}
					listBool.add(ga.checkTheAnswers(para.get(i), qs, sb.toString(), answerString));
				}
			}
		}
		ga.printResult(listBool);

	}

	// Iterate through all the paragraphs, questions and answers and print their
	// tags
	public static void parseAndPrint(ParseTags pt) {
		@SuppressWarnings("unused")
		int count = 0;
		ParseDataSet dt = null;
		try {
			dt = new ParseDataSet();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Iterate over the paragraphs
		List<String> para = dt.getAllParagraphs();
		for (int i = 0; i < para.size(); i++) {
			@SuppressWarnings("unused")
			boolean cond = false;
			List<String> question = dt.getQuestionFromParagraph(i);
			for (int j = 0; j < question.size(); j++) {
				String qs = question.get(j);
				String arr[] = qs.split(" ", 2);
				if (arr[0].equalsIgnoreCase("when")) {
					count++;
					cond = true;
					List<String> answers = dt.getAnswersFromQuestion(i, j);
					StringBuilder sb = new StringBuilder();
					for (String a : answers) {
						sb.append(a);
						sb.append(" ,");
					}
					pt.printTags(para.get(i), qs, sb.toString());

				}

			}

		}
	}*/

}
