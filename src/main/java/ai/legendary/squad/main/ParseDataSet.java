package ai.legendary.squad.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ai.legendary.squad.dataset.Answer;
import ai.legendary.squad.dataset.Article;
import ai.legendary.squad.dataset.Dataset;
import ai.legendary.squad.dataset.Paragraph;
import ai.legendary.squad.dataset.QuestionAnswerService;

public class ParseDataSet {

	Dataset dt;
	List<Article> article;
	List<Paragraph> paragraphs;
	List<Paragraph> lparagraphs;
	List<QuestionAnswerService> qas;

	ParseDataSet() throws IOException {
		dt = Dataset.loadDataset("src/main/resources/dev-v1.1.json");
		article = dt.getData();
		lparagraphs = new ArrayList<Paragraph>();

	}

	ParseDataSet(String filename) throws IOException {
		dt = Dataset.loadDataset(filename);
		article = dt.getData();

	}

	public List<String> getAllParagraphs() {
		List<String> listParagraphs = new ArrayList<String>();
		for (Article a : article) {
			paragraphs = a.getParagraphs();
			for (Paragraph p : paragraphs) {
				// listParagraphs.add(p.getContext());
				lparagraphs.add(p);
			}

		}

		System.out.println(lparagraphs.size());
		for (Paragraph p : lparagraphs)
			listParagraphs.add(p.getContext());

		return listParagraphs;
	}

	public List<String> getQuestionFromParagraph(int pid) {
		List<String> question = new ArrayList<String>();
		Paragraph p = lparagraphs.get(pid);
		// System.out.println(p.getContext());

		List<QuestionAnswerService> qas = p.getQas();
		for (QuestionAnswerService qa : qas) {
			question.add(qa.getQuestion());
		}
		return question;
	}

	public List<String> getAnswersFromQuestion(int pid, int qid) {
		List<String> answers = new ArrayList<String>();
		Paragraph p = lparagraphs.get(pid);
		List<QuestionAnswerService> qas = p.getQas();
		List<Answer> ans = qas.get(qid).getAnswers();

		for (Answer a : ans) {
			answers.add(a.getText());
		}

		return answers;

	}

}
