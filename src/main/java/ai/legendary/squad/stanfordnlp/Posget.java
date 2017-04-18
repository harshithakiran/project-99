package ai.legendary.squad.stanfordnlp;
import java.util.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

public class Posget {

	public Collection<TypedDependency> Depende(String ques) {
		// TODO Auto-generated method stub
		LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
		lp.setOptionFlags(new String[] { "-maxLength", "80","-retainTmpSubcategories" });
		String[] sent = ques.split(" ");
		System.out.println("<---------------------------------------->");

		List<CoreLabel> rawWords = Sentence.toCoreLabelList(sent);
		Tree parse = lp.apply(rawWords);
		System.out.println();
		
		TreebankLanguagePack tlp = new PennTreebankLanguagePack();
		GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
		GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
		Collection<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
		System.out.println();
//		System.out.println(tdl);
//		TreePrint tp = new TreePrint("penn,typedDependenciesCollapsed");
//		tp.printTree(parse);
		return tdl;
	}
}
