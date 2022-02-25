package de.tuberlin.sese.swtpp.gameserver.model.xiangqi.figure;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.Position;
import java.io.Serializable;
import java.util.function.Predicate;

public class General extends _BaseFigure implements Serializable {

	private static final long serialVersionUID = 7662465147923352468L;

	@Override
	public String getCharacterRepresentation() {
		return "g";
	}

	@Override
	public boolean isValidMoveDelta(Position deltaPosition, Predicate<Position> isFree) {
		return Math.abs(deltaPosition.x) + Math.abs(deltaPosition.y) == 1;
	}

}
