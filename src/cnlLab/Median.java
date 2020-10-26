package cnlLab;

public class Median {
	private double[] median;
	private int count;
	
	/**
	 * creates empty median object
	 * @param length total number of doubles you will want to get median from
	 */
	public Median(int length) {
		super();
		this.median = new double[length];
	}
	
	/**
	 * adds a double to the median object
	 * @param x the number to add
	 * @return false if there are no more spots to add, true if number was added
	 */
	public boolean add(double x) {
		if(this.count==this.median.length) {
			return false;
		}
		++this.count;
		boolean added = false;
		double nextIndex = 0;
		for(int i=0;i<this.count;++i) {
			if(x>this.median[i]) {
				if(!added) {
					nextIndex = this.median[i];
					this.median[i] = x;
					added = true;
				}
				else {
					double last = nextIndex;
					nextIndex = this.median[i];
					this.median[i] = last;
				}
			}
		}
		return true;
	}
	
	/**
	 * gets the median (assumes that the array is full for this method)
	 * @return
	 */
	public double getMedian() {
		int medianLoc = this.median.length/2;
		double median = this.median[medianLoc];
		if(this.median.length%2==0) {
			median = (this.median[medianLoc+1]+median)/2;
		}
		return median;
	}
}
