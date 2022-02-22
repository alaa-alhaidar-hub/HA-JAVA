package de.tuberlin.sese.swtpp.gameserver.model.xiangqi.figure;

import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.Position;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.XiangqiGame;

import java.io.Serializable;
import java.util.function.Predicate;

public class Soldier extends _BaseFigure implements Serializable {

	private static final long serialVersionUID = -7678841322666589105L;

	@Override
	public String getCharacterRepresentation() {
		return "s";
	}
	
	@Override
	public boolean isValidMoveDelta(Position deltaPosition, Predicate<Position> isFree) {
		boolean movingExactlyOneSquare = Math.abs(deltaPosition.x) + Math.abs(deltaPosition.y) == 1;
		boolean movingHorizontallyOnlyWhenRiverCrossed = this.hasCrossedRiver() || deltaPosition.x == 0;
		boolean movingVerticallyAccordingToColor = deltaPosition.y == 0 || deltaPosition.y == this.yAxisDirection();

		return movingExactlyOneSquare && movingHorizontallyOnlyWhenRiverCrossed && movingVerticallyAccordingToColor;
	}

}
