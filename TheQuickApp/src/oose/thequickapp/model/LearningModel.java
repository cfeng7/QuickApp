package oose.thequickapp.model;

/**
 * The interface of the model of algorithms
 * @author niuxiang
 *
 */
public interface LearningModel {
	/**
	 * used to train the model by user feedback
	 * @param feedBack user feedback
	 */
	public abstract void train(FeedBack feedBack);
	
	/**
	 * used to predict the apps by the model
	 * @param x the double values of user patterns
	 * @return predicted apps scores
	 */
	public abstract double[] predict(double[] x);
}
