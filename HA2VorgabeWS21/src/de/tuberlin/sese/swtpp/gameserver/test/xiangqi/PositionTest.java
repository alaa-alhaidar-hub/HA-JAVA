package de.tuberlin.sese.swtpp.gameserver.test.xiangqi;

import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.Position;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PositionTest {
    private Position position = null;
    private Position deltaPosition = null;
    private Position offsetPosition = null;


    @After
    public void teardown() {
        this.position = null;
        this.deltaPosition = null;
        this.offsetPosition = null;
    }

    @Test
    public void testTopLeftCorner() {
        givenPosition("a9");
        expectPositionToBe(new Position(0, 0));
    }

    @Test
    public void testTopRightCorner() {
        givenPosition("i9");
        expectPositionToBe(
                new Position(8, 0)
        );
    }

    @Test
    public void testBottomLefCorner() {
        givenPosition("a0");
        expectPositionToBe(
                new Position(0, 9)
        );
    }

    @Test
    public void testBottomRight() {
        givenPosition("i0");
        expectPositionToBe(new Position(8, 9));
    }

    @Test
    public void testBottomLeftRedAdvisor() {
        givenPosition("d0");
        expectPositionToBe(new Position(3, 9));
    }

    @Test
    public void testCenterPalaceRed() {
        givenPosition("e1");
        expectPositionToBe(new Position(4, 8));
    }

    @Test
    public void testE2() {
        givenPosition("e2");
        expectPositionToBe(new Position(4, 7));
    }

    @Test
    public void testD1() {
        givenPosition("d1");
        expectPositionToBe(new Position(3, 8));
    }

    @Test
    public void testC3() {
        givenPosition("c3");
        expectPositionToBe(new Position(2, 6));
    }

    @Test
    public void testE3() {
        givenPosition("e3");
        expectPositionToBe(new Position(4, 6));
    }


    @Test
    public void testPositionDelta() {
        givenPosition(3, 6);
        whenComputingDeltaTo(0, 9);
        expectDeltaToBe(new Position(-3, 3));
    }

    @Test
    public void testPositionDeltaD1E3() {
        givenPosition(3, 8);
        whenComputingDeltaTo(4, 6);
        expectDeltaToBe(new Position(1, -2));
    }

    @Test
    public void testPositionDeltaBlackElephantC9E7() {
        givenPosition("c9");
        whenComputingDeltaTo(4,2);
        expectDeltaToBe(new Position(2, 2));
    }

    @Test
    public void deltaPositionRedAdvisorBottomLeftToPalaceCenter() {
        givenPosition(3, 9);
        whenComputingDeltaTo(4, 8);
        expectDeltaToBe(new Position(1, -1));
    }

    @Test
    public void testPositionOffset() {
        givenPosition(3, 6);
        givenDelta(2, 2);
        whenComputingOffsetWithDelta();
        expectOffsetPositionToBe(new Position(5, 8));
    }

    @Test
    public void testPositionToString() {
        givenPosition(9, 8);
        expectRepresentationToBe("i0");
    }

    private void givenPosition(String positionRepresentation) {
        this.position = new Position(positionRepresentation);
    }

    private void givenPosition(int x, int y) {
        this.position = new Position(x, y);
    }

    private void givenDelta(int x, int y) {
        this.deltaPosition = new Position(x, y);
    }

    private void whenComputingDeltaTo(int x, int y) {
        Position targetPosition = new Position(x, y);
        this.deltaPosition = this.position.deltaTo(targetPosition);
    }

    private void whenComputingOffsetWithDelta() {
        this.offsetPosition = this.position.offsetWith(this.deltaPosition);
    }

    private void expectPositionToBe(Position expectedPosition) {
        PositionTest.assertExpectedPositionEqualsActual(expectedPosition, this.position);
    }

    private void expectDeltaToBe(Position expectedDelta) {
        PositionTest.assertExpectedPositionEqualsActual(expectedDelta, this.deltaPosition);
    }

    private void expectOffsetPositionToBe(Position expectedOffsetPosition) {
        PositionTest.assertExpectedPositionEqualsActual(expectedOffsetPosition, this.offsetPosition);
    }

    private void expectRepresentationToBe(String positionRepresentation) {
        assertEquals(positionRepresentation, this.position.toString());
    }

    private static void assertExpectedPositionEqualsActual(Position expected, Position actual) {
        assertEquals(expected.x, actual.x);
        assertEquals(expected.y, actual.y);
    }
}
