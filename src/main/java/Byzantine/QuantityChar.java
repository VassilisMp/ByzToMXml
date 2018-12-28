package Byzantine;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import org.audiveris.proxymusic.Note;
import org.audiveris.proxymusic.NoteType;
import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class QuantityChar extends ByzChar implements Comparable {
    private final static BiMap<Step, Integer> stepMap = EnumHashBiMap.create(Step.class);
    static {
        stepMap.put(Step.C,0);
        stepMap.put(Step.D,1);
        stepMap.put(Step.E,2);
        stepMap.put(Step.F,3);
        stepMap.put(Step.G,4);
        stepMap.put(Step.A,5);
        stepMap.put(Step.B,6);
    }
    private static final long serialVersionUID = 976878594555516894L;
    //moves
    private Move[] moves;

    public QuantityChar(int codePoint, String font, Byzantine.ByzClass byzClass, @NotNull List<Move> moves) {
        super(codePoint, font, byzClass);
        this.moves = moves.toArray(new Move[0]);
    }

    public QuantityChar(int codePoint, String font, Byzantine.ByzClass byzClass, Move... moves) {
        super(codePoint, font, byzClass);
        this.moves = moves;
    }

    public Move[] getMoves() {
        return moves;
    }

    @Override
    public String toString() {
        return "QuantityChar{" +
                "moves=" + Arrays.toString(moves) +
                ", ByzClass=" + ByzClass +
                ", codePoint=" + codePoint +
                ", font=" + font +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuantityChar)) return false;
        if (!super.equals(o)) return false;
        QuantityChar that = (QuantityChar) o;
        return Arrays.equals(moves, that.moves) && codePoint==that.codePoint && ByzClass==that.ByzClass;
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
            Pitch pitch = getPitch();
            Step step = pitch.getStep();
            int octave = pitch.getOctave();
            int stepNum = stepMap.get(step);
            String strNum = octave + "" + stepNum;
            int parsedInt = Integer.parseInt(strNum, 7);

            String newPitch = Integer.toString(parsedInt + move.getMove(), 7);
            //System.out.println(newPitch);

            Main.noteList.add(note);

            // Pitch
            Pitch thisPitch = new Pitch();
            note.setPitch(thisPitch);
            int thisStepNum = Integer.parseInt(newPitch.charAt(1) + "");
            thisPitch.setStep(stepMap.inverse().get(thisStepNum));
            int thisOctave = Integer.parseInt(newPitch.charAt(0) + "");
            thisPitch.setOctave(thisOctave);

            // Duration
            note.setDuration(new BigDecimal(TimeChar.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);

            //System.out.println(note);
        }
    }

    // this method was created to overpass previous notes that are rests
    private static Pitch getPitch() {
        ListIterator<Note> iterator = Main.noteList.listIterator(Main.noteList.size());
        Pitch pitch = null;
        while (iterator.hasPrevious()) {
            ExtendedNote exNote = (ExtendedNote) iterator.previous();
            pitch = exNote.getPitch();
            if (pitch != null) {
                break;
            }
        }
        if (pitch == null)
            throw new NullPointerException("Couldn't find a Note with pitch");
        return pitch;
    }

}
