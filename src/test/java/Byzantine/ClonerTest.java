package Byzantine;

import Mxml.Note;
import org.audiveris.proxymusic.NoteType;
import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ClonerTest {

    @Test
    void NoteDeepClone() {
        org.audiveris.proxymusic.Note note = new Note(true, true);
        // Pitch
        Pitch pitch = new Pitch();
        note.setPitch(pitch);
        pitch.setStep(Step.C);
        pitch.setOctave(4);

        // Duration
        note.setDuration(new BigDecimal(1));

        // Type
        NoteType type = new NoteType();
        type.setValue("quarter");
        note.setType(type);

        org.audiveris.proxymusic.Note cloneNote = Cloner.deepClone(note);
        cloneNote.getPitch().setStep(Step.E);
        cloneNote.getPitch().setOctave(2);

        System.out.println(note + "\n" + cloneNote);
    }

    @Test
    void test() throws ClassNotFoundException {
        Class<ByzChar> byzCharClass = ByzChar.class;
        Class<ByzChar> byzCharClass2 = ByzChar.class;
        System.out.println(byzCharClass.getName());
        System.out.println(Class.forName(QuantityChar.class.getName()));
    }
}