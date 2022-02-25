package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

import de.tuberlin.sese.swtpp.gameserver.model.*;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.figure.IFigure;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.figure.FigureColor;
//TODO: more imports from JVM allowed here

import java.io.Serializable;


public class XiangqiGame extends Game implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5424778147226994452L;

	/************************
	 * member
	 ***********************/
	// just for better comprehensibility of the code: assign red and black player
	private Player blackPlayer;
	private Player redPlayer;
	
	
	// internal representation of the game state
	public String state;
	public Board board;

	/************************
	 * constructors
	 ***********************/

	public XiangqiGame() {
		super();
		// TODO: initialization of game state can go here
		state = "rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR";
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
        // Note: This method is for automatic testing. A regular game would not start at some artificial state.
		// It can be assumed that the state supplied is a regular board that can be reached during a game.
		board = Board.fromRepresentation(state);
    }

	@Override
	public String getBoard() {
		return board.getCurrentBoardStateInStringRepresentation();
	}

	public Position getStartPosition(String moveString) {
		String[] moveStringPositions = moveString.split("-");
		return new Position(moveStringPositions[0]);
	}

	public Position getTargetPosition(String moveString) {
		String[] moveStringPositions = moveString.split("-");
		return new Position(moveStringPositions[1]);
	}

	public boolean correctMoveString(String moveString) {
		return moveString.matches("[a-i][0-9]-[a-i][0-9]");
	}

	@Override
	public boolean tryMove(String moveString, Player player) {
		if (!correctMoveString(moveString)) {
			return false;
		}

		Position startPosition = getStartPosition(moveString);
		Position targetPosition = getTargetPosition(moveString);

		IFigure figureAtStart = board.figureAt(startPosition);
		IFigure figureAtTarget = board.figureAt(targetPosition);

		FigureColor playerColor = player == redPlayer ? FigureColor.RED : FigureColor.BLACK;

		if (nextPlayer != player) {
			return false;
		}

		if (figureAtStart == null) {
			return false;
		}

		if (figureAtStart.getColor() != playerColor) {
			return false;
		}

		if (startPosition.equals(targetPosition)) {
			return false;
		}

		if (!board.isValidFigureMove(figureAtStart, startPosition, targetPosition)) {
			return false;
		}

		board.removeFigure(figureAtTarget);
		board.moveFigure(figureAtStart, targetPosition);

		Move currentMove = new Move(moveString, this.getBoard(), player);
		history.add(currentMove);
		setHistory(history);

		endGameIfPlayerWon(player);
		
		changePlayer(player);

		return true;
	}

	private void endGameIfPlayerWon(Player player) {
	    Player otherPlayer = getOtherPlayer(player);
	    FigureColor otherPlayerFigureColor = getPlayerFigureColor(otherPlayer);
        if (!board.hasValidMoves(otherPlayerFigureColor)) {
            regularGameEnd(player);
            changePlayer(player);
        }
    }

    private void changePlayer(Player currentPlayer) {
	    setNextPlayer(getOtherPlayer(currentPlayer));
    }

	private FigureColor getPlayerFigureColor(Player player) {
	    if (player == redPlayer) {
	        return FigureColor.RED;
        }

	    return FigureColor.BLACK;
    }

    private Player getOtherPlayer(Player player) {
	    if (player == redPlayer) {
	        return blackPlayer;
        }

	    return redPlayer;
    }

}