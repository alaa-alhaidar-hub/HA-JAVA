package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

import java.io.Serializable;

public class Position implements Serializable {

	public int x;
	public int y;
	private static final int ROWS_COUNT = 9;

	private static final long serialVersionUID = 5482937570941095306L;

	public Position(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Position(String positionRepresentation) {
		char rowRepresentation = positionRepresentation.charAt(0);
		char columnRepresentation = positionRepresentation.charAt(1);

		this.x = rowRepresentation - 'a';
		this.y = ROWS_COUNT - (Character.getNumericValue(columnRepresentation));
	}

	public Position deltaTo(Position targetPos) {
		return new Position(targetPos.x - this.x, targetPos.y - this.y);
	}

	public Position offsetWith(Position deltaPosition) {
		return new Position(this.x + deltaPosition.x, this.y + deltaPosition.y);
	}

	public Position copy() {
		return new Position(this.x, this.y);
	}

//	@Override
////	public String toString() {
//////		char rowRepresentation = (char) ((ROWS_COUNT - this.x) + '0');
//////		char columnRepresentation = (char) (this.y + 'a');
////
////		char rowRepresentation = (char) ((ROWS_COUNT - this.y) + '0');
////		char columnRepresentation = (char) (this.x + 'a');
////
////		return Character.toString(columnRepresentation) + rowRepresentation;
////	}
}
