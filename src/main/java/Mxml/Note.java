package Mxml;

import org.audiveris.proxymusic.NoteType;
import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;

import java.math.BigDecimal;

public final class Note extends org.audiveris.proxymusic.Note implements Cloneable {

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
    private Integer accidentalCommmas = null;

    public Note(Note note) {
        this(note.lyric, note.time);
        pitch = new Pitch();
        pitch.setStep(Step.valueOf(note.pitch.getStep().toString()));
        pitch.setOctave(note.pitch.getOctave());
        duration = new BigDecimal(note.duration.intValue());
        type = new NoteType();
        type.setValue(note.type.getValue());
    }

    public Note(boolean lyric, boolean time) {
        this.lyric = lyric;
        this.time = time;
    }

    public Note(boolean lyric, boolean time, Step step, int octave, int duration, String noteType) {
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

    public Integer getAccidentalCommmas() {
        return accidentalCommmas;
    }

    public void setAccidentalCommmas(int accidentalCommmas) {
        this.accidentalCommmas = accidentalCommmas;
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
}
