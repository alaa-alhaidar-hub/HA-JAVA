package de.tuberlin.sese.swtpp.gameserver.model.xiangqi.figure;

import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.Position;

public abstract class _BaseFigure implements IFigure {

	private static final long serialVersionUID = 8218913367653870892L;
	private FigureColor color = FigureColor.BLACK;
	private Position position;

	_BaseFigure() {
		super();
		this.position = new Position(0, 0);
	}

	@Override
	public void setColor(FigureColor color) {
		this.color = color;
	}

	@Override
	public FigureColor getColor() {
		return this.color;
	}

	@Override
	public void setPosition(Position _position) {
		this.position = _position;
	}

	@Override
	public Position getPosition() {
		return this.position;
	}

	public boolean hasCrossedRiver() {
		return this.isPositionBeyondRiver(this.position);
	}

	public boolean wouldCrossRiverWithMove(Position moveDelta) {
		Position targetPosition = this.position.offsetWith(moveDelta);
		return this.isPositionBeyondRiver(targetPosition);
	}

	private boolean isPositionBeyondRiver(Position _position) {
		if (this.getColor() == FigureColor.BLACK) {
			return _position.y >= 5;
		}

		return _position.y <= 4;
	}

	public int yAxisDirection() {
		return this.getColor() == FigureColor.BLACK ? 1 : -1;
	}

	@Override
	public String toString() {
		if (this.getColor() == FigureColor.BLACK) {
			return this.getCharacterRepresentation();
		}

		return this.getCharacterRepresentation().toUpperCase();
	}
}
