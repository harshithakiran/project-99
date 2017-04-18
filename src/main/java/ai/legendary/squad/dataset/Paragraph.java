package ai.legendary.squad.dataset;

import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import java.util.List;

public final class Paragraph {

	private String context;
	private List<QuestionAnswerService> qas;

	private transient List<Sentence> contextSentences;

	public String getContext() {
		return context;
	}

	public List<QuestionAnswerService> getQas() {
		return qas;
	}

	public List<Sentence> getContextSentences() {
		if (contextSentences == null) {
			final Document document = new Document(context);
			contextSentences = document.sentences();
		}
		return contextSentences;
	}

	public static List<Sentence> priority;

	@Override
	public String toString() {
		return "Paragraph{" + "context='" + context + '\'' + ", qas=" + qas + '}';
	}
}
