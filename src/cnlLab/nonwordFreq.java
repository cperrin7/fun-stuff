package cnlLab;


import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;

public class nonwordFreq {

	public static void main(String[] args) {
		//just getting words into a file
		String list1NameT = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/list1_wav files_targets.csv";//list 1, targets (wav files)
		In list1Targets = new In(list1NameT);
		String words = "";
		while(list1Targets.hasNextLine()) {
			String line = list1Targets.readLine();
			String word = line.substring(0, line.indexOf("."));
			words = words+word+"\n";
		}
		words = words.trim();//get rid of last \n
		String outputFullFile = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/target words and frequencies.txt";//place to put all frequencies and wav file names. "majorities" will be empty
		Out full = new Out(outputFullFile);
		full.print(words);
		System.out.println("Output to "+outputFullFile);
	}

}
