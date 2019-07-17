package Byzantine;

import org.audiveris.proxymusic.Note;
import org.audiveris.proxymusic.NoteType;
import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static Byzantine.TimeChar.division;
import static org.junit.jupiter.api.Assertions.*;

class ByzClassTest {

    @Test
    void PAnnotationTest() throws NotSupportedException {
        ByzClass byzClass = ByzClass.P;
        boolean annotationPresent = false;
        try {
            annotationPresent = ByzClass.class.getField(byzClass.toString()).isAnnotationPresent(NotSupported.class);
            System.out.println(byzClass + " annotation isPresent: " + annotationPresent);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        boolean finalAnnotationPresent = annotationPresent;
        Executable executable = () -> {
            if (finalAnnotationPresent) {
                throw new NotSupportedException("ByzClass." + byzClass + " is not supported");
            }
        };

        assertThrows(NotSupportedException.class, executable);
    }

    @Test
    void BAnnotationTest() {
        ByzClass byzClass = ByzClass.B;
        try {
            boolean annotationPresent = ByzClass.class.getField(byzClass.toString()).isAnnotationPresent(NotSupported.class);
            System.out.println(byzClass + "annotation isPresent: " + annotationPresent);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Test
    void ArrayListTest() {
        List<Note> noteList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            // Note
            Note note;
            if (i == 1 || i==0)
                note = new ExtendedNote(true, true);
            else
                note = new ExtendedNote(true, false);
            noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            if (i==0) {
                // Duration
                note.setDuration(new BigDecimal(division*2));

                // Type
                NoteType type = new NoteType();
                type.setValue("half");
                note.setType(type);
                continue;
            }

            // Duration
            note.setDuration(new BigDecimal(division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        System.out.println(noteList);
        Note note = new Note();
        note.setDuration(new BigDecimal(division));
        noteList.add(0, note);
        System.out.println(noteList);
    }
}