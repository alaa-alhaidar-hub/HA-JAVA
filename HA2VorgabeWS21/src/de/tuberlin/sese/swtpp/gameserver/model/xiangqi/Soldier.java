package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

public class Soldier extends Figure {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7678841322666589105L;
		
	public Soldier(boolean red, Position position, String name) {
		super(red, position, name);
	}

	@Override
	public boolean checkMove(String moveString, Figure[][] board, XiangqiGame xg) {
		//Figure[][] arrCopy = ps.create2D(xg.getBoard());
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
		
		// Check if the figure is a soldier
		if (arrCopy[yold][xold].name != "s" && arrCopy[yold][xold].name != "S" ) {
			System.out.println("Not a soldier.");
			return false;
		}
		
		// If correct player on turn
		if (arrCopy[yold][xold].red && xg.isRedNext()) {
			return isValidMoveRedFigure(moveString, board);
		} else if (!arrCopy[yold][xold].red && !xg.isRedNext()) {
			return isValidMoveBlackFigure(moveString, board);
		}
		
		return false;
	}
	
	public boolean isValidMoveBlackFigure(String moveString, Figure[][] board) {
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
		
		// If yold <= 4 (has not crossed the river), black soldier can move only vertically 
		// in the direction of the red figures
		// dify can be only -1 because going down is increasing the y
		if ((yold <= 4) && (dify == 1) && (difx == 0)) {
			return true;
		}
		
		// If yold >=5, the black soldier has crossed the river and moves vertically
		if ((yold >= 5) && (dify == 1) && (difx == 0)) {
			return true;
		}
		// If yold >=5, the black soldier has crossed the river and can also move horizontally
		if ((yold >= 5) && (dify == 0) && (Math.abs(difx) == 1)) {
			return true;
		}
		
		// End of the board (lower side), black soldier can move only horizontally
		if ((moveString.charAt(1) == '0') && (dify == 0) && (Math.abs(difx) == 1)) {
			return true;
		}
		return false;
	}
	
	public boolean isValidMoveRedFigure(String moveString, Figure[][] board) {
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
		
		// If yold >= 4 (has not crossed the river), red soldier can move only vertically 
		// in the direction of the black figures
		// dify can be only -1 because going up is decreasing the y
		if ((yold >= 5) && (dify == -1) && (difx == 0)) {
			return true;
		}
		
		// If yold <= 4, the red soldier has crossed the river and moves vertically
		if ((yold <= 4) && (dify == -1) && (difx == 0)) {
			return true;
		}
		// If yold <= 4, the red soldier has crossed the river and can also move horizontally
		if ((yold <= 4) && (dify == 0) && (Math.abs(difx) == 1)) {
			return true;
		}
		
		// End of the board (lower side), red soldier can move only horizontally
		if ((moveString.charAt(1) == '9') && (dify == 0) && (Math.abs(difx) == 1)) {
			return true;
		}
		return false;
	}
	
}
