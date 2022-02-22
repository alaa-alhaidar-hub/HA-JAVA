package de.tuberlin.sese.swtpp.gameserver.model.xiangqi.figure;

import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.Position;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.XiangqiGame;

import java.io.Serializable;
import java.util.function.Predicate;

public interface IFigure extends Serializable {

    void setColor(FigureColor color);

    FigureColor getColor();

    void setPosition(Position _position);

    Position getPosition();

    String getCharacterRepresentation();

    // boolean checkMove(String moveString, IFigure[][] board, XiangqiGame xg);

    /**
     *
     * @param deltaPosition
     * The delta of the move from source to target.
     * @param isFree
     * A predicate which returns true iff the board at the given delta position is free.
     * @return {@code true} if the figure can move according to the given
     * delta.
     * E.g. if deltaPosition = {x:1, y:0}, the method will return
     * true iff the figure can move one position horizontally to the right.
     */
    boolean isValidMoveDelta(Position deltaPosition, Predicate<Position> isFree);

    boolean hasCrossedRiver();

    boolean wouldCrossRiverWithMove(Position moveDelta);

    int yAxisDirection();
}
