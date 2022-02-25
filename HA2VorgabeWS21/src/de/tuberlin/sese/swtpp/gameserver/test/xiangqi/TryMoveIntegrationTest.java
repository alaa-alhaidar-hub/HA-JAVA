package de.tuberlin.sese.swtpp.gameserver.test.xiangqi;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.tuberlin.sese.swtpp.gameserver.control.GameController;
import de.tuberlin.sese.swtpp.gameserver.model.Game;
import de.tuberlin.sese.swtpp.gameserver.model.Player;
import de.tuberlin.sese.swtpp.gameserver.model.User;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.Board;

public class TryMoveIntegrationTest {


	User user1 = new User("Alice", "alice");
	User user2 = new User("Bob", "bob");
	
	Player redPlayer = null;
	Player blackPlayer = null;
	Game game = null;
	GameController controller;
	
	final String state1 = "rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEH1";
	final String state2 = "";
	Board board = new Board();
	
	@Before
	public void setUp() throws Exception {
		controller = GameController.getInstance();
		controller.clear();
		
		int gameID = controller.startGame(user1, "", "xiangqi");
		
		game =  controller.getGame(gameID);
		redPlayer = game.getPlayer(user1);

	}
	
	public void startGame() {
		controller.joinGame(user2, "xiangqi");		
		blackPlayer = game.getPlayer(user2);
	}
	
	public void startGame(String initialBoard, boolean redNext) {
		startGame();
		
		game.setBoard(initialBoard);
		game.setNextPlayer(redNext? redPlayer:blackPlayer);
	}
	
	public void assertMove(String move, boolean red, boolean expectedResult) {
		if (red)
			assertEquals(expectedResult, game.tryMove(move, redPlayer));
		else 
			assertEquals(expectedResult,game.tryMove(move, blackPlayer));
	}
	
	public void assertGameState(String expectedBoard, boolean redNext, boolean finished, boolean redWon) {
		assertEquals(expectedBoard,game.getBoard());
		assertEquals(finished, game.isFinished());

		if (!game.isFinished()) {
			assertEquals(redNext, game.getNextPlayer() == redPlayer);
		} else {
			assertEquals(redWon, redPlayer.isWinner());
			assertEquals(!redWon, blackPlayer.isWinner());
		}
	}
	

	/*******************************************
	 * !!!!!!!!! To be implemented !!!!!!!!!!!!
	 *******************************************/
	//TODO: implement test cases of same kind as example here

	///////////////////////////////////////////////////////////////////////////
	// GENERAL TESTS

	// Red General can make a horizontal step to the left.
	@Test
	public void redGeneralMakesHorizontalStep() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("e0-d0", true, true);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HEG1AEHR", false, false, false);
	}

	// Black General can move one vertical step back.
	@Test
	public void blackGeneralMakesVerticalStepBack() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", false);
		assertMove("e8-e9", false, true);
		assertGameState("rheaga1h1/9/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true, false, false);
	}

	// Red General can move because there is a figure between it and the Black General - no direct stare.
	@Test
	public void redGeneralMovesToSameColumnAsBlackNoDirectStare() {
		startGame("4g4/9/9/9/9/9/9/9/4C4/3G4H", true);
		assertMove("d0-e0", true, true);
		assertGameState("4g4/9/9/9/9/9/9/9/4C4/4G3H", false, false, false);
	}

	// Moving the Black General in the same column as the red one without figures between
	// the two should not be possible - direct stare.
	@Test
	public void blackGeneralMovesToSameColumnAsRedNoFigureBetween() {
		startGame("3Rg4/9/9/9/9/9/9/9/9/3G5", false);
		assertMove("e9-d9", false, false);
		assertGameState("3Rg4/9/9/9/9/9/9/9/9/3G5", false, false, false);
	}

	// Moving Red General in the same column as the Black General is not possible - direct stare.
	@Test
	public void redGeneralMovesToSameColumnAsBlackNoFigureBetweenA() {
		startGame("rhea1a1h1/3g5/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("e0-d0", true, false);
		assertGameState("rhea1a1h1/3g5/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true, false, false);
	}

	// Red General cannot move because there is no other figure between it and the black one.
	@Test
	public void redGeneralMovesToSameColumnAsBlackNoFigureBetweenB() {
		startGame("4g4/9/9/9/9/9/9/9/9/3G4H", true);
		assertMove("d0-e0", true, false);
		assertGameState("4g4/9/9/9/9/9/9/9/9/3G4H", true, false, false);
	}

	// Red General cannot move because there is no other figure between it and the black one.
	@Test
	public void redGeneralMovesToSameColumnAsBlackNoFigureBetweenC() {
		startGame("9/3g5/9/9/9/9/9/9/9/1HE1GAEHR", true);
		assertMove("e0-d0", true, false);
		assertGameState("9/3g5/9/9/9/9/9/9/9/1HE1GAEHR", true, false, false);
	}

	// Red General makes one horizontal step to the right and can take the opponent's figure - Black Horse.
	@Test
	public void redGeneralTakesBlackHorse() {
		startGame("3g5/3a5/9/9/9/9/9/3S5/9/4Gh3", true);
		assertMove("e0-f0", true, true);
		assertGameState("3g5/3a5/9/9/9/9/9/3S5/9/5G3", false, false, false);
	}

	// Red general makes one horizontal step to the left - it is allowed because the Generals are not facing each other
	// and the General is not in check.
	@Test
	public void redGeneralMakeOneHorizontalStepLeftNoDirectStare() {
		startGame("3g5/3a5/9/9/9/9/9/3S5/9/4Gh3", true);
		assertMove("e0-d0", true, true);
		assertGameState("3g5/3a5/9/9/9/9/9/3S5/9/3G1h3", false, false, false);
	}

	// Red General tries to make one diagonal step, but this move is not allowed for him.
	@Test
	public void redGeneralCannotMakeOneDiagonalStep() {
		startGame("3g5/3a5/9/9/9/9/9/3S5/9/4Gh3", true);
		assertMove("e0-f1", true, false);
		assertGameState("3g5/3a5/9/9/9/9/9/3S5/9/4Gh3", true, false, false);
	}

	// Black General tries to make one horizontal step to the right, but this would place him in the sight of the
	// Red General, so the move should not be allowed.
	@Test
	public void blackGeneralTriesToMoveOneHorizontallyToTheRight() {
		startGame("3g5/3a5/9/9/9/9/9/3S5/9/4Gh3", false);
		assertMove("d9-e9", false, false);
		assertGameState("3g5/3a5/9/9/9/9/9/3S5/9/4Gh3", false, false, false);
	}

	// The Black General moves one step horizontally to the right and takes the Red Cannon.
	@Test
	public void blackGeneralTakesRedCannon() {
		startGame("2egC1c2/4HcH2/4e4/1h2s4/9/s1E6/4S4/4E4/9/4G4", false);
		assertMove("d9-e9", false, true);
		assertGameState("2e1g1c2/4HcH2/4e4/1h2s4/9/s1E6/4S4/4E4/9/4G4", true, false, false);
	}

	// Red General cannot leave the Red Palace.
	@Test
	public void redGeneralCannotLeavePalace() {
		startGame("2egC1c2/4HcH2/4e4/1h2s4/9/s1E6/4S4/4E4/9/4G4", true);
		assertMove("e0-g0", true, false);
		assertGameState("2egC1c2/4HcH2/4e4/1h2s4/9/s1E6/4S4/4E4/9/4G4", true, false, false);
	}

	///////////////////////////////////////////////////////////////////////////
	// ADVISOR TESTS

	// Red Advisor can move diagonally from the Palace center up to the right corner of the Palace.
	@Test
	public void redAdvisorDiagonallyUpFromPalaceCenterToTheRight() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("e1-f2", true, true);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R4A3/9/1HE1GAEHR", false, false, false);
	}

	// Red Advisor can move diagonally from the Palace center up to the left corner of the Palace.
	@Test
	public void redAdvisorDiagonallyUpFromCenterToTheLeft() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("e1-d2", true, true);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R2A5/9/1HE1GAEHR", false, false, false);
	}

	// Red Advisor cannot make a vertical step up - only diagonal steps are allowed.
	@Test
	public void redAdvisorCannotMakeOneVerticalStepUp() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("f0-f1", true, false);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true, false, false);
	}

	// Red Advisor can make a diagonal step from the Palace center to the lower left corner of the Palace.
	@Test
	public void redAdvisorMakesOneDiagonalStepFromPalaceCenterToLowerLeftCorner() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("e1-d0", true, true);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/9/1HEAGAEHR", false, false, false);
	}

	// Red Advisor can make a diagonal step from the Palace center to the lower right corner of the Palace.
	@Test
	public void redAdvisorMakesOneDiagonalStepFromPalaceCenterToLowerRightCorner() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1G1EHR", true);
		assertMove("e1-f0", true, true);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/9/1HE1GAEHR", false, false, false);
	}

	@Test
	public void redAdvisorMakesOneDiagonalStepFromLowerRightCornerToPalaceCenter() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/9/1HE1GAEHR", true);
		assertMove("f0-e1", true, true);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1G1EHR", false, false, false);
	}

	// Red Advisor cannot cross the river.
	@Test
	public void redAdvisorIsCrossingTheRiver() {
		startGame("3g5/3a5/9/9/9/9/9/4A4/3S5/3G5", true);
		assertMove("e2-b5", true, false);
		assertGameState("3g5/3a5/9/9/9/9/9/4A4/3S5/3G5", true, false, false);
	}

	// Placing the Black Advisor outside of the palace is not possible.
	@Test
	public void placingBlackAdvisorOutsidePalace() {
		startGame("3g5/9/9/3a5/9/9/9/4A4/3S5/3G5", false);
		assertMove("d6-e5", false, false);
		assertGameState("3g5/9/9/3a5/9/9/9/4A4/3S5/3G5", false, false, false);
	}

	// Red Advisor cannot cross the river!
	// Same explanation as below.
	@Test
	public void redAdvisorCannotCrossTheRiver() {
		startGame("3AG4/9/9/9/9/9/9/9/9/3ga4", true);
		assertMove("d9-d3", true, false);
		assertGameState("3AG4/9/9/9/9/9/9/9/9/3ga4", true, false, false);
	}

	// Red Advisor cannot go outside of the palace.
	// It is not a valid situation in a real game I think, it is not finished, because both player can infinitelly move
	// the figures back and forth without anything happening in the game.
	@Test
	public void redAdvisorCannotGoOutsideThePalaceA() {
		startGame("3ag4/9/9/9/9/9/9/9/9/3GA4", true);
		assertMove("d9-g9", true, false);
		assertGameState("3ag4/9/9/9/9/9/9/9/9/3GA4", true, false, false);
	}

	// Red General is in danger from the Black Soldier, but red player is on turn and moves the Red Advisor to
	// save the Red General.
	@Test
	public void redAdvisorSavesTheGeneral() {
		startGame("2eag4/4ah3/2r1e1ch1/4s3C/s1s2r2S/3R2H2/S1c1s4/E1H1E4/1C2s3R/3AG4", true);
		assertMove("d0-e1", true, true);
		assertGameState("2eag4/4ah3/2r1e1ch1/4s3C/s1s2r2S/3R2H2/S1c1s4/E1H1E4/1C2A3R/4G4", false, false, false);
	}

	///////////////////////////////////////////////////////////////////////////
	// ELEPHANT TESTS

	// Red Elephant is moving diagonally up.
	@Test
	public void redElephantDiagonallyUp() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("g0-i2", true, true);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R7E/4A4/1HE1GA1HR", false, false, false);
	}

	// Black Elephant is moving diagonally down from starting position, but now there it is the exactly one figure between
	// the Red Cannon and the Black General, so on the next turn the Red Cannon can check the Black General.
	@Test
	public void blackElephantDiagonallyDownFromStartAndBlackGeneralLeftInCheck() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", false);
		assertMove("c9-e7", false, false);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", false, false, false);
	}

	// The Black Elephant cannot to cross the river.
	@Test
	public void blackElephantTriesToCrossTheRiver() {
		startGame("3g5/3a5/9/2e6/9/9/9/4A4/3S5/3G5", false);
		assertMove("c6-e4", false, false);
		assertGameState("3g5/3a5/9/2e6/9/9/9/4A4/3S5/3G5", false, false, false);
	}

	// The Black Elephant cannot be put across the river.
	@Test
	public void blackElephantCannotBePutAcrossTheRive() {
		startGame("3g5/3a5/9/9/9/9/9/4A4/3S5/3Ge4", false);
		assertMove("f2-d4", false, false);
		assertGameState("3g5/3a5/9/9/9/9/9/4A4/3S5/3Ge4", false, false, false);
	}

	// The Red Elephant cannot be put across the river.
	@Test
	public void redElephantCannotBePutAcrossTheRive() {
		startGame("3gE4/3a5/9/9/9/9/9/4A4/3S5/3G5", true);
		assertMove("e9-c7", true, false);
		assertGameState("3gE4/3a5/9/9/9/9/9/4A4/3S5/3G5", true, false, false);
	}

	// The Red Elephant is trying to cross the river, but this move is not allowed.
	@Test
	public void redElephantTriesToCrossTheRiver() {
		startGame("3g5/3a5/9/2e6/9/6E2/9/4A4/3S5/3G5", true);
		assertMove("g4-e6", true, false);
		assertGameState("3g5/3a5/9/2e6/9/6E2/9/4A4/3S5/3G5", true, false, false);
	}

	// Red Elephant cannot make vertical steps.
	@Test
	public void redElephantCannotMoveTwoVerticalStepsUp() {
		startGame("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", true);
		assertMove("c0-c2", true, false);
		assertGameState("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", true, false, false);
	}

	// Black Elephant cannot make horizontal steps.
	@Test
	public void blackElephantCannotMoveTwoHorizontalStepsToTheRight() {
		startGame("rhe5g/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", false);
		assertMove("c9-e9", false, false);
		assertGameState("rhe5g/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", false, false, false);
	}

	// Black Elephant moves and captures the Red Cannon on position e7.
	@Test
	public void blackElephantCapturesRedCannon() {
		startGame("rhe5g/9/1c2Cc3/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", false);
		assertMove("c9-e7", false, true);
		assertGameState("rh6g/9/1c2ec3/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", true, false, false);
	}

	///////////////////////////////////////////////////////////////////////////
	// HORSE TESTS

	// Black Horse should not be able to move with delta 2 for both x and y.
	@Test
	public void blackHorseB9D7() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("b9-d7", true, false);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true, false, false);
	}

	// Black Horse captures the Red Soldier.
	@Test
	public void blackHorseCapturesRedSoldier() {
		startGame("r1eag2hr/4a4/1c2e2c1/4s1C1s/shs6/6S2/S1S1S3S/2H1E2C1/4A4/R2AG1EHR", false);
		assertMove("b5-c3", false, true);
		assertGameState("r1eag2hr/4a4/1c2e2c1/4s1C1s/s1s6/6S2/S1h1S3S/2H1E2C1/4A4/R2AG1EHR", true, false, false);
	}

	// Red Horse captures the Black Rook.
	@Test
	public void redHorseCapturesBlackRock() {
		startGame("1reag4/4a4/4e1hc1/4s1r2/s1s5s/5HS2/S1h1S3S/2H1E2C1/1c2A4/1R1AG1E1R", true);
		assertMove("f4-g6", true, true);
		assertGameState("1reag4/4a4/4e1hc1/4s1H2/s1s5s/6S2/S1h1S3S/2H1E2C1/1c2A4/1R1AG1E1R", false, false, false);
	}

	// The Red Horse cannot jump over the Red Soldier.
	@Test
	public void redHorseTriesToMoveButHasFigureInFront() {
		startGame("3g5/3a5/9/2e6/9/6E2/5S3/4AH3/3S5/3G5", true);
		assertMove("f2-f4", true, false);
		assertGameState("3g5/3a5/9/2e6/9/6E2/5S3/4AH3/3S5/3G5", true, false, false);
	}

	// The Red Horse moves one step horizontally to the right, but then it cannot move one step down
	// diagonally to the left - invalid gamma.
	@Test
	public void redHorseMovesWithInvalidGamma() {
		startGame("3g5/3a5/9/2e6/9/6E2/5S3/4AH3/3S5/3G5", true);
		assertMove("f2-f1", true, false);
		assertGameState("3g5/3a5/9/2e6/9/6E2/5S3/4AH3/3S5/3G5", true, false, false);
	}

	///////////////////////////////////////////////////////////////////////////
	// ROOK TESTS

	// Black Rook moves two steps vertically down. Positions are empty, so it is a valid move.
	@Test
	public void blackRookMovesTwoStepsDown() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", false);
		assertMove("a9-a7", false, true);
		assertGameState("1hea1a1h1/4g4/rc3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true, false, false);
	}

	@Test
	public void blackRookMovesTwoStepsUp() {
		startGame("1hea1a1h1/4g4/rc3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", false);
		assertMove("a7-a9", false, true);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true, false, false);
	}

	// Red Rook moves one vertical step down. Position is empty, so it is a valid move.
	@Test
	public void redRookMovesOneStepDOwn() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("a2-a1", true, true);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/9/R3A4/1HE1GAEHR", false, false, false);
	}

	// Red Rook moves horizontally to the right. Positions are empty, so the move is valid.
	@Test
	public void redRookMovesHorizontallyToTheRight() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("a2-f2", true, true);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/5R3/4A4/1HE1GAEHR", false, false, false);
	}

	// Red Rook moves horizontally to the left. Positions are empty, so the move is valid.
	@Test
	public void redRookMovesWrong() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/5R3/4A4/1HE1GAEHR", true);
		assertMove("f2-g3", true, false);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/5R3/4A4/1HE1GAEHR", true, false, false);
	}

	// Black Rook cannot move, because positions between start and target are not empty.
	@Test
	public void blackRookMovesHorizontallyToTheRight() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", false);
		assertMove("a9-e9", false, false);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", false, false, false);
	}

	// Black Rook captures the Red Cannon.
	@Test
	public void blackRookCapturesRedCannon() {
		startGame("1reag4/4a4/4e1hc1/4s1C1r/s1s5s/5HS2/S1h1S3S/2H1E2C1/1c2A4/1R1AG1E1R", false);
		assertMove("i6-g6", false, true);
		assertGameState("1reag4/4a4/4e1hc1/4s1r2/s1s5s/5HS2/S1h1S3S/2H1E2C1/1c2A4/1R1AG1E1R", true, false, false);
	}

	// Red Rook cannot move, because there are figures on the positions between start and target.
	@Test
	public void blackRookCannotMoveVerticallyBecauseOtherFiguresOnItsWay() {
		startGame("1a7/9/9/9/1c7/1s7/9/9/9/1R7", true);
		assertMove("b0-b9", true, false);
		assertGameState("1a7/9/9/9/1c7/1s7/9/9/9/1R7", true, false, false);
	}

	// Red Rook cannot move, because there is only one figure between the start and target position.
	@Test
	public void blackRookCannotMoveAndAttackVerticallyBecauseOneFigureOnItsWay() {
		startGame("1a7/9/9/9/9/1s7/9/9/9/1R7", true);
		assertMove("b0-b9", true, false);
		assertGameState("1a7/9/9/9/9/1s7/9/9/9/1R7", true, false, false);
	}

	///////////////////////////////////////////////////////////////////////////
	// CANNON TESTS

	// Moving Red Cannon, which is protecting the general in the column, one up:
	// move should be valid since cannon is still between the two generals.
	@Test
	public void moveRedCannonBetweenTwoGenerals() {
		startGame("4g4/9/9/9/9/9/9/9/4C4/4G4", true);
		assertMove("e1-e2", true, true);
		assertGameState("4g4/9/9/9/9/9/9/4C4/9/4G4", false, false, false);
	}

	// Red Cannon can move two steps up, because the position is empty and there are no figures on its way.
	@Test
	public void redCannonMovesTwoStepsUp() {
		startGame("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", true);
		assertMove("b2-b4", true, true);
		assertGameState("rheagaehr/9/1c5c1/s1s1s1s1s/9/1C7/S1S1S1S1S/7C1/9/RHEAGAEHR", false, false, false);
	}

	// Black Cannon moves two steps horizontally to the right. The move is possible since the target is free and there are no figures on its way.
	@Test
	public void blackCannonMovesTwoStepsHorizontally() {
		startGame("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", false);
		assertMove("b7-d7", false, true);
		assertGameState("rheagaehr/9/3c3c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", true, false, false);
	}

	// Red Cannon moves one position vertically up.
	@Test
	public void redCannonMovesOneStepUp() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("h3-h4", true, true);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/7C1/S1S3S1S/R8/4A4/1HE1GAEHR", false, false, false);
	}

	// Red Cannon captures the Black Rock, because there is exactly one figure between them.
	@Test
	public void cannonMovesButAndThereIsOneFigure() {
		startGame("4gae2/r2h1C3/2cae1r2/s1s1s3s/4c1s2/9/S3S1S1h/H2GC4/4A4/R1E2AEHR", true);
		assertMove("f8-a8", true, true);
		assertGameState("4gae2/C2h5/2cae1r2/s1s1s3s/4c1s2/9/S3S1S1h/H2GC4/4A4/R1E2AEHR", false, false, false);
	}

	// Red Cannon moves (to an empty position) and has one figure on the path - move is not valid.
	@Test
	public void cannonTriesToMoveToAnEMptyPositionButAndThereIsOneFigureOnThePath() {
		startGame("4gae2/r2h1C3/2cae1r2/s1s1s3s/4c1s2/9/S3S1S1h/H2GC4/4A4/R1E2AEHR", true);
		assertMove("f8-b8", true, false);
		assertGameState("4gae2/r2h1C3/2cae1r2/s1s1s3s/4c1s2/9/S3S1S1h/H2GC4/4A4/R1E2AEHR", true, false, false);
	}

	// Red Cannon captures Black Soldier.
	@Test
	public void redCannonCapturesBlackSoldier() {
		startGame("r1eaga1hr/9/1c2e2c1/3Cs1s1s/shs6/6S2/S1S1S3S/2H1E2C1/9/R2AGAEHR", true);
		assertMove("d6-g6", true, true);
		assertGameState("r1eaga1hr/9/1c2e2c1/4s1C1s/shs6/6S2/S1S1S3S/2H1E2C1/9/R2AGAEHR", false, false, false);
	}

	// Black Cannon captures the Red Elephant.
	@Test
	public void blackCannonCapturesRedElephant() {
		startGame("1reag4/4a4/4e1h2/4s1H2/s1s5s/6S2/S1h1S3S/2H1EC1c1/4A4/1R1AG1ER1", false);
		assertMove("h2-e2", false, true);
		assertGameState("1reag4/4a4/4e1h2/4s1H2/s1s5s/6S2/S1h1S3S/2H1cC3/4A4/1R1AG1ER1", true, false, false);
	}

	// Red Cannon tries to capture the Black General, but it is not possible, because there are more than one
	// figures on the way.
	@Test
	public void redCannonTriesToCaptureButMoreFiguresOnTheWay() {
		startGame("3g5/3a5/9/3e5/9/3C5/5S3/4AH3/3S5/3G5", true);
		assertMove("d4-d9", true, false);
		assertGameState("3g5/3a5/9/3e5/9/3C5/5S3/4AH3/3S5/3G5", true, false, false);
	}

	///////////////////////////////////////////////////////////////////////////
	// SOLDIER TESTS

	// Moving the Red Soldier vertically one position up.
	@Test
	public void redSoldierOneUp() {
	    startGame("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR",true);
	    assertMove("e3-e4",true,true);
	    assertGameState("rheagaehr/9/1c5c1/s1s1s1s1s/9/4S4/S1S3S1S/1C5C1/9/RHEAGAEHR",false,false,false);
	}

	// Move the Red Soldier one up.
	@Test
	public void redSoldierVerticallyUp() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("c3-c4", true, true);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/2S6/S5SCS/R8/4A4/1HE1GAEHR", false, false, false);
	}

	// Red Soldier checks the Black General and the red player wins.
	@Test
	public void redSoldierChecksBlackRedWins() {
		startGame("9/4g4/4S4/9/9/9/9/9/9/4G4", true);
		assertMove("e7-e8", true, true);
		assertGameState("9/4S4/9/9/9/9/9/9/9/4G4", false, true, true);
	}

	// Red Soldier cannot move backwards (not in the direction of the Black Palace).
	@Test
	public void redSoldierTriesToMoveBackVertically() {
		startGame("3g5/3a5/9/3e5/9/3C5/5S3/4AH3/3S5/3G5", true);
		assertMove("f3-f2", true, false);
		assertGameState("3g5/3a5/9/3e5/9/3C5/5S3/4AH3/3S5/3G5", true, false, false);
	}

	// Red Soldier has crossed the river and can move horizontally to capture the Black Elephant.
	@Test
	public void redSoldierCapturesHorizontallyWhenRiverIsCrossed() {
		startGame("3g5/3a5/9/3eS4/9/3C5/9/4AH3/3S5/3G5", true);
		assertMove("e6-d6", true, true);
		assertGameState("3g5/3a5/9/3S5/9/3C5/9/4AH3/3S5/3G5", false, false, false);
	}

	// Black Soldier is at the end of the board and cannot move vertically back in the direction of his Palace.
	@Test
	public void blackSoldierTriesToMoveVerticallyWhenAtTheEndOfTheBoard() {
		startGame("3g5/3a5/9/3eS4/9/3C5/9/4AH3/3S5/3G2s2", false);
		assertMove("g0-g1", false, false);
		assertGameState("3g5/3a5/9/3eS4/9/3C5/9/4AH3/3S5/3G2s2", false, false, false);
	}

	// Red Soldier is at the upper end of the board and cannot move backwards.
	@Test
	public void redSoldierAtTheEndOfTheBoard() {
		startGame("3S5/9/9/9/9/9/9/9/9/9", true);
		assertMove("d9-d8", true, false);
		assertGameState("3S5/9/9/9/9/9/9/9/9/9", true, false, false);
	}

	// Red Soldier is at the upper end of the board and can capture the Black Advisor with a horizontal move to the right.
	@Test
	public void redSoldierAtTheEndOfTheBoardCapturesBlackAdvisor() {
		startGame("3Sa4/9/9/9/9/9/9/9/9/9", true);
		assertMove("d9-e9", true, true);
		assertGameState("4S4/9/9/9/9/9/9/9/9/9", false, true, true);
	}

	// Black Soldier is at the lower end of the board and can capture the Red Advisor with a horizontal move to the right.
	@Test
	public void blackSoldierAtTheEndOfTheBoardCapturesRedAdvisor() {
		startGame("9/9/9/9/9/9/9/9/9/3sA4", false);
		assertMove("d0-e0", false, true);
		assertGameState("9/9/9/9/9/9/9/9/9/4s4", true, true, false);
	}

	///////////////////////////////////////////////////////////////////////////
	// GAME SITUATIONS

	// Red Rock takes Black Advisor, on the next turn Black player still have valid moves? Weird situation.
	@Test
	public void GameSituation2a() {
		startGame("3ag4/9/9/9/9/9/9/3R5/9/3G5", true);
		assertMove("d2-d9", true, true);
		assertGameState("3Rg4/9/9/9/9/9/9/9/9/3G5", false, false, false);
	}

	// Black tries to escape
	@Test
	public void GameSituation2c() {
		startGame("3Rg4/9/9/9/9/9/9/9/9/3G5", false);
		assertMove("e9-e8", false, true);
		assertGameState("3R5/4g4/9/9/9/9/9/9/9/3G5", true, false, false);
	}

	// see blackSoldierAtTheEndOfTheBoardCapturesRedAdvisor()
	// this is indeed a valid board and everything
	// but the move here doesn't move!
	@Test
	public void everyFigureNeedsToMove() {
		startGame("9/9/9/9/9/9/9/9/9/3sA4", false);
		assertMove("d0-d0", false, false);
		assertGameState("9/9/9/9/9/9/9/9/9/3sA4", false, false, false);
	}

	// Black is in check by the Red Rock and Red General, Red player makes its turn and after that the Black player
	// does not have any valid moves, so the game ends and the Red player wins.
	@Test
	public void blackPlayerIsInCheckByRock() {
		startGame("3g5/9/9/9/9/R8/9/4G4/9/9", true);
		assertMove("a4-d4", true, true);
		assertGameState("3g5/9/9/9/9/3R5/9/4G4/9/9", false, true, true);
	}

	// Red Player is in check.
	@Test
	public void redPlayerHasNoMoreValidMoves() {
		startGame("2ea2C2/3ra1gH1/9/4s4/2e5s/6S2/s1s1SRh1S/5G3/9/3A5", false);
		assertMove("d8-d2", false, true);
		assertGameState("2ea2C2/4a1gH1/9/4s4/2e5s/6S2/s1s1SRh1S/3r1G3/9/3A5", true, true, false);
	}

	// Moving to a target position having a figure of the same color is not possible.
	@Test
	public void tryingToMoveToSameFigureColor() {
		startGame("3g5/3a5/9/9/9/9/9/4A4/3S5/3G5", true);
		assertMove("d0-d1", true, false);
		assertGameState("3g5/3a5/9/9/9/9/9/4A4/3S5/3G5", true, false, false);
	}

	// Not a valid move when trying to move from an empty position.
	@Test
	public void tryingToMoveFromAnEmptyPosition() {
		startGame("3Sa4/9/9/9/9/9/9/9/9/9", true);
		assertMove("d1-d2", true, false);
		assertGameState("3Sa4/9/9/9/9/9/9/9/9/9", true, false, false);
	}

	// Advisors and Generals cannot be placed outside the palace
	@Test
	public void advisorsAndGeneralsOutsidePalace() {
		startGame("AG7/9/9/9/9/9/9/9/9/ag7", true);
		assertMove("a9-a8", true, false);
		assertGameState("AG7/9/9/9/9/9/9/9/9/ag7", true, false, false);
	}

	// Encountering the character 'p' - should be transformed to "null" - not a figure, since this
	// character representation is not a valid one.
	@Test
	public void boardWithAnInvalidCharacter() {
		startGame("A1G6/9/9/1p7/9/9/9/9/9/ag7", true);
		assertMove("a9-a8", true, false);
		assertGameState("A1G6/9/9/9/9/9/9/9/9/ag7", true, false, false);
	}

	// moveString is not correct.
	@Test
	public void moveStringIsNotCorrect() {
		startGame("A1G6/9/9/9/9/9/9/9/9/ag7", true);
		assertMove("gibberish-gibberish", true, false);
		assertGameState("A1G6/9/9/9/9/9/9/9/9/ag7", true, false, false);
	}

	// Next player must be the Black player, but instead Red Player on turn - not valid.
	@Test
	public void wrongPlayerOnTurnRed() {
		startGame("3Rg4/9/9/9/9/9/9/9/9/3G5", false);
		assertMove("e9-f9", true, false);
		assertGameState("3Rg4/9/9/9/9/9/9/9/9/3G5", false, false, false);
	}

	// Black is on turn (should have valid moves, although it is not a valid game situation) - Black General cannot take
	// the Red Rook, because this would place him in "direct stare".
	@Test
	public void GameSituation2b() {
		startGame("3Rg4/9/9/9/9/9/9/9/9/3G5", false);
		assertMove("e9-d9", false, false);
		assertGameState("3Rg4/9/9/9/9/9/9/9/9/3G5", false, false, false);
	}

	// Red player tries to use a Black player's figure - not valid.
	@Test
	public void redPlayerTriesToMoveBlackPlayersFigure() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("a9-a8", true, false);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true, false, false);
	}

	// Next player on turn must be the Red Player, but it is Black and is also trying to move a red figure - not valid.
	@Test
	public void redPlayerOnTurnButBlackTriesToMoveAFigure() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("a9-a8", false, false);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true, false, false);
	}

	// Next player on turn must be the Red Player, but it is Black - not valid.
	@Test
	public void wrongPlayerOnTurnBlack() {
		startGame("3Rg4/9/9/9/9/9/9/9/9/3G5", true);
		assertMove("d9-d8", false, false);
		assertGameState("3Rg4/9/9/9/9/9/9/9/9/3G5", true, false, false);
	}

	// If Red General moves, the Black Soldier can take it - not a valid move!
	@Test
	public void redGeneralMovesIntoCheck() {
		startGame("9/3ga4/9/9/9/9/9/3s5/9/3G5", true);
		assertMove("d0-d1", true, false);
		assertGameState("9/3ga4/9/9/9/9/9/3s5/9/3G5", true, false, false);
	}

	// Start a regular game with the regular game initial state, make an invalid move. The state stays the same.
	@Test
	public void noStateGiven() {
		startGame();
		assertMove("n0-x1", true, false);
		assertGameState("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", true, false, false);
	}
	
}
