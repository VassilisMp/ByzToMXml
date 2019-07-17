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
        TimeChar.division = 1;
    }

    @AfterEach
    void TearDown() {
        noteList = null;
        TimeChar.division = 1;
        TimeChar.mapValuesInsert();
    }
    @Test
    void testGorgon() {
        noteList = new ArrayList<>();

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
        timeChar.accept(noteList);
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
        TimeChar.division = 1;
        timeChar.accept(noteList);
        System.out.println(noteList);
        assertAll(
                () -> assertEquals(2, noteList.get(0).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(0).getType().getValue()),
                () -> assertEquals(StartStop.START, ((Tuplet)noteList.get(0).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType()),
                () -> assertEquals(2, noteList.get(1).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(1).getType().getValue()),
                () -> assertEquals(2, noteList.get(2).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(2).getType().getValue()),
                () -> assertEquals(StartStop.STOP, ((Tuplet)noteList.get(2).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType())
        );
    }

    @Test
    void testTriGorgon() {
        noteList = new ArrayList<>();


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
        timeChar.accept(noteList);
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
    void testTetraGorgon() {
        noteList = new ArrayList<>();


        for (int i = 0; i < 5; i++) {
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
        timeChar = new TimeChar(234, "", ByzClass.B, 0, 4, false);
        //System.out.println(noteList);
        timeChar.accept(noteList);
        //System.out.println(noteList);
        assertAll(
                () -> assertEquals(4, noteList.get(0).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(0).getType().getValue()),
                () -> assertEquals(StartStop.START, ((Tuplet)noteList.get(0).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType()),
                () -> assertEquals(4, noteList.get(1).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(1).getType().getValue()),
                () -> assertEquals(4, noteList.get(2).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(2).getType().getValue()),
                () -> assertEquals(4, noteList.get(3).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(3).getType().getValue()),
                () -> assertEquals(4, noteList.get(4).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(4).getType().getValue()),
                () -> assertEquals(StartStop.STOP, ((Tuplet)noteList.get(4).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType())
        );
    }

    @Test
    void testXronos1Gorgon() {
        noteList = new ArrayList<>();


        for (int i = 0; i < 2; i++) {
            // Note
            Note note;
            note = new ExtendedNote(true, true);
            noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            if (i==0) {
                // Duration
                note.setDuration(new BigDecimal(TimeChar.division*2));

                // Type
                NoteType type = new NoteType();
                type.setValue("half");
                note.setType(type);
                continue;
            }

            // Duration
            note.setDuration(new BigDecimal(TimeChar.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 0, 1, false);
        System.out.println(noteList);
        timeChar.accept(noteList);
        System.out.println(noteList);
        assertAll(
                () -> assertEquals(3, noteList.get(0).getDuration().intValue()),
                () -> assertEquals("quarter", noteList.get(0).getType().getValue()),
                () -> assertNotNull(noteList.get(0).getDot().get(0)),
                () -> assertEquals(1, noteList.get(1).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(1).getType().getValue())
        );
    }

    @Test
    void testXronos1DiGorgon() {
        noteList = new ArrayList<>();


        for (int i = 0; i < 3; i++) {
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
                note.setDuration(new BigDecimal(TimeChar.division*2));

                // Type
                NoteType type = new NoteType();
                type.setValue("half");
                note.setType(type);
                continue;
            }

            // Duration
            note.setDuration(new BigDecimal(TimeChar.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 0, 2, false);
        System.out.println(noteList);
        timeChar.accept(noteList);
        System.out.println(noteList);
        assertAll(
                () -> assertEquals(6, noteList.get(0).getDuration().intValue()),
                () -> assertEquals("quarter", noteList.get(0).getType().getValue()),
                () -> assertEquals(StartStop.START, noteList.get(0).getTie().get(0).getType()),
                () -> assertEquals(2, noteList.get(1).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(1).getType().getValue()),
                () -> assertEquals(StartStop.START, ((Tuplet)noteList.get(1).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType()),
                () -> assertEquals(StartStop.STOP, noteList.get(1).getTie().get(0).getType()),
                () -> assertEquals(2, noteList.get(2).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(2).getType().getValue()),
                () -> assertEquals(2, noteList.get(3).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(3).getType().getValue()),
                () -> assertEquals(StartStop.STOP, ((Tuplet)noteList.get(3).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType())
        );
    }

    @Test
    void testXronos1TriGorgon() {
        noteList = new ArrayList<>();


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
                note.setDuration(new BigDecimal(TimeChar.division*2));

                // Type
                NoteType type = new NoteType();
                type.setValue("half");
                note.setType(type);
                continue;
            }

            // Duration
            note.setDuration(new BigDecimal(TimeChar.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 0, 3, false);
        System.out.println(noteList);
        timeChar.accept(noteList);
        System.out.println(noteList);
        assertAll(
                () -> assertEquals(4, noteList.get(0).getDuration().intValue()),
                () -> assertEquals("quarter", noteList.get(0).getType().getValue()),
                () -> assertEquals(StartStop.START, noteList.get(0).getTie().get(0).getType()),
                () -> assertEquals(1, noteList.get(1).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(1).getType().getValue()),
                () -> assertEquals(StartStop.STOP, noteList.get(1).getTie().get(0).getType()),
                () -> assertEquals(1, noteList.get(2).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(2).getType().getValue()),
                () -> assertEquals(1, noteList.get(3).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(3).getType().getValue()),
                () -> assertEquals(1, noteList.get(4).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(4).getType().getValue())
        );
    }

    @Test
    void testXronos1TetraGorgon() {
        noteList = new ArrayList<>();


        for (int i = 0; i < 5; i++) {
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
                note.setDuration(new BigDecimal(TimeChar.division*2));

                // Type
                NoteType type = new NoteType();
                type.setValue("half");
                note.setType(type);
                continue;
            }

            // Duration
            note.setDuration(new BigDecimal(TimeChar.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 0, 4, false);
        System.out.println(noteList);
        timeChar.accept(noteList);
        System.out.println(noteList);
        assertAll(
                () -> assertEquals(20, noteList.get(0).getDuration().intValue()),
                () -> assertEquals("quarter", noteList.get(0).getType().getValue()),
                () -> assertEquals(StartStop.START, noteList.get(0).getTie().get(0).getType()),
                () -> assertEquals(4, noteList.get(1).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(1).getType().getValue()),
                () -> assertEquals(StartStop.START, ((Tuplet)noteList.get(1).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType()),
                () -> assertEquals(StartStop.STOP, noteList.get(1).getTie().get(0).getType()),
                () -> assertEquals(4, noteList.get(2).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(2).getType().getValue()),
                () -> assertEquals(4, noteList.get(3).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(3).getType().getValue()),
                () -> assertEquals(4, noteList.get(4).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(4).getType().getValue()),
                () -> assertEquals(4, noteList.get(5).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(5).getType().getValue()),
                () -> assertEquals(StartStop.STOP, ((Tuplet)noteList.get(5).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType())
        );
    }

    @Test
    void testXronos2Gorgon() {
        noteList = new ArrayList<>();


        for (int i = 0; i < 2; i++) {
            // Note
            Note note;
            note = new ExtendedNote(true, true);
            noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            if (i==1) {
                // Duration
                note.setDuration(new BigDecimal(TimeChar.division*2));

                // Type
                NoteType type = new NoteType();
                type.setValue("half");
                note.setType(type);
                continue;
            }

            // Duration
            note.setDuration(new BigDecimal(TimeChar.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 0, 1, false);
        System.out.println(noteList);
        timeChar.accept(noteList);
        System.out.println(noteList);
        assertAll(
                () -> assertEquals(1, noteList.get(0).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(0).getType().getValue()),
                () -> assertEquals(3, noteList.get(1).getDuration().intValue()),
                () -> assertEquals("quarter", noteList.get(1).getType().getValue()),
                () -> assertNotNull(noteList.get(1).getDot().get(0))
        );
    }

    @Test
    void testDot1Gorgon() {
        noteList = new ArrayList<>();


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
        timeChar = new TimeChar(234, "", ByzClass.B, 1, 1, false);
        //System.out.println(noteList);
        timeChar.accept(noteList);
        //System.out.println(noteList);
        assertAll(
                () -> assertEquals(4, noteList.get(0).getDuration().intValue()),
                () -> assertEquals("quarter", noteList.get(0).getType().getValue()),
                () -> assertEquals(StartStop.START, ((Tuplet)noteList.get(0).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType()),
                () -> assertEquals(2, noteList.get(1).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(1).getType().getValue()),
                () -> assertEquals(StartStop.STOP, ((Tuplet)noteList.get(1).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType())
        );
    }

    @Test
    void testDot2Gorgon() {
        noteList = new ArrayList<>();


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
        timeChar = new TimeChar(234, "", ByzClass.B, 2, 1, false);
        //System.out.println(noteList);
        timeChar.accept(noteList);
        //System.out.println(noteList);
        assertAll(
                () -> assertEquals(2, noteList.get(0).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(0).getType().getValue()),
                () -> assertEquals(StartStop.START, ((Tuplet)noteList.get(0).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType()),
                () -> assertEquals(4, noteList.get(1).getDuration().intValue()),
                () -> assertEquals("quarter", noteList.get(1).getType().getValue()),
                () -> assertEquals(StartStop.STOP, ((Tuplet)noteList.get(1).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType())
        );
    }

    @Test
    void testDot1DiGorgon() {
        noteList = new ArrayList<>();


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
        timeChar = new TimeChar(234, "", ByzClass.B, 1, 2, false);
        //System.out.println(noteList);
        timeChar.accept(noteList);
        //System.out.println(noteList);
        assertAll(
                () -> assertEquals(2, noteList.get(0).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(0).getType().getValue()),
                () -> assertEquals(1, noteList.get(1).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(1).getType().getValue()),
                () -> assertEquals(1, noteList.get(2).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(2).getType().getValue())
        );
    }

    @Test
    void testDot1TriGorgon() {
        noteList = new ArrayList<>();


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
        timeChar = new TimeChar(234, "", ByzClass.B, 1, 3, false);
        //System.out.println(noteList);
        timeChar.accept(noteList);
        //System.out.println(noteList);
        assertAll(
                () -> assertEquals(8, noteList.get(0).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(0).getType().getValue()),
                () -> assertEquals(StartStop.START, ((Tuplet)noteList.get(0).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType()),
                () -> assertEquals(4, noteList.get(1).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(1).getType().getValue()),
                () -> assertEquals(4, noteList.get(2).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(2).getType().getValue()),
                () -> assertEquals(4, noteList.get(3).getDuration().intValue()),
                () -> assertEquals("16th", noteList.get(3).getType().getValue()),
                () -> assertEquals(StartStop.STOP, ((Tuplet)noteList.get(3).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType())
        );
    }

    @Test
    void testXronos1Dot1Gorgon() {
        noteList = new ArrayList<>();


        for (int i = 0; i < 2; i++) {
            // Note
            Note note;
            note = new ExtendedNote(true, true);
            noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            if (i==0) {
                // Duration
                note.setDuration(new BigDecimal(TimeChar.division*2));

                // Type
                NoteType type = new NoteType();
                type.setValue("half");
                note.setType(type);
                continue;
            }

            // Duration
            note.setDuration(new BigDecimal(TimeChar.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 1, 1, false);
        //System.out.println(noteList);
        timeChar.accept(noteList);
        //System.out.println(noteList);
        assertAll(
                () -> assertEquals(6, noteList.get(0).getDuration().intValue()),
                () -> assertEquals("quarter", noteList.get(0).getType().getValue()),
                () -> assertEquals(StartStop.START, noteList.get(0).getTie().get(0).getType()),
                () -> assertEquals(4, noteList.get(1).getDuration().intValue()),
                () -> assertEquals("quarter", noteList.get(1).getType().getValue()),
                () -> assertEquals(StartStop.STOP, noteList.get(1).getTie().get(0).getType()),
                () -> assertEquals(StartStop.START, ((Tuplet)noteList.get(1).getNotations().get(0).getTiedOrSlurOrTuplet().get(1)).getType()),
                () -> assertEquals(2, noteList.get(2).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(2).getType().getValue()),
                () -> assertEquals(StartStop.STOP, ((Tuplet)noteList.get(2).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType())
        );
    }

    @Test
    void testXronos2Dot1Gorgon() {
        noteList = new ArrayList<>();


        for (int i = 0; i < 2; i++) {
            // Note
            Note note;
            note = new ExtendedNote(true, true);
            noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            if (i==1) {
                // Duration
                note.setDuration(new BigDecimal(TimeChar.division*2));

                // Type
                NoteType type = new NoteType();
                type.setValue("half");
                note.setType(type);
                continue;
            }

            // Duration
            note.setDuration(new BigDecimal(TimeChar.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 1, 1, false);
        //System.out.println(noteList);
        timeChar.accept(noteList);
        //System.out.println(noteList);
        assertAll(
                () -> assertEquals(4, noteList.get(0).getDuration().intValue()),
                () -> assertEquals("quarter", noteList.get(0).getType().getValue()),
                () -> assertEquals(StartStop.START, ((Tuplet)noteList.get(0).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType()),
                () -> assertEquals(2, noteList.get(1).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(1).getType().getValue()),
                () -> assertEquals(StartStop.STOP, ((Tuplet)noteList.get(1).getNotations().get(0).getTiedOrSlurOrTuplet().get(1)).getType()),
                () -> assertEquals(StartStop.START, noteList.get(1).getTie().get(0).getType()),
                () -> assertEquals(6, noteList.get(2).getDuration().intValue()),
                () -> assertEquals("quarter", noteList.get(2).getType().getValue()),
                () -> assertEquals(StartStop.STOP, noteList.get(2).getTie().get(0).getType())
        );
    }

    @Test
    void testVaria_Apli() {
        noteList = new ArrayList<>();


        Note note;
        note = new ExtendedNote(true, true);
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

        timeChar = new TimeChar(92, "", ByzClass.L, 0, -1, false);
        //System.out.println(noteList);
        timeChar.accept(noteList);
        //System.out.println(noteList);
        assertAll(
                () -> assertEquals(TimeChar.division, noteList.get(0).getDuration().intValue()),
                () -> assertEquals("quarter", noteList.get(0).getType().getValue()),
                () -> assertEquals(TimeChar.division, noteList.get(1).getDuration().intValue()),
                () -> assertEquals("quarter", noteList.get(1).getType().getValue()),
                () -> assertNull(noteList.get(1).getPitch())
        );
    }

    /*@Test
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
            pitch.setStep(Step.HARD_DIATONIC);
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
        timeChar.accept(noteList);
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
    void testDiGorgonXronosGorgon() { // TODO make testDiGorgonXronosGorgon() work
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
            pitch.setStep(Step.HARD_DIATONIC);
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
        timeChar = new TimeChar(234, "", ByzClass.B, 0, 2, false);
        //System.out.println(noteList);
        timeChar.accept(noteList);
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
    }*/

    // TODO test duration adds of Xronos after notes after digorgon or more compicated TimeChar, to get more complicated durations
    @Test // Tsakisma, apli, dipli..
    void testXronos() {
        noteList = new ArrayList<>();


        for (int i = 0; i < 2; i++) {
            // Note
            Note note;
            if (i == 0)
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
        timeChar = new TimeChar(234, "", ByzClass.B, 0, -1, false);
        //System.out.println(noteList);
        timeChar.accept(noteList);
        //System.out.println(noteList);
        assertAll(
                () -> assertEquals(2, noteList.get(0).getDuration().intValue()),
                () -> assertEquals("half", noteList.get(0).getType().getValue()),
                () -> assertEquals(1, noteList.get(1).getDuration().intValue()),
                () -> assertEquals("quarter", noteList.get(1).getType().getValue())
        );
    }

    @Test
    void testGorgonXronos() {
        noteList = new ArrayList<>();


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
        timeChar = new TimeChar(235, "", ByzClass.B, 0, 1, false);
        //System.out.println(noteList);
        timeChar.accept(noteList);
        timeChar = new TimeChar(234, "", ByzClass.B, 0, -1, false);
        //System.out.println(noteList);
        timeChar.accept(noteList);
        System.out.println(noteList);
        assertAll(
                () -> assertEquals(1, noteList.get(0).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(0).getType().getValue()),
                () -> assertEquals(3, noteList.get(1).getDuration().intValue()),
                () -> assertEquals("quarter.", noteList.get(1).getType().getValue()),
                () -> assertEquals(1, noteList.get(1).getDot().size())
        );
    }

    @Test
    void testGorgonXronos2() {
        noteList = new ArrayList<>();


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
        timeChar = new TimeChar(235, "", ByzClass.B, 0, 1, false);
        //System.out.println(noteList);
        timeChar.accept(noteList);
        timeChar = new TimeChar(57, "", ByzClass.B, 0, -2, false);
        //System.out.println(noteList);
        timeChar.accept(noteList);
        System.out.println(noteList);
        assertAll(
                () -> assertEquals(1, noteList.get(0).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(0).getType().getValue()),
                () -> assertEquals(4, noteList.get(1).getDuration().intValue()),
                () -> assertEquals("half", noteList.get(1).getType().getValue()),
                () -> assertEquals(1, noteList.get(2).getDuration().intValue()),
                () -> assertEquals("eighth", noteList.get(2).getType().getValue())
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