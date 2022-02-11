package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

import de.tuberlin.sese.swtpp.gameserver.model.Move;
import de.tuberlin.sese.swtpp.gameserver.model.Player;

public class Rock extends Figure {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2345235451070921766L;
	
	public Rock(boolean red, Position position, String name) {
		super(red, position, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean checkMove(String moveString, Player player, Figure[][] board, XiangqiGame xg) {
		System.out.println("Rock checkMove called.");
		
		Figure[][] arrCopy = board;

		// Starting position
		int xold = moveString.charAt(0) - 97;
		int xnew = moveString.charAt(3) - 97;
		
		// Target position
		int yold = 9 - Character.getNumericValue(moveString.charAt(1));
		int ynew = 9 - Character.getNumericValue(moveString.charAt(4));
		
		if (arrCopy[yold][xold] != null) {
			if (arrCopy[yold][xold].name != "R" && arrCopy[yold][xold].name != "r") {
				System.out.println("Not a Rock.");
				return false;
			}
		}
		
		// If field is empty
		if (arrCopy[yold][xold] == null) {
			System.out.println("Field is empty.");
			return false;
		}

		if (arrCopy[yold][xold].red && xg.isRedNext()) {
			return isvalidmoveRedFigure(moveString, player, arrCopy, xg);
		} 
		else if (!arrCopy[yold][xold].red && !xg.isRedNext()) {
			return isvalidmoveBlackFigure(moveString, player, arrCopy, xg);
		}
		return false;
	}

	public boolean roadFreeForRock(String moveString, Player player, Figure[][] board, XiangqiGame xg) {
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

		// only in y achse vertical, x achse is same

		if (difx == 0 && Math.abs(dify) == 1) {
			if (arrCopy[ynew][xold] == null) {
				//arrCopy[ynew][xold] = arrCopy[yold][xold];
				//arrCopy[yold][xold] = null;
				player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
				return true;
			} 
			else if (arrCopy[ynew][xold].red != arrCopy[yold][xold].red) {
				//arrCopy[ynew][xold] = arrCopy[yold][xold];
				//arrCopy[yold][xold] = null;
				player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
				return true;
			}
		}
		
		if (dify == 0 && Math.abs(difx) == 1) {
			if (arrCopy[yold][xnew] == null) {
				//arrCopy[yold][xnew] = arrCopy[yold][xold];
				//arrCopy[yold][xold] = null;
				player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
				return true;
			} 
			else if (arrCopy[yold][xnew].red != arrCopy[yold][xold].red) {
				//arrCopy[yold][xnew] = arrCopy[yold][xold];
				//arrCopy[yold][xold] = null;
				player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
				return true;
			}
		}
		// Fall 1 y<0

		if (difx == 0 && dify < 0) {
			// 9- because we do search in the array map 0 to 9 etc.
			// yold+1 so starting from the next feld and see if the road empty
			for (int i = yold - 1; i > ynew; i--) {
				if (arrCopy[i][xold] != null) {
					System.out.println("Error on the road is: " + arrCopy[i][xold]);
					return false;
				} else {
					// check target feld
					if (i - 1 == ynew && arrCopy[ynew][xold] == null) {
						//arrCopy[ynew][xold] = arrCopy[yold][xold];
						//arrCopy[yold][xold] = null;
						player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
						return true;
					} else if (i - 1 == ynew && arrCopy[ynew][xold].red != arrCopy[yold][xold].red) {
						//arrCopy[ynew][xold] = arrCopy[yold][xold];
						//arrCopy[yold][xold] = null;
						player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
						return true;
					}
				}

			}
		} 
		
		// Fall 2 y>0
		if (difx == 0 && dify > 0) {
			// 9- because we do search in the array map 0 to 9 etc.
			// yold+1 so starting from the next feld and see if the road empty
			for (int i = yold + 1; i < ynew; i++) {
				if (arrCopy[i][xold] != null && i != ynew) {
					System.out.println("Error on the road is: " + arrCopy[i][xold]);
					return false;
				}
				// check target feld
				if (i + 1 == ynew && arrCopy[ynew][xold] == null) {
					//arrCopy[ynew][xold] = arrCopy[yold][xold];
					//arrCopy[yold][xold] = null;
					player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
					return true;
				} 
				else if (i + 1 == ynew && arrCopy[ynew][xold].red != arrCopy[yold][xold].red) {
					//arrCopy[ynew][xold] = arrCopy[yold][xold];
					//arrCopy[yold][xold] = null;
					player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
					return true;
				}

			}

			// only in x achse horisontal, y achse is same
		} 
		// Fall 3 < 0
		if (dify == 0 && difx < 0) {
			for (int i = xold - 1; i > xnew; i--) {
				if (arrCopy[yold][i] != null && arrCopy[yold][xold] != arrCopy[yold][i]) {
					System.out.println("Error on the road is: " + arrCopy[yold][i]);
					return false;
				} else {
					// check target feld
					if (i - 1 == xnew && arrCopy[yold][xnew] == null) {
						//arrCopy[yold][xnew] = arrCopy[yold][xold];
						//arrCopy[yold][xold] = null;
						player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
						return true;
					} else if (i - 1 == xnew && arrCopy[yold][xnew].red != arrCopy[yold][xold].red) {
						//arrCopy[yold][xnew] = arrCopy[yold][xold];
						//arrCopy[yold][xold] = null;
						player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
						return true;
					}
				}

			}
			// Fall 4 x>0
		} 
		else if (dify == 0 && difx > 0) {
			for (int i = xold + 1; i < xnew; i++) {
				if (arrCopy[yold][i] != null && arrCopy[yold][xold] != arrCopy[yold][i]) {
					System.out.println("Error on the road is: " + arrCopy[yold][i]);
					return false;
				} 
				else {
					// check target feld
					if (i + 1 == xnew && arrCopy[yold][xnew] == null) {
						//arrCopy[yold][xnew] = arrCopy[yold][xold];
						//arrCopy[yold][xold] = null;
						player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
						return true;
					} 
					else if (i + 1 == xnew && arrCopy[yold][xnew].red != arrCopy[yold][xold].red) {
						//arrCopy[yold][xnew] = arrCopy[yold][xold];
						//arrCopy[yold][xold] = null;
						player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
						return true;
					}
				}
			}
		}
		return false;

	}
	
	public boolean isvalidmoveBlackFigure(String moveString, Player player, Figure[][] board, XiangqiGame xg) {
		System.out.println("Black method called.");
		
		// Starting position
		int xold = moveString.charAt(0) - 97;
		int yold = (Character.getNumericValue(moveString.charAt(1)));
		
		// Target position
		int xnew = moveString.charAt(3) - 97;
		int ynew = (Character.getNumericValue(moveString.charAt(4)));
		
		// Delta positions
		int difx = Math.abs(xnew - xold);
		int dify = Math.abs(ynew - yold);

		Figure[][] arrCopy = board;
		
		if ((arrCopy[ynew][xnew] != null && arrCopy[yold][xold] != null)
				&& arrCopy[ynew][xnew].red == arrCopy[yold][xold].red) {
			System.out.println("Same color.");
			return false;
		}
		if ((dify > 0 && difx == 0) || (dify < 0 && difx == 0) ) {
			if (roadFreeForRock(moveString, player, arrCopy, xg)) {
				xg.updatedState(moveString);
				xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
				player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
				return true;
			}

		} else if ((difx > 0 && dify == 0) || (difx < 0 && dify == 0)) {
			if (roadFreeForRock(moveString,player, arrCopy, xg)) {
				xg.updatedState(moveString);
				xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
				player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
				return true;
			}
		}
		return false;
	}

	public boolean isvalidmoveRedFigure(String moveString, Player player, Figure[][] board, XiangqiGame xg) {
		System.out.println("Red method called.");
		
		// Starting position.
		int xold = moveString.charAt(0) - 97;
		int yold = 9 - (Character.getNumericValue(moveString.charAt(1)));
		
		// Target position.
		int xnew = moveString.charAt(3) - 97;
		int ynew = 9 - (Character.getNumericValue(moveString.charAt(4)));
		
		// Delta position.
		int difx = Math.abs(xnew - xold);
		int dify = Math.abs(ynew - yold);

		Figure[][] arrCopy = board;

		if ((arrCopy[ynew][xnew] != null && arrCopy[yold][xold] != null)
				&& arrCopy[ynew][xnew].red == arrCopy[yold][xold].red) {
			System.out.println("Same color.");
			return false;
		}
		
		if (dify > 0 && difx == 0) {
			if (roadFreeForRock(moveString, player, arrCopy, xg)) {
				xg.updatedState(moveString);
				xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
				player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
				return true;
			}
			return false;
		} 
		else if (difx > 0 && dify == 0) {
			if (roadFreeForRock(moveString, player, arrCopy, xg)) {
				xg.updatedState(moveString);
				xg.board.updateArrayPerMoveString(moveString, xg.getBoard(), xg.board.arr);
				player.getGame().getHistory().add(new Move(moveString, xg.getBoard(), player));
				return true;
			}
			return false;
		}
		return false;
	}

	public static void main(String[] args) {
		XiangqiGame xg = new XiangqiGame();
		System.out.println(xg.getBoard());
		
		Rock r = new Rock(true, null, "");
		
		System.out.println(r.checkMove("a2-a4", null, xg.board.arr, xg));
		System.out.println();
		System.out.println(r.checkMove("a3-a4", null, xg.board.arr, xg));
		System.out.println();
		System.out.println(r.checkMove("d0-d1", null, xg.board.arr, xg));
		System.out.println();
		System.out.println(r.checkMove("a0-a1", null, xg.board.arr, xg));
		System.out.println();
	}

}