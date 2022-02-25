package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;
import java.io.Serializable;

public class Position implements Serializable {

	private static final long serialVersionUID = 5482937570941095306L;

	public int x;
	public int y;
	private static final int ROWS_COUNT = 9;

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

	@Override
	public boolean equals(Object other) {
		Position otherPosition = (Position) other;
		return this.x == otherPosition.x && this.y == otherPosition.y;
	}

}
