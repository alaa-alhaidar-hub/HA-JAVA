package de.tuberlin.sese.swtpp.gameserver.model.xiangqi.figure;

import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.Position;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.XiangqiGame;

import java.io.Serializable;
import java.util.function.Predicate;

public class Rock extends _BaseFigure implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2345235451070921766L;

	@Override
	public String getCharacterRepresentation() {
		return "r";
	}

	@Override
	public boolean isValidMoveDelta(Position deltaPosition, Predicate<Position> isFree) {
		Position deltaPositionCopy = deltaPosition.copy();

		// return +/- 1 depending on the delta
		int signX = (int) Math.signum(deltaPosition.x);
		int signY = (int) Math.signum(deltaPosition.y);

		// moving horizontally left or right, y does not change
		if (deltaPositionCopy.y == 0 && (deltaPositionCopy.x != 0))
		{
			while(Math.abs(deltaPositionCopy.x) > 1)
			{
				deltaPositionCopy.x = deltaPositionCopy.x - signX;
				if (!isFree.test(deltaPositionCopy))
				{
					return false;
				}
			}
			return true;
		}

		// moving vertically up or down, x does not change
		if (deltaPositionCopy.x == 0 && (deltaPositionCopy.y != 0))
		{
			while(Math.abs(deltaPositionCopy.y) > 1)
			{
				deltaPositionCopy.y = deltaPositionCopy.y - signY;
				if (!isFree.test(deltaPositionCopy))
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}

}