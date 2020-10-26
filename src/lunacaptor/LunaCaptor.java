package lunacaptor;

import java.awt.Color;

import edu.princeton.cs.introcs.StdDraw;

public class LunaCaptor {
	//make a maze game where it generates random levels that move down toward you randomly? not really a maze
	//can show story at beginning of game?
	//maybe make powerups and extra points and stuff too
	private int numLevelsOnScreen;
	private double beginX, endX, beginY, endY, unitX, unitY;
	private Level[] map;
	private int playerLevel;
	private int playerLoc;
	private int lunaLevel;
	private int speed;
	private int levelStart;
	private int score;


	/**
	 * creates a Luna Captor game, based on the inputs
	 * @param numLevelsOnScreen
	 * @param speed the speed the levels move (if 5, then every 5 loops it will move a level, etc.)
	 * @param beginX x and y value that canvas starts at
	 * @param endX x and y value that canvas ends at
	 * @param levelLength the width of the level
	 */
	public LunaCaptor(int numLevelsOnScreen, int speed, double beginX, double endX, int levelLength) {
		super();
		this.speed = speed;
		this.numLevelsOnScreen = numLevelsOnScreen;
		this.beginX = beginX;
		this.endX = endX;
		this.beginY = beginX;
		this.endY = endX;
		this.playerLevel = numLevelsOnScreen/2;//start at middle
		this.lunaLevel = numLevelsOnScreen/2;
		this.map = new Level[numLevelsOnScreen];
		this.playerLoc = levelLength/2;
		this.map[0] = new Level(this.playerLoc, levelLength);
		for(int i=1;i<numLevelsOnScreen;++i) {
			int nextEntrance = this.map[i-1].getExitLoc();
			this.map[i] = new Level(nextEntrance, levelLength);
		}
		this.unitX = (endX-beginX)/levelLength;
		this.unitY = (this.endY-this.beginY)/levelLength;
		this.levelStart = 0;
	}
	
	/**
	 * calculates the highest level
	 * @return
	 */
	private int topLevel() {
		return this.levelStart==0 ? this.numLevelsOnScreen-1 : this.levelStart-1;
	}

	/**
	 * moves player up a level if possible
	 */
	public void movePlayerUp() {
		int topLevel = topLevel();
		int lunaLev = this.lunaLevel;
		if(this.lunaLevel<this.levelStart&&this.playerLevel>=this.levelStart) {
			lunaLev = this.numLevelsOnScreen+this.lunaLevel;
		}

		if(this.playerLevel!=topLevel&&this.playerLevel<lunaLev) {
			int exit = this.map[this.playerLevel].getExitLoc();
			if(this.playerLoc==exit) {
				++this.playerLevel;
				++this.score;
				if(this.playerLevel==this.numLevelsOnScreen) {
					this.playerLevel = 0;
				}
			}
		}
	}

	/**
	 * moves player down a level if possible
	 */
	public void movePlayerDown() {// FIX ME
		if(convertIndex(this.playerLevel)>0) {
			int entrance = this.map[this.playerLevel].getEntranceLoc();
			if(this.playerLoc==entrance) {
				--this.score;
				--this.playerLevel;
			}
		}
	}

	/**
	 * moves player right, if possible
	 */
	public void movePlayerRight() {
		if(this.playerLoc<this.map[0].getLevelLength()-1) {
			++this.playerLoc;
		}
	}

	/**
	 * moves player left, if possible
	 */
	public void movePlayerLeft() {
		if(this.playerLoc>0) {
			--this.playerLoc;
		}
	}

	/**
	 * draws current map
	 * @param right true if player is moving right, false if moving left
	 */
	public void drawCurrentMap(boolean right) {
		double y = this.beginY;
		//start color black at bottom (0,0,0), cyan (0, 255, 255) at top
		int unitColor = 255/this.numLevelsOnScreen*2;
		int blue = 0;
		int green = 0;
		Color levelColor = new Color(0,green,blue);
		for(int i=this.levelStart;i<map.length;++i) {
			if(convertIndex(i)<convertIndex(this.lunaLevel)) {
				this.map[i].drawLevel(beginX, endX, y, y+this.unitY, levelColor, false, true);
			}
			else if(i==this.lunaLevel) {
				this.map[i].drawLevel(beginX, endX, y, y+this.unitY, levelColor, true, false);
			}
			else {
				this.map[i].drawLevel(beginX, endX, y, y+this.unitY, levelColor, false, false);
			}
			if(i==this.playerLevel) {
				drawPlayer(y, y+this.unitY, right);
			}
			y = y+this.unitY;
			if(blue<255-unitColor) {
				blue = blue+unitColor;
			}
			else {
				green = green+unitColor;
			}
			green = green>255 ? 255 : green;
			levelColor = new Color(0,green,blue);
		}
		for(int i=0;i<this.levelStart;++i) {
			if(convertIndex(i)<convertIndex(this.lunaLevel)) {
				this.map[i].drawLevel(beginX, endX, y, y+this.unitY, levelColor, false, true);
			}
			else if(i==this.lunaLevel) {
				this.map[i].drawLevel(beginX, endX, y, y+this.unitY, levelColor, true, false);
			}
			else {
				this.map[i].drawLevel(beginX, endX, y, y+this.unitY, levelColor, false, false);
			}
			if(i==this.playerLevel) {
				drawPlayer(y, y+this.unitY, right);
			}
			y = y+this.unitY;
			if(blue<255-unitColor) {
				blue = blue+unitColor;
			}
			else {
				green = green+unitColor;
			}
			green = green>255 ? 255 : green;
			levelColor = new Color(0,green,blue);
		}
	}

	/**
	 * converts this index to a normal level scheme
	 * @param index
	 * @return the converted index
	 */
	private int convertIndex(int index) {
		if(index>=this.levelStart) {
			return index - this.levelStart;
		}
		return this.numLevelsOnScreen - this.levelStart + index;
	}

	/**
	 * draws player, facing right if third input is true left if false
	 * @param yBegin beginning of this level
	 * @param yEnd end of this level
	 * @param right true if player is moving right
	 */
	public void drawPlayer(double yBegin, double yEnd, boolean right) {
		//pixel art of girl with bug net
		String filename = "taingstuff/lunacaptor/sprite_left.png";
		if(right) {
			filename = "taingstuff/lunacaptor/sprite_right.png";
		}
		StdDraw.picture(this.playerLoc*this.unitX+this.unitX/2, yBegin+(yEnd-yBegin)/2, filename, this.unitX-(this.unitX/10), this.unitY-this.unitY/10);
	}

	/**
	 * checks if we captured the luna, and moves the luna if we did
	 * @return true if we captured the luna
	 */
	public boolean captureLuna() {
		if(this.playerLevel==this.lunaLevel&&this.playerLoc==this.map[this.lunaLevel].getLunaLoc()&&this.lunaLevel!=topLevel()) {
			++this.lunaLevel;
			if(this.lunaLevel==10) {
				this.lunaLevel = 0;
			}
			return true;
		}
		return false;
	}

	/**
	 * checks if it's time to rotate the levels, and if so updates the map
	 * @param time
	 * @return  time should be set as (reset if we rotate) 
	 */
	public int rotateLevels(int time) {
		if(time>=speed) {
			//			System.out.println(this.playerLevel+" and "+this.levelStart+" and "+this.lunaLevel);

			int pastLevel = this.levelStart - 1;
			if(this.levelStart==0) {
				pastLevel = this.map.length - 1;
			}
			this.map[this.levelStart] = new Level(this.map[pastLevel].getExitLoc(), this.map[0].getLevelLength());//add new level to the map, replacing the lowest level
			++this.levelStart;//make the lowest level the next level
			if(this.levelStart>=this.map.length) {
				this.levelStart = 0;
			}
			return 0;
		}
		return time;
	}

	/**
	 * checks if the player went out of bounds
	 * @return true if the player is about to go out of bounds
	 */
	public boolean checkEndGame() {
		return convertIndex(this.playerLevel)<0;
	}
	
	/**
	 * calculates the score the player got: based on the number of levels the moved from their starting place
	 * @return
	 */
	public int getScore() {
		return this.score;
	}

}
