package Byzantine;

import org.audiveris.proxymusic.Note;
import org.audiveris.proxymusic.NoteType;
import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public final class ExtendedNote extends Note implements Cloneable {
    public enum NoteTypeEnum {
        MAXIMA("maxima"), LONG("long"), BREVE("breve"), WHOLE("whole"), HALF("half"), QUARTER("quarter"),
        EIGHTH("eighth"), SIXTEENTH("16th"), THIRTY_SECOND("32nd"), SIXTY_FOURTH("64th"), ONE_TWO_EIGHTH("128th"),
        TWO_FIVE_SIXTH("256th"), FIVE_TWELFTH("512th"), TEN_TWENTY_FOURTH("1024th");
        public String noteType;

        NoteTypeEnum(String noteType) {
            this.noteType = noteType;
        }
    }
    private final boolean lyric;
    private boolean time;

    public ExtendedNote(boolean lyric, boolean time) {
        this.lyric = lyric;
        this.time = time;
    }

    public ExtendedNote(boolean lyric, boolean time, Step step, int octave, int duration, String noteType) {
        this(lyric, time);
        // Pitch
        Pitch pitch = new Pitch();
        pitch.setStep(step);
        pitch.setOctave(octave);
        this.setPitch(pitch);
        // Duration
        this.setDuration(new BigDecimal(duration));
        // Type
        NoteType type = new NoteType();
        type.setValue(noteType);
        this.setType(type);
    }

    public boolean canGetLyric() {
        return lyric;
    }

    public boolean canGetTime() {
        return time;
    }

    public void setTime(boolean time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ExtendedNote{" +
                "lyric=" + lyric +
                ", time=" + time +
                ", pitch=" + (pitch!=null?pitch.getStep() + "" + pitch.getOctave():"null") +
                ", duration=" + duration +
                ", type=" + type.getValue() +
                //", tie=" + ((getTie().size()>0)?getTie().get(0).getType():"null") +
                '}';
    }

    /**
     * @return a deep copy of some fields of this object without calling super.clone()
     */
    @Override
    protected @NotNull Object clone() {
        ExtendedNote Note = new ExtendedNote(lyric, time);
        Note.pitch = new Pitch();
        Note.pitch.setStep(Step.valueOf(pitch.getStep().toString()));
        Note.pitch.setOctave(pitch.getOctave());
        Note.duration = new BigDecimal(duration.intValue());
        Note.type = new NoteType();
        Note.type.setValue(type.getValue());
        return Note;
    }

}
