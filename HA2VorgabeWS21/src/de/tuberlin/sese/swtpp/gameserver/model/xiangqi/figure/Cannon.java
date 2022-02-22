package de.tuberlin.sese.swtpp.gameserver.model.xiangqi.figure;

import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.Position;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.XiangqiGame;

import java.util.function.Predicate;

public class Cannon extends _BaseFigure {

	private static final long serialVersionUID = -235325495009772220L;

	@Override
	public String getCharacterRepresentation() {
		return "c";
	}

	@Override
	public boolean isValidMoveDelta(Position deltaPosition, Predicate<Position> isFree) {

		// this should check if the target position is empty?
		boolean cannonMoves = isFree.test(deltaPosition);
		boolean cannonAttacks = !isFree.test(deltaPosition);
		int countFiguresOnTheWay = 0;

		Position deltaPositionCopy = deltaPosition.copy();

		// return +/- 1 depending on the delta
		int signX = (int) Math.signum(deltaPosition.x);
		int signY = (int) Math.signum(deltaPosition.y);

		// moving horizontally left or right, y does not change
		if (cannonMoves && deltaPositionCopy.y == 0 && (deltaPositionCopy.x != 0))
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
		if (cannonMoves && deltaPositionCopy.x == 0 && (deltaPositionCopy.y != 0))
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

		// attacking horizontally left or right, y does not change
		if (cannonAttacks && deltaPositionCopy.y == 0 && (deltaPositionCopy.x != 0))
		{
			while(Math.abs(deltaPositionCopy.x) > 1)
			{
				deltaPositionCopy.x = deltaPositionCopy.x - signX;
				if (!isFree.test(deltaPositionCopy))
				{
					countFiguresOnTheWay++;
				}
			}
			return countFiguresOnTheWay == 1;
		}

		// moving vertically up or down, x does not change
		if (cannonAttacks && deltaPositionCopy.x == 0 && (deltaPositionCopy.y != 0))
		{
			while(Math.abs(deltaPositionCopy.y) > 1)
			{
				deltaPositionCopy.y = deltaPositionCopy.y - signY;
				if (!isFree.test(deltaPositionCopy))
				{
					countFiguresOnTheWay++;
				}
			}
			return countFiguresOnTheWay == 1;
		}
		return false;
	}

}
