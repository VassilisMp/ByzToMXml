package Byzantine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ByzScaleTest {

    ByzScale disDiapaswn;

    @BeforeEach
    void beforeEach() {
        disDiapaswn = ByzScale.get2OctavesScale();
        Engine.initAccidentalCommas(disDiapaswn, ByzStep.DI);
    }

    @Test
    void AccidentalCommasDefault() {
        assertAll(
                () -> assertEquals( 0, disDiapaswn.get(0).getAccidentalCommas()), // DI
                () -> assertEquals( 0, disDiapaswn.get(1).getAccidentalCommas()), // KE
                () -> assertEquals( 4, disDiapaswn.get(2).getAccidentalCommas()), // ZW
                () -> assertEquals( 0, disDiapaswn.get(3).getAccidentalCommas()), // NH
                () -> assertEquals( 0, disDiapaswn.get(4).getAccidentalCommas()), // PA
                () -> assertEquals(-1, disDiapaswn.get(5).getAccidentalCommas()), // BOY
                () -> assertEquals( 0, disDiapaswn.get(6).getAccidentalCommas()), // GA
                () -> assertEquals( 0, disDiapaswn.get(7).getAccidentalCommas()), // DI
                () -> assertEquals( 0, disDiapaswn.get(8).getAccidentalCommas()), // KE
                () -> assertEquals( 4, disDiapaswn.get(9).getAccidentalCommas()), // ZW
                () -> assertEquals( 0, disDiapaswn.get(10).getAccidentalCommas()), // NH
                () -> assertEquals( 0, disDiapaswn.get(11).getAccidentalCommas()), // PA
                () -> assertEquals(-1, disDiapaswn.get(12).getAccidentalCommas()), // BOU
                () -> assertEquals( 0, disDiapaswn.get(13).getAccidentalCommas()), // GA
                () -> assertEquals( 0, disDiapaswn.get(14).getAccidentalCommas()) // DI
        );
    }

    @Test
    void applyNenanwDiToAgia() {
        disDiapaswn.getByStep(ByzStep.DI, 0);
        disDiapaswn.applyFthora(ByzScale.NEXEANES.getByStep(ByzStep.DI, null));
        assertAll(
                () -> assertEquals( 0, disDiapaswn.get(0).getAccidentalCommas()), // DI
                () -> assertEquals(-4, disDiapaswn.get(1).getAccidentalCommas()), // KE
                () -> assertEquals( 4, disDiapaswn.get(2).getAccidentalCommas()), // ZW
                () -> assertEquals( 0, disDiapaswn.get(3).getAccidentalCommas()), // NH
                () -> assertEquals( 0, disDiapaswn.get(4).getAccidentalCommas()), // PA
                () -> assertEquals(-4, disDiapaswn.get(5).getAccidentalCommas()), // BOY
                () -> assertEquals( 4, disDiapaswn.get(6).getAccidentalCommas()), // GA
                () -> assertEquals( 0, disDiapaswn.get(7).getAccidentalCommas()), // DI
                () -> assertEquals( 0, disDiapaswn.get(8).getAccidentalCommas()), // KE
                () -> assertEquals( 1, disDiapaswn.get(9).getAccidentalCommas()), // ZW
                () -> assertEquals( 4, disDiapaswn.get(10).getAccidentalCommas()), // NH
                () -> assertEquals( 0, disDiapaswn.get(11).getAccidentalCommas()), // PA
                () -> assertEquals( 0, disDiapaswn.get(12).getAccidentalCommas()), // BOU
                () -> assertEquals( 1, disDiapaswn.get(13).getAccidentalCommas()), // GA
                () -> assertEquals( 4, disDiapaswn.get(14).getAccidentalCommas()) // DI
        );
    }

    @Test
    void applyAnanesToAgia() {
        disDiapaswn.getByStep(ByzStep.DI, 0);
        disDiapaswn.applyFthora(ByzScale.SOFT_DIATONIC.getByStep(ByzStep.KE, null));
        assertAll(
                () -> assertEquals( 0, disDiapaswn.get(0).getAccidentalCommas()), // DI
                () -> assertEquals(-1, disDiapaswn.get(1).getAccidentalCommas()), // KE
                () -> assertEquals( 0, disDiapaswn.get(2).getAccidentalCommas()), // ZW
                () -> assertEquals( 0, disDiapaswn.get(3).getAccidentalCommas()), // NH
                () -> assertEquals(-1, disDiapaswn.get(4).getAccidentalCommas()), // PA
                () -> assertEquals(-5, disDiapaswn.get(5).getAccidentalCommas()), // BOY
                () -> assertEquals( 0, disDiapaswn.get(6).getAccidentalCommas()), // GA
                () -> assertEquals( 0, disDiapaswn.get(7).getAccidentalCommas()), // DI
                () -> assertEquals(-1, disDiapaswn.get(8).getAccidentalCommas()), // KE
                () -> assertEquals( 0, disDiapaswn.get(9).getAccidentalCommas()), // ZW
                () -> assertEquals( 0, disDiapaswn.get(10).getAccidentalCommas()), // NH
                () -> assertEquals(-1, disDiapaswn.get(11).getAccidentalCommas()), // PA
                () -> assertEquals(-5, disDiapaswn.get(12).getAccidentalCommas()), // BOU
                () -> assertEquals( 0, disDiapaswn.get(13).getAccidentalCommas()), // GA
                () -> assertEquals( 0, disDiapaswn.get(14).getAccidentalCommas()) // DI
        );
    }
}