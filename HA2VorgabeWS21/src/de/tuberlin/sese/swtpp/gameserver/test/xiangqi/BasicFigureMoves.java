package de.tuberlin.sese.swtpp.gameserver.test.xiangqi;

import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.Board;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.Position;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.figure.IFigure;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


import java.util.function.Predicate;

public class BasicFigureMoves {
	Board board = new Board();

	@Test
    public void figureAtPositionIsRedAdvisor() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/9/9/9/9/9/9/3A5");
        String moveString = "d0-e1";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        // Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redAdvisor = board.figureAt(startPosition);
        assertEquals("A", redAdvisor.getCharacterRepresentation());
    }

	@Test
    public void redAdvisorMovesFromBottomLeftToPalaceCenter() {
	    board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/9/9/9/9/9/9/3A5");
        String moveString = "d0-e1";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redAdvisor = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForRedAdvisor = redAdvisor.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveForRedAdvisor);
    }

    @Test
    public void redAdvisorMovesFromBottomRightToPalaceCenter() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/9/9/9/9/9/9/5A3");
        String moveString = "f0-e1";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redAdvisor = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForRedAdvisor = redAdvisor.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveForRedAdvisor);
    }

    @Test
    public void redAdvisorMovesFromUpperLeftToPalaceCenter() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/9/9/9/9/3A5/9/9");
        String moveString = "d2-e1";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redAdvisor = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForRedAdvisor = redAdvisor.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveForRedAdvisor);
    }

    @Test
    public void redAdvisorMovesFromUpperRightToPalaceCenter() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/9/9/9/9/5A3/9/9");
        String moveString = "f2-e1";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redAdvisor = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForRedAdvisor = redAdvisor.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveForRedAdvisor);
    }

    @Test
    public void blackAdvisorMovesFromUpperLeftToPalaceCenter() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("3a5/9/9/9/9/9/9/9/9/9");
        String moveString = "d9-e8";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure blackAdvisor = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForBlackAdvisor = blackAdvisor.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveForBlackAdvisor);
    }

    @Test
    public void blackAdvisorMovesFromUpperRightToPalaceCenter() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("5a3/9/9/9/9/9/9/9/9/9");
        String moveString = "f9-e8";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure blackAdvisor = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForBlackAdvisor = blackAdvisor.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveForBlackAdvisor);
    }

    @Test
    public void blackAdvisorMovesFromBottomLeftToPalaceCenter() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/3a5/9/9/9/9/9/9/9");
        String moveString = "d7-e8";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure blackAdvisor = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForBlackAdvisor = blackAdvisor.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveForBlackAdvisor);
    }

    @Test
    public void blackAdvisorMovesFromBottomRightToPalaceCenter() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/5a3/9/9/9/9/9/9/9");
        String moveString = "f7-e8";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure blackAdvisor = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForBlackAdvisor = blackAdvisor.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveForBlackAdvisor);
    }

    @Test
    public void redSoldierHasNotCrossedRiver() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/9/9/9/9/2S6/9/9");
        String moveString = "c2-c3";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redSoldier = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForRedSoldier = redSoldier.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveForRedSoldier);
    }

    @Test
    public void redSoldierMovesBackwards() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/9/9/9/9/2S6/9/9");
        String moveString = "c2-c1";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redSoldier = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForRedSoldier = redSoldier.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(false, isValidMoveForRedSoldier);
    }

    @Test
    public void redSoldierHasCrossedRiverAndMovesRight() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/2S6/9/9/9/9/9/9");
        String moveString = "c6-d6";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redSoldier = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForRedSoldier = redSoldier.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveForRedSoldier);
    }

    @Test
    public void redSoldierHasCrossedRiverAndMovesBackwards() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/2S6/9/9/9/9/9/9");
        String moveString = "c6-c5";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redSoldier = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForRedSoldier = redSoldier.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(false, isValidMoveForRedSoldier);
    }

    @Test
    public void blackSoldierHasNotCrossedRiver() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/2s6/9/9/9/9/9/9/9");
        String moveString = "c7-c6";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redSoldier = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForBlackSoldier = redSoldier.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveForBlackSoldier);
    }

    @Test
    public void blackSoldierHasNotCrossedRiverAndMovesRight() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/2s6/9/9/9/9/9/9/9");
        String moveString = "c7-d7";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redSoldier = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForBlackSoldier = redSoldier.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(false, isValidMoveForBlackSoldier);
    }

    @Test
    public void blackSoldierHasCrossedRiverAndMovesLeft() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/2s6/9/9/9/3s5/9/9/9");
        String moveString = "d3-c3";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redSoldier = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForBlackSoldier = redSoldier.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveForBlackSoldier);
    }

    @Test
    public void redGeneralMovesE0E1() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/9/9/9/9/9/9/4G4");
        String moveString = "e0-e1";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redGeneral = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForRedGeneral = redGeneral.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveForRedGeneral);
    }

    @Test
    public void blackGeneralMovesE9E8() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("4g4/9/9/9/9/9/9/9/9/9");
        String moveString = "e9-e8";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure blackGeneral = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForBlackGeneral = blackGeneral.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveForBlackGeneral);
    }

    @Test
    public void blackGeneralMovesE7F7() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/4g4/9/9/9/9/9/9/9");
        String moveString = "e7-f7";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure blackGeneral = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForBlackGeneral = blackGeneral.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveForBlackGeneral);
    }

    @Test
    public void blackGeneralMovesE7E6() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/4g4/9/9/9/9/9/9/9");
        String moveString = "e7-e6";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure blackGeneral = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForBlackGeneral = blackGeneral.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(false, isValidMoveForBlackGeneral);
    }

    @Test
    public void blackGeneralMovesE6E5() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/4g4/9/9/9/9/9/9");
        String moveString = "e6-e5";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure blackGeneral = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForBlackGeneral = blackGeneral.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(false, isValidMoveForBlackGeneral);
    }

    @Test
    public void redElephantMovesC0E2() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/9/9/9/9/9/9/2E6");
        String moveString = "c0-e2";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redElephant = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForRedElephant = redElephant.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveForRedElephant);
    }

    @Test
    public void redElephantMovesC0A2() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/9/9/9/9/9/9/2E6");
        String moveString = "c0-a2";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redElephant = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForRedElephant = redElephant.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveForRedElephant);
    }

    @Test
    public void blackElephantC9E7() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("2e6/9/9/9/9/9/9/9/9/9");
        String moveString = "c9-e7";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure blackElephant = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForBlackElephant = blackElephant.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveForBlackElephant);
    }

    @Test
    public void blackElephantHasCrossedRiverG5E3() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/9/6e2/9/9/9/9/9");
        String moveString = "g5-i3";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure blackElephant = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForBlackElephant = blackElephant.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(false, isValidMoveForBlackElephant);
    }

    @Test
    public void redElephantHasCrossedRiverD3F5() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/9/9/9/3e5/9/9/9");
        String moveString = "d3-f5";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redElephant = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveForRedElephant = redElephant.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(false, isValidMoveForRedElephant);
    }

    // Red Horse moves vertically up and then either diagonally left or right (all positions are free): d1-e3, d1-c3
    @Test
    public void redHorseD1C3() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/9/9/9/9/9/3H5/9");
        String moveString = "d1-c3";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redHorse = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveRedHorse = redHorse.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveRedHorse);
    }

    @Test
    public void redHorseD1E3() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/9/9/9/9/9/3H5/9");
        String moveString = "d1-e3";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redHorse = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveRedHorse = redHorse.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveRedHorse);
    }

    @Test
    public void blackHorse() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("1h7/9/9/9/9/9/9/9/9/9");
        String moveString = "b9-c7";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure blackHorse = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveBlackHorse = blackHorse.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveBlackHorse);
    }

    // Black horse moving first horizontally right and then diagonally up
    @Test
    public void blackHorseC7E8() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/2h6/9/9/9/9/9/9/9");
        String moveString = "c7-e8";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure blackHorse = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveBlackHorse = blackHorse.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveBlackHorse);
    }

    // Black horse moving first horizontally right and then diagonally backwards to the left (should not be possible)
    @Test
    public void blackHorseC7C8() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/2h6/9/9/9/9/9/9/9");
        String moveString = "c7-c8";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure blackHorse = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveBlackHorse = blackHorse.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(false, isValidMoveBlackHorse);
    }

    // Red horse moving first vertically up and then diagonally up to the right corner:
    // the move should not be valid, because there is a figure on the way
    @Test
    public void redHorseC0B2() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/9/9/9/9/9/2c6/2H6");
        String moveString = "c0-b2";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redHorse = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveRedHorse = redHorse.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(false, isValidMoveRedHorse);
    }

    // Red Rock moving vertically up
    @Test
    public void redRockA0A5() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/9/9/9/9/9/9/R8");
        String moveString = "a0-a5";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redRock = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveRedRock = redRock.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveRedRock);
    }

    // Red Rook moving horizontally to the right
    @Test
    public void redRockB1G1() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/9/9/9/9/9/1R7/9");
        String moveString = "b1-g1";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redRock = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveRedRock = redRock.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveRedRock);
    }

    // Black rook moving vertically down
    @Test
    public void blackRockG8G4() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/6R2/9/9/9/9/9/9/9/9");
        String moveString = "g8-g4";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure blackRock = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveBlackRock = blackRock.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveBlackRock);
    }

    // Black rook moving vertically down, but there is a figure on the way
    @Test
    public void blackRockG8G4WithFigureOnTheWay() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/6R2/6C2/9/9/9/9/9/9/9");
        String moveString = "g8-g4";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure blackRock = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveBlackRock = blackRock.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(false, isValidMoveBlackRock);
    }

    // Cannon moves to an empty field
    @Test
    public void redCannonH0A0() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/9/9/9/9/9/9/7C1");
        String moveString = "h0-a0";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redCannon = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveRedCannon = redCannon.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveRedCannon);
    }

    // Red cannon attacks, but no figure on its way - should not be possible
    @Test
    public void redCannonAttacksH0A0() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/9/9/9/9/9/9/c6C1");
        String moveString = "h0-a0";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redCannon = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveRedCannon = redCannon.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(false, isValidMoveRedCannon);
    }

    // Red cannon attacks and has a screen - should be possible
    @Test
    public void redCannonAttacksWithScreenH0A0() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/9/9/9/9/9/9/c2C3C1");
        String moveString = "h0-a0";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redCannon = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveRedCannon = redCannon.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveRedCannon);
    }

    // Red cannon attacks but here are more than one figures on its way
    @Test
    public void redCannonAttacksMoreFiguresOnTheWayH0A0() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/9/9/9/9/9/9/9/cHEC3C1");
        String moveString = "h0-a0";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure redCannon = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveRedCannon = redCannon.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(false, isValidMoveRedCannon);
    }

    // Black cannon attacks and there is a screen
    @Test
    public void blackCannonAttackVerticallyB7B2() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/1c7/9/1S7/9/9/1C7/9/9");
        String moveString = "b7-b2";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure blackCannon = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveBlackCannon = blackCannon.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(true, isValidMoveBlackCannon);
    }

    // Black cannon attacks and there are is no screen
    @Test
    public void blackCannonAttackVerticallyNoScreenB7B2() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/1c7/9/9/9/9/1C7/9/9");
        String moveString = "b7-b2";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure blackCannon = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveBlackCannon = blackCannon.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(false, isValidMoveBlackCannon);
    }

    // Black cannon attacks and there is more than one figure on the way - should not be possible
    @Test
    public void blackCannonAttackVerticallyMoreFiguresOnTheWayB7B2() {
        board.setFiguresOnBoardAccordingToGivenStringRepresentation("9/9/1c7/1H7/9/1e7/9/1C7/9/9");
        String moveString = "b7-b2";
        String[] moveStringPositions = moveString.split("-");
        Position startPosition = new Position(moveStringPositions[0]);
        Position targetPosition = new Position(moveStringPositions[1]);
        Position deltaPosition = startPosition.deltaTo(targetPosition);
        IFigure blackCannon = board.figureAt(startPosition);
        Predicate<Position> isPositionFree = board.getIsFreePredicateRelativeTo(startPosition);
        boolean isValidMoveBlackCannon = blackCannon.isValidMoveDelta(deltaPosition, isPositionFree);
        assertEquals(false, isValidMoveBlackCannon);
    }










}
