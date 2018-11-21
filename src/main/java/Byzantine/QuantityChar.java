package Byzantine;

import org.audiveris.proxymusic.Note;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class QuantityChar extends ByzChar implements Comparable {
    private static final long serialVersionUID = 976878594555516894L;
    //moves
    Move[] moves;

    public QuantityChar(int codePoint, String font, Byzantine.ByzClass byzClass, List<Move> moves) {
        super(codePoint, font, byzClass);
        this.moves = moves.toArray(new Move[0]);
    }

    @Override
    public String toString() {
        return "QuantityChar{" +
                "moves=" + Arrays.toString(moves) +
                ", ByzClass=" + ByzClass +
                ", codePoint=" + codePoint +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuantityChar)) return false;
        if (!super.equals(o)) return false;
        QuantityChar that = (QuantityChar) o;
        return Arrays.equals(moves, that.moves);
    }

    @Override
    public int hashCode() {

        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(moves);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public void run() {
        for (Move move : moves) {
            Note note = new ExtendedNote(move.getLyric(), move.getTime());

        }
    }

}
