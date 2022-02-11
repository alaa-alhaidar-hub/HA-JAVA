package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

import de.tuberlin.sese.swtpp.gameserver.model.Move;
import de.tuberlin.sese.swtpp.gameserver.model.Player;

public class Horse extends Figure {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8926665404590043762L;
	
	public Horse(boolean red, Position position,String name) {
		super(red, position,name);
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
			return isvalidmoveRedFigure(moveString, player, board, xg);
		} else if (!arrCopy[yold][xold].red && !xg.isRedNext()) {
			return isvalidmoveBlackFigure(moveString, player, board, xg);
		}
		
		return false;
	}
	
	public boolean isvalidmoveBlackFigure(String moveString, Player player, Figure[][] board, XiangqiGame xg) {
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
		
		if (arrCopy[yold][xold].name != "h") {
			System.out.println("Not a horse.");
			return false;
		}
		
		// Edge case:
		// If it is on the 9th row - it can move vertically only down (for y)
		if ((moveString.charAt(1) == '9') && (Math.abs(difx) == 1 && Math.abs(dify) == 2) 
				&& (arrCopy[yold + 1][xold] == null)) {
			xg.updatedState(moveString);
			xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
			player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
			return true;
		}
		
		// If it is on the a column - it can move horizontally only right 
		if ((moveString.charAt(0) == 'a') && 
				(Math.abs(difx) == 2 && Math.abs(dify) == 1) 
				&& (arrCopy[yold][xold + 1] == null)) {
			xg.updatedState(moveString);
			xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
			player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
			return true;
		}
		
		// If it is on the i column - it can move horizontally only left
		if ((moveString.charAt(0) == 'i') && 
				(Math.abs(difx) == 2 && Math.abs(dify) == 1) 
				&& (arrCopy[yold][xold - 1] == null)) {
			xg.updatedState(moveString);
			xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
			player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
			return true;
		}
		
		// Check movements for all other cases:
		// One vertical and one diagonal move
		// Make sure that the first position vertically is empty
		if ((Math.abs(difx) == 1 && Math.abs(dify) == 2) && 
				(arrCopy[yold - 1][xold] == null || arrCopy[yold + 1][xold] == null)) {
			xg.updatedState(moveString);
			xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
			player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
			return true;
		}
		
		// One horizontal and one diagonal move
		// Make sure the first position horizontally is empty
		if ((Math.abs(dify) == 1 && Math.abs(difx) == 2) && 
				(arrCopy[yold][xold + 1] == null || arrCopy[yold][xold - 1] == null)) {
			xg.updatedState(moveString);
			xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
			player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
			return true;
		}
		
		return false;
		
	}
	
	public boolean isvalidmoveRedFigure(String moveString, Player player, Figure[][] board, XiangqiGame xg) {
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
		
		if (arrCopy[yold][xold].name != "H") {
			System.out.println("Not a horse.");
			return false;
		}
		
		// Edge cases:
		// If it is on the 0th row - it can move vertically only up (for y)
		if ((moveString.charAt(1) == '0') && (Math.abs(difx) == 1 && Math.abs(dify) == 2) 
				&& (arrCopy[yold - 1][xold] == null)) {
			xg.updatedState(moveString);
			xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
			player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
			return true;
		}
		
		// If it is on the a column - it can move horizontally only right 
		if ((moveString.charAt(0) == 'a') && 
				(Math.abs(difx) == 2 && Math.abs(dify) == 1) 
				&& (arrCopy[yold][xold + 1] == null)) {
			xg.updatedState(moveString);
			xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
			player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
			return true;
		}
		
		// If it is on the i column - it can move horizontally only left
		if ((moveString.charAt(0) == 'i') && 
				(Math.abs(difx) == 2 && Math.abs(dify) == 1) 
				&& (arrCopy[yold][xold - 1] == null)) {
			xg.updatedState(moveString);
			xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
			player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
			return true;
		}
		
		// Check movements for all other cases:
		// One vertical and one diagonal move
		// Make sure that the first position vertically is empty
		if ((Math.abs(difx) == 1 && Math.abs(dify) == 2) && 
				(arrCopy[yold - 1][xold] == null || arrCopy[yold + 1][xold] == null)) {
			xg.updatedState(moveString);
			xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
			player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
			return true;
		}
		
		// One horizontal and one diagonal move
		// Make sure the first position horizontally is empty
		if ((Math.abs(dify) == 1 && Math.abs(difx) == 2) && 
				(arrCopy[yold][xold + 1] == null || arrCopy[yold][xold - 1] == null)) {
			xg.updatedState(moveString);
			xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
			player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
			return true;
		}
		
		return false;
		
	}
	
	public static void main(String[] args) {
		XiangqiGame xg = new XiangqiGame();
		System.out.println(xg.getBoard());
		Horse h = new Horse(false, null, "h");
		System.out.println(h.checkMove("b9-a7", null, xg.board.arr, xg));
	}
	
}
