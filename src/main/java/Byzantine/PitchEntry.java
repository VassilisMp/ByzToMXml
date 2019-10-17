package Byzantine;

import org.audiveris.proxymusic.AccidentalValue;
import org.audiveris.proxymusic.Step;

import java.util.*;

public class PitchEntry {
    static final Map<Integer, AccidentalValue> ACCIDENTALS_MAP = new HashMap<>();
    static {
        ACCIDENTALS_MAP.put(1, AccidentalValue.QUARTER_SHARP);
        ACCIDENTALS_MAP.put(4, AccidentalValue.SHARP);
        ACCIDENTALS_MAP.put(5, AccidentalValue.SLASH_QUARTER_SHARP);
        ACCIDENTALS_MAP.put(8, AccidentalValue.SLASH_SHARP);
        ACCIDENTALS_MAP.put(9, AccidentalValue.DOUBLE_SHARP);
        ACCIDENTALS_MAP.put(-1, AccidentalValue.QUARTER_FLAT);
        ACCIDENTALS_MAP.put(-4, AccidentalValue.SLASH_FLAT);
        ACCIDENTALS_MAP.put(-5, AccidentalValue.FLAT);
        ACCIDENTALS_MAP.put(-8, AccidentalValue.DOUBLE_SLASH_FLAT);
        ACCIDENTALS_MAP.put(-9, AccidentalValue.FLAT_FLAT);
    }
    static final List<Step> FLATS_FOURTHS = Arrays.asList(Step.B, Step.E, Step.A, Step.D, Step.G, Step.C, Step.F);
    static final List<Step> SHARP_FIFTHS = Arrays.asList(Step.F, Step.C, Step.G, Step.D, Step.A, Step.E, Step.B);
    int commas;
    Step step;
    int accidentalCommas;
    ByzStep byzStep;

    public PitchEntry(int commas, Step step, int accidentalCommas, ByzStep byzStep) {
        this.commas = commas;
        this.step = step;
        this.accidentalCommas = accidentalCommas;
        this.byzStep = byzStep;
    }

    public PitchEntry(PitchEntry pitchEntry) {
        this.commas = pitchEntry.commas;
        this.step = pitchEntry.step;
        this.accidentalCommas = pitchEntry.accidentalCommas;
        this.byzStep = pitchEntry.byzStep;
    }

    public PitchEntry(int commas, Step step) {
        this(commas, step, 0, null);
    }

    public PitchEntry(int commas, Step step, ByzStep byzStep) {
        this(commas, step, 0, byzStep);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PitchEntry that = (PitchEntry) o;
        return commas == that.commas &&
                accidentalCommas == that.accidentalCommas &&
                step == that.step;
    }

    @Override
    public int hashCode() {
        return Objects.hash(commas, step, accidentalCommas);
    }

    public boolean stepEquals(Step step) {
        return this.step == step;
    }

    public boolean byzStepEquals(ByzStep step) {
        return this.byzStep == step;
    }

    @Override
    public String toString() {
        return "PitchEntry{" +
                "commas=" + commas +
                ", step=" + step +
                ", accidentalCommas=" + accidentalCommas +
                '}';
    }
}
