package ai.legendary.squad.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.stanford.nlp.simple.Sentence;

public class GetAnswers {

	private ParseTags pTags;

	GetAnswers(ParseTags parseTags) {
		this.pTags = parseTags;
	}

	// Print the hit rate
	public void printResult(List<Boolean> listBool) {
		System.out.println("\nTotal question -" + listBool.size());
		System.out.println("Correct Answer -" + getCorrectAnswerCount(listBool));
		System.out.println("The success rate is " + getCorrectAnswerRate(listBool) + " %");

	}

	public float getCorrectAnswerRate(List<Boolean> data) {
		return ((float) (getCorrectAnswerCount(data)) * 100) / ((data.size()));
	}

	public int getCorrectAnswerCount(List<Boolean> data) {
		int count = 0;
		for (Boolean res : data) {
			if (res)
				count++;
		}
		return count;
	}

	public boolean checkSecondWordIsNoun(String s) {
		Sentence sentence = new Sentence(s);
		List<String> wo = sentence.words();
		if (sentence.posTag(1).startsWith("N"))
			return true;
		else
			return false;

	}

	public boolean checkTheAnswers(String para, String question, String answer, ArrayList<String> answerList) {
		boolean checkCondition = false;
		System.out.println("\nParagraphs - " + para);
		System.out.println("\nQuestion - " + question);
		System.out.println("\nAnswer - " + answer);

		String calculatedAnswer = this.getAnswer(para, question, this.pTags);

		try {
			calculatedAnswer = calculatedAnswer.substring(0, calculatedAnswer.length() - 1);
		} catch (Exception e) {

		}
		System.out.println("\nMatching Answers - ");
		for (String s : answerList) {

			System.out.println(calculatedAnswer + "--" + s);
			if (s.equalsIgnoreCase(calculatedAnswer)) {
				System.out.println("Matched");
				checkCondition = true;
			}
		}

		return checkCondition;
	}

	public String getAnswer(String para, String question, ParseTags pt) {
		String answer = null;

		Map<Integer, List<String>> hashMapPOSTags = new HashMap<Integer, List<String>>();
		Map<Integer, List<String>> hashMapWordTags = new HashMap<Integer, List<String>>();
		Map<Integer, List<String>> hashMapNerTags = new HashMap<Integer, List<String>>();
		List<Sentence> sentences = pt.getSentencesFromParagraph(para);

		for (int i = 0; i < sentences.size(); i++) {
			hashMapPOSTags.put(i, sentences.get(i).posTags());
			hashMapWordTags.put(i, sentences.get(i).words());
			hashMapNerTags.put(i, sentences.get(i).nerTags());

		}

		Sentence sent = new Sentence(question);
		List<String> wordsQ = pt.getWords(sent);
		List<String> posQ = pt.getPOSTags(sent);
		List<String> nerQ = pt.getNERTags(sent);

		// Get all verbs and nouns from the questions
		List<Integer> indexListV = new ArrayList<Integer>();
		List<Integer> indexListN = new ArrayList<Integer>();
		for (int i = 0; i < posQ.size(); i++) {
			if (posQ.get(i).startsWith("VB")) {
				indexListV.add(i);
			}
			if (posQ.get(i).startsWith("NN")) {
				indexListN.add(i);
			}
		}

		// Get all verbs, dates and nouns from the paragraphs
		Map<Integer, List<Integer>> indexVerb = new HashMap<Integer, List<Integer>>();
		Map<Integer, List<Integer>> indexNoun = new HashMap<Integer, List<Integer>>();
		Map<Integer, List<Integer>> indexDate = new HashMap<Integer, List<Integer>>();

		for (int i = 0; i < sentences.size(); i++) {
			List<Integer> indexListPaV = new ArrayList<Integer>();
			List<Integer> indexListPaN = new ArrayList<Integer>();
			List<Integer> indexListPaD = new ArrayList<Integer>();

			for (int j = 0; j < hashMapPOSTags.get(i).size(); j++) {

				if (hashMapPOSTags.get(i).get(j).startsWith("VB")) {
					indexListPaV.add(j);
				}
				if (hashMapPOSTags.get(i).get(j).startsWith("NN")) {
					indexListPaN.add(j);
				}
			}
			for (int j = 0; j < hashMapNerTags.get(i).size(); j++) {
				if (hashMapNerTags.get(i).get(j).startsWith("DATE")) {
					indexListPaD.add(j);
				}
			}

			indexVerb.put(i, indexListPaV);
			indexNoun.put(i, indexListPaN);
			indexDate.put(i, indexListPaD);
		}

		// verb match
		List<Integer> vMatch = new ArrayList<Integer>();
		Map<Integer, Integer> matchCount = new HashMap<Integer, Integer>();

		for (Integer i : indexListV) {
			String wVerb = sent.lemma(i);
			for (int j = 0; j < sentences.size(); j++) {
				int count = 0;
				for (Integer k : indexVerb.get(j)) {
					if (sentences.get(j).lemma(k).equalsIgnoreCase(wVerb)) {
						if (matchCount.get(j) == null) {
							matchCount.put(j, 1);
						} else {
							matchCount.put(j, matchCount.get(j) + 1);
						}

						count++;
					}

				}
			}
		}
		// Noun match
		for (Integer i : indexListN) {
			String wNoun = sent.lemma(i);
			for (int j = 0; j < sentences.size(); j++) {
				int count = 0;
				for (Integer k : indexNoun.get(j)) {

					if (sentences.get(j).lemma(k).equalsIgnoreCase(wNoun)) {

						if (matchCount.get(j) == null) {
							matchCount.put(j, 1);
						} else {
							matchCount.put(j, matchCount.get(j) + 1);
						}
						count++;
					}

				}
			}
		}

		int prev = 0;
		int maxKey = 0;
		for (Integer key : matchCount.keySet()) {

			int next = matchCount.get(key);

			if (next > prev) {
				prev = matchCount.get(key);
				maxKey = key;
			}
		}
		System.out.println();
		System.out.println("Finding Answers.....");
		System.out.println("Answer is in sentence " + matchCount.get(maxKey));
		StringBuilder sb1 = new StringBuilder();
		try {
			for (Integer j : indexDate.get(maxKey)) {

				sb1.append(hashMapWordTags.get(maxKey).get(j) + " ");
			}
			System.out.println("Answer is " + sb1.toString());
		} catch (Exception e) {
			sb1.append("aaa");
			System.out.println("Answer is " + sb1.toString());
		}
		answer = sb1.toString();

		return answer;

	}

}
