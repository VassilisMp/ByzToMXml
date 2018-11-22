package Byzantine;

import org.audiveris.proxymusic.Note;
import org.audiveris.proxymusic.NoteType;
import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
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
        //System.out.println(Main.noteList.get(0));
        for (Move move : moves) {
            Note note = new ExtendedNote(move.getLyric(), move.getTime());

            Note lastNote = Main.noteList.get(Main.noteList.size() - 1);
            Pitch pitch = lastNote.getPitch();
            Step step = pitch.getStep();
            int octave = pitch.getOctave();
            int stepNum = stepMap.get(step);
            String strNum = octave + "" + stepNum;
            int parsedInt = Integer.parseInt(strNum, 7);

            String newPitch = Integer.toString(parsedInt + move.getMove(), 7);

            Main.noteList.add(note);

            // Pitch
            Pitch thisPitch = new Pitch();
            note.setPitch(thisPitch);
            int thisStepNum = Integer.parseInt(newPitch.charAt(1) + "");
            thisPitch.setStep(stepMap.inverse().get(thisStepNum));
            int thisOctave = Integer.parseInt(newPitch.charAt(0) + "");
            thisPitch.setOctave(thisOctave);

            // Duration
            note.setDuration(new BigDecimal(1));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);

            System.out.println(note);
        }
    }

}
