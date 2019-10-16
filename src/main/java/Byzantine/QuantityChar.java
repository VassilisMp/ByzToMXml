package Byzantine;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import com.google.gson.annotations.Expose;
import org.audiveris.proxymusic.*;
import org.jetbrains.annotations.NotNull;

import java.lang.String;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class QuantityChar extends ByzChar implements Comparable {
    private static final BiMap<Step, Integer> stepMap = EnumHashBiMap.create(Step.class);
    static {
        stepMap.put(Step.C,0);
        stepMap.put(Step.D,1);
        stepMap.put(Step.E,2);
        stepMap.put(Step.F,3);
        stepMap.put(Step.G,4);
        stepMap.put(Step.A,5);
        stepMap.put(Step.B,6);
    }
    //moves
    @Expose
    private Move[] moves;

    QuantityChar(int codePoint, Byzantine.ByzClass byzClass, @NotNull List<Move> moves) {
        super(codePoint, byzClass);
        this.moves = moves.toArray(new Move[0]);
    }

    QuantityChar(int codePoint, Byzantine.ByzClass byzClass, Move... moves) {
        super(codePoint, byzClass);
        this.moves = moves;
    }

    Move[] getMoves() {
        return moves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        QuantityChar that = (QuantityChar) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(moves, that.moves);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(moves);
        return result;
    }

    @Override
    public String toString() {
        return "QuantityChar{" +
                super.toString() +
                "moves=" + Arrays.toString(moves) +
                "} ";
    }

    @Override
    protected ByzChar clone() {
        QuantityChar clone = (QuantityChar) super.clone();
        clone.moves = Move.movesClone(this.moves);
        return clone;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        return 0;
    }

    /*@Override
    protected Object clone() {
        QuantityChar clone;
        try {
            clone = (QuantityChar) super.clone();
            clone.moves = Arrays.stream(moves).map(Move::new).toArray(Move[]::new);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            clone = new QuantityChar(codePoint)
        }
        return clone;
    }*/

    public void accept(Engine engine) {
        for (Move move : moves) {
            Note note = new ExtendedNote(move.getLyric(), move.getTime());
            Pitch pitch = getPitch(engine.noteList);
            Step step = pitch.getStep();
            int octave = pitch.getOctave();
            int stepNum = stepMap.get(step);
            String strNum = octave + "" + stepNum;
            int parsedInt = Integer.parseInt(strNum, 7);

            String newPitch = Integer.toString(parsedInt + move.getMove(), 7);
            engine.noteList.add(note);

            // Pitch
            Pitch thisPitch = new Pitch();
            note.setPitch(thisPitch);
            int thisStepNum = Integer.parseInt(newPitch.charAt(1) + "");
            thisPitch.setStep(stepMap.inverse().get(thisStepNum));
            int thisOctave = Integer.parseInt(newPitch.charAt(0) + "");
            thisPitch.setOctave(thisOctave);

            // Duration
            note.setDuration(new BigDecimal(engine.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        if (this.getText() != null && !this.getText().equals("")) {
            Note fNote = engine.noteList.get(engine.noteList.size() - moves.length);
            Lyric lyric = new Lyric();
            lyric.getElisionAndSyllabicAndText().add(Syllabic.SINGLE);
            TextElementData textElementData = new TextElementData();
            textElementData.setValue(this.getText());
            lyric.getElisionAndSyllabicAndText().add(textElementData);
            fNote.getLyric().add(lyric);
        }
    }

    // this method was created to overpass previous notes that are rests
    @NotNull
    static Pitch getPitch(@NotNull List<Note> notes) {
        ListIterator<Note> iterator = notes.listIterator(notes.size());
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
