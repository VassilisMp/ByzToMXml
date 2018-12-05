package Byzantine;

import org.audiveris.proxymusic.Note;

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

    @Override
    public String toString() {
        return "ExtendedNote{" +
                "lyric=" + lyric +
                ", time=" + time +
                ", pitch=" + pitch.getStep() + pitch.getOctave() +
                ", duration=" + duration +
                ", type=" + type.getValue() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return false;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
