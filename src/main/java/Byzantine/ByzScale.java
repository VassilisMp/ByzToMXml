package Byzantine;

import org.audiveris.proxymusic.Key;
import org.audiveris.proxymusic.ObjectFactory;
import org.audiveris.proxymusic.Step;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static Byzantine.Martyria.ACCIDENTALS_MAP;
import static Byzantine.Martyria.FLATS_FOURTHS;
import static java.util.Arrays.asList;

public final class ByzScale implements CircularList<Martyria> {
    // TODO add the missing scales
    static final ByzScale NEXEANES = createScale(asList(
            new Martyria(0, ByzStep.PA, MartirikoSimio.NEXEANESx, 5),
            new Martyria(0, ByzStep.BOU, MartirikoSimio.NENANO, 12),
            new Martyria(0, ByzStep.GA, MartirikoSimio.NEXEANESx, 5),
            new Martyria(0, ByzStep.DI, MartirikoSimio.NENANO, 9)
    ));
    static final ByzScale NEANES = createScale(asList(
            new Martyria(0, ByzStep.DI, MartirikoSimio.NEANES, 6),
            new Martyria(0, ByzStep.KE, MartirikoSimio.NEANES2, 11),
            new Martyria(0, ByzStep.ZW, MartirikoSimio.NEANES, 5),
            new Martyria(1, ByzStep.NH, MartirikoSimio.NEANES2, 9)
    ));
    static final ByzScale SOFT_DIATONIC = createScale(asList(
            new Martyria((byte) -1, ByzStep.DI, MartirikoSimio.AGIA, 9),
            new Martyria((byte) -1, ByzStep.KE, MartirikoSimio.ANANES, 8),
            new Martyria((byte) -1, ByzStep.ZW, MartirikoSimio.AANES, 5),
            new Martyria((byte) 0, ByzStep.NH, MartirikoSimio.NEAGIE, 9),
            new Martyria((byte) 0, ByzStep.PA, MartirikoSimio.ANEANES, 8),
            new Martyria((byte) 0, ByzStep.BOU, MartirikoSimio.NEHEANES, 5),
            new Martyria((byte) 0, ByzStep.GA, MartirikoSimio.NANA, 9)
    ));
    static final ByzScale HARD_DIATONIC = createScale(asList(
            new Martyria(0, ByzStep.NH, MartirikoSimio.NEAGIE, 9),
            new Martyria(0, ByzStep.PA, MartirikoSimio.ANEANES, 9),
            new Martyria(0, ByzStep.BOU, MartirikoSimio.NEHEANES, 4),
            new Martyria(0, ByzStep.GA, MartirikoSimio.NANA, 9),
            new Martyria(0, ByzStep.DI, MartirikoSimio.AGIA, 9),
            new Martyria(0, ByzStep.KE, MartirikoSimio.ANANES, 9),
            new Martyria(0, ByzStep.ZW, MartirikoSimio.AANES, 4)
    ));

    /**
     * list holding martyrias
     */
    private final List<Martyria> scale;
    /**
     * cursor current position
     */
    private int cursorPos = 0;
    // TODO write code for fthorikoSimio in FthoraChar
    /**
     * the last fthora that was applied
     */
    private FthorikoSimio fthorikoSimio;
    /**
     * martyria on which the last fthora was applied
     */
    private Martyria fthoraHolder;

    private ByzScale(Collection<? extends Martyria> collection, FthorikoSimio fthorikoSimio, ByzStep step, int octave) {
        this.scale = collection != null ? new ArrayList<>(collection) : new ArrayList<>();
        this.fthorikoSimio = fthorikoSimio;
        this.fthoraHolder = getMartyria(step, octave).orElse(null);
    }

    /*public ByzScale(List<Martyria> list, FthorikoSimio fthorikoSimio, ByzStep step, int octave*//*, Martyria fthoraHolder*//*) {
        this.list = list;
        this.cursorPos = 0;
        this.fthorikoSimio = fthorikoSimio;
        this.fthoraHolder = this.getMartyria(step, octave);
        // this.fthoraHolder = fthoraHolder;
    }*/

    ByzScale(@NotNull ByzScale byzScale) {
        this.scale = byzScale.scale.stream()
                .map(Martyria::new)
                .collect(Collectors.toList());
        this.cursorPos = byzScale.cursorPos;
        this.fthorikoSimio = byzScale.fthorikoSimio;
        if (byzScale.fthoraHolder != null) {
            int index = byzScale.scale.indexOf(byzScale.fthoraHolder);
            this.fthoraHolder = this.scale.get(index);
        }
    }

    private static @NotNull ByzScale createScale(List<Martyria> martyrias) {
        martyrias = Collections.unmodifiableList(martyrias);
        ByzScale byzScale = new ByzScale(martyrias, null, null, 0);
        byzScale.getItemToLeft(2);
        // set commasToPrev using commasToNext value of the previous martyria
        byzScale.scale.forEach(martyria -> martyria.commasToPrev = byzScale.getNext().commasToNext);
        byzScale.calcAbsPos();
        byzScale.resetCursor();
        return byzScale;
    }

    @NotNull
    @Contract("_, _, _, _ -> new")
    public static ByzScale ByzScaleOf(@NotNull Collection<? extends Martyria> collection,
                                      FthorikoSimio fthorikoSimio, ByzStep step, int octave) {
        return new ByzScale(collection, fthorikoSimio, step, octave);
    }

    @NotNull
    public static ByzScale get2OctavesScale() {
        // create first octave
        // clone SOFT_DIATONIC scale
        // wrap in ByzScale
        final ByzScale diatonicByzScale = new ByzScale(SOFT_DIATONIC);
        diatonicByzScale.fthorikoSimio = FthorikoSimio.NH_D;
        diatonicByzScale.fthoraHolder = diatonicByzScale.getMartyria(ByzStep.NH, 0).orElse(null);
        // go two positions to left, to call getNext().commasToNext on the previous martyria
        diatonicByzScale.getItemToLeft(2);
        // set commasToPrev using commasToNext value of the previous martyria
        diatonicByzScale.scale.forEach(martyria -> martyria.commasToPrev = diatonicByzScale.getNext().commasToNext);
        // deep copy the first octave, increasing the octave number by 1
        List<Martyria> copy = diatonicByzScale.scale.stream()
                .map(martyria1 -> {
                    Martyria martyria = new Martyria(martyria1);
                    martyria.octave++;
                    return martyria;
                })
                .collect(Collectors.toList());
        // copy last element, DI''
        final Martyria martyria = new Martyria(diatonicByzScale.scale.get(0));
        martyria.octave += 2;
        diatonicByzScale.scale.addAll(copy);
        diatonicByzScale.scale.add(martyria);
        diatonicByzScale.calcAbsPos();
        return diatonicByzScale;
    }

    static void initAccidentalCommas(@NotNull ByzScale currentByzScale, ByzStep relativeStandardStep) {
        final ByzScale HARD_DIATONIC = ByzScale.HARD_DIATONIC.getByStep(relativeStandardStep, null);
        int HARD_DIATONIC_cursorPos = HARD_DIATONIC.getCursorPos();
        HARD_DIATONIC.setCursorPos(HARD_DIATONIC_cursorPos - 1);
        currentByzScale.getByStep(ByzStep.NH, null);
        final int currentByzScaleCursorPos = currentByzScale.getCursorPos();
        for (int i = currentByzScaleCursorPos, difference = 0; i < currentByzScale.size(); i++) {
            final Martyria a = currentByzScale.get(i);
            final Martyria b = HARD_DIATONIC.getNext();
            difference = a.getCommasToNext() - b.getCommasToNext() + difference;
            final int setIndex = (i + 1) == currentByzScale.size() ? 0 : i + 1;
            currentByzScale.get(setIndex).setAccidentalCommas(difference);
        }
        HARD_DIATONIC.setCursorPos(HARD_DIATONIC_cursorPos);
        for (int i = currentByzScaleCursorPos - 1, difference = 0; i >= 0; i--) {
            final Martyria a = currentByzScale.get(i);
            final Martyria b = HARD_DIATONIC.getPrev();
            difference = a.getCommasToNext() - b.getCommasToNext() + difference;
            a.setAccidentalCommas(-difference);
        }
        currentByzScale.resetCursor();
    }

    ByzScale getByStep(ByzStep step, @Nullable Integer octave) {
        int indexOf = indexOf(step, octave);
        if (indexOf >= 0) this.cursorPos = indexOf;
        return this;
    }

    int indexOf(ByzStep step, @Nullable Integer octave) {
        byte minOctave = scale.get(0).octave;
        byte maxOctave = scale.get(scale.size() - 1).octave;
        final Martyria[] martyria = new Martyria[1];
        Supplier<Boolean> booleanSupplier;
        if (octave == null)
            booleanSupplier = () -> martyria[0].step == step;
        else if (octave >= minOctave && octave <= maxOctave)
            booleanSupplier = () -> martyria[0].step == step && martyria[0].octave == octave;
        else booleanSupplier = () -> martyria[0].step == step;
        for (int i = 0; i < scale.size(); i++) {
            martyria[0] = scale.get(i);
            if (booleanSupplier.get()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param byzStep the step to search for.
     * @return the index of the step if exists, or else -1.
     */
    private int indexOfStep(ByzStep byzStep) {
        for (int i = 0; i < scale.size(); i++) {
            Martyria martyria = scale.get(i);
            if (martyria.getStep() == byzStep)
                return i;
        }
        return -1;
    }

    @Nullable
    public Martyria getNextIfExists() {
        return cursorPos < this.scale.size() - 1 ? this.scale.get(++cursorPos) : null;
    }

    @Nullable
    public Martyria getPrevIfExists() {
        return cursorPos > 0 ? this.scale.get(--cursorPos) : null;
    }

    public Martyria getNext() {
        return getItemToRight(1);
    }

    public Martyria getPrev() {
        return getItemToLeft(1);
    }

    // TODO use cursorPos as one Element array parameter to be able to iterate static fthora scales on many threads
    public Martyria getItemToRight(int num) {
        cursorPos = (cursorPos + num) % this.scale.size();
        return this.scale.get(cursorPos);
    }

    public Martyria getItemToLeft(int num) {
        cursorPos = indexOfLeftItem(num);
        return this.scale.get(cursorPos);
    }

    int indexOfLeftItem(int num) {
        int index = (cursorPos - num) % this.scale.size();
        if (index < 0) index = this.scale.size() + index;
        return index;
    }

    int getCursorPos() {
        return cursorPos;
    }

    void setCursorPos(int cursorPos) {
        this.cursorPos = cursorPos;
    }

    private void calcAbsPos() {
        Martyria martyria1 = getMartyria(ByzStep.DI, 0).orElse(null);
        int indexOfMesoDI = scale.indexOf(martyria1);
        for (int i = 0, thisSize = this.scale.size(); i < thisSize; i++) {
            Martyria martyria = this.scale.get(i);
            int commasCounter = 0;
            if (i < indexOfMesoDI) for (int j = i; j < indexOfMesoDI; j++) commasCounter -= scale.get(j).commasToNext;
            else if (i > indexOfMesoDI)
                for (int j = i; j > indexOfMesoDI; j--) commasCounter += scale.get(j).commasToPrev;
            martyria.absolPos = commasCounter;
        }
    }

    @NotNull Optional<Martyria> getMartyria(ByzStep step, int octave) {
        return this.scale.stream()
                .filter(martyria -> martyria.step == step && martyria.octave == octave)
                .findFirst();
    }

    @NotNull
    Optional<Martyria> getMartyriaByStepOctave(@NotNull Martyria martyria) {
        return getMartyria(martyria.step, martyria.octave);
    }

    public void resetCursor() {
        this.cursorPos = 0;
    }

    void applyFthora(ByzScale fthora) {
        if (fthora != null) {
//            HARD_DIATONIC.indexOfStep(relativeStep)
            // save cursor position for both scales
            int fthoraCursorPos = fthora.cursorPos;
            int cursorPos = this.cursorPos;
            this.fthoraHolder = this.scale.get(cursorPos);

            Martyria a = this.getPrevIfExists();
            Martyria b = fthora.getPrev();
            // iterate scale to left first
            int diff = 0;
            while (a != null) {
                diff = a.getCommasToNext() + diff - b.getCommasToNext();
                a.addAccidentalCommas(diff);
                a.setCommasToNext(b.commasToNext);
                a.commasToPrev = b.commasToPrev;
                a.simio = b.simio;
                a = this.getPrevIfExists();
                b = fthora.getPrev();
//                System.out.println(String.format("%s, %s", martyria, fthoraMart));
            }
            // reset cursor positions
            fthora.cursorPos = fthoraCursorPos;
            this.cursorPos = cursorPos;
            // iterate scale to right
            diff = 0;
            for (a = this.get(this.cursorPos), b = fthora.get(fthora.cursorPos); a != null; a = this.getNextIfExists(),
                    b = fthora.getNext()) {
                a.addAccidentalCommas(diff);
                diff = b.getCommasToNext() + diff - a.getCommasToNext();
                a.commasToNext = b.commasToNext;
                a.commasToPrev = b.commasToPrev;
                a.simio = b.simio;
            }
            this.calcAbsPos();
        }
    }

    Martyria get(int i) {
        return scale.get(i);
    }

    int size() {
        return scale.size();
    }

    @Override
    public String toString() {
        return "ByzScale{" +
                "cursorPos=" + cursorPos +
                ", fthorikoSimio=" + fthorikoSimio +
                ", fthoraHolder=" + fthoraHolder +
                ", scale=" + scale +
                '}';
    }

    Key getKey(Map<ByzStep, Step> STEPS_MAP, @Nullable ByzStep startStep, @Nullable Integer octave) {
        List<Martyria> scale = this.scale;
        if (startStep != null) {
            final int start = this.indexOf(startStep, octave);
            scale = this.scale.subList(start, start + 7);
        }
        Key key = new ObjectFactory().createKey();
        List<Object> nonTraditionalKey = key.getNonTraditionalKey();
        AtomicReference<Function<Martyria, Boolean>> funRef = new AtomicReference<>(
                (@NotNull Martyria martyria) -> martyria.getAccidentalCommas() < 0
        );
        List<Martyria> finalScale = scale;
        final Consumer<Step> addAccidentals = (Step step) -> finalScale.stream()
                .filter(martyria -> STEPS_MAP.get(martyria.getStep()).equals(step)
                        && funRef.get().apply(martyria))
                .findAny()
                .ifPresent(martyria -> Collections.addAll(nonTraditionalKey
                        , step
                        , BigDecimal.valueOf(martyria.getAccidentalCommas() * 2.0 / 9)
                                .setScale(2, RoundingMode.HALF_EVEN)
                        , ACCIDENTALS_MAP.get(martyria.getAccidentalCommas())
                ));
        FLATS_FOURTHS.forEach(addAccidentals);
        funRef.set((@NotNull Martyria martyria) -> martyria.getAccidentalCommas() > 0);
        Martyria.SHARP_FIFTHS.forEach(addAccidentals);
        return key;
    }

}
