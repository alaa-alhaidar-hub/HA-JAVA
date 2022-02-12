package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

import java.io.Serializable;

public class Position implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5482937570941095306L;

	public Position(char x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	// 0-9
	char x;
	// a-i
	int y;

	public int getX() {
		return x;
	}

	public void setX(char x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return " " + this.y + "," + this.x + " ";
	}

}
