package de.tuberlin.sese.swtpp.gameserver.model.xiangqi.figure;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.Position;
import java.io.Serializable;
import java.util.function.Predicate;

public class Advisor extends _BaseFigure implements Serializable {

	private static final long serialVersionUID = -9119129045697685842L;

	@Override
	public String getCharacterRepresentation() {
		return "a";
	}

	@Override
	public boolean isValidMoveDelta(Position deltaPosition, Predicate<Position> isFree) {
		return Math.abs(deltaPosition.x) == 1 && Math.abs(deltaPosition.y) == 1;
	}

}
