package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

import de.tuberlin.sese.swtpp.gameserver.model.Move;
import de.tuberlin.sese.swtpp.gameserver.model.Player;

public class Cannon extends Figure{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -235325495009772220L;
	
	public Cannon(boolean red, Position position,String name) {
		super(red, position, name);
	}

	@Override
	public boolean checkMove(String moveString, Player player, Figure[][] board, XiangqiGame xg) {
		Figure[][] arrCopy = board;
		
		// Starting position
		int xold = moveString.charAt(0) - 97;
		int xnew = moveString.charAt(3) - 97;
		
		// Target position
		int yold = 9 - Character.getNumericValue(moveString.charAt(1));
		int ynew = 9 - Character.getNumericValue(moveString.charAt(4));

		// If field or the color is not the same
		if (arrCopy[yold][xold] == null) {
			System.out.println("Field is empty.");
			return false;
		}
		
		// If the two positions are not empty then the color must be different
		if ((arrCopy[ynew][xnew] != null && arrCopy[yold][xold] != null)
				&& arrCopy[ynew][xnew].red == arrCopy[yold][xold].red) {
			System.out.println("Same color.");
			return false;
		}
		
		// If correct player on turn
		if (arrCopy[yold][xold].red && xg.isRedNext()) {
			return isValidMove(moveString, player, board, xg);
		} else if (!arrCopy[yold][xold].red && !xg.isRedNext()) {
			return isValidMove(moveString, player, board, xg);
		}
		
		return false;
	}
	
	// Only one method for both colors, because functionality is not different
	public boolean isValidMove(String moveString, Player player, Figure[][] board, XiangqiGame xg) {
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
		
		if (arrCopy[yold][xold].name != "c" && arrCopy[yold][xold].name != "C") {
			System.out.println("Not a cannon.");
			return false;
		}
		
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
			// All the positions on the path are empty => move is valid.
			xg.updatedState(moveString);
			xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
			player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
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
			// All the positions on the path are empty => move is valid.
			xg.updatedState(moveString);
			xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
			player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
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
			// All the positions on the path are empty => move is valid.
			xg.updatedState(moveString);
			xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
			player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
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
			// All the positions on the path are empty => move is valid.
			xg.updatedState(moveString);
			xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
			player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
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
					xg.updatedState(moveString);
					xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
					player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
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
					xg.updatedState(moveString);
					xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
					player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
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
					xg.updatedState(moveString);
					xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
					player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
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
					xg.updatedState(moveString);
					xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
					player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
					return true;
				}
			}
		}
		return false;
	}
	
	// Must change the players turn in teh function at the start of the class!
	/*public static void main(String[] args) {
		XiangqiGame xg = new XiangqiGame();
		System.out.println(xg.getBoard());
		Cannon c = new Cannon(false, null, "C");
		System.out.println(c.checkMove("e5-a5", null, xg.board.arr, xg));
	}*/
	
}
