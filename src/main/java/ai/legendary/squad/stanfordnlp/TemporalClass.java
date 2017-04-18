package ai.legendary.squad.stanfordnlp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.AnnotationPipeline;
import edu.stanford.nlp.pipeline.POSTaggerAnnotator;
import edu.stanford.nlp.pipeline.TokenizerAnnotator;
import edu.stanford.nlp.pipeline.WordsToSentencesAnnotator;
import edu.stanford.nlp.time.SUTime.Duration;
import edu.stanford.nlp.time.SUTime.Range;
import edu.stanford.nlp.time.SUTime.Temporal;
import edu.stanford.nlp.time.TimeAnnotations;
import edu.stanford.nlp.time.TimeAnnotator;
import edu.stanford.nlp.time.TimeExpression;
import edu.stanford.nlp.util.CoreMap;


public class TemporalClass {

	private AnnotationPipeline pipeline; 

	public TemporalClass() {
		Properties props = new Properties();
		pipeline = new AnnotationPipeline();
		pipeline.addAnnotator(new TokenizerAnnotator(false));
		pipeline.addAnnotator(new WordsToSentencesAnnotator(false));
		pipeline.addAnnotator(new POSTaggerAnnotator(false));
		pipeline.addAnnotator(new TimeAnnotator("sutime", props));
		
		props.setProperty("sutime.markTimeRanges", "true");
		props.setProperty("sutime.includeRange", "true");
		TimeAnnotator sutime = new  TimeAnnotator("sutime", props);
		pipeline.addAnnotator(sutime);
	}

	public void processTemporal(String sentence) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String[] sent = sentence.split(" ");
		//for (int i = 0; i < sent.length; i++) System.out.println(sent[i]);
		System.out.println("\n\n" + sentence); 

		for (String text : sent) {
			Annotation annotation = new Annotation(text);
			annotation.set(CoreAnnotations.DocDateAnnotation.class, dateFormat.format(date).toString());
			pipeline.annotate(annotation);

			//System.out.println(annotation.get(CoreAnnotations.TextAnnotation.class));
			List<CoreMap> timexAnnsAll = annotation.get(TimeAnnotations.TimexAnnotations.class);	      
			for (CoreMap cm : timexAnnsAll) {
				List<CoreLabel> tokens = cm.get(CoreAnnotations.TokensAnnotation.class);
//				String startOffset = tokens.get(0).get(CoreAnnotations.CharacterOffsetBeginAnnotation.class).toString();
//				String endOffset = tokens.get(tokens.size() - 1).get(CoreAnnotations.CharacterOffsetEndAnnotation.class).toString();
				Temporal temp = cm.get(TimeExpression.Annotation.class).getTemporal();
				Range rng = temp.getRange();
				Duration dur = rng.getDuration();
				System.out.println(rng);
				System.out.println(dur);
				System.out.println("   " +  cm + "      [fancy time is --> " + cm.get(TimeExpression.Annotation.class).getTemporal());
			}
		}
	}
}