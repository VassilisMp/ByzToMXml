package Byzantine;

import com.google.gson.annotations.Expose;
import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static Byzantine.Scale.*;

final class FthoraChar extends ByzChar {

    /**
     * Map to match Type to Scale
     */
    static final Map<Type, Scale> TYPE_MAP;
    static Map<PitchEntry, Boolean> pitchToBooleanMap = new HashMap<>();

    // TODO insert all scales
    static {
        Map<Type, Scale> map = new EnumMap<>(Type.class);
        map.put(Type.S_D, SOFT_DIATONIC);
        map.put(Type.H_C, HARD_CHROMATIC);
        map.put(Type.S_C, SOFT_CHROMATIC);
        TYPE_MAP = Collections.unmodifiableMap(map);
    }

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
    public void accept(@NotNull Engine engine) {
        // get relative Byzantine step from Step
        Pitch lastPitch = QuantityChar.getLastPitch(engine.noteList);
        Step step = lastPitch.getStep();
        ByzStep byzStep = engine.stepToByzStep(step);
        // get relative byzantine octave
        int octave = lastPitch.getOctave();
        int byzOctave = octave - 5;
        ByzScale byzScale = null;
        switch (type) {
            case S_D:
                if (this.step == ByzStep.NH && commas == 53) byzOctave = 1;
                byzScale = ByzScale.getByStep(ByzScale.SOFT_DIATONIC, this.step, byzOctave);
                break;
            case H_C:
                byzScale = ByzScale.getByStep(ByzScale.NEXEANES, this.step, null);
                break;
            case S_C:
                byzScale = ByzScale.getByStep(ByzScale.NEANES, this.step, null);
                break;
        }
        if (byzScale != null) {
            ByzScale scale = ByzScale.getByStep(engine.getCurrentByzScale(), byzStep, byzOctave);
            scale.applyFthora(byzScale);
            engine.putFthoraScale(new ByzScale(scale), engine.getNoteListSize());
        }
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
