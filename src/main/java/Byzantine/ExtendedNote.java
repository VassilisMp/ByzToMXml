package Byzantine;

import org.audiveris.proxymusic.Note;

public final class ExtendedNote extends Note {
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

    @Override
    public String toString() {
        return "ExtendedNote{" +
                "lyric=" + lyric +
                ", time=" + time +
                ", pitch=" + pitch.getStep() + pitch.getOctave() +
                '}';
    }
}