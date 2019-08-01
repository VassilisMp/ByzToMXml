package Byzantine;

import org.audiveris.proxymusic.Note;
import org.audiveris.proxymusic.NoteType;
import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ClonerTest {

    @Test
    void NoteDeepClone() {
        Note note = new ExtendedNote(true, true);
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

        Note cloneNote = Cloner.deepClone(note);
        cloneNote.getPitch().setStep(Step.E);
        cloneNote.getPitch().setOctave(2);

        System.out.println(note + "\n" + cloneNote);
    }
}