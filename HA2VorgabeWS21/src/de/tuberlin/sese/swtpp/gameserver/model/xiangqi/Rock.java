package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

public class Rock extends Figure {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2345235451070921766L;
	
	public Rock(boolean red, Position position, String name) {
		super(red, position, name);
	}

	@Override
	public boolean checkMove(String moveString, Figure[][] board, XiangqiGame xg) {
		System.out.println("Rock checkMove called.");
		
		Figure[][] arrCopy = board;

		// Starting position
		int xold = moveString.charAt(0) - 97;
		int yold = 9 - Character.getNumericValue(moveString.charAt(1));
		
		if (arrCopy[yold][xold] != null) {
			if (arrCopy[yold][xold].name != "R" && arrCopy[yold][xold].name != "r") {
				System.out.println("Not a Rock.");
				return false;
			}
		}

		// If the figure is red and the player on turn is red, 
		// check if it is a valid move for the red Rock
		if (arrCopy[yold][xold].red && xg.isRedNext()) {
			return isvalidmoveRedFigure(moveString, arrCopy);
		} 
		
		// If the figure is black and the player on turn is black, 
		// check if it is a valid move for the black Rock
		else if (!arrCopy[yold][xold].red && !xg.isRedNext()) {
			return isvalidmoveBlackFigure(moveString, arrCopy);
		}
		
		return false;
	}

	public boolean roadFreeForRock(String moveString, Figure[][] board) {
		Figure[][] arrCopy = board;
		
		// Starting position
		int xold = moveString.charAt(0) - 97;
		int xnew = moveString.charAt(3) - 97;
		
		// Target position
		int yold = 9 - Character.getNumericValue(moveString.charAt(1));
		int ynew = 9 - Character.getNumericValue(moveString.charAt(4));
		
		// Delta position
		int difx = xnew - xold;
		int dify = ynew - yold;
		
		// return +/- 1 depending on the delta 
		int signX = (int) Math.signum(difx);  
		int signY = (int) Math.signum(dify);
		
		// Moving horizontally left or right
		if (dify == 0 && (difx > 0 || difx < 0)) {
			while (Math.abs(difx) > 1) {
				difx = difx - signX;
				if (arrCopy[yold][difx] != null) {
					return false;
				}
			}
			return true;
		}
		
		// Moving vertically up or down
		if (difx == 0 && (dify > 0 || dify < 0)) {
			while(Math.abs(dify) > 1) {
				dify = dify - signY;
				if (arrCopy[dify][xold] != null) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean isvalidmoveBlackFigure(String moveString, Figure[][] board) {
		System.out.println("Black method called.");
		
		// Starting position
		int xold = moveString.charAt(0) - 97;
		int yold = (Character.getNumericValue(moveString.charAt(1)));
		
		// Target position
		int xnew = moveString.charAt(3) - 97;
		int ynew = (Character.getNumericValue(moveString.charAt(4)));

		Figure[][] arrCopy = board;
		
		if ((arrCopy[ynew][xnew] != null && arrCopy[yold][xold] != null)
				&& arrCopy[ynew][xnew].red == arrCopy[yold][xold].red) {
			System.out.println("Same color.");
			return false;
		}
		
		if (roadFreeForRock(moveString, board)) {
			return true;
		}
		return false;
	}

	public boolean isvalidmoveRedFigure(String moveString, Figure[][] board) {
		System.out.println("Red method called.");
		
		// Starting position.
		int xold = moveString.charAt(0) - 97;
		int yold = 9 - (Character.getNumericValue(moveString.charAt(1)));
		
		// Target position.
		int xnew = moveString.charAt(3) - 97;
		int ynew = 9 - (Character.getNumericValue(moveString.charAt(4)));
		
		Figure[][] arrCopy = board;

		if ((arrCopy[ynew][xnew] != null && arrCopy[yold][xold] != null)
				&& arrCopy[ynew][xnew].red == arrCopy[yold][xold].red) {
			System.out.println("Same color.");
			return false;
		}
		
		if (roadFreeForRock(moveString, board)) {
			return true;
		}
		return false;
	}
}