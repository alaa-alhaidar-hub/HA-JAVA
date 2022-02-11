package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

import de.tuberlin.sese.swtpp.gameserver.model.Move;
import de.tuberlin.sese.swtpp.gameserver.model.Player;

public class Soldier extends Figure {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7678841322666589105L;
	
	//private ParsingString ps;
	//private XiangqiGame xg;
	//String testBoard = "rhea1aeh1/4g4/1c3r3/7cs/s1s1C4/9/S1S2ESCS/R8/4A4/1HE1GAEHR";
	
	public Soldier(boolean red, Position position, String name) {
		super(red, position, name);
	}

	@Override
	public boolean checkMove(String moveString, Player player, Figure[][] board, XiangqiGame xg) {
		//Figure[][] arrCopy = ps.create2D(xg.getBoard());
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
			return isValidMoveRedFigure(moveString, player, board, xg);
		} else if (!arrCopy[yold][xold].red && !xg.isRedNext()) {
			return isValidMoveBlackFigure(moveString, player, board, xg);
		}
		
		return false;
	}
	
	public boolean isValidMoveBlackFigure(String moveString, Player player, Figure[][] board, XiangqiGame xg) {
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
		
		if (arrCopy[yold][xold].name != "s") {
			System.out.println("Not a soldier.");
			return false;
		}
		
		// If yold <= 4 (has not crossed the river), black soldier can move only vertically 
		// in the direction of the red figures
		// dify can be only -1 because going down is increasing the y
		if ((yold <= 4) && (dify == 1) && (difx == 0)) {
			xg.updatedState(moveString);
			xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
			player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
			return true;
		}
		
		// If yold >=5, the black soldier has crossed the river and moves vertically
		if ((yold >= 5) && (dify == 1) && (difx == 0)) {
			xg.updatedState(moveString);
			xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
			player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
			return true;
		}
		// If yold >=5, the black soldier has crossed the river and can also move horizontally
		if ((yold >= 5) && (dify == 0) && (Math.abs(difx) == 1)) {
			xg.updatedState(moveString);
			xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
			player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
			return true;
		}
		
		// End of the board (lower side), black soldier can move only horizontally
		if ((moveString.charAt(1) == '0') && (dify == 0) && (Math.abs(difx) == 1)) {
			xg.updatedState(moveString);
			xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
			player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
			return true;
		}
		return false;
	}
	
	public boolean isValidMoveRedFigure(String moveString, Player player, Figure[][] board, XiangqiGame xg) {
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
		
		if (arrCopy[yold][xold].name != "S") {
			System.out.println("Not a soldier.");
			return false;
		}
		
		// If yold >= 4 (has not crossed the river), red soldier can move only vertically 
		// in the direction of the black figures
		// dify can be only -1 because going up is decreasing the y
		if ((yold >= 5) && (dify == -1) && (difx == 0)) {
			xg.updatedState(moveString);
			xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
			player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
			return true;
		}
		
		// If yold <= 4, the red soldier has crossed the river and moves vertically
		if ((yold <= 4) && (dify == -1) && (difx == 0)) {
			xg.updatedState(moveString);
			xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
			player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
			return true;
		}
		// If yold <= 4, the red soldier has crossed the river and can also move horizontally
		if ((yold <= 4) && (dify == 0) && (Math.abs(difx) == 1)) {
			xg.updatedState(moveString);
			xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
			player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
			return true;
		}
		
		// End of the board (lower side), red soldier can move only horizontally
		if ((moveString.charAt(1) == '9') && (dify == 0) && (Math.abs(difx) == 1)) {
			//xg.updatedState(moveString);
			xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
			xg.setBoard(xg.getBoard());
			player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
			return true;
		}
		return false;
	}
	
	// Must change the players turn in the function at the start of the class!
	/*public static void main(String[] args) {
		XiangqiGame xg = new XiangqiGame();
		System.out.println(xg.getBoard());
		Soldier s = new Soldier(false, null, "s");
		System.out.println(s.checkMove("c5-c4", null, xg.board.arr, xg));
		
	}*/
	
}
