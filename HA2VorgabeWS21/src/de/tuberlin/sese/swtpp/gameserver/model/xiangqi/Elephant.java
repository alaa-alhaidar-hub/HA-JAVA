package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

import java.io.Serializable;

public class Elephant extends Figure implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7500624995679337949L;
	
	public Elephant(boolean red, Position position,String name) {
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
		
		// Check if the figure is an Elephant
		if (arrCopy[yold][xold].name != "e" && arrCopy[yold][xold].name != "E") {
			System.out.println("Not an elephant.");
			return false;
		}
		
		// If correct player on turn
		if (arrCopy[yold][xold].red && xg.isRedNext()) {
			return isvalidmoveRedFigure(moveString, board);
		} else if (!arrCopy[yold][xold].red && !xg.isRedNext()) {
			return isvalidmoveBlackFigure(moveString, board);
		}
		
		return false;
	}
	
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
		
		// Edge case:
		// If it is on the 9th row - it can move diagonally only down for y
		// and left or right for x
		if ((moveString.charAt(1) == '9') && (Math.abs(difx) == 2 && Math.abs(dify) == 2) 
				&& 
				(arrCopy[yold + 1][xold + 1] == null || (arrCopy[yold + 1][xold - 1] == null))) {
			return true;
		}
		
		// If it is on the a column - it can move diagonally only right for x 
		if ((moveString.charAt(0) == 'a') && 
				(Math.abs(difx) == 2 && Math.abs(dify) == 2) 
				&& 
				((arrCopy[yold - 1][xold + 1] == null) || (arrCopy[yold + 1][xold + 1] == null))) {
			return true;
		}
		
		// If it is on the i column - it can move diagonally only left for x
		if ((moveString.charAt(0) == 'i') && 
				(Math.abs(difx) == 2 && Math.abs(dify) == 1) 
				&& 
				((arrCopy[yold - 1][xold - 1] == null) || (arrCopy[yold + 1][xold - 1] == null))) {
			return true;
		}
		
		// Check movements for all other cases:
		// Elephant is moving always diagonally with two fields
		// Check if the first field is empty!
		if ((Math.abs(difx) == 2 && Math.abs(dify) == 2) && 
				(arrCopy[yold + 1][xold + 1] == null || arrCopy[yold - 1][xold - 1] == null)) {
			return true;
		}
		
		// Black elephant cannot cross the river
		if (ynew >= 5) {
			return false;
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
		
		if (arrCopy[yold][xold].name != "E") {
			System.out.println("Not an elephant.");
			return false;
		}
		
		// Edge case:
		// If it is on the 0th row - it can move diagonally only up for y
		// and left or right for x
		if ((moveString.charAt(1) == '0') && (Math.abs(difx) == 2 && Math.abs(dify) == 2) 
				&& 
				(arrCopy[yold - 1][xold + 1] == null || (arrCopy[yold - 1][xold - 1] == null))) {
			return true;
		}
		
		// If it is on the a column - it can move diagonally only right for x 
		if ((moveString.charAt(0) == 'a') && 
				(Math.abs(difx) == 2 && Math.abs(dify) == 2) 
				&& 
				((arrCopy[yold - 1][xold + 1] == null) || (arrCopy[yold + 1][xold + 1] == null))) {
			return true;
		}
		
		// If it is on the i column - it can move diagonally only left for x
		if ((moveString.charAt(0) == 'i') && 
				(Math.abs(difx) == 2 && Math.abs(dify) == 1) 
				&& 
				((arrCopy[yold - 1][xold - 1] == null) || (arrCopy[yold + 1][xold - 1] == null))) {
			return true;
		}
		
		// Check movements for all other cases:
		// Elephant is moving always diagonally with two fields
		// Check if the first field is empty!
		if ((Math.abs(difx) == 2 && Math.abs(dify) == 2) && 
				(arrCopy[yold + 1][xold + 1] == null || arrCopy[yold - 1][xold - 1] == null)) {
			return true;
		}
		
		// Black elephant cannot cross the river
		if (ynew <= 4) {
			return false;
		}
		
		return false;
	}
}
