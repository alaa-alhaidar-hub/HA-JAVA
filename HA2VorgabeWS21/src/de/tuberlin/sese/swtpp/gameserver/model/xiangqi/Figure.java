package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

import java.io.Serializable;

public abstract class Figure implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8218913367653870892L;
	boolean red = false;
	Position position;
	String name;
	
	public Figure(boolean red, Position position, String name) {
		super();
		this.red = red;
		this.position = position;
		this.name = name;
	}

	public abstract boolean checkMove(String moveString, Figure[][] board, XiangqiGame xg);
	
}
