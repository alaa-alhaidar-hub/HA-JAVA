package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

import de.tuberlin.sese.swtpp.gameserver.model.Move;
import de.tuberlin.sese.swtpp.gameserver.model.Player;

public class Advisor extends Figure {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9119129045697685842L;
	
	public Advisor(boolean red, Position position, String name) {
		super(red, position, name);
	}

	@Override
	public boolean checkMove(String moveString, Player player, Figure[][] board, XiangqiGame xg) {
		Figure[][] arrCopy = board;

		int xold = moveString.charAt(0) - 97;
		int xnew = moveString.charAt(3) - 97;
		int yold = 9 - Character.getNumericValue(moveString.charAt(1));
		int ynew = 9 - Character.getNumericValue(moveString.charAt(4));

		// If the field is empty.
		if (arrCopy[yold][xold] == null) {
			System.out.println("Field is empty.");
			return false;
		}
		
		// If the two positions are not empty - the figure's color on the target 
		// must be different.
		if ((arrCopy[ynew][xnew] != null && arrCopy[yold][xold] != null)
				&& arrCopy[ynew][xnew].red == arrCopy[yold][xold].red) {
			System.out.println("same color");
			return false;

		}
		if (arrCopy[yold][xold].red && xg.isRedNext()) {
			return isvalidmoveRedFigure(moveString, player, board, xg);
		} else if (!arrCopy[yold][xold].red && !xg.isRedNext()) {
			return isvalidmoveBlackFigure(moveString, player, board, xg);
		}
		return false;
	}

	/*
	 * General can only be placed in 3 column d, e, f:
	 * Diagonally when delta y = 1 and delta x = 1
	 * Horizontally or vertically when only delta x = 0 OR delta y = 0
	 */
	
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
		
		if (arrCopy[yold][xold].name != "a" && arrCopy[yold][xold].name != "A") {
			System.out.println("Not an advisor.");
			return false;
		}
		// if dify = 1 -> moving vertically and difx must be 0
		// if difx = 1 -> moving vertically and dify must be 0
		if (moveString.contains("d") || moveString.contains("e") || moveString.contains("f")) {
			if (yold >= 7 && yold <= 9 && xold >= 3 && xold <= 5) {
				if (ynew >= 7 && ynew <= 9 && xnew >= 3 && xnew <= 5) {
					if (Math.abs(dify) == 1 && Math.abs(difx) == 1) {
						xg.updatedState(moveString);
						xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
						player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
						return true;
					} 
					else if (Math.abs(difx) == 1 && Math.abs(dify) == 1) {
						xg.updatedState(moveString);
						xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
						player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
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
		
		if (arrCopy[yold][xold].name != "A") {
			System.out.println("Not a Advisor.");
			return false;
		}

		if (moveString.contains("d") || moveString.contains("e") || moveString.contains("f")) {
			if (yold >= 7 && yold <= 9 && xold >= 3 && xold <= 5) {
				if (ynew >= 7 && ynew <= 9 && xnew >= 3 && xnew <= 5) {
					if (Math.abs(dify) == 1 && Math.abs(difx) == 1) {
						xg.updatedState(moveString);
						xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
						player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
						return true;
					} 
					else if (Math.abs(difx) == 1 && Math.abs(dify) == 1) {
						xg.updatedState(moveString);
						xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
						player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
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

	public static void main(String[] args) {
		XiangqiGame xg = new XiangqiGame();
		System.out.println(xg.getBoard());
		Advisor r = new Advisor(false, null, "a");
		System.out.println(r.checkMove("f7-e8", null, xg.board.arr, xg));
		System.out.println(r.checkMove("f7-f8", null, xg.board.arr, xg));
	}

}
