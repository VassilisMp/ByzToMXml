package Byzantine;

import org.audiveris.proxymusic.Step;

import java.util.*;

class FthoraChar extends ByzChar {
    private static final long serialVersionUID = -6546627387935545846L;
    enum ByzStep {
        NH, PA, BOU, GA, DI, KE, ZW
    }
    //static final ByzStep DEFAULT_STEP = ByzStep.NH;
    static final Map<ByzStep, Step> STEPS_MAP = new HashMap<>();
    static {
        STEPS_MAP.put(ByzStep.NH, Step.C);
        STEPS_MAP.put(ByzStep.PA, Step.D);
        STEPS_MAP.put(ByzStep.BOU, Step.E);
        STEPS_MAP.put(ByzStep.GA, Step.F);
        STEPS_MAP.put(ByzStep.DI, Step.G);
        STEPS_MAP.put(ByzStep.KE, Step.A);
        STEPS_MAP.put(ByzStep.ZW, Step.B);
    }
    static final List<PitchEntry> SOFT_DIATONIC = Arrays.asList(
            new PitchEntry(9, Step.C, ByzStep.NH),
            new PitchEntry(8, Step.D, ByzStep.PA),
            new PitchEntry(5, Step.E, ByzStep.BOU),
            new PitchEntry(9, Step.F, ByzStep.GA),
            new PitchEntry(9, Step.G, ByzStep.DI),
            new PitchEntry(8, Step.A, ByzStep.KE),
            new PitchEntry(5, Step.B, ByzStep.ZW)
    );
    static final List<PitchEntry> HARD_DIATONIC = Arrays.asList(
            new PitchEntry(9, Step.C, ByzStep.ZW),
            new PitchEntry(9, Step.D, ByzStep.NH),
            new PitchEntry(4, Step.E, ByzStep.PA),
            new PitchEntry(9, Step.F, ByzStep.BOU),
            new PitchEntry(9, Step.G, ByzStep.GA),
            new PitchEntry(9, Step.A, ByzStep.DI),
            new PitchEntry(4, Step.B, ByzStep.KE)
    );
    static final List<PitchEntry> HARD_DIATONIC_GA = Arrays.asList(
            new PitchEntry(9, Step.C, ByzStep.NH),
            new PitchEntry(9, Step.D, ByzStep.PA),
            new PitchEntry(4, Step.E, ByzStep.BOU),
            new PitchEntry(9, Step.F, ByzStep.GA),
            new PitchEntry(9, Step.G, ByzStep.DI),
            new PitchEntry(8, Step.A, ByzStep.KE),
            new PitchEntry(5, Step.B, ByzStep.ZW)
    );
    static final List<PitchEntry> HARD_DIATONIC_BOU = Arrays.asList(
            new PitchEntry(9, Step.C, ByzStep.NH),
            new PitchEntry(4, Step.D, ByzStep.PA),
            new PitchEntry(9, Step.E, ByzStep.BOU),
            new PitchEntry(9, Step.F, ByzStep.GA),
            new PitchEntry(9, Step.G, ByzStep.DI),
            new PitchEntry(8, Step.A, ByzStep.KE),
            new PitchEntry(5, Step.B, ByzStep.ZW)
    );
    static final List<PitchEntry> HARD_DIATONIC_ZW = Arrays.asList(
            new PitchEntry(9, Step.C, ByzStep.NH),
            new PitchEntry(8, Step.D, ByzStep.PA),
            new PitchEntry(5, Step.E, ByzStep.BOU),
            new PitchEntry(9, Step.F, ByzStep.GA),
            new PitchEntry(9, Step.G, ByzStep.DI),
            new PitchEntry(4, Step.A, ByzStep.KE),
            new PitchEntry(9, Step.B, ByzStep.ZW)
    );
    static final List<PitchEntry> SPATHI_KE = Arrays.asList(
            new PitchEntry(8, Step.D, ByzStep.PA),
            new PitchEntry(5, Step.E, ByzStep.BOU),
            new PitchEntry(13, Step.F, ByzStep.GA),
            new PitchEntry(5, Step.G, ByzStep.DI),
            new PitchEntry(4, Step.A, ByzStep.KE),
            new PitchEntry(9, Step.B, ByzStep.ZW),
            new PitchEntry(9, Step.C, ByzStep.NH)
    );
    static final List<PitchEntry> SPATHI_GA = Arrays.asList(
            new PitchEntry(8, Step.D, ByzStep.PA),
            new PitchEntry(5, Step.E, ByzStep.BOU),
            new PitchEntry(5, Step.F, ByzStep.GA),
            new PitchEntry(13, Step.G, ByzStep.DI),
            new PitchEntry(4, Step.A, ByzStep.KE),
            new PitchEntry(9, Step.B, ByzStep.ZW),
            new PitchEntry(9, Step.C, ByzStep.NH)
    );
    static final List<PitchEntry> ZYGOS = Arrays.asList(
            new PitchEntry(13, Step.C, ByzStep.NH),
            new PitchEntry(4, Step.D, ByzStep.PA),
            new PitchEntry(9, Step.E, ByzStep.BOU),
            new PitchEntry(5, Step.F, ByzStep.GA),
            new PitchEntry(8, Step.G, ByzStep.DI),
            new PitchEntry(9, Step.A, ByzStep.KE),
            new PitchEntry(5, Step.B, ByzStep.ZW)
    );
    static final List<PitchEntry> HARD_CHROMATIC = Arrays.asList(
            new PitchEntry(5, Step.D, ByzStep.PA),
            new PitchEntry(12, Step.E, ByzStep.BOU),
            new PitchEntry(5, Step.F, ByzStep.GA),
            new PitchEntry(9, Step.G, ByzStep.DI),
            new PitchEntry(5, Step.A, ByzStep.KE),
            new PitchEntry(12, Step.B, ByzStep.ZW),
            new PitchEntry(5, Step.C, ByzStep.NH)
    );
    static final List<PitchEntry> SOFT_CHROMATIC = Arrays.asList(
            new PitchEntry(6, Step.C, ByzStep.NH),
            new PitchEntry(11, Step.D, ByzStep.PA),
            new PitchEntry(5, Step.E, ByzStep.BOU),
            new PitchEntry(9, Step.F, ByzStep.GA),
            new PitchEntry(6, Step.G, ByzStep.DI),
            new PitchEntry(11, Step.A, ByzStep.KE),
            new PitchEntry(5, Step.B, ByzStep.ZW)
    );
    static List<PitchEntry> current = PitchEntry.FthoraApply(Cloner.deepClone(HARD_DIATONIC), SOFT_DIATONIC);
    static Type currentGenos = Type.S_D;
    static Map<PitchEntry, Boolean> pitchToBooleanMap = new HashMap<>();
    static {
        current.forEach(pitchEntry -> pitchToBooleanMap.put(pitchEntry, false));
    }

    Type type;
    ByzStep step;
    int commas;

    FthoraChar(int codePoint, String font, Byzantine.ByzClass byzClass, Type type, ByzStep step, int commas) {
        super(codePoint, font, byzClass);
        this.type = type;
        this.step = step;
        this.commas = commas;
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

    @Override
    public void run() {
        List<PitchEntry> fthora;
        if (type == Type.S_D) {
            try {
                fthora = PitchEntry.ListByStep(SOFT_DIATONIC, STEPS_MAP.get(step));
                // gets Pitch of the last note, in which Fthora is applied
                Step step = QuantityChar.getPitch().getStep();
                List<PitchEntry> currentByStep = PitchEntry.ListByStep(current, step);
                PitchEntry.FthoraApply(currentByStep, fthora);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    enum Type {
        S_D, H_D, H_C, S_C, HENARMONIC, ZYGOS, SPATHI, KLITON, PERMANENT_SHARP, PERMANENT_FLAT, SHARP, FLAT
    }

    private static final Map<Type, List<PitchEntry>> TYPE_MAP;// = Collections.unmodifiableMap(new EnumMap<Type, List<PitchEntry>>(Type.class){{
       //put(Type.S_D, SOFT_DIATONIC);
       //put(Type.H_C, HARD_CHROMATIC);
       //put(Type.S_C, SOFT_CHROMATIC);
    //}});
    static {
        Map<Type, List<PitchEntry>> map = new EnumMap<Type, List<PitchEntry>>(Type.class);
        map.put(Type.S_D, SOFT_DIATONIC);
        map.put(Type.H_C, HARD_CHROMATIC);
        map.put(Type.S_C, SOFT_CHROMATIC);
        TYPE_MAP = Collections.unmodifiableMap(map);
    }
}
