package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

import de.tuberlin.sese.swtpp.gameserver.control.GameController;
import de.tuberlin.sese.swtpp.gameserver.model.*;
//TODO: more imports from JVM allowed here

import java.io.Serializable;
import java.util.List;

public class XiangqiGame extends Game implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5424778147226994452L;

	/************************
	 * member
	 ***********************/
	//ParsingString ps = new ParsingString();
	// just for better comprehensibility of the code: assign red and black player
	private Player blackPlayer;
	private static Player redPlayer;
	
	
	// internal representation of the game state
	// TODO: insert additional game data here
	public String state;
	public ParsingString board;

	/************************
	 * constructors
	 ***********************/

	public XiangqiGame() {
		super();
		// TODO: initialization of game state can go here
		//state = "rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR";
		state = "rheagaehr/9/1c5c1/s1s1s1s1s/9/4H4/S1S1S1S1S/1C5C1/9/RHEAGAE2";
		setBoard(state);
	}

	public String getType() {
		return "xiangqi";
	}

	/*******************************************
	 * Game class functions already implemented
	 ******************************************/

	@Override
	public boolean addPlayer(Player player) {
		if (!started) {
			players.add(player);

			// game starts with two players
			if (players.size() == 2) {
				started = true;
				this.redPlayer = players.get(0);
				this.blackPlayer = players.get(1);
				nextPlayer = redPlayer;
			}
			return true;
		}

		return false;
	}

	@Override
	public String getStatus() {
		if (error)
			return "Error";
		if (!started)
			return "Wait";
		if (!finished)
			return "Started";
		if (surrendered)
			return "Surrendered";
		if (draw)
			return "Draw";

		return "Finished";
	}

	@Override
	public String gameInfo() {
		String gameInfo = "";

		if (started) {
			if (blackGaveUp())
				gameInfo = "black gave up";
			else if (redGaveUp())
				gameInfo = "red gave up";
			else if (didRedDraw() && !didBlackDraw())
				gameInfo = "red called draw";
			else if (!didRedDraw() && didBlackDraw())
				gameInfo = "black called draw";
			else if (draw)
				gameInfo = "draw game";
			else if (finished)
				gameInfo = blackPlayer.isWinner() ? "black won" : "red won";
		}

		return gameInfo;
	}

	@Override
	public String nextPlayerString() {
		return isRedNext() ? "r" : "b";
	}

	@Override
	public int getMinPlayers() {
		return 2;
	}

	@Override
	public int getMaxPlayers() {
		return 2;
	}

	@Override
	public boolean callDraw(Player player) {

		// save to status: player wants to call draw
		if (this.started && !this.finished) {
			player.requestDraw();
		} else {
			return false;
		}

		// if both agreed on draw:
		// game is over
		if (players.stream().allMatch(Player::requestedDraw)) {
			this.draw = true;
			finish();
		}
		return true;
	}

	@Override
	public boolean giveUp(Player player) {
		if (started && !finished) {
			if (this.redPlayer == player) {
				redPlayer.surrender();
				blackPlayer.setWinner();
			}
			if (this.blackPlayer == player) {
				blackPlayer.surrender();
				redPlayer.setWinner();
			}
			surrendered = true;
			finish();

			return true;
		}

		return false;
	}

	/*
	 * ****************************************** Helpful stuff
	 */

	/**
	 *
	 * @return True if it's red player's turn
	 */
	public boolean isRedNext() {
		return nextPlayer == redPlayer;
	}

	/**
	 * Ends game after regular move (save winner, finish up game state,
	 * histories...)
	 *
	 * @param winner player who won the game
	 * @return true if game was indeed finished
	 */
	public boolean regularGameEnd(Player winner) {
		// public for tests
		if (finish()) {
			winner.setWinner();
			winner.getUser().updateStatistics();
			return true;
		}
		return false;
	}

	public boolean didRedDraw() {
		return redPlayer.requestedDraw();
	}

	public boolean didBlackDraw() {
		return blackPlayer.requestedDraw();
	}

	public boolean redGaveUp() {
		return redPlayer.surrendered();
	}

	public boolean blackGaveUp() {
		return blackPlayer.surrendered();
	}

	/*******************************************
	 * !!!!!!!!! To be implemented !!!!!!!!!!!!
	 ******************************************/

	@Override
    public void setBoard(String state) {
        // Note: This method is for automatic testing. A regular game would not start at
        // some artificial state.
        // It can be assumed that the state supplied is a regular board that can be
        // reached during a game.
        // TODO: implement
		board = ParsingString.fromRepresentation(state);
    }

	@Override
	public String getBoard() {
		// TODO: implement
		if (board == null) {
			return ""; 
		}
		return board.createBoardStringFrom2D();
	}
	
	public String updatedState(String moveString) {

		// Get the board state until now
		String oldState = getBoard();
		
		System.out.println("This is the old state: " + oldState);
		
		// Re-formating the string, check the method in ParsingString class
		String s = board.formatString(oldState);
		String[] sArr = s.split("/", 10);

		int a = 9 - Character.getNumericValue(moveString.charAt(1));
		int b = 9 - Character.getNumericValue(moveString.charAt(4));

		String from = sArr[a]; // char from this field
		String to = sArr[b]; // to this field

		char[] carr2 = (from.toCharArray()); // Row as an array -> 0 column c
		char[] carr = (to.toCharArray()); // Row as an array -> 1 column d
		
		carr[(int) moveString.charAt(3) - 97] = carr2[(int) moveString.charAt(0) - 97]; // replace the position for the old																						// one
		carr2[(int) moveString.charAt(0) - 97] = 'x'; // old one is null

		String f = "";
		String f1 = "";
		
		// Transform the character array "from - to" again to a string
		for (int i = 0; i < carr.length; i++) {
			f = f.concat(String.valueOf(carr[i]));
			f1 = f1.concat(String.valueOf(carr2[i]));
		}
		
		// Join the split array fields together as a string
		StringBuffer newState = new StringBuffer();
		for (int i = 0; i < sArr.length; i++) {
			if (i == a) {
				sArr[i] = f1;
			} else if (i == b) {
				sArr[i] = f;
			}
			if (i == 9) {
				newState.append(sArr[i]);
				break;
			}
			newState = newState.append(sArr[i]).append("/");
		}

		System.out.println("This is the new state " + board.countNull(newState.toString()));
		return board.countNull(newState.toString());
	}
	
	public boolean isKingChecked() {
		return false;
	}
	
	// Count the valid moves a player has, 
	// if there are none (a.k.a counter = 0), the player loses the game
	public int numberValidMoves(Figure figure, char currX, int currY) {
		int counter = 0;
		char currentX = currX;
		char currentY = (char) ('0' + currY);
		
		for (int i = 0;  i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				char targetX = (char) (j + 97);
				System.out.println("This is the targetX: " + targetX);
				char targetY = (char) ('0' + (9 - i));
				System.out.println("This is the targetY: " + targetY);
				String makeMoveString = "" + currentX + currentY + '-' + targetX + targetY;
				System.out.println("This is the move string: " + makeMoveString);
				if (figure.checkMove(makeMoveString, board.arr, this)) {
					counter = counter + 1;
				}
			}
		}
		return counter;
	}
	
	// If the number of valid moves is > 0, the player is allowed to make a move
	public boolean hasValidMoves(Player player) {
		
		boolean playersColor = (player == redPlayer) ? true : false;		
		
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 9; j++) {
				if (board.arr[i][j] != null) {
					Figure figure = board.arr[i][j];
					char positionX = board.arr[i][j].position.x;
					int positionY = board.arr[i][j].position.y;
					if (board.arr[i][j].red == playersColor && (numberValidMoves(figure, positionX, positionY) != 0)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean tryMove(String moveString, Player player) {
		// TODO: implement
		
		// Is the state after the move possible
		Boolean resultAfterMove;
		
		// No figure on the starting position
		if (board.getFigurePerMoveString(moveString) == null) {
			return false;
		}
		
		List<Player> players = this.getPlayers();
		for (Player p : players) {
			if (p == player) {
				if (!player.isGameInvalid() && !this.isFinished() && this.isStarted()) {
					System.out.println("Figure to call his check methode: " + board.getFigurePerMoveString(moveString));
					
					// If the player on turn has no valid moves
					if (hasValidMoves(player) == false) {
						if (player == redPlayer) {
							regularGameEnd(blackPlayer);
						}
						else {
							regularGameEnd(redPlayer);
						}
						
						System.out.println("Player has no valid moves.");
						return false;
					}
					
					// If the player has valid moves, check if this move is a valid one 
					// and if yes, make the move
					resultAfterMove =  board.getFigurePerMoveString(moveString).checkMove(moveString, board.arr, this);
					
					if (resultAfterMove == true) {
						
						updatedState(moveString);
						board.updateArrayPerMoveString(moveString, this.getBoard(), board.arr);
						player.getGame().getHistory().add(new Move(moveString, this.getBoard(), player));
						
						// Iterate red and black player when they have made a successful move
						setNextPlayer(isRedNext() ? blackPlayer : redPlayer);
						
						return resultAfterMove;
					}
					else {
						
						return resultAfterMove;
						
					}
				}
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		//XiangqiGame xg = new XiangqiGame();
		//String moveString = "b2-b4";
		//User user1 = new User("Alice", "alice");
		
		//xg.tryMove(moveString, redPlayer);
		
	}
}