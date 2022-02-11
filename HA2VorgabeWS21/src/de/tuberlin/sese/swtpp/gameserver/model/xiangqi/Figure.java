package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

import java.io.Serializable;

import de.tuberlin.sese.swtpp.gameserver.model.Player;

public abstract class Figure implements Serializable {
	boolean red = false;
	Position position;
	String name;
	
	public Figure(boolean red, Position position, String name) {
		super();
		this.red = red;
		this.position = position;
		this.name = name;
	}

	public abstract boolean checkMove(String moveString, Player player, Figure[][] board, XiangqiGame xg);

	@Override
	public String toString() {
		String farbe="";
		if(red) {farbe="red";}else farbe="black";

		return "["+ name + "]";
	}

}
