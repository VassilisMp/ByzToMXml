package Byzantine;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Move implements Serializable {
    private static final long serialVersionUID = 4704489479038189979L;

    @Expose
    private int move;
    @Expose
    private boolean lyric;
    @Expose
    private boolean time;

    Move(int move, boolean lyric, boolean time) {
        this.move = move;
        this.lyric = lyric;
        this.time = time;
    }

    Move(Move move) {
        this.move = move.move;
        this.lyric = move.lyric;
        this.time = move.time;
    }

    int getMove() {
        return move;
    }

    Boolean getLyric() {
        return lyric;
    }

    Boolean getTime() {
        return time;
    }

    void setTime(Boolean time) {
        this.time = time;
    }

    public void setLyric(boolean lyric) {
        this.lyric = lyric;
    }

    @Override
    public String toString() {
        return "Move{" + move + (lyric?"l":"") + (time?"t":"") + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        Move move1 = (Move) o;
        return move == move1.move &&
                Objects.equals(lyric, move1.lyric) &&
                Objects.equals(time, move1.time);
    }

    @Override
    public int hashCode() {

        return Objects.hash(move, lyric, time);
    }
}