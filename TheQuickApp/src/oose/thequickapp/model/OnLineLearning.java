package oose.thequickapp.model;

public class OnLineLearning implements LearningModel{
	/** online learning model*/
	private static OnLineLearning model;
	/** the parameters of the model, each row represent a list of pattern values for a Y*/
	private double[][] params;
	/** the predicted app scores*/
	private double[] predictedY;
	/** the inputs from user*/
	private double[] inputs;

	/**
	 * @param m the number of apps
	 * @param n the number of pattern features
	 */
	private OnLineLearning(int m, int n){
		params = new double[m][n];
		loadParams();
	}
	
	/**
	 * get the online learning model instance
	 * @param m the number of apps
	 * @param n the number of pattern features
	 * @return the online learning model instance
	 */
	public static OnLineLearning getInstance(int m, int n){
		if (model==null) model = new OnLineLearning(m,n);	//this line has been changed
		return model;
	}
	
	/**
	 * get the online learning instance
	 * @return online learning instance
	 */
	public static OnLineLearning getInstance(){
		return model;
	}
	
	/**
	 * load parameters
	 */
	private void loadParams(){
	}
	
	/**
	 * train the model parameters by user feedback
	 * @param feedBack user feedback
	 */
	@Override
	public void train(FeedBack feedBack) {
		if(params==null || params.length==0 || params[0]==null) return;
		if(predictedY==null || predictedY.length!=params.length) return;

		int n = params[0].length;
		
		int correctYid = feedBack.getCorrectAppId();
		int inCorrectYid = feedBack.getInCorrectAppId();
		for(int i=0;i<n;i++){
			params[correctYid][i] += inputs[i];
		}
	
		for(int i=0;i<n;i++){
			params[inCorrectYid][i] -= inputs[i];
		}
	}

	/**
	 * predict apps by user input pattern values
	 * @param inputs input pattern values
	 * @return predicted apps scores
	 */
	@Override
	public double[] predict(double[] inputs) {
		if(params==null || params.length==0 || params[0]==null) return null;
		if(inputs==null || inputs.length!=params[0].length) return null;
		
		int m = params.length;
		int n = inputs.length;
		
		this.inputs = inputs;
		predictedY = new double[m];
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++){
				predictedY[i] += inputs[j]*params[i][j];
			}
		}
		return predictedY;
	}
}
