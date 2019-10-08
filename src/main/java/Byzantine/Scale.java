package Byzantine;

import org.audiveris.proxymusic.Key;
import org.audiveris.proxymusic.ObjectFactory;
import org.audiveris.proxymusic.Step;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static Byzantine.PitchEntry.*;

public class Scale {
    List<PitchEntry> scale;
    // TODO include type in Constructor
    FthoraChar.Type type;

    static final Scale SOFT_DIATONIC = new Scale(Arrays.asList(
            new PitchEntry(9, Step.C, FthoraChar.ByzStep.NH),
            new PitchEntry(8, Step.D, FthoraChar.ByzStep.PA),
            new PitchEntry(5, Step.E, FthoraChar.ByzStep.BOU),
            new PitchEntry(9, Step.F, FthoraChar.ByzStep.GA),
            new PitchEntry(9, Step.G, FthoraChar.ByzStep.DI),
            new PitchEntry(8, Step.A, FthoraChar.ByzStep.KE),
            new PitchEntry(5, Step.B, FthoraChar.ByzStep.ZW)
    ));
    static final Scale HARD_DIATONIC = new Scale(Arrays.asList(
            new PitchEntry(9, Step.C, FthoraChar.ByzStep.ZW),
            new PitchEntry(9, Step.D, FthoraChar.ByzStep.NH),
            new PitchEntry(4, Step.E, FthoraChar.ByzStep.PA),
            new PitchEntry(9, Step.F, FthoraChar.ByzStep.BOU),
            new PitchEntry(9, Step.G, FthoraChar.ByzStep.GA),
            new PitchEntry(9, Step.A, FthoraChar.ByzStep.DI),
            new PitchEntry(4, Step.B, FthoraChar.ByzStep.KE)
    ));
    static final Scale HARD_DIATONIC_GA = new Scale(Arrays.asList(
            new PitchEntry(9, Step.C, FthoraChar.ByzStep.NH),
            new PitchEntry(9, Step.D, FthoraChar.ByzStep.PA),
            new PitchEntry(4, Step.E, FthoraChar.ByzStep.BOU),
            new PitchEntry(9, Step.F, FthoraChar.ByzStep.GA),
            new PitchEntry(9, Step.G, FthoraChar.ByzStep.DI),
            new PitchEntry(8, Step.A, FthoraChar.ByzStep.KE),
            new PitchEntry(5, Step.B, FthoraChar.ByzStep.ZW)
    ));
    static final Scale HARD_DIATONIC_BOU = new Scale(Arrays.asList(
            new PitchEntry(9, Step.C, FthoraChar.ByzStep.NH),
            new PitchEntry(4, Step.D, FthoraChar.ByzStep.PA),
            new PitchEntry(9, Step.E, FthoraChar.ByzStep.BOU),
            new PitchEntry(9, Step.F, FthoraChar.ByzStep.GA),
            new PitchEntry(9, Step.G, FthoraChar.ByzStep.DI),
            new PitchEntry(8, Step.A, FthoraChar.ByzStep.KE),
            new PitchEntry(5, Step.B, FthoraChar.ByzStep.ZW)
    ));
    static final Scale HARD_DIATONIC_ZW = new Scale(Arrays.asList(
            new PitchEntry(9, Step.C, FthoraChar.ByzStep.NH),
            new PitchEntry(8, Step.D, FthoraChar.ByzStep.PA),
            new PitchEntry(5, Step.E, FthoraChar.ByzStep.BOU),
            new PitchEntry(9, Step.F, FthoraChar.ByzStep.GA),
            new PitchEntry(9, Step.G, FthoraChar.ByzStep.DI),
            new PitchEntry(4, Step.A, FthoraChar.ByzStep.KE),
            new PitchEntry(9, Step.B, FthoraChar.ByzStep.ZW)
    ));
    static final Scale SPATHI_KE = new Scale(Arrays.asList(
            new PitchEntry(8, Step.D, FthoraChar.ByzStep.PA),
            new PitchEntry(5, Step.E, FthoraChar.ByzStep.BOU),
            new PitchEntry(13, Step.F, FthoraChar.ByzStep.GA),
            new PitchEntry(5, Step.G, FthoraChar.ByzStep.DI),
            new PitchEntry(4, Step.A, FthoraChar.ByzStep.KE),
            new PitchEntry(9, Step.B, FthoraChar.ByzStep.ZW),
            new PitchEntry(9, Step.C, FthoraChar.ByzStep.NH)
    ));
    static final Scale SPATHI_GA = new Scale(Arrays.asList(
            new PitchEntry(8, Step.D, FthoraChar.ByzStep.PA),
            new PitchEntry(5, Step.E, FthoraChar.ByzStep.BOU),
            new PitchEntry(5, Step.F, FthoraChar.ByzStep.GA),
            new PitchEntry(13, Step.G, FthoraChar.ByzStep.DI),
            new PitchEntry(4, Step.A, FthoraChar.ByzStep.KE),
            new PitchEntry(9, Step.B, FthoraChar.ByzStep.ZW),
            new PitchEntry(9, Step.C, FthoraChar.ByzStep.NH)
    ));
    static final Scale ZYGOS = new Scale(Arrays.asList(
            new PitchEntry(13, Step.C, FthoraChar.ByzStep.NH),
            new PitchEntry(4, Step.D, FthoraChar.ByzStep.PA),
            new PitchEntry(9, Step.E, FthoraChar.ByzStep.BOU),
            new PitchEntry(5, Step.F, FthoraChar.ByzStep.GA),
            new PitchEntry(8, Step.G, FthoraChar.ByzStep.DI),
            new PitchEntry(9, Step.A, FthoraChar.ByzStep.KE),
            new PitchEntry(5, Step.B, FthoraChar.ByzStep.ZW)
    ));
    static final Scale HARD_CHROMATIC = new Scale(Arrays.asList(
            new PitchEntry(5, Step.D, FthoraChar.ByzStep.PA),
            new PitchEntry(12, Step.E, FthoraChar.ByzStep.BOU),
            new PitchEntry(5, Step.F, FthoraChar.ByzStep.GA),
            new PitchEntry(9, Step.G, FthoraChar.ByzStep.DI),
            new PitchEntry(5, Step.A, FthoraChar.ByzStep.KE),
            new PitchEntry(12, Step.B, FthoraChar.ByzStep.ZW),
            new PitchEntry(5, Step.C, FthoraChar.ByzStep.NH)
    ));
    static final Scale SOFT_CHROMATIC = new Scale(Arrays.asList(
            new PitchEntry(6, Step.C, FthoraChar.ByzStep.NH),
            new PitchEntry(11, Step.D, FthoraChar.ByzStep.PA),
            new PitchEntry(5, Step.E, FthoraChar.ByzStep.BOU),
            new PitchEntry(9, Step.F, FthoraChar.ByzStep.GA),
            new PitchEntry(6, Step.G, FthoraChar.ByzStep.DI),
            new PitchEntry(11, Step.A, FthoraChar.ByzStep.KE),
            new PitchEntry(5, Step.B, FthoraChar.ByzStep.ZW)
    ));

    public Scale(List<PitchEntry> scale) {
        this.scale = scale.stream().map(PitchEntry::new).collect(Collectors.toList());
    }

    public Scale(Scale scale) {
        this.scale = scale.scale.stream().map(PitchEntry::new).collect(Collectors.toList());
    }

    public Scale clone() {
        return new Scale(this);
    }

    Scale applyFthora(/*Genos*/ FthoraChar.Type type, /*Fthoggos*/ FthoraChar.ByzStep byzStep, /*note*/ Step step) {
        new Scale(FthoraChar.TYPE_MAP.get(type)).byStep(step);
        return this;
    }

    Scale applyFthora(Scale fthora) {
        for (int i = 0, difference = 0; i < scale.size(); i++) {
            PitchEntry a = scale.get(i);
            PitchEntry b = fthora.scale.get(i);
            a.byzStep = b.byzStep;
            difference = a.commas-(b.commas-difference);
            a.commas = b.commas;
            if ((i+1) == scale.size())
                scale.get(0).accidentalCommas = -difference;
            else
                scale.get(i+1).accidentalCommas = -difference;
        }
        return this;
    }

    Scale byStep(Step step) {
        int wanted = -1;
        for (int i = 0; i < scale.size(); i++) {
            if(scale.get(i).stepEquals(step))
                wanted = i;
        }
        if (wanted == -1)
            return null;
        if (wanted == 0)
            return this;
        List<PitchEntry> newScale = new ArrayList<>(scale.size());
        newScale.addAll(scale.subList(wanted, scale.size()));
        newScale.addAll(scale.subList(0, wanted));
        this.scale = newScale;
        return this;
    }

    Scale byByzStep(FthoraChar.ByzStep step) {
        int wanted = -1;
        for (int i = 0; i < scale.size(); i++) {
            if(scale.get(i).byzStepEquals(step))
                wanted = i;
        }
        if (wanted == -1)
            return null;
        if (wanted == 0)
            return this;
        List<PitchEntry> newScale = new ArrayList<>(scale.size());
        newScale.addAll(scale.subList(wanted, scale.size()));
        newScale.addAll(scale.subList(0, wanted));
        this.scale = newScale;
        return this;
    }

    @Nullable
    static List<PitchEntry> ListByStep(@NotNull List<PitchEntry> list, Step step) {
        int wanted = -1;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).stepEquals(step))
                wanted = i;
        }
        if (wanted == -1)
            return null;
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
