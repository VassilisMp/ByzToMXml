package Byzantine;

import org.audiveris.proxymusic.Note;
import org.audiveris.proxymusic.NoteType;
import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuantityCharTest {

    private static List<Note> noteList;
    private static QuantityChar qChar;


    @BeforeAll
    static void setUpBeforeAll() throws Exception {
        Main.noteList = new ArrayList<>();
        noteList = Main.noteList;
        Note note = new ExtendedNote(true, false);
        noteList.add(note);

        // Pitch
        Pitch pitch = new Pitch();
        note.setPitch(pitch);
        pitch.setStep(Step.C);
        pitch.setOctave(4);

        // Duration
        note.setDuration(new BigDecimal(TimeChar.division));

        // Type
        NoteType type = new NoteType();
        type.setValue("quarter");
        note.setType(type);
    }
    @AfterAll
    static void tearDownAfterAll() throws Exception {

        noteList.clear();
        qChar = null;
    }

    @Test
    void run() {
        qChar = new QuantityChar(225, "", ByzClass.B, Collections.singletonList(
                new Move(2, false, true)
        ));
        qChar.run();
        ExtendedNote note = (ExtendedNote) noteList.get(1);
        Pitch pitch = note.getPitch();
        int octave = pitch.getOctave();
        Step step = pitch.getStep();
        boolean lyric = note.canGetLyric();
        boolean time = note.canGetTime();

        assertAll("Should be Step.E and octave 4",
                () -> assertEquals(Step.E, step),
                () -> assertEquals(4, octave),
                () -> assertFalse(lyric),
                () -> assertTrue(time));
    }
}