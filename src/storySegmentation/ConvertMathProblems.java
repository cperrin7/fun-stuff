package storySegmentation;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;

public class ConvertMathProblems {

	public static void main(String[] args) {
		String mathP = "C:/Users/CKPer/Box/WashU/Peelle Lab/Story Segmentation/Online Sona data/Segmentation3 delay/Math problems_Chris Zerr.csv";//file with all the words and their frequencies
		In math = new In(mathP);
//		System.out.println(math.readAll());
		//? go to / (division sign)
		//get rid of " and ,
		String output = "";
		while(math.hasNextLine()) {
			String line = math.readLine();
			line = line.replace('"', (char) 0);
			line = line.replace(',', (char) 0);
			line = line.trim();
			//have 14 + 7 = 21 
			//want 14 + 7 =, 21
			String q = line.substring(0, line.indexOf('=')+1);
			String ans = line.substring(line.indexOf('=')+2);
			output = output+q+","+ans+"\n";
		}
		output = output.trim();
//		System.out.println(output);
		Out file = new Out(mathP);
		file.print(output);

	}

}
