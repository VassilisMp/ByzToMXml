package Byzantine;

import com.google.gson.annotations.Expose;
import org.audiveris.proxymusic.Step;

import java.util.*;

import static Byzantine.Scale.*;

class FthoraChar extends ByzChar {
    private static final long serialVersionUID = -6546627387935545846L;
    enum ByzStep {
        NH, PA, BOU, GA, DI, KE, ZW
    }
    enum Type {
        S_D, H_D, H_C, S_C, HENARMONIC, ZYGOS, SPATHI, KLITON, PERMANENT_SHARP, PERMANENT_FLAT, SHARP, FLAT
    }
    //static final ByzStep DEFAULT_STEP = ByzStep.NH;
    private static final Map<ByzStep, Step> STEPS_MAP = new HashMap<>();
    static {
        STEPS_MAP.put(ByzStep.NH, Step.C);
        STEPS_MAP.put(ByzStep.PA, Step.D);
        STEPS_MAP.put(ByzStep.BOU, Step.E);
        STEPS_MAP.put(ByzStep.GA, Step.F);
        STEPS_MAP.put(ByzStep.DI, Step.G);
        STEPS_MAP.put(ByzStep.KE, Step.A);
        STEPS_MAP.put(ByzStep.ZW, Step.B);
    }
    //static List<PitchEntry> current = new Scale(HARD_DIATONIC).clone().applyFthora(new Scale(SOFT_DIATONIC));
    static Type currentGenos = Type.S_D;
    static Map<PitchEntry, Boolean> pitchToBooleanMap = new HashMap<>();
    static {
        //current.forEach(pitchEntry -> pitchToBooleanMap.put(pitchEntry, false));
    }

    @Expose
    Type type;
    @Expose
    ByzStep step;
    @Expose
    int commas;

    FthoraChar(int codePoint, Byzantine.ByzClass byzClass, Type type, ByzStep step, int commas) {
        super(codePoint, byzClass);
        this.type = type;
        this.step = step;
        this.commas = commas;
        classType = this.getClass().getSimpleName();
    }

    @Override
    public void accept(Engine engine) {
        Scale fthora;
        if (type == Type.S_D) {
            fthora = SOFT_DIATONIC.byByzStep(step);//, STEPS_MAP.get(step));
            // gets Pitch of the last note, in which Fthora is applied
            Step step = QuantityChar.getPitch(engine.noteList).getStep();
            engine.scale.
                    byStep(step).
                    applyFthora(fthora);
                    //applyFthora(TYPE_MAP.get(type));
            //List<PitchEntry> currentByStep = Scale.ListByStep(engine.scale.scale, step);
            //engine.scale.applyFthora(new Scale(Objects.requireNonNull(fthora)));
            //Scale.FthoraApply(currentByStep, fthora);
        }
    }

    static final Map<Type, Scale> TYPE_MAP;
    // TODO insert all scales
    static {
        Map<Type, Scale> map = new EnumMap<>(Type.class);
        map.put(Type.S_D, SOFT_DIATONIC);
        map.put(Type.H_C, HARD_CHROMATIC);
        map.put(Type.S_C, SOFT_CHROMATIC);
        TYPE_MAP = Collections.unmodifiableMap(map);
    }

    @Override
    public String toString() {
        return "FthoraChar{" +
                "type=" + type +
                ", step=" + step +
                ", commas=" + commas +
                ", ByzClass=" + ByzClass +
                ", codePoint=" + codePoint +
                '}';
    }
}
