package Byzantine;

import org.audiveris.proxymusic.AccidentalValue;
import org.audiveris.proxymusic.Key;
import org.audiveris.proxymusic.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static Byzantine.ByzScale.SOFT_DIATONIC;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ByzScaleTest {

    ByzScale disDiapaswn;

    @BeforeEach
    void beforeEach() {
        disDiapaswn = ByzScale.get2OctavesScale();
        ByzScale.initAccidentalCommas(disDiapaswn, ByzStep.DI);
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
        disDiapaswn.applyFthora(SOFT_DIATONIC.getByStep(ByzStep.KE, null));
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

    @Test
    void getKey() {
        final Map<ByzStep, Step> STEPS_MAP = new HashMap<>(7);
        Engine.setDefaultStepsMap(STEPS_MAP);
        final ByzScale byzScale = new ByzScale(SOFT_DIATONIC);
        ByzScale.initAccidentalCommas(disDiapaswn, ByzStep.DI);
        final Key key = disDiapaswn.getKey(STEPS_MAP, ByzStep.DI, null);
        // simpler way to assert using String
        /*final String result = key.getNonTraditionalKey().stream()
                .map(Object::toString).collect(Collectors.joining("\n"));
        String expected = "B\n-0.22\nQUARTER_FLAT\nF\n0.89\nSHARP";
        assertEquals(expected, result);*/
        assertAll(
                () -> assertEquals(6, key.getNonTraditionalKey().size()),
                () -> assertEquals(Step.B, key.getNonTraditionalKey().get(0)),
                () -> assertEquals(AccidentalValue.QUARTER_FLAT, key.getNonTraditionalKey().get(2)),
                () -> assertEquals(Step.F, key.getNonTraditionalKey().get(3)),
                () -> assertEquals(AccidentalValue.SHARP, key.getNonTraditionalKey().get(5))
        );
    }

    @Test
    void general() {
        final Queue<AbstractMap.SimpleImmutableEntry<Integer, String>> queue = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            final AbstractMap.SimpleImmutableEntry<Integer, String> entry =
                    new AbstractMap.SimpleImmutableEntry<>(i, String.format("%d", i));
            queue.add(entry);
        }
    }
}