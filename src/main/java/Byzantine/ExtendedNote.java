package Byzantine;

import org.audiveris.proxymusic.Note;
import org.audiveris.proxymusic.NoteType;
import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;

import java.math.BigDecimal;

public final class ExtendedNote extends Note implements Cloneable {
    private boolean lyric;
    private boolean time;

    public ExtendedNote(Boolean lyric, Boolean time) {
        this.lyric = lyric;
        this.time = time;
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

    @Override
    protected Object clone() {
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
