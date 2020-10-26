package cnlLab;


import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;

public class LogFreq {

	public static void main(String[] args) {
		//code to sort through words from semantic priming experiment to pair them with their frequencies
		//old, didn't use this
		
		String statsName = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/logfreq_info.csv";//file of words from compressedwords with frequencies
		String statsName2 = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/logfreq_info_others.csv";//file of frequencies from English Lexicon Project
		String logfreqName = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/logfreq_setup.csv";//file of names of wav files, in order that they're in for semantic priming
		In statsFile = new In(statsName);
		In statsFile2 = new In(statsName2);
		In logfreqFile = new In(logfreqName);
		//		String statistics = statsFile.readAll();
		Map<String, Double> statistics = new HashMap<String, Double>();
		//load first file into map
		while(statsFile.hasNextLine()) {
			String line = statsFile.readString();
			String word = line.substring(0,line.indexOf(","));
			String logfreqS = line.substring(line.indexOf(",")+1,line.length());
			double logfreq = Double.parseDouble(logfreqS);
			statistics.put(word, logfreq);
			if(word.equals("boot")) {//it tried to go past the end for some reason
				break;
			}
		}
		
		//load second file into map
		while(statsFile2.hasNextLine()) {
			String line = statsFile2.readString();
//			System.out.println(line);
			String word = line.substring(0,line.indexOf(","));
			String logfreqS = line.substring(line.indexOf(",")+1,line.length());
			double logfreq = Double.parseDouble(logfreqS);
			statistics.put(word, logfreq);
			if(word.equals("sand")) {//it tried to go past the end for some reason
				break;
			}
		}

		//find the word frequencies to match the words for semantic priming, then output it
		String outputGood = "";
		String outputNull = "";
		String outputFull = "";
		while(logfreqFile.hasNextLine()) {
			String line = logfreqFile.readLine();
			String word = line.substring(0, line.indexOf("_"));
			if(word.charAt(word.length()-1)=='1'||word.charAt(word.length()-1)=='2') {
				word = word.substring(0, word.length()-1);
			}
//			System.out.println(word);
			if(statistics.get(word)!=null) {
				outputGood = outputGood+line+","+word+","+statistics.get(word)+"\n";
				outputFull = outputFull+line+","+word+","+statistics.get(word)+"\n";
			}
			else {
				outputNull = outputNull+line+","+word+"\n";
				outputFull = outputFull+line+","+word+"\n";
			}
		}
		
		System.out.println(outputNull);
		String outputGoodFile = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/logfreq_good.csv";
		String outputNullFile = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/logfreq_null.csv";
		String outputFullFile = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/logfreq_full.csv";
		Out good = new Out(outputGoodFile);
		good.print(outputGood);
		Out nullFile = new Out(outputNullFile);
		nullFile.print(outputNull);
		Out full = new Out(outputFullFile);
		full.print(outputFull);

		System.out.println("Done");
		statsFile.close();
		logfreqFile.close();
		
		double sum = 0;
		int count = 0;
		for(String word : statistics.keySet()) {
			sum = sum + statistics.get(word);
			++count;
		}
		System.out.println("Mean frequency: "+sum/count);

	}

}
