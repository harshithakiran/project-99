package ai.legendary.squad.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WhenDataSet {

	ParseDataSet dt;
	Map<Integer, String> paragraphs; // All paragraphs with when questions
	Map<Integer, List<String>> questions; // All when questions

	WhenDataSet() {
		this.dt = null;
		this.paragraphs = new HashMap<Integer, String>();
		this.questions = new HashMap<Integer, List<String>>();

		setDataSet();
		setAllParagraphs();

	}

	public void setDataSet() {
		try {
			this.dt = new ParseDataSet();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setAllParagraphs() {
		// Fetch all the paragraphs from the JSON
		List<String> para = this.dt.getAllParagraphs();
		for (int i = 0; i < para.size(); i++) {
			boolean cond = false;
			List<String> question = dt.getQuestionFromParagraph(i);
			for (int j = 0; j < question.size(); j++) {
				String qs = question.get(j);
				String arr[] = qs.split(" ", 2);

				if (arr[0].equalsIgnoreCase("when")) {

					List<String> answer = dt.getAnswersFromQuestion(i, j);
					if (!paragraphs.containsKey(i))
						paragraphs.put(i, para.get(i));

					if (!questions.containsKey(i))
						questions.put(i, question);

				}
			}

		}

	}

}
