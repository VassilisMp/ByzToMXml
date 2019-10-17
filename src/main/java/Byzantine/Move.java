package Byzantine;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

class Move implements Cloneable {

    @Expose
    private final int move;
    @Expose
    private final boolean lyric;
    @Expose
    private boolean time;

    Move(int move, boolean lyric, boolean time) {
        this.move = move;
        this.lyric = lyric;
        this.time = time;
    }

    private Move(Move move) {
        this.move = move.move;
        this.lyric = move.lyric;
        this.time = move.time;
    }

    int getMove() {
        return move;
    }

    boolean getLyric() {
        return lyric;
    }

    boolean getTime() {
        return time;
    }

    void setTime(boolean time) {
        this.time = time;
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

    @Override
    protected Move clone() {
        try {
            return (Move)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return new Move(this);
        }
    }

    static Move[] movesClone(Move[] moves) {
        return Arrays.stream(moves).map(Move::clone).toArray(Move[]::new);
    }
}