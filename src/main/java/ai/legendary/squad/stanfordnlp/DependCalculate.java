package ai.legendary.squad.stanfordnlp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Multimap;

import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.trees.UniversalEnglishGrammaticalRelations;

public class DependCalculate {

	public Integer DepCalci(Collection<TypedDependency> ls1, Collection<TypedDependency> ls2, Map<Integer, List<String>> NerTags_para, Map<Integer, List<String>> WordTags_para, Integer k,  Multimap<Integer, List<String>> multimap,int count){

		List<String> auxques1 = new ArrayList<String>();
		List<String> auxques2 = new ArrayList<String>();

		List<String> caseques1 = new ArrayList<String>();
		List<String> caseques2 = new ArrayList<String>();

		List<String> nsubjques1 = new ArrayList<String>();
		List<String> nsubjques2 = new ArrayList<String>();

		List<String> advmodques1 = new ArrayList<String>();
		List<String> advmodques2 = new ArrayList<String>();

		List<String> amodques1 = new ArrayList<String>();
		List<String> amodques2 = new ArrayList<String>();

		List<String> nummodques1 = new ArrayList<String>();
		List<String> nummodques2 = new ArrayList<String>();

		List<String> xcompques1 = new ArrayList<String>();
		List<String> xcompques2 = new ArrayList<String>();

		List<String> casepara1 = new ArrayList<String>();
		List<String> casepara2 = new ArrayList<String>();

		List<String> nsubjpara1 = new ArrayList<String>();
		List<String> nsubjpara2 = new ArrayList<String>();

		List<String> nmodpara1 = new ArrayList<String>();
		List<String> nmodpara2 = new ArrayList<String>();

		List<String> nummodpara1 = new ArrayList<String>();
		List<String> nummodpara2 = new ArrayList<String>();

		List<String> amodpara1 = new ArrayList<String>();
		List<String> amodpara2 = new ArrayList<String>();

		List<String> advmodpara1 = new ArrayList<String>();
		List<String> advmodpara2 = new ArrayList<String>();

		List<String> compoundpara1 = new ArrayList<String>();
		List<String> compoundpara2 = new ArrayList<String>();
		//int count1 = 0;

		for(TypedDependency td : ls1) {

			if(td.reln().equals(UniversalEnglishGrammaticalRelations.AUX_MODIFIER) 
					|| td.reln().equals(UniversalEnglishGrammaticalRelations.AUX_PASSIVE_MODIFIER)) {
				auxques1.add(td.dep().word());
				auxques2.add(td.gov().word());

			}
			if (td.reln().equals(UniversalEnglishGrammaticalRelations.CASE_MARKER)) {
				caseques1.add(td.dep().word());
				caseques2.add(td.gov().word());

			}
			if (td.reln().equals(UniversalEnglishGrammaticalRelations.NOMINAL_SUBJECT)) {
				nsubjques1.add(td.dep().word());
				nsubjques2.add(td.gov().word());

			}
			if (td.reln().equals(UniversalEnglishGrammaticalRelations.ADJECTIVAL_MODIFIER)) {
				amodques1.add(td.dep().word());
				amodques2.add(td.gov().word());

			}
			if (td.reln().equals(UniversalEnglishGrammaticalRelations.ADVERBIAL_MODIFIER)) {
				advmodques1.add(td.dep().word());
				advmodques2.add(td.gov().word());

			}

			if (td.reln().equals(UniversalEnglishGrammaticalRelations.NUMERIC_MODIFIER)) {
				nummodques1.add(td.dep().word());
				nummodques2.add(td.gov().word());

			}

			if (td.reln().equals(UniversalEnglishGrammaticalRelations.DIRECT_OBJECT)) {
				xcompques1.add(td.dep().word());
				xcompques2.add(td.gov().word());

			}

		}
		System.out.println("AUX Ques: " + auxques1 + "\t"+auxques2);
		System.out.println("CASE Ques: " + caseques1 +"\t"+ caseques2);
		System.out.println("NSUBJ Ques: " + nsubjques1 +"\t"+ nsubjques2);
		System.out.println("AMOD Ques: " + amodques1 +"\t"+ amodques2);
		System.out.println("AdvMOD Ques: " + advmodques1 +"\t"+ advmodques2);
		System.out.println("NumMOD Ques: " + nummodques1 +"\t"+ nummodques2);
		System.out.println("Xcomp Ques: " + xcompques1 +"\t"+ xcompques2);
		System.out.println();

		for(TypedDependency td1 : ls2) {

			if(td1.reln().equals(UniversalEnglishGrammaticalRelations.CASE_MARKER)) {
				casepara1.add(td1.dep().word());
				casepara2.add(td1.gov().word());
			}

			if(td1.reln().equals(UniversalEnglishGrammaticalRelations.NOMINAL_SUBJECT)) {
				nsubjpara1.add(td1.dep().word());
				nsubjpara2.add(td1.gov().word());

			}

			if (td1.reln().equals(UniversalEnglishGrammaticalRelations.NOMINAL_MODIFIER)) {
				nmodpara1.add(td1.dep().word());
				nmodpara2.add(td1.gov().word());
			}

			if (td1.reln().equals(UniversalEnglishGrammaticalRelations.NUMERIC_MODIFIER)) {
				nummodpara1.add(td1.dep().word());
				nummodpara2.add(td1.gov().word());
			}


			if (td1.reln().equals(UniversalEnglishGrammaticalRelations.ADJECTIVAL_MODIFIER)) {
				amodpara1.add(td1.dep().word());
				amodpara2.add(td1.gov().word());
			}

			if (td1.reln().equals(UniversalEnglishGrammaticalRelations.ADVERBIAL_MODIFIER)) {
				advmodpara1.add(td1.dep().word());
				advmodpara2.add(td1.gov().word());
			}
			if (td1.reln().equals(UniversalEnglishGrammaticalRelations.COMPOUND_MODIFIER)) {
				compoundpara1.add(td1.dep().word());
				compoundpara2.add(td1.gov().word());
			}

		}
		System.out.println("Case Para: " + casepara1 +"\t"+ casepara2);
		System.out.println("NSubj Para: " + nsubjpara1 +"\t"+ nsubjpara2);
		System.out.println("NMOD Para: " + nmodpara1 +"\t"+ nmodpara2);
		System.out.println("NUMMOD Para: " + nummodpara1 +"\t"+ nummodpara2);
		System.out.println("AMOD Para: " + amodpara1 +"\t"+ amodpara2);
		System.out.println("AdvMOD Para: " + advmodpara1 +"\t"+ advmodpara2);
		System.out.println("Compnd Para: " + compoundpara1 +"\t"+ compoundpara2);	

	
		
		if ((!auxques1.isEmpty()) && (!auxques2.isEmpty())) {
			System.out.println(" Aux present ");
			for (int i = 0; i < nummodpara1.size() || i < nummodpara2.size(); i++) {
				String fin = nummodpara1.get(i);
				String fin1 = nummodpara2.get(i);
				if (multimap.containsEntry(fin, fin1)) { 
					count++;
					System.out.println(" Matched ");	
				}
			}
		}
		else {
			System.out.println(" Aux not present ");
			for (int i = 0; i < nsubjpara1.size(); i++) {
				for (int j = 0; j < nsubjques1.size(); j++) {
					if (nsubjpara1.get(i)==nsubjques1.get(j) || nsubjpara2.get(i)==nsubjques2.get(j)) {
						System.out.println(" But Matched ");
						count++;
						
					}
				}
			}
			
		}
		return count;
	}
}