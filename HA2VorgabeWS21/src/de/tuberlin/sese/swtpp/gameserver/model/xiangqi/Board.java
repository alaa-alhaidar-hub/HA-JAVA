package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.exceptions.DeadGeneralException;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.figure.FigureColor;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.figure.FigureFactory;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.figure.IFigure;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class Board implements Serializable {

	private static final long serialVersionUID = -268967919404280946L;

	// The game board and a list of figures in the game
	private IFigure[][] boardMatrix;
	private List<IFigure> figures;

	public Board() {
		this.figures = new LinkedList<>();
		this.boardMatrix = new IFigure[9][10];
	}

	public static Board fromRepresentation(String internalRepresentation) {
		Board newBoard = new Board();
		newBoard.setFiguresOnBoardAccordingToGivenStringRepresentation(internalRepresentation);
		return newBoard;
	}

	public void setFiguresOnBoardAccordingToGivenStringRepresentation(String internalRepresentation) {
		String s = formatString(internalRepresentation);
		String[] boardRows = s.split("/", 10);
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 9; x++) {
				IFigure newFigure = createFigure(boardRows[y].charAt(x));
				if (newFigure != null) {
					addFigure(newFigure, new Position(x, y));
				}

				// TODO: if a general, store figure somewhere
			}
		}
	}

	public String getCurrentBoardStateInStringRepresentation() {
		String currentState = "";
		String figureRepresentation;
		for(int y = 0; y < 10; y++) {
			for(int x = 0; x < 9; x++) {
				if (boardMatrix[x][y] == null) {
					currentState = currentState + "x";
				}
				else {
					figureRepresentation = boardMatrix[x][y].toString();
					currentState = currentState + figureRepresentation;
				}
			}
			if (y < 9) {
				currentState = currentState + "/";
			}
		}
		// Add the "/", because it is needed so that the transformation works.
		currentState = currentState.concat("/");
		// Count all the "x" from the string representation and add their count to the final representation.
		StringBuilder output = new StringBuilder();
		int count = 0;
		for (int i = 0; i < currentState.length(); i++) {
			if (currentState.charAt(i) == "x".charAt(0)) {
				count++;
			}
			else  {
				if (count != 0) {
					output.append(count);
					output.append(currentState.charAt(i));
					count = 0;
				}
				else {
					output.append(currentState.charAt(i));
				}
			}
		}
		// Remove the last "/"
		return output.substring(0, output.toString().length() - 1);
	}

	// Create a new figure from its character representation
	public IFigure createFigure(char s) {
		if (s == 'x') {
			return null;
		}
		return FigureFactory.createFigure(s);
	}

	// Add the figure to the list of figures that are currently on the board
	public void addFigure(IFigure figure, Position position) {
		if (null == figure) {
			return;
		}

		boardMatrix[position.x][position.y] = figure;
		figures.add(figure);
		figure.setPosition(position);
	}

	public void removeFigure(IFigure figure) {
		if (figure == null) {
			return;
		}

		if (!this.figures.contains(figure)) {
			throw new RuntimeException("Removing a figure that doesn't exist!");
		}

		Position figurePosition = figure.getPosition();
		this.boardMatrix[figurePosition.x][figurePosition.y] = null;
		this.figures.remove(figure);
	}

	public IFigure figureAt(Position position) {
		return this.boardMatrix[position.x][position.y];
	}

	public void moveFigure(IFigure figure, Position position) {
		if (this.boardMatrix[position.x][position.y] != null) {
			throw new RuntimeException("Cannot move figure, position occupied!");
		}

		Position initialPosition = figure.getPosition();
		this.boardMatrix[initialPosition.x][initialPosition.y] = null;
		this.boardMatrix[position.x][position.y] = figure;
		figure.setPosition(position);
	}

	public List<IFigure> getFigures() {
		return Collections.unmodifiableList(this.figures);
	}

	private boolean isFree(Position position) {
		return figureAt(position) == null;
	}

	private boolean isFree(Position absolutePosition, Position deltaPosition) {
		return isFree(absolutePosition.offsetWith(deltaPosition));
	}

	public Predicate<Position> getIsFreePredicateRelativeTo(Position originPosition) {
		return (delta) -> isFree(originPosition, delta);
	}

	public Position getBlackGeneralPosition() {
		for (IFigure fig : figures) {
			if (fig.getCharacterRepresentation().equals("g")) {
				return fig.getPosition();
			}
		}

		throw new DeadGeneralException("Black general not found.");
	}

	public Position getRedGeneralPosition() {
		for (IFigure fig : figures) {
			if (fig.getCharacterRepresentation().equals("G")) {
				return fig.getPosition();
			}
		}

		throw new DeadGeneralException("Red general not found.");
	}

	public boolean isGeneralInCheck(FigureColor color) {
		Position generalsPosition = color == FigureColor.RED ? this.getRedGeneralPosition() : this.getBlackGeneralPosition();
		for (int i = 0; i < this.figures.size(); i++) {
			IFigure figureOnBoard = this.figures.get(i);
			if (figureOnBoard.getColor() != color) {
				Position deltaPosition = figureOnBoard.getPosition().deltaTo(generalsPosition);
				if (figureOnBoard.isValidMoveDelta(deltaPosition, this.getIsFreePredicateRelativeTo(figureOnBoard.getPosition()))) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isValidFigureMove(IFigure figure, Position startPosition, Position targetPosition) {
		Position deltaPosition = figure.getPosition().deltaTo(targetPosition);
		Predicate<Position> isFree = this.getIsFreePredicateRelativeTo(figure.getPosition());
		IFigure figureAtTarget = this.figureAt(targetPosition);

		if (this.figureAt(targetPosition) != null) {
			if (figure.getColor() == (this.figureAt(targetPosition).getColor())) {
				return false;
			}
		}

		if (!figure.isValidMoveDelta(deltaPosition, isFree)) {
			return false;
		}

		// move
		this.removeFigure(figureAtTarget);
		this.moveFigure(figure, targetPosition);

		// check
		boolean isMoveValid;
		try {
			isMoveValid =!generalsAreFacingEachOther() && !isGeneralInCheck(figure.getColor());
		}
		catch (DeadGeneralException e) {
			// how could he be in check if dead, yo?
			isMoveValid = true;
		}

		// undo
		this.moveFigure(figure, startPosition);
		this.addFigure(figureAtTarget, targetPosition);

		return isMoveValid;
	}

	public boolean hasValidMoves(FigureColor color) {
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 9; x++ ) {
				Position currentPositionOnBoard = new Position(x,y);
				for (int i = 0; i < this.figures.size(); i++) {
					IFigure figureOnBoard = this.figures.get(i);
					if (figureOnBoard.getColor() == color && isValidFigureMove(figureOnBoard, figureOnBoard.getPosition(), currentPositionOnBoard)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean generalsAreFacingEachOther() {
		int redGeneralRow = getRedGeneralPosition().y;
		int blackGeneralRow = getBlackGeneralPosition().y;
		int rowBetweenGenerals = redGeneralRow;
		int numberOfFiguresBetweenGenerals = 0;

		// If one of the Generals is not on Board or is their column coordinates are different
		if ((getBlackGeneralPosition().x != getRedGeneralPosition().x)) {
			return false;
		}

		while(rowBetweenGenerals > blackGeneralRow + 1) {
			rowBetweenGenerals = rowBetweenGenerals - 1;
			if (boardMatrix[getRedGeneralPosition().x][rowBetweenGenerals] != null)
			{
				numberOfFiguresBetweenGenerals++;
			}
		}
		return (numberOfFiguresBetweenGenerals == 0);
	}

	///////////////////////// HELPER FUNCTIONS //////////////////////

	// A function to replace integers in the string with the 
	// according number of "x"
	public String formatString(String b) {

		String f, f1, f2, f3, f4, f5, f6, f7, f8 = "";

		f = b.replaceAll("1", "x");
		f1 = f.replaceAll("2", "xx");
		f2 = f1.replaceAll("3", "xxx");
		f3 = f2.replaceAll("4", "xxxx");
		f4 = f3.replaceAll("5", "xxxxx");
		f5 = f4.replaceAll("6", "xxxxxx");
		f6 = f5.replaceAll("7", "xxxxxxx");
		f7 = f6.replaceAll("8", "xxxxxxxx");
		f8 = f7.replaceAll("9", "xxxxxxxxx");
		return f8;
	}
}