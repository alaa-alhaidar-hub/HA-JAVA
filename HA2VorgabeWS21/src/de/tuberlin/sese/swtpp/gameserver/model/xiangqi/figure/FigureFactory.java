package de.tuberlin.sese.swtpp.gameserver.model.xiangqi.figure;
import java.io.Serializable;

public class FigureFactory implements Serializable {
	private FigureFactory() {

	}

	private static final long serialVersionUID = -5252369819361557429L;

	public static IFigure createFigure(char representation) {
		FigureColor color = Character.isUpperCase(representation) ? FigureColor.RED : FigureColor.BLACK;
		IFigure newFigure = getFigureInstance(Character.toLowerCase(representation));

		if (newFigure != null) {
			newFigure.setColor(color);
		}

		return newFigure;
	}

	private static IFigure getFigureInstance(char figureLetter) {
		switch(figureLetter) {
			case 'a':
				return new PalaceConstrainedFigureDecorator(new Advisor());

			case 'e':
				return new Elephant();

			case 'h':
				return new Horse();

			case 'r':
				return new Rock();

			case 'c':
				return new Cannon();

			case 's':
				return new Soldier();
			case 'g':
				return new PalaceConstrainedFigureDecorator(new General());
		}

		return null;
	}

}
