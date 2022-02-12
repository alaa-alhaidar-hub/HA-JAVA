package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

public class Cannon extends Figure{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -235325495009772220L;
	
	public Cannon(boolean red, Position position,String name) {
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
		
		// If the two positions are not empty then the color must be different
		if ((arrCopy[ynew][xnew] != null && arrCopy[yold][xold] != null)
				&& arrCopy[ynew][xnew].red == arrCopy[yold][xold].red) {
			System.out.println("Same color.");
			return false;
		}
		
		// Check if the figure is a Cannon
		if (arrCopy[yold][xold].name != "c" && arrCopy[yold][xold].name != "C") {
			System.out.println("Not a cannon.");
			return false;
		}
		
		// If correct player on turn
		if (arrCopy[yold][xold].red && xg.isRedNext()) {
			return isValidMove(moveString, board);
		} else if (!arrCopy[yold][xold].red && !xg.isRedNext()) {
			return isValidMove(moveString, board);
		}
		
		return false;
	}
	
	// Only one method for both colors, because functionality is not different
	public boolean isValidMove(String moveString, Figure[][] board) {
		System.out.println("Method called.");
		
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
		
		///////////////////////////////////////////////////////////////////
		
		// Cases when cannon is only moving:
		// 1) Move horizontally right - x coordinate is growing => difx > 0
		// 	  Check if all the fields on the way are empty!
		if ((dify == 0) && (difx > 0) && (arrCopy[ynew][xnew] == null)) {
			for (int step = 1; step < Math.abs(difx); step++) {
				if (arrCopy[yold][xold + step] != null) {
					return false;
				}
			}
			return true;
		}
		
		// 2) Move horizontally left - x coordinate is smaller => difx < 0
		//    Check if all the fields on the way are empty!
		if ((dify == 0) && (difx < 0) && (arrCopy[ynew][xnew] == null)) {
			for (int step = 1; step < Math.abs(difx); step++) {
				if (arrCopy[yold][xold - step] != null) {
					return false;
				}
			}
			return true;
		}
		
		// 3) Move vertically down - y coordinate is growing => dify > 0
		//    Check if all the fields on the way are empty!
		if ((difx == 0) && (dify > 0) && (arrCopy[ynew][xnew] == null)) {
			for (int step = 1; step < Math.abs(dify); step++) {
				if (arrCopy[yold + step][xold] != null) {
					return false;
				}
			}
			return true;
		}
		
		// 4) Move vertically up - y coordinate is smaller => dify < 0
		//    Check if all the fields on the way are empty!
		if ((difx == 0) && (dify < 0) && (arrCopy[ynew][xnew] == null)) {
			for (int step = 1; step < Math.abs(dify); step++) {
				if (arrCopy[yold - step][xold] != null) {
					return false;
				}
			}
			return true;
		}
		
		///////////////////////////////////////////////////////////////////
		
		// Cases when the cannon is capturing:
		// 1) Move horizontally right - x coordinate is growing => difx > 0
		// 	  Check if there is only one figure on the way
		if ((dify == 0) && (difx > 0) && (arrCopy[ynew][xnew] != null)) {
			int countFigures = 0;
			for (int step = 1; step < Math.abs(difx); step++) {
				if (arrCopy[yold][xold + step] != null) {
					countFigures++;
				}
				if (countFigures == 1) {
					return true;
				}
			}
		}
		
		// 2) Move horizontally left - x coordinate is smaller => difx < 0
		// 	  Check if there is only one figure on the way
		if ((dify == 0) && (difx < 0) && (arrCopy[ynew][xnew] != null)) {
			int countFigures = 0;
			for (int step = 1; step < Math.abs(difx); step++) {
				if (arrCopy[yold][xold - step] != null) {
					countFigures++;
				}
				if (countFigures == 1) {
					return true;
				}
			}
		}
		
		// 3) Move vertically down - y coordinate is growing => dify > 0
		//	  Check if there is only one figure on the way
		if ((difx == 0) && (dify > 0) && (arrCopy[ynew][xnew] != null)) {
			int countFigures = 0;
			for (int step = 1; step < Math.abs(difx); step++) {
				if (arrCopy[yold + step][xold] != null) {
					countFigures++;
				}
				if (countFigures == 1) {
					return true;
				}
			}
		}
		
		// 4) Move vertically up - y coordinate is smaller => dify < 0
		//	  Check if there is only one figure on the way
		if ((difx == 0) && (dify < 0) && (arrCopy[ynew][xnew] != null)) {
			int countFigures = 0;
			for (int step = 1; step < Math.abs(difx); step++) {
				if (arrCopy[yold - step][xold] != null) {
					countFigures++;
				}
				if (countFigures == 1) {
					return true;
				}
			}
		}
		return false;
	}
}
