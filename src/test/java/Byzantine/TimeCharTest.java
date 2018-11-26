package Byzantine;

import org.audiveris.proxymusic.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.String;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TimeCharTest {
    private List<Note> noteList;
    private TimeChar timeChar;

    @BeforeAll
    static void BeforeAll() {
        Main.noteList = null;
        TimeChar.division = 1;
    }

    @AfterEach
    void TearDown() {
        Main.noteList = null;
        noteList = null;
        TimeChar.division = 1;
    }
    @Test
    void testGorgon() {
        noteList = new ArrayList<>();
        Main.noteList = noteList;

        for (int i = 0; i < 2; i++) {
            // Note
            Note note;
            if (i == 1)
                note = new ExtendedNote(true, true);
            else
                note = new ExtendedNote(true, false);
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
        timeChar = new TimeChar(234, "", ByzClass.B, 0, 1, false);
        //System.out.println(noteList);
        timeChar.run();
        //System.out.println(noteList);
        assertAll(
                () -> assertEquals(1, noteList.get(0).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(0).getType().getValue()),
                () -> assertEquals(1, noteList.get(1).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(1).getType().getValue())
        );
    }

    @Test
    void testDiGorgon() {
        noteList = new ArrayList<>();
        Main.noteList = noteList;

        for (int i = 0; i < 3; i++) {
            // Note
            Note note;
            if (i == 1)
                note = new ExtendedNote(true, true);
            else
                note = new ExtendedNote(true, false);
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
        timeChar = new TimeChar(234, "", ByzClass.B, 0, 2, false);
        //System.out.println(noteList);
        timeChar.run();
        //System.out.println(noteList);
        assertAll(
                () -> assertEquals(1, noteList.get(0).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(0).getType().getValue()),
                () -> assertEquals(1, noteList.get(1).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(1).getType().getValue()),
                () -> assertEquals(1, noteList.get(2).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(2).getType().getValue())
        );
    }

    @Test
    void testTriGorgon() {
        noteList = new ArrayList<>();
        Main.noteList = noteList;

        for (int i = 0; i < 4; i++) {
            // Note
            Note note;
            if (i == 1)
                note = new ExtendedNote(true, true);
            else
                note = new ExtendedNote(true, false);
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
        timeChar = new TimeChar(234, "", ByzClass.B, 0, 3, false);
        //System.out.println(noteList);
        timeChar.run();
        //System.out.println(noteList);
        assertAll(
                () -> assertEquals(1, noteList.get(0).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(0).getType().getValue()),
                () -> assertEquals(1, noteList.get(1).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(1).getType().getValue()),
                () -> assertEquals(1, noteList.get(2).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(2).getType().getValue()),
                () -> assertEquals(1, noteList.get(3).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(3).getType().getValue())
        );
    }

    @Test
    void testGorgonXronosGorgon() {
        TimeChar.division = 4;
        TimeChar.mapValuesInsert();
        noteList = new ArrayList<>();
        Main.noteList = noteList;

        for (int i = 0; i < 4; i++) {
            // Note
            Note note;
            if (i == 1 | i == 2)
                note = new ExtendedNote(true, true);
            else
                note = new ExtendedNote(true, false);
            noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            if (i == 0) {
                // Duration
                note.setDuration(new BigDecimal(TimeChar.division / 2));
                // Type
                NoteType type = new NoteType();
                type.setValue("eighth");
                note.setType(type);
            } else if (i == 1) {
                // Duration
                note.setDuration(new BigDecimal(TimeChar.division + 2));
                // Type
                NoteType type = new NoteType();
                type.setValue("quarter");
                note.setType(type);
                note.getDot().add(new EmptyPlacement());
            } else {
                // Duration
                note.setDuration(new BigDecimal(TimeChar.division));
                // Type
                NoteType type = new NoteType();
                type.setValue("quarter");
                note.setType(type);
            }
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 0, 1, false);
        //System.out.println(noteList);
        timeChar.run();
        //System.out.println(noteList);
        assertAll(
                () -> assertEquals(2, noteList.get(0).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(0).getType().getValue()),
                () -> assertEquals(4, noteList.get(1).getDuration().intValue()),
                () -> assertEquals("quarter", noteList.get(1).getType().getValue()),
                () -> assertEquals(0, noteList.get(1).getDot().size()),
                () -> assertEquals(2, noteList.get(2).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(2).getType().getValue()),
                () -> assertEquals(4, noteList.get(3).getDuration().intValue()),
                () -> assertEquals("quarter", noteList.get(3).getType().getValue())
        );
    }

    @Test
    void testIfNullInNotations() {
        assertAll(
                () -> assertNotNull(new ExtendedNote(true, true).getNotations()),
                () ->assertNotNull(new Notations().getTiedOrSlurOrTuplet())
        );
    }

    private String tupletToString(Tuplet tuplet) {
        return "Tuplet{" +
                "Bracket=" + tuplet.getBracket().value() +
                ", number=" + tuplet.getNumber() +
                ", placement=" + tuplet.getPlacement().value() +
                ", type=" + tuplet.getType().value() +
                '}';
    }
}