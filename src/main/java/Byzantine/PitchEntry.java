package Byzantine;

import org.audiveris.proxymusic.AccidentalValue;
import org.audiveris.proxymusic.Key;
import org.audiveris.proxymusic.ObjectFactory;
import org.audiveris.proxymusic.Step;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class PitchEntry {
    final static Map<Integer, AccidentalValue> ACCIDENTALS_MAP = new HashMap<>();
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

    public PitchEntry(int commas, Step step, int accidentalCommas) {
        this.commas = commas;
        this.step = step;
        this.accidentalCommas = accidentalCommas;
    }

    public PitchEntry(int commas, Step step) {
        this(commas, step, 0);
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

    @Override
    public String toString() {
        return "PitchEntry{" +
                "commas=" + commas +
                ", step=" + step +
                ", accidentalCommas=" + accidentalCommas +
                '}';
    }

    static int indexOfStep(CircularLinkedList<PitchEntry> list, Step step) {
        int index = 0;
        CircularLinkedList.Node<PitchEntry> x = list.head;
        while (!x.item.stepEquals(step)) {
            x = x.next;
            index++;
            if (index>=list.size) {
                return -1;
            }
        }
        return index;
    }

    static CircularLinkedList<PitchEntry> ListOfStep(CircularLinkedList<PitchEntry> list, Step step) {
        int index = 0;
        CircularLinkedList.Node<PitchEntry> x = list.head;
        while (!x.item.stepEquals(step)) {
            x = x.next;
            index++;
            if (index>=list.size) {
                return null;
            }
        }
        return new CircularLinkedList<>(list.size, x);
    }

    static void FthoraApply(@NotNull List<PitchEntry> list, List<PitchEntry> fthora) {
        for (int i = 0, difference = 0; i < list.size(); i++) {
            PitchEntry a = list.get(i);
            PitchEntry b = fthora.get(i);
            difference = a.commas-(b.commas-difference);
            a.commas = b.commas;
            if ((i+1) == list.size())
                list.get(0).accidentalCommas = -difference;
            else
                list.get(i+1).accidentalCommas = -difference;
        }
    }

    static List<PitchEntry> ListByStep(@NotNull List<PitchEntry> list, Step step) throws Exception {
        int wanted = -1;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).stepEquals(step))
                wanted = i;
        }
        if (wanted == -1)
            throw new Exception("error in finding Step in List");
        if (wanted == 0)
            return list;
        List<PitchEntry> newList = new ArrayList<>(list.size());
        newList.addAll(list.subList(wanted, list.size()));
        newList.addAll(list.subList(0, wanted));
        return newList;
    }

    static Key KeyFromPitches(List<PitchEntry> list) {
        Key key = new ObjectFactory().createKey();
        List<Object> nonTraditionalKey = key.getNonTraditionalKey();
        for (Step step :
                FLATS_FOURTHS) {
            list.stream()
                    .filter(pitchEntry -> pitchEntry.stepEquals(step) && pitchEntry.accidentalCommas<0)
                    .findAny()
                    .ifPresent(pitchEntry -> Collections.addAll(nonTraditionalKey
                            , step
                            , BigDecimal.valueOf(pitchEntry.accidentalCommas * 2.0 / 9).setScale(2, RoundingMode.HALF_EVEN)
                            , ACCIDENTALS_MAP.get(pitchEntry.accidentalCommas)
                    ));
        }
        for (Step step :
                SHARP_FIFTHS) {
            list.stream()
                    .filter(pitchEntry -> pitchEntry.stepEquals(step) && pitchEntry.accidentalCommas>0)
                    .findAny()
                    .ifPresent(pitchEntry -> Collections.addAll(nonTraditionalKey
                            , step
                            , BigDecimal.valueOf(pitchEntry.accidentalCommas * 2.0 / 9).setScale(2, RoundingMode.HALF_EVEN)
                            , ACCIDENTALS_MAP.get(pitchEntry.accidentalCommas)
                    ));
        }

        return key;
    }
}
