package ai.legendary.squad.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.simple.Sentence;

public class SolvingWhen {

	static ParseDataSet dt; // Object to parse the and get info from json data
	static List<Boolean> boolResult; // To store the result for calculating
										// percentage
	static List<String> paragraphs; // To store all the paragraphs in a list
	static List<String> questions; // To store all the questions
	static int checkLoopCounter = 0; //

	public static void main(String[] args) {
		System.out.println("Project-99");
		startup();
		int count = 0;
		for (String s : questions) {

			Sentence sentence = new Sentence(s);
			List<String> wo = sentence.words();

			if (wo.get(0).equalsIgnoreCase("when")) {

				if (sentence.posTag(1).startsWith("N")) {
					System.out.println(s);
					count++;
					System.out.println(sentence.posTag(1));
				}

			}

		}

		System.out.println(count);

	}

	// SolvingWhen() {
	public static void startup() {
		dt = null;
		boolResult = new ArrayList<Boolean>();
		paragraphs = new ArrayList<String>();
		questions = new ArrayList<String>();
		checkLoopCounter = 0;

		setDataSet();
		setAllParagraphs();
		setAllQuestions();

	}

	public static void setDataSet() {
		try {
			dt = new ParseDataSet();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setAllParagraphs() {
		// Fetch all the paragraphs from the JSON
		if (dt != null)
			paragraphs = dt.getAllParagraphs();
		else
			paragraphs = null;

	}

	public static void setAllQuestions() {
		for (int i = 0; i < numberOfParagraphs(); i++) {
			List<String> question = dt.getQuestionFromParagraph(i);
			questions.addAll(question);
		}
	}

	public static int numberOfParagraphs() {
		return paragraphs.size();
	}

	public static List<String> getAllParagraphs() {
		return paragraphs;
	}

	public static String getParagraphByIndex(int index) {
		return paragraphs.get(index);
	}

}
