package Byzantine;

import org.jetbrains.annotations.NotNull;

public class Martyria {
    /** octave number in the byzantine perspective, meaning count of tonoi(´) on a note,
     * new ´ adds after Zw note, backwards are used negative numbers which aren't shown in fact */
    // humans can hear up to 10 octaves
    byte octave;
    final ByzStep step;
    MartirikoSimio simio;
    /**absolute position of this Note according to the difference in commas from middle Di*/
    int absolPos;
    /**commas to the next note*/
    int commasToNext;
    /**commas to the previous note*/
    int commasToPrev;
    boolean startOfPentachord;
    boolean endOfPentachord;
    boolean startOfTetrachord;
    boolean endOfTetrachord;

    public Martyria(byte octave, ByzStep step, MartirikoSimio simio, int commasToNext) {
        this.octave = octave;
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
    }
}
