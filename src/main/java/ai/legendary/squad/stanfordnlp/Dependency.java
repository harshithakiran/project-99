package ai.legendary.squad.stanfordnlp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import ai.legendary.squad.dataset.Answer;
import ai.legendary.squad.dataset.Article;
import ai.legendary.squad.dataset.Dataset;
import ai.legendary.squad.dataset.Paragraph;
import ai.legendary.squad.dataset.QuestionAnswerService;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.trees.TypedDependency;

public class Dependency {

	public void DependencyGet() throws IOException, ClassNotFoundException {

		int fin = 0;
		float hitrate = 0;
		// Map<Integer, List<String>> POSTags_para = new HashMap<Integer,
		// List<String>>();
		// Map<Integer, List<String>> WordTags_para = new HashMap<Integer,
		// List<String>>();
		// Map<Integer, List<String>> NerTags_para = new HashMap<Integer,
		// List<String>>();
		List<String> paracont = new ArrayList<String>();
		Collection<TypedDependency> ls = null;
		Collection<TypedDependency> ls1 = null;

		Map<Integer, List<String>> POSTags_ques = new HashMap<Integer, List<String>>();
		Map<Integer, List<String>> WordTags_ques = new HashMap<Integer, List<String>>();
		Map<Integer, List<String>> NerTags_ques = new HashMap<Integer, List<String>>();

		// Map<Integer, List<String>> answerList = new HashMap<Integer,
		// List<String>>();
		List<Sentence> ques_list = new ArrayList<Sentence>();
		Multimap<Integer, List<String>> multimap = ArrayListMultimap.create();

		List<Integer> parindex = new ArrayList<Integer>();
		List<Integer> quesindex = new ArrayList<Integer>();
		int count = 0;

		// read JSON file data as String
		Dataset dt = Dataset.loadDataset(
				"src/main/resources/dev-v1.1.json");
		List<Article> article = dt.getData();
		for (int i = 0; i < article.size(); i++) {
			List<Paragraph> par = article.get(i).getParagraphs();
			for (int j = 0; j < par.size(); j++) {
				paracont.add(par.get(j).getContext());
				List<QuestionAnswerService> ques = par.get(j).getQas();
				List<Answer> answer = new ArrayList<Answer>();
				for (int l = 0; l < ques.size(); l++) {
					String strnew = ques.get(l).getQuestion().toString().toLowerCase();
					List<String> ans = new ArrayList<String>();
					String arrr[] = strnew.split(" ", 2);
					if (arrr[0].equalsIgnoreCase("when")) {
						count++;
						answer.addAll(ques.get(l).getAnswers());
						for (int k = 0; k < answer.size(); k++) {
							ans.add(answer.get(k).getText());
							multimap.put(j, ans);
							quesindex.add(k);
						}
						Sentence sen = new Sentence(strnew);
						ques_list.add(sen);
						parindex.add(j);
					}
				}
			}
		}

		// for (Object key : multimap.values()) {
		// System.out.println(key);
		// }

		// POS, NER and Dependencies for "when" question
		for (int m = 0; m < ques_list.size(); m++) {
			String str1 = ques_list.get(m).toString().toLowerCase();

			if (POSTags_ques.get(m) != null && WordTags_ques.get(m) != null && NerTags_ques.get(m) != null) {

			} else {
				String para = paracont.get(parindex.get(m));
				List<Sentence> par_list = new ArrayList<Sentence>();
				Document doc = new Document(para);
				for (Sentence sen : doc.sentences()) {
					par_list.add(sen);
				}
				POSTags_ques.put(m, ques_list.get(m).posTags());
				WordTags_ques.put(m, ques_list.get(m).words());
				NerTags_ques.put(m, ques_list.get(m).nerTags());
				System.out.println();
				Posget ps = new Posget();
				ls = ps.Depende(str1);
				System.out.println();

				for (int k = 0; k < par_list.size(); k++) {
					Map<Integer, List<String>> NerTags_para = new HashMap<Integer, List<String>>();
					NerTags_para.put(m, par_list.get(k).posTags());
					Map<Integer, List<String>> WordTags_para = new HashMap<Integer, List<String>>();
					WordTags_para.put(m, par_list.get(k).words());
					String strpara = par_list.get(k).toString().toLowerCase();
					ls1 = ps.Depende(strpara);
					System.out.println();
					DependCalculate dc = new DependCalculate();
					fin = dc.DepCalci(ls, ls1, NerTags_para, WordTags_para, k, multimap, fin);
					System.out.println();
					// System.out.println(fin);
					hitrate = ((fin / 2) / count) * 100;
					// System.out.println(fin);
				}
				System.out.println();
			}
		}
		// System.out.println("Total hit % " + hitrate);
		// System.out.println(ls);
		// System.out.println(ls1);

		// POS, NER Tags for paragraph
		// for (int k = 0; k < parindex.size(); k++) {
		// if (POSTags_para.get(k) != null && WordTags_para.get(k)!=null &&
		// NerTags_para.get(k)!=null) {
		//
		// }
		// else {
		//
		// Sentence senpara = new Sentence(paracont.get(k));
		// par_list.add(senpara);
		// POSTags_para.put(k, senpara.posTags());
		// WordTags_para.put(k, senpara.posTags());
		// NerTags_para.put(k, senpara.posTags());
		// String para = senpara.toString().toLowerCase();
		// Posget ps = new Posget();
		// ls = ps.Depende(para);
		// System.out.println();
		//
		// System.out.println("\nPrinting Sentence: "+ paracont.get(k));
		// System.out.println("POS - ");
		// for (String strpos : POSTags_para.get(k))
		// System.out.print(strpos + " | ");
		// System.out.println("\nWORD - ");
		// for String strword : WordTags_para.get(k))
		// System.out.print(strword + " | ");
		// System.out.println("\nNER - ");
		// for (String strner : NerTags_para.get(k))
		// System.out.print(strner + " | ");

		// }
		// }
	}
}