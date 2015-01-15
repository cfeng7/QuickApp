package oose.thequickapp.model;

public class DrawPattern implements Pattern{
	/** two dimensional positions*/
	private double[][] doubleValues2D;
	
	/**
	 * draw pattern constructor 
	 * @param doubleValues two dimensional positions
	 */
	public DrawPattern(double[][] doubleValues){
		this.doubleValues2D = doubleValues;
	}

	/**
	 * get one dimensional values from draw pattern
	 */
	@Override
	public double[] getDoubleValues() {
		if(this.doubleValues2D == null || this.doubleValues2D.length==0 || this.doubleValues2D[0].length==0) return null;
		int width = this.doubleValues2D.length;
		int height = this.doubleValues2D[0].length;
		double[] doubleValues1D = new double[width*height];
		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				doubleValues1D[i*height+j] = doubleValues2D[i][j];
			}
		}
		return doubleValues1D;
	}

}
