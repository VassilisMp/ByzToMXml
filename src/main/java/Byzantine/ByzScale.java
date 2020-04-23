package Byzantine;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class ByzScale implements CircularList<Martyria> {
    static ByzScale SOFT_DIATONIC = new ByzScale(null, null, null, 0);
//    static final ByzScale NEXEANES = new ByzScale(null, null, null, 0);
    static {
        /*List<Martyria> martyrias = Arrays.asList(
                *//*new Martyria(-1, ByzStep.DI, MartirikoSimio.AGIA, 9),
                new Martyria(-1, ByzStep.KE, MartirikoSimio.ANANES, 8),*//*
                new Martyria(0, ByzStep.ZW, MartirikoSimio.AANES, 5),
                new Martyria(0, ByzStep.NH, MartirikoSimio.NEAGIE, 9),
                new Martyria(0, ByzStep.PA, MartirikoSimio.ANEANES, 8),
                new Martyria(0, ByzStep.BOU, MartirikoSimio.NEHEANES, 5),
                new Martyria(0, ByzStep.GA, MartirikoSimio.NANA, 9),
                new Martyria(0, ByzStep.DI, MartirikoSimio.AGIA, 9),
                new Martyria(0, ByzStep.KE, MartirikoSimio.ANANES, 8),
        );
        SOFT_DIATONIC.scale.addAll(martyrias);
        // go two positions to left, to call getNext().commasToNext on the previous martyria
        SOFT_DIATONIC.getItemToLeft(2);
        // set commasToPrev using commasToNext value of the previous martyria
        SOFT_DIATONIC.scale.forEach(martyria -> martyria.commasToPrev = SOFT_DIATONIC.getNext().commasToNext);*/
        SOFT_DIATONIC = ByzScale.get2OctavesScale();

        /*martyrias = Arrays.asList(
                new Martyria(0, ByzStep.PA, MartirikoSimio.NEXEANESx, 5),
                new Martyria(0, ByzStep.BOU, MartirikoSimio.NENANO, 12),
                new Martyria(0, ByzStep.GA, MartirikoSimio.NEXEANESx, 5),
                new Martyria(0, ByzStep.DI, MartirikoSimio.NENANO, 9),
                new Martyria(0, ByzStep.KE, MartirikoSimio.NEXEANESx, 5)
        );*/
    }

    /**
     * cursor current position
     */
    private int cursorPos = 0;
    /**the last fthora that was applied*/
    private FthorikoSimio fthorikoSimio;
    /**martyria on which the last fthora was applied*/
    private Martyria fthoraHolder;
    /**list holding martyrias*/
    private List<Martyria> scale;

    /*public ByzScale(List<Martyria> list, FthorikoSimio fthorikoSimio, ByzStep step, int octave*//*, Martyria fthoraHolder*//*) {
        this.list = list;
        this.cursorPos = 0;
        this.fthorikoSimio = fthorikoSimio;
        this.fthoraHolder = this.getMartyria(step, octave);
        // this.fthoraHolder = fthoraHolder;
    }*/

    private ByzScale(Collection<? extends Martyria> collection, FthorikoSimio fthorikoSimio, ByzStep step, int octave) {
        this.scale = collection != null ? new ArrayList<>(collection) : new ArrayList<>();
        this.fthorikoSimio = fthorikoSimio;
        this.fthoraHolder = this.getMartyria(step, octave);
    }

    private ByzScale(@NotNull ByzScale byzScale) {
        this.scale = byzScale.scale.stream()
                .map(Martyria::new)
                .collect(Collectors.toList());
        this.cursorPos = 0;
        this.fthorikoSimio = byzScale.fthorikoSimio;
        int index = byzScale.scale.indexOf(byzScale.fthoraHolder);
        this.fthoraHolder = this.scale.get(index);
    }

    @NotNull
    @Contract("_, _, _, _ -> new")
    public static ByzScale ByzScaleOf(@NotNull Collection<? extends Martyria> collection, FthorikoSimio fthorikoSimio, ByzStep step, int octave) {
        return new ByzScale(collection, fthorikoSimio, step, octave);
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
        cursorPos = (cursorPos+num)%this.scale.size();
        return this.scale.get(cursorPos);
    }

    public Martyria getItemToLeft(int num) {
        cursorPos = (cursorPos-num)%this.scale.size();
        if (cursorPos < 0) cursorPos = this.scale.size() + cursorPos;
        return this.scale.get(cursorPos);
    }

    public void setCursor(int cursorPos) {
        this.cursorPos = cursorPos;
    }

    private void calcAbsPos() {
        Martyria martyria1 = getMartyria(ByzStep.DI, 0);
        int indexOfMesoDI = scale.indexOf(martyria1);
        for (int i = 0, thisSize = this.scale.size(); i < thisSize; i++) {
            Martyria martyria = this.scale.get(i);
            int commasCounter = 0;
            if (i<indexOfMesoDI) for (int j = i; j < indexOfMesoDI; j++) commasCounter -= scale.get(j).commasToNext;
            else if (i>indexOfMesoDI) for (int j = i; j > indexOfMesoDI; j--) commasCounter += scale.get(j).commasToPrev;
            martyria.absolPos = commasCounter;
        }
    }

    @Nullable
    Martyria getMartyria(ByzStep step, int  octave) {
        return this.scale.stream()
                .filter(martyria -> martyria.step == step && martyria.octave == octave)
                .findFirst()
                .orElse(null);
    }

    @Nullable
    Martyria getMartyriaByStepOctave(@NotNull Martyria martyria) {
        return getMartyria(martyria.step, martyria.octave);
    }

    public void resetCursor() {
        this.cursorPos = 0;
    }

    @NotNull
    public static ByzScale get2OctavesScale() {
        // create first octave
        List<Martyria> martyrias = Arrays.asList(
                new Martyria((byte) -1, ByzStep.DI, MartirikoSimio.AGIA, 9),
                new Martyria((byte) -1, ByzStep.KE, MartirikoSimio.ANANES, 8),
                new Martyria((byte) 0, ByzStep.ZW, MartirikoSimio.AANES, 5),
                new Martyria((byte) 0, ByzStep.NH, MartirikoSimio.NEAGIE, 9),
                new Martyria((byte) 0, ByzStep.PA, MartirikoSimio.ANEANES, 8),
                new Martyria((byte) 0, ByzStep.BOU, MartirikoSimio.NEHEANES, 5),
                new Martyria((byte) 0, ByzStep.GA, MartirikoSimio.NANA, 9)
        );
        // wrap in ByzScale
        final ByzScale diatonicByzScale = new ByzScale(martyrias, FthorikoSimio.NH_D, ByzStep.NH, 0);
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

    public static ByzScale getByStep(ByzScale byzScale, ByzStep step, int octave) {
        byte minOctave = byzScale.scale.get(0).octave;
        byte maxOctave = byzScale.scale.get(byzScale.scale.size()-1).octave;
        final Martyria[] martyria = new Martyria[1];
        Supplier<Boolean> booleanSupplier;
        if (octave >= minOctave && octave <= maxOctave)
            booleanSupplier = () -> martyria[0].step == step && martyria[0].octave == octave;
        else booleanSupplier = () -> martyria[0].step == step;
        for (int i = 0; i < byzScale.scale.size(); i++) {
            martyria[0] = byzScale.scale.get(i);
            if (booleanSupplier.get()) {
                byzScale.cursorPos = i;
                return byzScale;
            }
        }
        return byzScale;
    }

    ByzScale applyFthora(ByzScale fthora) {
        if (fthora != null) {
            // save cursor position for both scales
            int fthoraCursorPos = fthora.cursorPos;
            int cursorPos = this.cursorPos;
            this.fthoraHolder = this.scale.get(cursorPos);
            // iterate scale to left first
            Martyria martyria = this.scale.get(this.cursorPos);
            Martyria fthoraMart = fthora.scale.get(fthora.cursorPos);
            System.out.println(String.format("%s, %s", martyria, fthoraMart));
            while (martyria != null) {
                martyria.commasToNext = fthoraMart.commasToNext;
                martyria.commasToPrev = fthoraMart.commasToPrev;
                martyria.simio = fthoraMart.simio;
                martyria = this.getPrevIfExists();
                fthoraMart = fthora.getPrev();
                System.out.println(String.format("%s, %s", martyria, fthoraMart));
            }
            // reset cursor positions
            fthora.cursorPos = fthoraCursorPos;
            this.cursorPos = cursorPos;
            // iterate scale to right
            martyria = this.getNextIfExists();
            fthoraMart = fthora.getNext();
            while (martyria != null) {
                martyria.commasToNext = fthoraMart.commasToNext;
                martyria.commasToPrev = fthoraMart.commasToPrev;
                martyria.simio = fthoraMart.simio;
                martyria = this.getNextIfExists();
                fthoraMart = fthora.getNext();
            }
            this.calcAbsPos();
        }
        return this;
    }
}
