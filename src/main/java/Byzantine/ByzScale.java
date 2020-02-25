package Byzantine;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public final class ByzScale extends ArrayList<Martyria> implements CircularList<Martyria> {
    /** cursor current position*/
    private int cursorPos = 0;
    /**the last fthora that was applied*/
    private FthorikoSimio fthorikoSimio;
    /**martyria on which the last fthora was applied*/
    private Martyria fthoraHolder;

    /*public ByzScale(List<Martyria> list, FthorikoSimio fthorikoSimio, ByzStep step, int octave*//*, Martyria fthoraHolder*//*) {
        this.list = list;
        this.cursorPos = 0;
        this.fthorikoSimio = fthorikoSimio;
        this.fthoraHolder = this.getMartyria(step, octave);
        // this.fthoraHolder = fthoraHolder;
    }*/

    private ByzScale(@NotNull Collection<? extends Martyria> collection, FthorikoSimio fthorikoSimio, ByzStep step, int octave) {
        super(collection);
        this.fthorikoSimio = fthorikoSimio;
        this.fthoraHolder = this.getMartyria(step, octave);
    }

    private ByzScale(@NotNull ByzScale byzScale) {
        super(byzScale.stream()
                .map(Martyria::new)
                .collect(Collectors.toList())
        );
        this.cursorPos = 0;
        this.fthorikoSimio = byzScale.fthorikoSimio;
        int index = byzScale.indexOf(byzScale.fthoraHolder);
        this.fthoraHolder = this.get(index);
    }

    @NotNull
    @Contract("_, _, _, _ -> new")
    public static ByzScale ByzScaleOf(@NotNull Collection<? extends Martyria> collection, FthorikoSimio fthorikoSimio, ByzStep step, int octave) {
        return new ByzScale(collection, fthorikoSimio, step, octave);
    }

    public Martyria getNext() {
        return getItemToRight(1);
            /*final Martyria element = list.get(cursorPos++);
            cursorPos %= list.size();
            return element;*/
    }

    public Martyria getPrev() {
        return getItemToLeft(1);
            /*final Martyria element = list.get(cursorPos--);
            if (cursorPos == -1) cursorPos = list.size() - 1;
            return element;*/
    }

    public Martyria getItemToRight(int num) {
        cursorPos = (cursorPos+num)%this.size();
        return this.get(cursorPos);
    }

    public Martyria getItemToLeft(int num) {
        cursorPos = this.size()-((cursorPos+num)%this.size());
        return this.get(cursorPos);
    }

    public void setCursor(int cursorPos) {
        this.cursorPos = cursorPos;
    }

    private void calcAbsPos() {
        Martyria martyria1 = getMartyria(ByzStep.DI, 0);
        int indexOfMesoDI = indexOf(martyria1);
        for (int i = 0, thisSize = this.size(); i < thisSize; i++) {
            Martyria martyria = this.get(i);
            int commasCounter = 0;
            if (i<indexOfMesoDI) for (int j = i; j < indexOfMesoDI; j++) commasCounter -= get(j).commasToNext;
            else if (i>indexOfMesoDI) for (int j = i; j > indexOfMesoDI; j--) commasCounter += get(j).commasToPrev;
            martyria.absolPos = commasCounter;
        }
    }

    @Nullable
    Martyria getMartyria(ByzStep step, int  octave) {
        return this.stream()
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
        diatonicByzScale.forEach(martyria -> martyria.commasToPrev = diatonicByzScale.getNext().commasToNext);
        // deep copy the first octave, increasing the octave number by 1
        List<Martyria> copy = diatonicByzScale.stream()
                .map(martyria1 -> {
                    Martyria martyria = new Martyria(martyria1);
                    martyria.octave++;
                    return martyria;
                })
                .collect(Collectors.toList());
        // copy last element, DI''
        final Martyria martyria = new Martyria(diatonicByzScale.get(0));
        martyria.octave += 2;
        diatonicByzScale.addAll(copy);
        diatonicByzScale.add(martyria);
        diatonicByzScale.calcAbsPos();
        return diatonicByzScale;
    }
}
