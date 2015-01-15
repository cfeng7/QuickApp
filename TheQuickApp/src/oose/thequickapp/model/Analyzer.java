package oose.thequickapp.model;


/**
 * an analyzer used to perform on a certain pattern.
 * @author ewen
 *
 */
public class Analyzer {
	/** a pattern performed by a user*/
	private Pattern pattern;
	/** an algorithm model used to analyze a pattern*/
	private LearningModel model;
	
	/**
	 * Analyzer constructor
	 * @param pattern a pattern performed by a user
	 * @param model an algorithm model
	 */
	public Analyzer(Pattern pattern, LearningModel model){
		this.pattern = pattern;
		this.model = model;
	}
	
	/**
	 * Given a pattern, find an ordered list of applications.
	 * @return application list
	 */
	public AppList analyze(){
		double[] inputs = pattern.getDoubleValues();
		double[] predictedY = model.predict(inputs);
		//for(int i=0;i<4;i++)
		//	System.err.println(inputs[i]);
		AppList.getInstance().setAppScores(predictedY);
		return AppList.getInstance();					//should return?
	}
	
	/**
	 * update model parameters by a user feedback
	 * @param feedBack a user feedback
	 */
	public void userFeedback(FeedBack feedBack){
		model.train(feedBack);
	}
}
