package de.tuberlin.sese.swtpp.gameserver.model.xiangqi.figure;

import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.Position;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.XiangqiGame;

import java.util.function.Predicate;

public class Horse extends _BaseFigure {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8926665404590043762L;

	@Override
	public String getCharacterRepresentation() {
		return "h";
	}

	@Override
	public boolean isValidMoveDelta(Position deltaPosition, Predicate<Position> isFree) {

		// TODO: implement this correctly

		// Moving vertically up
		// First vertically two up and horizontally one
		// Or first horizontally two up and vertically one
		boolean movesInAGammaPattern = Math.abs(deltaPosition.x) + Math.abs(deltaPosition.y) == 3 &&
				(Math.abs(deltaPosition.x) == 1 || Math.abs(deltaPosition.y) == 1);

		if (!movesInAGammaPattern) {
			return false;
		}

		if (Math.abs(deltaPosition.x) > Math.abs(deltaPosition.y)) {
			// horizontal gamma

			// in z direction of z delta x, wan space to z direction i mean, it must be free!
			// in z diagonal, which forms in z direction of z delta x and z delta y, it must be free!
			return isFree.test(new Position((int) Math.signum(deltaPosition.x), 0));
		}
		else {
			// vertical gamma

			// trivial, see above
			return isFree.test(new Position(0, (int) Math.signum(deltaPosition.y)));
		}
	}
	
}
