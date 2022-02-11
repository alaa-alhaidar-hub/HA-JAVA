package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

import java.util.ArrayList;
import java.util.HashMap;

import de.tuberlin.sese.swtpp.gameserver.model.Player;

public class ParsingString {

	// This is the game board
	public Figure[][] arr;

	// A constructor to create the array (a.k.a) the board
	public ParsingString() {
		this.arr = new Figure[10][9];
	}

	// Static method to create the board when the function setBoard() is called from
	// the game
	public static ParsingString fromRepresentation(String representation) {
		ParsingString newBoard = new ParsingString();
		newBoard.create2D(representation);
		return newBoard;
	}

	// This is a void method which does not return an array, but sets the figures on
	// the
	// already constructed array (arr from the ParsingString constructor)
	public void create2D(String gameState) {
		String s = formatString(gameState);

		String[] sArr = s.split("/", 10);
		for (int i = 0; i < 10; i++) { // 0-9 also 10 fields

			for (int j = 0; j < 9; j++) {// 0-8 also 9 fields
				arr[i][j] = getFigure(sArr[i].charAt(j));
				if (arr[i][j] != null) {
					arr[i][j].position.setX((char) (j + 97));// 0+79 is a ascii
					arr[i][j].position.setY(9 - i);
				}
			}
		}
	}

	// A function to create a new figure from its character representation
	public Figure getFigure(char s) {

		if (s == 'x') {
			return null;
		}

		else {
			Figure figure = FigureFactory.createFigure(s);
			return figure;
		}
	}

	// A function that returns the figure that is on
	// this board position
	public Figure getFigurePerMoveString(String moveString) {
		int xold = moveString.charAt(0) - 97;
		int yold = 9 - Character.getNumericValue(moveString.charAt(1));
		return arr[yold][xold];
	}

	// A function that updates the board position after a move is being made
	public Figure[][] updateArrayPerMoveString(String moveString, String gameState, Figure[][] arr) {
		int xold = moveString.charAt(0) - 97;
		int xnew = moveString.charAt(3) - 97;
		int yold = 9 - Character.getNumericValue(moveString.charAt(1));
		int ynew = 9 - Character.getNumericValue(moveString.charAt(4));
		arr[ynew][xnew] = arr[yold][xold];
		arr[yold][xold] = null;
		return arr;
	}

	// To be used for the getBoard() function in the game class for creating
	// a string representation of the current state of the board
	public String createBoardStringFrom2D() {
		String currentState = "";
		String figureRepresentation;

		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 9; x++) {
				// If the current board position is empty, add X to the state representation
				if (arr[y][x] == null) {
					currentState = currentState + "x";
				}
				// If there is a figure, add the figure representation to the state
				// representation
				else {
					figureRepresentation = arr[y][x].name;
					currentState = currentState + figureRepresentation;
				}
			}
			// Add "/" at the end of each row, except for the last one
			if (y < 9) {
				currentState = currentState + "/";
			}
		}

		// Add the "/", because it is needed so that the transformation under works
		currentState = currentState.concat("/");

		System.out.println("This is the current state " + currentState);

		// count all the "x" from the string representation
		// and add their count to the final string representation, instead of the Xs
		StringBuilder output = new StringBuilder();
		int count = 0;
		for (int i = 0; i < currentState.length(); i++) {
			if (currentState.charAt(i) == "x".charAt(0)) {
				count++;
			} else {
				if (count != 0) {
					output.append(count);
					output.append(currentState.charAt(i));
					count = 0;
				} else {
					output.append(currentState.charAt(i));
				}
			}
		}

		// Remove the last "/"
		// String result = output.substring(0, output.toString().length() - 1);
		// System.out.println("This is the transformed state: " + result);
		return output.substring(0, output.toString().length() - 1);

	}

	///////////////////////// HELPER FUNCTIONS //////////////////////

	// A function to replace integers in the string with the
	// according number of "x"
	public String formatString(String b) {

		String f, f1, f2, f3, f4, f5, f6, f7, f8 = "";

		f = b.replaceAll("1", "x");
		f1 = f.replaceAll("2", "xx");
		f2 = f1.replaceAll("3", "xxx");
		f3 = f2.replaceAll("4", "xxxx");
		f4 = f3.replaceAll("5", "xxxxx");
		f5 = f4.replaceAll("6", "xxxxxx");
		f6 = f5.replaceAll("7", "xxxxxxx");
		f7 = f6.replaceAll("8", "xxxxxxxx");
		f8 = f7.replaceAll("9", "xxxxxxxxx");
		return f8;
	}

	// A function to count the "x" in the string
	public String countNull(String str) {
		if (str.contains("xxxxxxxxx")) {
			str = str.replaceAll("xxxxxxxxx", "9");
		}
		if (str.contains("xxxxxxxx")) {
			str = str.replaceAll("xxxxxxxx", "8");
		}
		if (str.contains("xxxxxxx")) {
			str = str.replaceAll("xxxxxxx", "7");
		}
		if (str.contains("xxxxxx")) {
			str = str.replaceAll("xxxxxx", "6");
		}
		if (str.contains("xxxxx")) {
			str = str.replaceAll("xxxxx", "5");
		}
		if (str.contains("xxxx")) {
			str = str.replaceAll("xxxx", "4");
		}
		if (str.contains("xxx")) {
			str = str.replaceAll("xxx", "3");
		}
		if (str.contains("xx")) {
			str = str.replaceAll("xx", "2");
		}
		if (str.contains("x")) {
			str = str.replaceAll("x", "1");
		}
		return str;
	}

	public ArrayList<String> getList(String name) {

		ArrayList<String> generalListB = new ArrayList<String>();
		ArrayList<String> generalListR = new ArrayList<String>();
		ArrayList<String> advisorListB = new ArrayList<String>();
		ArrayList<String> advisorListR = new ArrayList<String>();
		ArrayList<String> horseListB = new ArrayList<String>();
		ArrayList<String> horseListR = new ArrayList<String>();
		ArrayList<String> elephantListB = new ArrayList<String>();
		ArrayList<String> elephantListR = new ArrayList<String>();
		ArrayList<String> rockListB = new ArrayList<String>();
		ArrayList<String> rockListR = new ArrayList<String>();
		ArrayList<String> soldierListB = new ArrayList<String>();
		ArrayList<String> soldierListR = new ArrayList<String>();
		ArrayList<String> cannonListB = new ArrayList<String>();
		ArrayList<String> cannonListR = new ArrayList<String>();

		switch (name) {
		case "g":
			return generalListB;
		case "G":
			return generalListR;
		case "a":
			return advisorListB;
		case "A":
			return advisorListR;
		case "c":
			return cannonListB;
		case "C":
			return cannonListR;
		case "h":
			return horseListB;
		case "H":
			return horseListR;
		case "e":
			return elephantListB;
		case "E":
			return elephantListR;
		case "s":
			return soldierListB;
		case "S":
			return soldierListR;
		case "r":
			return rockListB;
		case "R":
			return rockListR;

		}

		return null;

	}

	public HashMap<String, ArrayList<String>> getValideMoves(String moveString, Figure[][] arr, String state,
			Player player, XiangqiGame xg) {
		HashMap<String, ArrayList<String>> allowedMoves = new HashMap<String, ArrayList<String>>();
		String move = "";
		// a0-b0 i==y y Achse, j==x x Achse

		int xold = moveString.charAt(0) - 97;
		int yold = 9 - Character.getNumericValue(moveString.charAt(1));

		for (int i = 0; i < 10; i++) {

			for (int j = 0; j < 9; j++) {
				System.out.println(xg.board.arr[yold][xold]);
				move = "";
				if (xg.board.arr[yold][xold] == null) {

					move = move.concat(Character.toString(moveString.charAt(0)).concat(String.valueOf(yold)))
							.concat("-").concat(Character.toString((char) (i + 97))).concat(String.valueOf(j));
					System.out.println(move);
					if (xg.board.arr[yold][xold].checkMove(move, player, arr, xg)) {

						System.out.println(xg.board.arr[yold][xold] + " " + (char) xg.board.arr[yold][xold].position.getX() + " "
								+ (9 - xg.board.arr[yold][xold].position.getY()));

						ArrayList<String> l = getList(xg.board.arr[yold][xold].name);
						l.add(move);
						System.out.println(move);
						allowedMoves.put(xg.board.arr[yold][xold].name, l);
						System.out.println("hashmap  " + allowedMoves.keySet() + "  " + allowedMoves.values());

					}

				}
			}

		}

		return allowedMoves;

	}

	// A function to visualize the string state as an array
	public void parr(Figure[][] a2d) {
		for (int i = 0; i < 10; i++) {
			System.out.println();
			for (int j = 0; j < 9; j++) {
				if (a2d[i][j] == null) {
					System.out.print("[-]");
					continue;
				}
				System.out.print(a2d[i][j]);
			}
		}
	}

	public static void main(String[] args) {
		XiangqiGame xg2 = new XiangqiGame();
		// ParsingString ps = new ParsingString();
		System.out.println(xg2.getBoard());
		String moveString = "e1-d0";
		System.out.println();
		System.out.println("State bevor: " + xg2.getBoard());
		System.out.println();
		System.out.println("State after: " + xg2.updatedState(moveString));
		System.out.println(Character.toString((char) (0 + 97)));
	}

}