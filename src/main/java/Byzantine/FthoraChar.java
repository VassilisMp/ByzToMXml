package Byzantine;

import com.google.gson.annotations.Expose;
import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static Byzantine.Scale.*;

final class FthoraChar extends ByzChar {

    /**
     * Map to match Type to Scale
     */
    static final Map<Type, Scale> TYPE_MAP;
    // TODO insert all scales
    static {
        Map<Type, Scale> map = new EnumMap<>(Type.class);
        map.put(Type.S_D, SOFT_DIATONIC);
        map.put(Type.H_C, HARD_CHROMATIC);
        map.put(Type.S_C, SOFT_CHROMATIC);
        TYPE_MAP = Collections.unmodifiableMap(map);
    }

    static Map<PitchEntry, Boolean> pitchToBooleanMap = new HashMap<>();
    static {
        //current.forEach(pitchEntry -> pitchToBooleanMap.put(pitchEntry, false));
    }

    @Expose
    final Type type;
    @Expose
    final ByzStep step;
    @Expose
    final int commas;

    FthoraChar(int codePoint, @NotNull Byzantine.ByzClass byzClass, @NotNull Type type, @NotNull ByzStep step, int commas) {
        super(codePoint, byzClass);
        this.type = type;
        this.step = step;
        this.commas = commas;
    }

    @Override
    public void accept(Engine engine) {
        // get relative Byzantne step from Step
        /*Pitch lastPitch = QuantityChar.getLastPitch(engine.noteList);
        Step step = lastPitch.getStep();
        ByzStep byzStep = engine.STEPS_MAP.inverse().get(step);
        // get relative byzantine octave
        int octave = lastPitch.getOctave();
        int byzOctave = octave - 7;
        ByzScale byzScale = null;
        switch (type) {
            case S_D:
                byzScale = ByzScale.getByStep(ByzScale.SOFT_DIATONIC, this.step, byzOctave);

        }
        ByzScale scale = ByzScale.getByStep(engine.getByzScale(), byzStep, byzOctave);
        scale.applyFthoraDiatonic(byzScale);*/
        /*Scale fthora = Objects.requireNonNull(TYPE_MAP.get(type)).byByzStep(step);
        if (type == Type.S_D) {
            fthora = SOFT_DIATONIC.byByzStep(step);//, STEPS_MAP.get(step));
            // gets Pitch of the last note, in which Fthora is applied
            Step step = QuantityChar.getLastPitch(engine.noteList).getStep();
            engine.scale.
                    byStep(step).
                    applyFthora(fthora);
                    //applyFthora(TYPE_MAP.get(type));
            //List<PitchEntry> currentByStep = Scale.ListByStep(engine.scale.scale, step);
            //engine.scale.applyFthora(new Scale(Objects.requireNonNull(fthora)));
            //Scale.FthoraApply(currentByStep, fthora);
        }*/
    }

    @Override
    public String toString() {
        return "FthoraChar{" +
                super.toString() +
                "type=" + type +
                ", step=" + step +
                ", commas=" + commas +
                "} ";
    }

    @Override
    protected ByzChar clone() {
        return super.clone();
    }
}
