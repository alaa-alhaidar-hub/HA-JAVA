package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

import java.io.Serializable;

public class Advisor extends Figure implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9119129045697685842L;
	
	public Advisor(boolean red, Position position, String name) {
		super(red, position, name);
	}

	@Override
	public boolean checkMove(String moveString, Figure[][] board, XiangqiGame xg) {
		Figure[][] arrCopy = board;

		// Starting position
		int xold = moveString.charAt(0) - 97;
		int xnew = moveString.charAt(3) - 97;
		
		// Target position
		int yold = 9 - Character.getNumericValue(moveString.charAt(1));
		int ynew = 9 - Character.getNumericValue(moveString.charAt(4));

		// If the two positions are not empty - the figure's color on the target 
		// must be different.
		if ((arrCopy[ynew][xnew] != null && arrCopy[yold][xold] != null)
				&& arrCopy[ynew][xnew].red == arrCopy[yold][xold].red) {
			System.out.println("same color");
			return false;
		}
		
		// Check if the figure is an Advisor
		if (arrCopy[yold][xold] != null) {
			if (arrCopy[yold][xold].name != "a" && arrCopy[yold][xold].name != "A") {
				return false;
			}
		}
				
		if (arrCopy[yold][xold].red && xg.isRedNext()) {
			return isvalidmoveRedFigure(moveString, board);
		} else if (!arrCopy[yold][xold].red && !xg.isRedNext()) {
			return isvalidmoveBlackFigure(moveString, board);
		}
		return false;
	}

	/*
	 * General can only be placed in 3 column d, e, f:
	 * Diagonally when delta y = 1 and delta x = 1
	 * Horizontally or vertically when only delta x = 0 OR delta y = 0
	 */
	
	public boolean isvalidmoveBlackFigure(String moveString, Figure[][] board) {
		System.out.println("Black method called.");
		
		// Starting position
		int xold = moveString.charAt(0) - 97;
		int yold = 9 - (Character.getNumericValue(moveString.charAt(1)));
		
		// Target position
		int xnew = moveString.charAt(3) - 97;
		int ynew = 9 - (Character.getNumericValue(moveString.charAt(4)));
		
		// Delta positions
		int difx = xnew - xold;
		int dify = ynew - yold;

		Figure[][] arrCopy = board;
		
		// if dify = 1 -> moving vertically and difx must be 0
		// if difx = 1 -> moving vertically and dify must be 0
		if (moveString.contains("d") || moveString.contains("e") || moveString.contains("f")) {
			if (yold >= 7 && yold <= 9 && xold >= 3 && xold <= 5) {
				if (ynew >= 7 && ynew <= 9 && xnew >= 3 && xnew <= 5) {
					if (Math.abs(dify) == 1 && Math.abs(difx) == 1) {
						return true;
					} 
					else if (Math.abs(difx) == 1 && Math.abs(dify) == 1) {
						return true;
					} 
					else {
						return false;
					}
				}
			}
		}

		return false;
	}

	public boolean isvalidmoveRedFigure(String moveString, Figure[][] board) {
		System.out.println("Red method called.");
		
		// Starting position
		int xold = moveString.charAt(0) - 97;
		int yold = 9 - (Character.getNumericValue(moveString.charAt(1)));
		
		// Target position
		int xnew = moveString.charAt(3) - 97;
		int ynew = 9 - (Character.getNumericValue(moveString.charAt(4)));
		
		// Delta positions
		int difx = xnew - xold;
		int dify = ynew - yold;

		Figure[][] arrCopy = board;

		if (moveString.contains("d") || moveString.contains("e") || moveString.contains("f")) {
			if (yold >= 7 && yold <= 9 && xold >= 3 && xold <= 5) {
				if (ynew >= 7 && ynew <= 9 && xnew >= 3 && xnew <= 5) {
					if (Math.abs(dify) == 1 && Math.abs(difx) == 1) {
						return true;
					} 
					else if (Math.abs(difx) == 1 && Math.abs(dify) == 1) {
						return true;
					} 
					else {
						return false;
					}
				}
			}
		}
		return false;
	}

}
