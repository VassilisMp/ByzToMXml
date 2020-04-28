package Byzantine;

import org.audiveris.proxymusic.AccidentalValue;
import org.audiveris.proxymusic.Step;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.*;

public class Martyria {
    static final Map<Integer, AccidentalValue> ACCIDENTALS_MAP = getAccidentalsMap();
    static final List<Step> FLATS_FOURTHS = Arrays.asList(Step.B, Step.E, Step.A, Step.D, Step.G, Step.C, Step.F);
    static final List<Step> SHARP_FIFTHS = Arrays.asList(Step.F, Step.C, Step.G, Step.D, Step.A, Step.E, Step.B);
    final ByzStep step;
    /**
     * octave number in the byzantine perspective, meaning count of tonoi(´) on a note,
     * new ´ adds after Zw note, backwards are used negative numbers which aren't shown in fact
     */
    // humans can hear up to 10 octaves
    byte octave;
    MartirikoSimio simio;
    /**
     * absolute position of this Note according to the difference in commas from middle Di
     */
    int absolPos;
    /**
     * commas to the next note
     */
    int commasToNext;
    /**
     * commas to the previous note
     */
    int commasToPrev;
    boolean startOfPentachord;
    boolean endOfPentachord;
    boolean startOfTetrachord;
    boolean endOfTetrachord;
    private int accidentalCommas = 0;
    public Martyria(int octave, ByzStep step, MartirikoSimio simio, int commasToNext) {
        this.octave = (byte) octave;
        this.step = step;
        this.simio = simio;
        this.commasToNext = commasToNext;
    }

    public Martyria(@NotNull Martyria martyria) {
        this.octave = martyria.octave;
        this.step = martyria.step;
        this.simio = martyria.simio;
        this.absolPos = martyria.absolPos;
        this.commasToNext = martyria.commasToNext;
        this.commasToPrev = martyria.commasToPrev;
        this.startOfPentachord = martyria.startOfPentachord;
        this.endOfPentachord = martyria.endOfPentachord;
        this.startOfTetrachord = martyria.startOfTetrachord;
        this.endOfTetrachord = martyria.endOfTetrachord;
        this.accidentalCommas = martyria.accidentalCommas;
    }

    private static @NotNull @UnmodifiableView Map<Integer, AccidentalValue> getAccidentalsMap() {
        Map<Integer, AccidentalValue> map = new HashMap<>();
        map.put(1, AccidentalValue.QUARTER_SHARP);
        map.put(4, AccidentalValue.SHARP);
        map.put(5, AccidentalValue.SLASH_QUARTER_SHARP);
        map.put(8, AccidentalValue.SLASH_SHARP);
        map.put(9, AccidentalValue.DOUBLE_SHARP);
        map.put(-1, AccidentalValue.QUARTER_FLAT);
        map.put(-4, AccidentalValue.SLASH_FLAT);
        map.put(-5, AccidentalValue.FLAT);
        map.put(-8, AccidentalValue.DOUBLE_SLASH_FLAT);
        map.put(-9, AccidentalValue.FLAT_FLAT);
        return Collections.unmodifiableMap(map);
    }

    int getCommasToNext() {
        return commasToNext;
    }

    void setCommasToNext(int commasToNext) {
        this.commasToNext = commasToNext;
    }

    void addAccidentalCommas(int accidentalCommas) {
        this.accidentalCommas += accidentalCommas;
    }

    public ByzStep getStep() {
        return step;
    }

    public int getAccidentalCommas() {
        return accidentalCommas;
    }

    public void setAccidentalCommas(int accidentalCommas) {
        this.accidentalCommas = accidentalCommas;
    }

    @Override
    public String toString() {
        return "Martyria{" +
                "octave=" + octave +
                ", step=" + step +
                ", simio=" + simio +
                ", absolPos=" + absolPos +
                ", commasToNext=" + commasToNext +
                ", commasToPrev=" + commasToPrev +
                ", accidentalCommas=" + accidentalCommas +
                '}';
    }
}
