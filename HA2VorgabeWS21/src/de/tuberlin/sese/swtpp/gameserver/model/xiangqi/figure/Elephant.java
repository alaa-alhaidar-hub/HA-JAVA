package de.tuberlin.sese.swtpp.gameserver.model.xiangqi.figure;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.Position;
import java.io.Serializable;
import java.util.function.Predicate;

public class Elephant extends _BaseFigure implements Serializable {

	private static final long serialVersionUID = -7500624995679337949L;

	@Override
	public String getCharacterRepresentation() {
		return "e";
	}

	@Override
	public boolean isValidMoveDelta(Position deltaPosition, Predicate<Position> isFree) {
		return !this.hasCrossedRiver()
				&& Math.abs(deltaPosition.x) == 2
				&& Math.abs(deltaPosition.y) == 2
				&& !this.wouldCrossRiverWithMove(deltaPosition);
	}

}
