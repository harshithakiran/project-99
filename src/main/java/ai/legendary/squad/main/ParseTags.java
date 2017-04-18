package ai.legendary.squad.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;

public class ParseTags {

	// Get Sentences From paragraphs
	public List<Sentence> getSentencesFromParagraph(String para) {
		List<Sentence> sentList = new ArrayList<Sentence>();
		Document doc = new Document(para);
		for (Sentence sent : doc.sentences()) {
			sentList.add(sent);
		}
		return sentList;
	}

	// Get POS Tags
	public List<String> getPOSTags(Sentence sent) {
		List<String> posTag = new ArrayList<String>();
		posTag = sent.posTags();
		return posTag;

	}

	// Get Word token from strings
	public List<String> getWords(Sentence sent) {
		List<String> posWord = new ArrayList<String>();
		posWord = sent.words();
		return posWord;
	}

	// Get NER Tags from sentence
	public List<String> getNERTags(Sentence sent) {
		List<String> posNER = new ArrayList<String>();
		posNER = sent.nerTags();
		return posNER;
	}

	// Print all the tags
	public void printTags(String para, String question, String ans) {
		System.out.println("Paragraphs - " + para);
		System.out.println("Question - " + question);
		System.out.println("Answer - " + ans);
		String answer = null;

		Map<Integer, List<String>> hashMapPOSTags = new HashMap<Integer, List<String>>();
		Map<Integer, List<String>> hashMapWordTags = new HashMap<Integer, List<String>>();
		Map<Integer, List<String>> hashMapNerTags = new HashMap<Integer, List<String>>();
		List<Sentence> sentences = this.getSentencesFromParagraph(para);

		for (int i = 0; i < sentences.size(); i++) {
			hashMapPOSTags.put(i, sentences.get(i).posTags());
			hashMapWordTags.put(i, sentences.get(i).words());
			hashMapNerTags.put(i, sentences.get(i).nerTags());

		}

		System.out.println("Paragraph Parsing");

		for (int i = 0; i < sentences.size(); i++) {
			System.out.println("\nSentence " + i);
			System.out.println(sentences.get(i));
			System.out.println("\nWORD - ");
			for (String s : hashMapWordTags.get(i))
				System.out.print(s + " | ");
			System.out.println("\nPOS - ");
			for (String s : hashMapPOSTags.get(i))
				System.out.print(s + " | ");
			System.out.println("\nNER - ");
			for (String s : hashMapNerTags.get(i))
				System.out.print(s + " | ");
		}
		System.out.println("\n\nQuestion Parsing");
		System.out.println(question);

		Sentence sent = new Sentence(question);
		List<String> wordsQ = this.getWords(sent);

		System.out.println("Words");
		for (String s : wordsQ)
			System.out.print(s + " | ");
		List<String> posQ = this.getPOSTags(sent);
		System.out.println();
		System.out.println("POS");
		for (String s : posQ)
			System.out.print(s + " | ");
		List<String> nerQ = this.getNERTags(sent);
		System.out.println();
		System.out.println("NER");
		for (String s : nerQ)
			System.out.print(s + " | ");

	}

}
