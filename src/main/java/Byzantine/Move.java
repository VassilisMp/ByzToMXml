package Byzantine;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Move implements Serializable {
    private static final long serialVersionUID = 4704489479038189979L;

    private int move;
    private Boolean lyric;
    private Boolean time;

    public Move(int move, Boolean lyric, Boolean time) {
        this.move = move;
        this.lyric = lyric;
        this.time = time;
    }

    public Move(Move move) {
        this.move = move.move;
        this.lyric = move.lyric;
        this.time = move.time;
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