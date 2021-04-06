package storySegmentation;

import cse131.ArgsProcessor;

public class ConvertFileNameSlashes {
	
	public static String convertSlashes(String filename) {
		String newFile = "";
		for(char c : filename.toCharArray()) {
			if(c=='\\') {
				newFile = newFile+"/";
			}
			else {
				newFile = newFile+c;
			}	
		}
		return newFile;
	}
	
	public static void main(String[] args) {
		//run this code to convert my copy-pasted directory to the filename that R likes (changes \ to /)
		ArgsProcessor ap = new ArgsProcessor(args);
		String file = ap.nextString("Paste in directory to convert slashes");
		System.out.println(convertSlashes(file));

	}

}
