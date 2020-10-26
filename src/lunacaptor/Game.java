package lunacaptor;

import cse131.ArgsProcessor;
import edu.princeton.cs.introcs.StdDraw;

public class Game {

	public static void main(String[] args) {
		ArgsProcessor ap = new ArgsProcessor(args);
		//using wasd
		StdDraw.setXscale(0, 10);
		StdDraw.setYscale(0, 10);
		
		//setup game
		int speed = 0;
		boolean validLevel = false;
		while(!validLevel) {
			validLevel = true;
			String level = ap.nextString("What level do you want? Easy, medium, or hard");
			if(level.equalsIgnoreCase("easy")) {
				speed = 500;
			}
			else if(level.equalsIgnoreCase("medium")) {
				speed = 300;
			}
			else if(level.equalsIgnoreCase("hard")) {
				speed = 100;
			}
			else {
				System.out.println("Please input 'easy', 'medium', or 'hard' ");
				validLevel = false;
			}
		}
		int numLevels = ap.nextInt("How many levels do you want on screen? (i.e. 10)");
		int width = ap.nextInt("How wide do you want levels to be? (i.e. 10)");
		LunaCaptor game = new LunaCaptor(numLevels, speed, 0, 10, width);
		
		//beginning for game (animation?, instructions, etc.)
		
		
		boolean right = true;
		game.drawCurrentMap(right);
		int time = 0;
		while(true) {
			if(game.checkEndGame()) {
				//FIX ME: this didn't work
				break;
			}
			StdDraw.clear();
			//draw map
			game.drawCurrentMap(right);
			
			//get player input
			if(StdDraw.hasNextKeyTyped()) {
				char key = StdDraw.nextKeyTyped();
				if(key=='a') {
					right = false;
					game.movePlayerLeft();
				}
				else if(key=='d') {
					right = true;
					game.movePlayerRight();
				}
				else if(key=='w') {
					game.movePlayerUp();
				}
				else if(key=='s') {
					game.movePlayerDown();//this isn't working properly
				}
				else {
					System.out.println("Player is moved using WASD");
				}
			}
			
			//check the luna capture
			game.captureLuna();
			
			//move the level
			time = game.rotateLevels(time);
			++time;

			StdDraw.show(10);
		}
		//handle the end of the game (death, points, etc.)
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.filledRectangle(5, 5, 10, 10);
		System.out.println("You died");
		System.out.println("You had a score of: "+game.getScore());
		StdDraw.show(10);
	}

}
