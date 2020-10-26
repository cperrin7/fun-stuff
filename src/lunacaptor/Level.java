package lunacaptor;

import java.awt.Color;

import edu.princeton.cs.introcs.StdDraw;

public class Level {
	//level object that I can draw and stuff
	private int exitLoc, entranceLoc, length, lunaLoc;
	private boolean lunaCaught;
	

	/**
	 * Creates a level, based on the size of the previous level's entrances specifying how wide the level is. Can have one to three exits and less walls
	 * @param entrances, an array specifying the entrances to this level (exits from last level)
	 */
	public Level(int entranceLoc, int levelLength) {
		super();
		this.entranceLoc = entranceLoc;
		if(levelLength<3) {
			throw new IllegalArgumentException("The level must be at least 3 units wide, but was instead " + levelLength);
		}
		this.length = levelLength;
		this.lunaCaught = false;
		setExitLocation();
		setLunaLocation();
	}

	/**
	 * sets location for the exit in level
	 */
	private void setExitLocation() {
		int loc = (int)(Math.random()*this.length);
		while(loc==this.entranceLoc) {//if there is an entrance here, don't place exit
			loc = (int)(Math.random()*this.length);
		}
		this.exitLoc = loc;
	}
	
	/**
	 * sets location of the luna
	 */
	private void setLunaLocation() {
		int loc = (int)(Math.random()*this.length);
		while(loc==this.entranceLoc||loc==this.exitLoc) {//if there is an entrance or exit here, don't place luna
			loc = (int)(Math.random()*this.length);
		}
		this.lunaLoc = loc;
	}

	/**
	 * 
	 * @return the location of the exit
	 */
	public int getExitLoc() {
		return this.exitLoc;
	}
	
	/**
	 * 
	 * @return the location of the entrance
	 */
	public int getEntranceLoc() {
		return this.entranceLoc;
	}
	
	/**
	 * 
	 * @return the length of this level
	 */
	public int getLevelLength() {
		return this.length;
	}
	
	/**
	 * 
	 * @return the location of the luna
	 */
	public int getLunaLoc() {
		return this.lunaLoc;
	}
	
	/**
	 * draws this level, after luna has been taken and exits revealed
	 * @param beginX the left side of the level
	 * @param endX the right side of the level
	 * @param beginY the bottom of the level
	 * @param endY the top of the level
	 * @param lineColor the color of this level
	 */
	private void drawLevelExits(double beginX, double endX, double beginY, double endY, Color levelColor) {
		double xUnit = ((double)endX-beginX)/this.length;
		//draw top line with the exits (previous level will have drawn bottom line)
		StdDraw.setPenColor(levelColor);
		StdDraw.filledRectangle(beginX+(endX-beginX)/2, beginY+(endY-beginY)/2, (endX-beginX)/2, (endY-beginY)/2);
		StdDraw.setPenColor(StdDraw.YELLOW);
		StdDraw.setPenRadius(0.02);
		double endSeg1 = xUnit*this.exitLoc;
		StdDraw.line(beginX, endY, endSeg1, endY);
		double startSeg2 = (xUnit)*this.exitLoc+1;
		StdDraw.line(startSeg2, endY, endX, endY);
		StdDraw.setPenRadius();
	}
	
	/**
	 * draws this level before luna has been taken and exits revealed
	 * @param beginX the left side of the level
	 * @param endX the right side of the level
	 * @param beginY the bottom of the level
	 * @param endY the top of the level
	 * @param lineColor the color of this level
	 */
	private void drawLevelNoExits(double beginX, double endX, double beginY, double endY, Color levelColor) {
		//draw top line with the exits (previous level will have drawn bottom line)
		StdDraw.setPenColor(levelColor);
		StdDraw.filledRectangle(beginX+(endX-beginX)/2, beginY+(endY-beginY)/2, (endX-beginX)/2, (endY-beginY)/2);
		StdDraw.setPenColor(StdDraw.YELLOW);
		StdDraw.setPenRadius(0.02);
		StdDraw.line(beginX, endY, endX, endY);
		StdDraw.setPenRadius();
	}
	
	/**
	 * draws the luna
	 * param beginX the left side of the level
	 * @param endX the right side of the level
	 * @param beginY the bottom of the level
	 * @param endY the top of the level
	 */
	private void drawLuna(double beginX, double endX, double beginY, double endY) {
		double d = (endY-beginY) - (endY-beginY)/10;
		double r = d/2;
		StdDraw.setPenColor(new Color(190, 247, 245));
		double y = beginY+(endY-beginY)/2.0;
		double xUnit = (endX-beginX)/(double)this.length;
		double x = xUnit*this.lunaLoc + xUnit/2;
		StdDraw.filledCircle(x, y, r);
		StdDraw.setPenColor(new Color(123, 158, 157));
		StdDraw.circle(x, y, r);
	}
	
	/**
	 * Will draw the level in the proper state, according to inputs
	 * @param beginX the left side of the level
	 * @param endX the right side of the level
	 * @param beginY the bottom of the level
	 * @param endY the top of the level
	 * @param lineColor the color of this level
	 * @param lunaExists true if we want to draw the luna (have arrived to the level and it is not caught)
	 * @param lunaCaught true if the luna has been caught (will not draw luna even if previous input is true) - will draw exits
	 */
	public void drawLevel(double beginX, double endX, double beginY, double endY, Color levelColor, boolean lunaExists, boolean lunaCaught) {
		if(lunaCaught) {//luna has been caught, reveal exits
			drawLevelExits(beginX, endX, beginY, endY, levelColor);
		}
		else if(lunaExists) {//just arrived on level
			drawLevelNoExits(beginX, endX, beginY, endY, levelColor);
			drawLuna(beginX, endX, beginY, endY);
		}
		else {//haven't arrived on level yet
			drawLevelNoExits(beginX, endX, beginY, endY, levelColor);
			//add a sun instead of a luna?
		}
	}



}
