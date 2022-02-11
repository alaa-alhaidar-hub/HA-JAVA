 package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

//import java.util.List;

import de.tuberlin.sese.swtpp.gameserver.model.Move;
import de.tuberlin.sese.swtpp.gameserver.model.Player;
//import de.tuberlin.sese.swtpp.gameserver.model.User;

public class General extends Figure {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7662465147923352468L;

	public General(boolean red, Position position, String name) {
		super(red, position, name);

	}

	@Override
	public boolean checkMove(String moveString, Player player, Figure[][] board, XiangqiGame xg) {
		System.out.println("General checkMove called.");
		
		Figure[][] arrCopy =  board;
		
		// Starting position
		int xold = moveString.charAt(0) - 97;
		int xnew = moveString.charAt(3) - 97;
		
		// Target position
		int yold = 9 - Character.getNumericValue(moveString.charAt(1));
		int ynew = 9 - Character.getNumericValue(moveString.charAt(4));
		
		// Check if the figure is a General
		if (arrCopy[yold][xold] != null) {
			if (arrCopy[yold][xold].name != "G" && arrCopy[yold][xold].name != "g") {
				return false;
			}
		}
		
		// If the field is empty
		if (arrCopy[yold][xold] == null) {
			System.out.println("Field is empty.");
			return false;
		}
		
		// If the two positions are not empty, then the color must be different
		if ((arrCopy[ynew][xnew] != null && arrCopy[yold][xold] != null)
				&& arrCopy[ynew][xnew].red == arrCopy[yold][xold].red) {
			System.out.println("Same color.");
			return false;

		}
		
		// The target position should always be in the either the "d", "e" or "f" column
		if (moveString.contains("d") || moveString.contains("e") || moveString.contains("f")) {
			if (arrCopy[yold][xold].red && xg.isRedNext()) {
				return isvalidmoveRedFigure(moveString, player, board, xg);
			} 
			else if (!arrCopy[yold][xold].red && !xg.isRedNext()) {
				return isvalidmoveBlackFigure(moveString, player, board, xg);
			}
		}
		return false;
	}

	public boolean todesBlickCheckBlack(String moveString, Player player, Figure[][] board, XiangqiGame xg) {
		
		// Target position on the y-axe (in the column)
		int ynew = 9 - Character.getNumericValue(moveString.charAt(4));	
		Figure[][] arr = board;

		// If the target field is in front of the another general directly without ANY other figure between the two generals,
		// move is not possible
		Boolean firstFigureInFrontIsGeneral = true;		
		
		// Target position is in column d
		if (moveString.charAt(3) == 'd') {
			for (int i = ynew + 1; i < arr.length - 1; i++) {
				if (arr[i][3] != null)
					// If the FIRST figure in front of the general is another general, 
					// then return false
					if ((arr[i][3].name == "g" || arr[i][3].name == "G") && (firstFigureInFrontIsGeneral == true)) {
						return true;
					}
					// If there is another first figure in front of the general, 
					// it is not a problem to have the opposite general in this columns
					else {
						firstFigureInFrontIsGeneral = false;
					}
			}
			return false;
		}
		
		// Target position is in column e
		if (moveString.charAt(3) == 'e') {
			for (int i = ynew + 1; i < arr.length - 1; i++) {
				if (arr[i][4] != null)
					// If the FIRST figure in front of the general is another general, 
					// then return false
					if ((arr[i][4].name == "G") && (firstFigureInFrontIsGeneral == true)) {
						return true;
					}
					// If there is another first figure in front of the general, 
					// it is not a problem to have the opposite general in this columns
					else {
						firstFigureInFrontIsGeneral = false;
					}
			}
			return false;
		}
		
		// Target position is in column f
		if (moveString.charAt(3) == 'f') {
			for (int i = ynew + 1; i < arr.length - 1; i++) {
				if (arr[i][5] != null)
					// If the FIRST figure in front of the general is another general, 
					// then return false
					if ((arr[i][5].name == "g" || arr[i][5].name == "G") && (firstFigureInFrontIsGeneral == true)) {
						return true;
					}
					// If there is another first figure in front of the general, 
					// it is not a problem to have the opposite general in this columns
					else {
						firstFigureInFrontIsGeneral = false;
					}
			}
		}
		return false;
	}

	public boolean todesBlickCheckRed(String moveString, Player player, Figure[][] board, XiangqiGame xg) {
		
		// Target position on the y-axe (in the column)
		int ynew = 9 - Character.getNumericValue(moveString.charAt(4));
		
		Figure[][] arr = board;
		
		// If the target field is in front of the another general directly without ANY other figure between the two generals,
		// move is not possible
		Boolean firstFigureInFrontIsGeneral = true;
		
		// Target position is in column d
		if (moveString.charAt(3) == 'd') {
			for (int i = ynew - 1; i >= 0; i--) {
				if (arr[i][3] != null)
					if ((arr[i][3].name == "g" || arr[i][3].name == "G") && (firstFigureInFrontIsGeneral == true)) {
						return true;
					}
					else {
						firstFigureInFrontIsGeneral = false;
					}
			}
			return false;
		}
		// Target position is in column e
		if (moveString.charAt(3) == 'e') {
			for (int i = ynew - 1; i >= 0; i--) {
				if (arr[i][4] != null)
					if ((arr[i][4].name == "g" || arr[i][4].name == "G") && (firstFigureInFrontIsGeneral == true)) {
						return true;
					}
					else {
						firstFigureInFrontIsGeneral = false;
					}
			}
			return false;
		}
		
		// Target position is in f->5 column
		if (moveString.charAt(3) == 'f') {
			for (int i = ynew - 1; i >= 0; i--) {
				if (arr[i][5] != null)
					if ((arr[i][5].name == "g" || arr[i][5].name == "G") && (firstFigureInFrontIsGeneral == true)) {
						return true;
					}
					else {
						firstFigureInFrontIsGeneral = false;
					}
			}
			return false;
		}
		return false;
	}

	/*
	 * yold must be calculated, because array start in 0, but in Spiel in 9. General
	 * can only palced in 3 column d, e, f. 2D Array X Achse a=0, b=1...,i=8
	 */
	public boolean isvalidmoveBlackFigure(String moveString, Player player, Figure[][] board, XiangqiGame xg) {
		System.out.println("Black method called.");
		
		// Starting position
		int xold = moveString.charAt(0) - 97;
		int yold = 9 - Character.getNumericValue(moveString.charAt(1));
		
		// Target position
		int xnew = moveString.charAt(3) - 97;
		int ynew = 9 - Character.getNumericValue(moveString.charAt(4));
		
		// Delta positions
		int difx = xnew - xold;
		int dify = ynew - yold;

		if (yold >= 0 && yold <= 2 && xold >= 3 && xold <= 5) {
			if (ynew >= 0 && ynew <= 2 && xnew >= 3 && xnew <= 5) {
				if (Math.abs(dify) == 1 & Math.abs(difx) == 0) {
					if (!todesBlickCheckBlack(moveString, player, board, xg)) {
						xg.updatedState(moveString);
						xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
						player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
						return true;
					}
				} 
				else if (Math.abs(difx) == 1 & Math.abs(dify) == 0) {
					if (!todesBlickCheckBlack(moveString, player, board, xg)) {
						xg.updatedState(moveString);
						xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
						player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
						return true;
					}
				}
			}
		}
		return false;
	}

	/*
	 * yold must be calculated, because array start in 0, but in Spiel in 9. General
	 * can only palced in 3 column d, e, f. 2D Array X Achse a=0, b=1...,i=8
	 */
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

		
		if (yold >= 7 && yold <= 9 && xold >= 3 && xold <= 5) {
			if (ynew >= 7 && ynew <= 9 && xnew >= 3 && xnew <= 5) {
				if (Math.abs(dify) == 1 & Math.abs(difx) == 0) {
					if (!todesBlickCheckRed(moveString, player, board, xg)) {
						xg.updatedState(moveString);
						xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
						player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
						return true;
					}
				} 
				else if (Math.abs(difx) == 1 & Math.abs(dify) == 0) {
					if (!todesBlickCheckRed(moveString, player, board, xg)) {
						xg.updatedState(moveString);
						xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
						player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
						return true;
					}
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {
		XiangqiGame xg = new XiangqiGame();
		System.out.println(xg.getBoard());
		General g = new General(false, null, "g");
		System.out.println(g.checkMove("e0-d0", null, xg.board.arr, xg));
	}
}