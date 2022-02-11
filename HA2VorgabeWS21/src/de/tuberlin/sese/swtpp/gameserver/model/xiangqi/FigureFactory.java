package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

import java.io.Serializable;

public abstract class FigureFactory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1288976529363673760L;
	
	public static Figure createFigure(char representation) {
		
		switch(representation) {
			case 'A':
				return new Advisor(true, new Position((char) 0, 0), "A");
		
			case 'E':
				return new Elephant(true, new Position((char) 0, 0), "E");
		
			case 'H':
				return new Horse(true, new Position((char) 0, 0), "H");
		
			case 'R':
				return new Rock(true, new Position((char) 0, 0), "R");
		
			case 'C':
				return new Cannon(true, new Position((char) 0, 0), "C");
		
			case 'S':
				return new Soldier(true, new Position((char) 0, 0), "S");
			case 'G':
				return new General(true, new Position((char) 0, 0), "G");
		
			case 'a':
				return new Advisor(false, new Position((char) 0, 0), "a");
		
			case 'e':
				return new Elephant(false, new Position((char) 0, 0), "e");
		
			case 'h':
				return new Horse(false, new Position((char) 0, 0), "h");
		
			case 'r':
				return new Rock(false, new Position((char) 0, 0), "r");
		
			case 'c':
				return new Cannon(false, new Position((char) 0, 0), "c");
		
			case 's':
				return new Soldier(false, new Position((char) 0, 0), "s");
			case 'g':
				return new General(false, new Position((char) 0, 0), "g");
			}
		
		return null;
	}
	

}
