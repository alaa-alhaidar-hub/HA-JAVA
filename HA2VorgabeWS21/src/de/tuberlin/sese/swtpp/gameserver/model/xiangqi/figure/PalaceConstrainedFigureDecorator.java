package de.tuberlin.sese.swtpp.gameserver.model.xiangqi.figure;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.Position;

import java.util.function.Predicate;

public class PalaceConstrainedFigureDecorator implements IFigure {

    private static final long serialVersionUID = 8218913367653870892L;

    private IFigure instance;

    public PalaceConstrainedFigureDecorator(_BaseFigure instance) {
        this.instance = instance;
    }

    @Override
    public void setColor(FigureColor color) {
        this.instance.setColor(color);
    }

    @Override
    public FigureColor getColor() {
        return this.instance.getColor();
    }

    @Override
    public void setPosition(Position _position) {
        this.instance.setPosition(_position);
    }

    @Override
    public Position getPosition() {
        return this.instance.getPosition();
    }

    @Override
    public String getCharacterRepresentation() {
        return this.instance.toString();
    }

    @Override
    public String toString() {
        return this.instance.toString();
    }

    @Override
    public boolean isValidMoveDelta(Position deltaPosition, Predicate<Position> isFree) {
        Position targetPosition = this.getPosition().offsetWith(deltaPosition);

        return isPositionInPlayersPalace(targetPosition) && this.instance.isValidMoveDelta(deltaPosition, isFree);
    }

    private boolean isPositionInPlayersPalace(Position targetPosition) {
        boolean xInPalaceRange = targetPosition.x >= 3 && targetPosition.x <= 5;

        boolean yInPalaceRange;
        if (this.getColor() == FigureColor.BLACK) {
            yInPalaceRange = targetPosition.y <= 2;
        }
        else {
            yInPalaceRange = targetPosition.y >= 7;
        }

        return xInPalaceRange && yInPalaceRange;
    }

}
