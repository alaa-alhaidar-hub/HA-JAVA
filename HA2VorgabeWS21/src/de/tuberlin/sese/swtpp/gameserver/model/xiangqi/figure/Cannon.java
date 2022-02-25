package de.tuberlin.sese.swtpp.gameserver.model.xiangqi.figure;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.Position;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class Cannon extends _BaseFigure implements Serializable {

	private static final long serialVersionUID = -235325495009772220L;

	@Override
	public String getCharacterRepresentation() {
		return "c";
	}

	@Override
	public boolean isValidMoveDelta(Position deltaPosition, Predicate<Position> isFree) {
		boolean movesOnlyInOneAxis = deltaPosition.x == 0 || deltaPosition.y == 0;

		if (!movesOnlyInOneAxis) {
			return false;
		}

		List<Position> trajectory = getMoveTrajectoryForDeltaTarget(deltaPosition);
		int amountOfFiguresOnTrajectory = getAmountOfFiguresOnTrajectory(trajectory, isFree);

		// either we jump over one figure and capture the other (2 on total path)
		// or it's all free
		return amountOfFiguresOnTrajectory == 0 || amountOfFiguresOnTrajectory == 2;
	}

	/**
	 * Returns all the positions the figure has to travel through in delta format.
	 * This doesn't include the delta (0,0), since the figure is already there.
	 */
	private List<Position> getMoveTrajectoryForDeltaTarget(Position deltaPosition) {
		List<Position> path = new LinkedList<>();

		int signX = (int) Math.signum(deltaPosition.x);
		int signY = (int) Math.signum(deltaPosition.y);
		Position positionOnPath = new Position(0, 0);

		while (!positionOnPath.equals(deltaPosition)) {
				positionOnPath = positionOnPath.offsetWith(new Position(
						signX, signY
				));
			path.add(positionOnPath);
		}

		return path;
	}

	private int getAmountOfFiguresOnTrajectory(List<Position> trajectory, Predicate<Position> isFree) {
		int count = 0;

		for (Position pos : trajectory) {
			if (!isFree.test(pos)) {
				++count;
			}
		}

		return count;
	}

}
