package Byzantine;

import com.google.gson.annotations.Expose;
import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;
import org.jetbrains.annotations.NotNull;

final class FthoraChar extends ByzChar {

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
                byzScale = ByzScale.SOFT_DIATONIC.getByStep(this.step, byzOctave);
                break;
            case H_C:
                byzScale = ByzScale.NEXEANES.getByStep(this.step, null);
                break;
            case S_C:
                byzScale = ByzScale.NEANES.getByStep(this.step, null);
                break;
        }
        if (byzScale != null) {
            ByzScale scale = engine.getCurrentByzScale().getByStep(byzStep, byzOctave);
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
