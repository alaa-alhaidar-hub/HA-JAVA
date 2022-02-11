package de.tuberlin.sese.swtpp.gameserver.test.xiangqi;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.tuberlin.sese.swtpp.gameserver.control.GameController;
import de.tuberlin.sese.swtpp.gameserver.model.Game;
import de.tuberlin.sese.swtpp.gameserver.model.Player;
import de.tuberlin.sese.swtpp.gameserver.model.User;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.ParsingString;

public class TryMoveIntegrationTest {


	User user1 = new User("Alice", "alice");
	User user2 = new User("Bob", "bob");
	
	Player redPlayer = null;
	Player blackPlayer = null;
	Game game = null;
	GameController controller;
	
	final String state1 = "rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEH1";
	final String state2 = "";
	ParsingString board = new ParsingString();
	
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
	
	@Test
	public void exampleTest() {
	    startGame("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR",true);
	    assertMove("e3-e4",true,true);
	    assertGameState("rheagaehr/9/1c5c1/s1s1s1s1s/9/4S4/S1S3S1S/1C5C1/9/RHEAGAEHR",false,false,false);
	}
	
	@Test
	public void redCannonTest() {
		startGame("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", true);
	    assertMove("b2-b4", true, true);
	    assertGameState("rheagaehr/9/1c5c1/s1s1s1s1s/9/1C7/S1S1S1S1S/7C1/9/RHEAGAEHR", false, false, false);
	}
	
	@Test
	public void blackCannonTest() {
		startGame("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", false);
	    assertMove("b7-d7", false, true);
	    assertGameState("rheagaehr/9/3c3c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", true, false, false);
	}
	
	@Test
	public void advisorTest1() {
		System.out.println("advisorTest1");
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("e1-f2", true, true);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R4A3/9/1HE1GAEHR", false, false, false);
	}
	
	@Test
	public void advisorTest2() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("e1-d2", true, true);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R2A5/9/1HE1GAEHR", false, false, false);
	}
	
	@Test
	public void advisorTest3() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("f0-f1", true, false);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true, false, false);
	}
	
	public void exampleTest2() {

		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("e0-d0", true, true);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HEG1AEHR", false, false, false);
	}
	
	@Test
	public void advisorTest4() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("c3-c4", true, true);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/2S6/S5SCS/R8/4A4/1HE1GAEHR", false, false, false);
	}
	
	@Test
	public void canonTest1() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("h3-h4", true, true);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/7C1/S1S3S1S/R8/4A4/1HE1GAEHR", false, false, false);
	}

	@Test
	public void exampleTest3() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("e1-d0", true, true);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/9/1HEAGAEHR", false, false, false);
	}
	
	@Test
	public void elephantTest1() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("g0-i2", true, true);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R7E/4A4/1HE1GA1HR", false, false, false);
	}
	
	@Test
	public void elephantTest2() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", false);
		assertMove("c9-e7", false, true);
		assertGameState("rh1a1a1h1/4g4/1c2er3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true, false, false);
	}
	
	@Test
	public void generalTest2() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("d0-e0", true, false);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true, false, false);
	}
	
	@Test
	public void horseTest1() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("b9-d7", true, false);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true, false, false);
	}

	
	@Test
	public void generalTest1() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", false);
		assertMove("e8-e9", true, true);
		assertGameState("rheaga1h1/9/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true, false, false);
	}

	@Test
	public void generalTest3() {
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("e8-d8", true, false);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true, false, false);
	}
	
	@Test
	public void rockTest1() {
		System.out.println("rockTest1");
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", false);
		assertMove("a9-a7", false, true);
		assertGameState("1hea1a1h1/4g4/rc3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true, false, false);
	}
	
	@Test
	public void rockTest2() {
		System.out.println("rockTest1");
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("a2-a1", true, true);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/9/R3A4/1HE1GAEHR", false, false, false);
	}
	
	@Test
	public void rockTest3() {
		System.out.println("rockTest1");
		startGame("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/R8/4A4/1HE1GAEHR", true);
		assertMove("a2-f2", true, true);
		assertGameState("rhea1a1h1/4g4/1c3r3/7cs/s1s1C4/9/S1S3SCS/5R3/4A4/1HE1GAEHR", false, false, false);
	}
	
	// Red General cannot move because there is no other figure between it and the black one
	@Test
	public void redGeneralTest4() {
		startGame("4g4/9/9/9/9/9/9/9/9/3G4H", true);
		assertMove("d0-e0", true, false);
		assertGameState("4g4/9/9/9/9/9/9/9/9/3G4H", true, false, false);
	}
	
	// Red General can move because there is a figure between it and the black General
	@Test
	public void redGeneralTest5() {
		startGame("4g4/9/9/9/9/9/9/9/4C4/3G4H", true);
		assertMove("d0-e0", true, true);
		assertGameState("4g4/9/9/9/9/9/9/9/4C4/4G3H", false, false, false);
	}
	
	@Test
	public void boardTest() {
		startGame("4g4/9/9/9/9/9/9/9/4C4/4G4", true);
		assertMove("e1-e2", true, true);
		assertGameState("4g4/9/9/9/9/9/9/4C4/9/4G4", false, false, false);
	}
	
	@Test
	public void boardTest1() {
		startGame("4g4/9/9/9/9/9/9/9/4C4/4G2H1", true);
		assertMove("e4-e2", true, false);
		assertGameState("4g4/9/9/9/9/9/9/9/4C4/4G2H1", true, false, false);
	}
	
	@Test
	public void testRepresentation1() {
		board.create2D("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEH1");
		assertEquals(state1, board.createBoardStringFrom2D());
	}
	
	
	
	

	//TODO: implement test cases of same kind as example here

}
