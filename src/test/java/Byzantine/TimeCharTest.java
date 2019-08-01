package Byzantine;

import org.audiveris.proxymusic.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.String;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TimeCharTest {
    private Engine engine = new Engine(1);
    private TimeChar timeChar;

    @AfterEach
    void AfterEach() {
        engine.division = 1;
        engine.noteList.clear();
    }

    @Test
    void testGorgon() {
        

        for (int i = 0; i < 2; i++) {
            // Note
            Note note;
            if (i == 1)
                note = new ExtendedNote(true, true);
            else
                note = new ExtendedNote(true, false);
            engine.noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            // Duration
            note.setDuration(new BigDecimal(engine.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 0, 1, false);
        //System.out.println(engine.noteList);
        timeChar.accept(engine);
        //System.out.println(engine.noteList);
        assertAll(
                () -> assertEquals(1, engine.noteList.get(0).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(0).getType().getValue()),
                () -> assertEquals(1, engine.noteList.get(1).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(1).getType().getValue())
        );
    }

    @Test
    void testDiGorgon() {
        


        for (int i = 0; i < 3; i++) {
            // Note
            Note note;
            if (i == 1)
                note = new ExtendedNote(true, true);
            else
                note = new ExtendedNote(true, false);
            engine.noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            // Duration
            note.setDuration(new BigDecimal(engine.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 0, 2, false);
        //System.out.println(engine.noteList);
        engine.division = 1;
        timeChar.accept(engine);
        System.out.println(engine.noteList);
        assertAll(
                () -> assertEquals(2, engine.noteList.get(0).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(0).getType().getValue()),
                () -> assertEquals(StartStop.START, ((Tuplet)engine.noteList.get(0).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType()),
                () -> assertEquals(2, engine.noteList.get(1).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(1).getType().getValue()),
                () -> assertEquals(2, engine.noteList.get(2).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(2).getType().getValue()),
                () -> assertEquals(StartStop.STOP, ((Tuplet)engine.noteList.get(2).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType())
        );
    }

    @Test
    void testTriGorgon() {
        


        for (int i = 0; i < 4; i++) {
            // Note
            Note note;
            if (i == 1)
                note = new ExtendedNote(true, true);
            else
                note = new ExtendedNote(true, false);
            engine.noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            // Duration
            note.setDuration(new BigDecimal(engine.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 0, 3, false);
        //System.out.println(engine.noteList);
        timeChar.accept(engine);
        //System.out.println(engine.noteList);
        assertAll(
                () -> assertEquals(1, engine.noteList.get(0).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(0).getType().getValue()),
                () -> assertEquals(1, engine.noteList.get(1).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(1).getType().getValue()),
                () -> assertEquals(1, engine.noteList.get(2).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(2).getType().getValue()),
                () -> assertEquals(1, engine.noteList.get(3).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(3).getType().getValue())
        );
    }

    @Test
    void testTetraGorgon() {
        


        for (int i = 0; i < 5; i++) {
            // Note
            Note note;
            if (i == 1)
                note = new ExtendedNote(true, true);
            else
                note = new ExtendedNote(true, false);
            engine.noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            // Duration
            note.setDuration(new BigDecimal(engine.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 0, 4, false);
        //System.out.println(engine.noteList);
        timeChar.accept(engine);
        //System.out.println(engine.noteList);
        assertAll(
                () -> assertEquals(4, engine.noteList.get(0).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(0).getType().getValue()),
                () -> assertEquals(StartStop.START, ((Tuplet)engine.noteList.get(0).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType()),
                () -> assertEquals(4, engine.noteList.get(1).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(1).getType().getValue()),
                () -> assertEquals(4, engine.noteList.get(2).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(2).getType().getValue()),
                () -> assertEquals(4, engine.noteList.get(3).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(3).getType().getValue()),
                () -> assertEquals(4, engine.noteList.get(4).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(4).getType().getValue()),
                () -> assertEquals(StartStop.STOP, ((Tuplet)engine.noteList.get(4).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType())
        );
    }

    @Test
    void testXronos1Gorgon() {
        


        for (int i = 0; i < 2; i++) {
            // Note
            Note note;
            note = new ExtendedNote(true, true);
            engine.noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            if (i==0) {
                // Duration
                note.setDuration(new BigDecimal(engine.division*2));

                // Type
                NoteType type = new NoteType();
                type.setValue("half");
                note.setType(type);
                continue;
            }

            // Duration
            note.setDuration(new BigDecimal(engine.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 0, 1, false);
        System.out.println(engine.noteList);
        timeChar.accept(engine);
        System.out.println(engine.noteList);
        assertAll(
                () -> assertEquals(3, engine.noteList.get(0).getDuration().intValue()),
                () -> assertEquals("quarter.", engine.noteList.get(0).getType().getValue()),
                () -> assertNotNull(engine.noteList.get(0).getDot().get(0)),
                () -> assertEquals(1, engine.noteList.get(1).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(1).getType().getValue())
        );
    }

    @Test
    void testXronos1DiGorgon() {
        


        for (int i = 0; i < 3; i++) {
            // Note
            Note note;
            if (i == 1 || i==0)
                note = new ExtendedNote(true, true);
            else
                note = new ExtendedNote(true, false);
            engine.noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            if (i==0) {
                // Duration
                note.setDuration(new BigDecimal(engine.division*2));

                // Type
                NoteType type = new NoteType();
                type.setValue("half");
                note.setType(type);
                continue;
            }

            // Duration
            note.setDuration(new BigDecimal(engine.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 0, 2, false);
        System.out.println(engine.noteList);
        timeChar.accept(engine);
        System.out.println(engine.noteList);
        assertAll(
                () -> assertEquals(6, engine.noteList.get(0).getDuration().intValue()),
                () -> assertEquals("quarter", engine.noteList.get(0).getType().getValue()),
                () -> assertEquals(StartStop.START, engine.noteList.get(0).getTie().get(0).getType()),
                () -> assertEquals(2, engine.noteList.get(1).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(1).getType().getValue()),
                () -> assertEquals(StartStop.START, ((Tuplet)engine.noteList.get(1).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType()),
                () -> assertEquals(StartStop.STOP, engine.noteList.get(1).getTie().get(0).getType()),
                () -> assertEquals(2, engine.noteList.get(2).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(2).getType().getValue()),
                () -> assertEquals(2, engine.noteList.get(3).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(3).getType().getValue()),
                () -> assertEquals(StartStop.STOP, ((Tuplet)engine.noteList.get(3).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType())
        );
    }

    @Test
    void testXronos1TriGorgon() {
        


        for (int i = 0; i < 4; i++) {
            // Note
            Note note;
            if (i == 1 || i==0)
                note = new ExtendedNote(true, true);
            else
                note = new ExtendedNote(true, false);
            engine.noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            if (i==0) {
                // Duration
                note.setDuration(new BigDecimal(engine.division*2));

                // Type
                NoteType type = new NoteType();
                type.setValue("half");
                note.setType(type);
                continue;
            }

            // Duration
            note.setDuration(new BigDecimal(engine.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 0, 3, false);
        System.out.println(engine.noteList);
        timeChar.accept(engine);
        System.out.println(engine.noteList);
        assertAll(
                () -> assertEquals(4, engine.noteList.get(0).getDuration().intValue()),
                () -> assertEquals("quarter", engine.noteList.get(0).getType().getValue()),
                () -> assertEquals(StartStop.START, engine.noteList.get(0).getTie().get(0).getType()),
                () -> assertEquals(1, engine.noteList.get(1).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(1).getType().getValue()),
                () -> assertEquals(StartStop.STOP, engine.noteList.get(1).getTie().get(0).getType()),
                () -> assertEquals(1, engine.noteList.get(2).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(2).getType().getValue()),
                () -> assertEquals(1, engine.noteList.get(3).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(3).getType().getValue()),
                () -> assertEquals(1, engine.noteList.get(4).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(4).getType().getValue())
        );
    }

    @Test
    void testXronos1TetraGorgon() {
        


        for (int i = 0; i < 5; i++) {
            // Note
            Note note;
            if (i == 1 || i==0)
                note = new ExtendedNote(true, true);
            else
                note = new ExtendedNote(true, false);
            engine.noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            if (i==0) {
                // Duration
                note.setDuration(new BigDecimal(engine.division*2));

                // Type
                NoteType type = new NoteType();
                type.setValue("half");
                note.setType(type);
                continue;
            }

            // Duration
            note.setDuration(new BigDecimal(engine.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 0, 4, false);
        System.out.println(engine.noteList);
        timeChar.accept(engine);
        System.out.println(engine.noteList);
        assertAll(
                () -> assertEquals(20, engine.noteList.get(0).getDuration().intValue()),
                () -> assertEquals("quarter", engine.noteList.get(0).getType().getValue()),
                () -> assertEquals(StartStop.START, engine.noteList.get(0).getTie().get(0).getType()),
                () -> assertEquals(4, engine.noteList.get(1).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(1).getType().getValue()),
                () -> assertEquals(StartStop.START, ((Tuplet)engine.noteList.get(1).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType()),
                () -> assertEquals(StartStop.STOP, engine.noteList.get(1).getTie().get(0).getType()),
                () -> assertEquals(4, engine.noteList.get(2).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(2).getType().getValue()),
                () -> assertEquals(4, engine.noteList.get(3).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(3).getType().getValue()),
                () -> assertEquals(4, engine.noteList.get(4).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(4).getType().getValue()),
                () -> assertEquals(4, engine.noteList.get(5).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(5).getType().getValue()),
                () -> assertEquals(StartStop.STOP, ((Tuplet)engine.noteList.get(5).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType())
        );
    }

    @Test
    void testXronos2Gorgon() {
        


        for (int i = 0; i < 2; i++) {
            // Note
            Note note;
            note = new ExtendedNote(true, true);
            engine.noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            if (i==1) {
                // Duration
                note.setDuration(new BigDecimal(engine.division*2));

                // Type
                NoteType type = new NoteType();
                type.setValue("half");
                note.setType(type);
                continue;
            }

            // Duration
            note.setDuration(new BigDecimal(engine.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 0, 1, false);
        System.out.println(engine.noteList);
        timeChar.accept(engine);
        System.out.println(engine.noteList);
        assertAll(
                () -> assertEquals(1, engine.noteList.get(0).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(0).getType().getValue()),
                () -> assertEquals(3, engine.noteList.get(1).getDuration().intValue()),
                () -> assertEquals("quarter.", engine.noteList.get(1).getType().getValue()),
                () -> assertNotNull(engine.noteList.get(1).getDot().get(0))
        );
    }

    @Test
    void testDot1Gorgon() {
        


        for (int i = 0; i < 2; i++) {
            // Note
            Note note;
            if (i == 1)
                note = new ExtendedNote(true, true);
            else
                note = new ExtendedNote(true, false);
            engine.noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            // Duration
            note.setDuration(new BigDecimal(engine.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 1, 1, false);
        //System.out.println(engine.noteList);
        timeChar.accept(engine);
        //System.out.println(engine.noteList);
        assertAll(
                () -> assertEquals(4, engine.noteList.get(0).getDuration().intValue()),
                () -> assertEquals("quarter", engine.noteList.get(0).getType().getValue()),
                () -> assertEquals(StartStop.START, ((Tuplet)engine.noteList.get(0).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType()),
                () -> assertEquals(2, engine.noteList.get(1).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(1).getType().getValue()),
                () -> assertEquals(StartStop.STOP, ((Tuplet)engine.noteList.get(1).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType())
        );
    }

    @Test
    void testDot2Gorgon() {
        


        for (int i = 0; i < 2; i++) {
            // Note
            Note note;
            if (i == 1)
                note = new ExtendedNote(true, true);
            else
                note = new ExtendedNote(true, false);
            engine.noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            // Duration
            note.setDuration(new BigDecimal(engine.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 2, 1, false);
        //System.out.println(engine.noteList);
        timeChar.accept(engine);
        //System.out.println(engine.noteList);
        assertAll(
                () -> assertEquals(2, engine.noteList.get(0).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(0).getType().getValue()),
                () -> assertEquals(StartStop.START, ((Tuplet)engine.noteList.get(0).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType()),
                () -> assertEquals(4, engine.noteList.get(1).getDuration().intValue()),
                () -> assertEquals("quarter", engine.noteList.get(1).getType().getValue()),
                () -> assertEquals(StartStop.STOP, ((Tuplet)engine.noteList.get(1).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType())
        );
    }

    @Test
    void testDot1DiGorgon() {
        


        for (int i = 0; i < 3; i++) {
            // Note
            Note note;
            if (i == 1)
                note = new ExtendedNote(true, true);
            else
                note = new ExtendedNote(true, false);
            engine.noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            // Duration
            note.setDuration(new BigDecimal(engine.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 1, 2, false);
        //System.out.println(engine.noteList);
        timeChar.accept(engine);
        //System.out.println(engine.noteList);
        assertAll(
                () -> assertEquals(2, engine.noteList.get(0).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(0).getType().getValue()),
                () -> assertEquals(1, engine.noteList.get(1).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(1).getType().getValue()),
                () -> assertEquals(1, engine.noteList.get(2).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(2).getType().getValue())
        );
    }

    @Test
    void testDot1TriGorgon() {
        


        for (int i = 0; i < 4; i++) {
            // Note
            Note note;
            if (i == 1)
                note = new ExtendedNote(true, true);
            else
                note = new ExtendedNote(true, false);
            engine.noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            // Duration
            note.setDuration(new BigDecimal(engine.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 1, 3, false);
        //System.out.println(engine.noteList);
        timeChar.accept(engine);
        //System.out.println(engine.noteList);
        assertAll(
                () -> assertEquals(8, engine.noteList.get(0).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(0).getType().getValue()),
                () -> assertEquals(StartStop.START, ((Tuplet)engine.noteList.get(0).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType()),
                () -> assertEquals(4, engine.noteList.get(1).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(1).getType().getValue()),
                () -> assertEquals(4, engine.noteList.get(2).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(2).getType().getValue()),
                () -> assertEquals(4, engine.noteList.get(3).getDuration().intValue()),
                () -> assertEquals("16th", engine.noteList.get(3).getType().getValue()),
                () -> assertEquals(StartStop.STOP, ((Tuplet)engine.noteList.get(3).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType())
        );
    }

    @Test
    void testXronos1Dot1Gorgon() {
        


        for (int i = 0; i < 2; i++) {
            // Note
            Note note;
            note = new ExtendedNote(true, true);
            engine.noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            if (i==0) {
                // Duration
                note.setDuration(new BigDecimal(engine.division*2));

                // Type
                NoteType type = new NoteType();
                type.setValue("half");
                note.setType(type);
                continue;
            }

            // Duration
            note.setDuration(new BigDecimal(engine.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 1, 1, false);
        //System.out.println(engine.noteList);
        timeChar.accept(engine);
        //System.out.println(engine.noteList);
        assertAll(
                () -> assertEquals(6, engine.noteList.get(0).getDuration().intValue()),
                () -> assertEquals("quarter", engine.noteList.get(0).getType().getValue()),
                () -> assertEquals(StartStop.START, engine.noteList.get(0).getTie().get(0).getType()),
                () -> assertEquals(4, engine.noteList.get(1).getDuration().intValue()),
                () -> assertEquals("quarter", engine.noteList.get(1).getType().getValue()),
                () -> assertEquals(StartStop.STOP, engine.noteList.get(1).getTie().get(0).getType()),
                () -> assertEquals(StartStop.START, ((Tuplet)engine.noteList.get(1).getNotations().get(0).getTiedOrSlurOrTuplet().get(1)).getType()),
                () -> assertEquals(2, engine.noteList.get(2).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(2).getType().getValue()),
                () -> assertEquals(StartStop.STOP, ((Tuplet)engine.noteList.get(2).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType())
        );
    }

    @Test
    void testXronos2Dot1Gorgon() {
        


        for (int i = 0; i < 2; i++) {
            // Note
            Note note;
            note = new ExtendedNote(true, true);
            engine.noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            if (i==1) {
                // Duration
                note.setDuration(new BigDecimal(engine.division*2));

                // Type
                NoteType type = new NoteType();
                type.setValue("half");
                note.setType(type);
                continue;
            }

            // Duration
            note.setDuration(new BigDecimal(engine.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 1, 1, false);
        //System.out.println(engine.noteList);
        timeChar.accept(engine);
        //System.out.println(engine.noteList);
        assertAll(
                () -> assertEquals(4, engine.noteList.get(0).getDuration().intValue()),
                () -> assertEquals("quarter", engine.noteList.get(0).getType().getValue()),
                () -> assertEquals(StartStop.START, ((Tuplet)engine.noteList.get(0).getNotations().get(0).getTiedOrSlurOrTuplet().get(0)).getType()),
                () -> assertEquals(2, engine.noteList.get(1).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(1).getType().getValue()),
                () -> assertEquals(StartStop.STOP, ((Tuplet)engine.noteList.get(1).getNotations().get(0).getTiedOrSlurOrTuplet().get(1)).getType()),
                () -> assertEquals(StartStop.START, engine.noteList.get(1).getTie().get(0).getType()),
                () -> assertEquals(6, engine.noteList.get(2).getDuration().intValue()),
                () -> assertEquals("quarter", engine.noteList.get(2).getType().getValue()),
                () -> assertEquals(StartStop.STOP, engine.noteList.get(2).getTie().get(0).getType())
        );
    }

    @Test
    void testVaria_Apli() {
        


        Note note;
        note = new ExtendedNote(true, true);
        engine.noteList.add(note);

        // Pitch
        Pitch pitch = new Pitch();
        note.setPitch(pitch);
        pitch.setStep(Step.C);
        pitch.setOctave(4);

        // Duration
        note.setDuration(new BigDecimal(engine.division));

        // Type
        NoteType type = new NoteType();
        type.setValue("quarter");
        note.setType(type);

        timeChar = new TimeChar(92, "", ByzClass.L, 0, -1, false);
        //System.out.println(engine.noteList);
        timeChar.accept(engine);
        //System.out.println(engine.noteList);
        assertAll(
                () -> assertEquals(engine.division, engine.noteList.get(0).getDuration().intValue()),
                () -> assertEquals("quarter", engine.noteList.get(0).getType().getValue()),
                () -> assertEquals(engine.division, engine.noteList.get(1).getDuration().intValue()),
                () -> assertEquals("quarter", engine.noteList.get(1).getType().getValue()),
                () -> assertNull(engine.noteList.get(1).getPitch())
        );
    }

    @Test
    void testGorgonXronosGorgon() {
        engine.setDivision(4);

        for (int i = 0; i < 4; i++) {
            // Note
            Note note;
            if (i == 1 | i == 2)
                note = new ExtendedNote(true, true);
            else
                note = new ExtendedNote(true, false);
            engine.noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.A);
            pitch.setOctave(4);

            if (i == 0) {
                // Duration
                note.setDuration(new BigDecimal(engine.division / 2));
                // Type
                NoteType type = new NoteType();
                type.setValue("eighth");
                note.setType(type);
            } else if (i == 1) {
                // Duration
                note.setDuration(new BigDecimal(engine.division + 2));
                // Type
                NoteType type = new NoteType();
                type.setValue("quarter");
                note.setType(type);
                note.getDot().add(new EmptyPlacement());
            } else {
                // Duration
                note.setDuration(new BigDecimal(engine.division));
                // Type
                NoteType type = new NoteType();
                type.setValue("quarter");
                note.setType(type);
            }
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 0, 1, false);
        //System.out.println(engine.noteList);
        timeChar.accept(engine);
        //System.out.println(engine.noteList);
        assertAll(
                () -> assertEquals(2, engine.noteList.get(0).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(0).getType().getValue()),
                () -> assertEquals(4, engine.noteList.get(1).getDuration().intValue()),
                () -> assertEquals("quarter", engine.noteList.get(1).getType().getValue()),
                () -> assertEquals(0, engine.noteList.get(1).getDot().size()),
                () -> assertEquals(2, engine.noteList.get(2).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(2).getType().getValue()),
                () -> assertEquals(4, engine.noteList.get(3).getDuration().intValue()),
                () -> assertEquals("quarter", engine.noteList.get(3).getType().getValue())
        );
    }

    @Test
    void testDiGorgonXronosGorgon() { // TODO make testDiGorgonXronosGorgon() work
        engine.setDivision(4);

        for (int i = 0; i < 4; i++) {
            // Note
            Note note;
            if (i == 1 | i == 2)
                note = new ExtendedNote(true, true);
            else
                note = new ExtendedNote(true, false);
            engine.noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.A);
            pitch.setOctave(4);

            if (i == 0) {
                // Duration
                note.setDuration(new BigDecimal(engine.division / 2));
                // Type
                NoteType type = new NoteType();
                type.setValue("eighth");
                note.setType(type);
            } else if (i == 1) {
                // Duration
                note.setDuration(new BigDecimal(engine.division + 2));
                // Type
                NoteType type = new NoteType();
                type.setValue("quarter");
                note.setType(type);
                note.getDot().add(new EmptyPlacement());
            } else {
                // Duration
                note.setDuration(new BigDecimal(engine.division));
                // Type
                NoteType type = new NoteType();
                type.setValue("quarter");
                note.setType(type);
            }
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 0, 2, false);
        timeChar.accept(engine);

        assertAll(
                () -> assertEquals(6, engine.noteList.get(0).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(0).getType().getValue()),
                () -> assertEquals(6, engine.noteList.get(1).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(1).getType().getValue()),
                () -> assertEquals(0, engine.noteList.get(1).getDot().size()),
                () -> assertEquals(StartStop.START, engine.noteList.get(1).getTie().get(0).getType()),
                () -> assertEquals(4, engine.noteList.get(2).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(2).getType().getValue()),
                () -> assertEquals(StartStop.STOP, engine.noteList.get(2).getTie().get(0).getType()),
                () -> assertEquals(4, engine.noteList.get(3).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(3).getType().getValue()),
                () -> assertEquals(4, engine.noteList.get(4).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(4).getType().getValue())
        );
    }

    // TODO test duration adds of Xronos after notes after digorgon or more compicated TimeChar, to get more complicated durations
    @Test // Tsakisma, apli, dipli..
    void testXronos() {
        


        for (int i = 0; i < 2; i++) {
            // Note
            Note note;
            if (i == 0)
                note = new ExtendedNote(true, true);
            else
                note = new ExtendedNote(true, false);
            engine.noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            // Duration
            note.setDuration(new BigDecimal(engine.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(234, "", ByzClass.B, 0, -1, false);
        //System.out.println(engine.noteList);
        timeChar.accept(engine);
        //System.out.println(engine.noteList);
        assertAll(
                () -> assertEquals(2, engine.noteList.get(0).getDuration().intValue()),
                () -> assertEquals("half", engine.noteList.get(0).getType().getValue()),
                () -> assertEquals(1, engine.noteList.get(1).getDuration().intValue()),
                () -> assertEquals("quarter", engine.noteList.get(1).getType().getValue())
        );
    }

    @Test
    void testGorgonXronos() {
        for (int i = 0; i < 2; i++) {
            // Note
            Note note;
            if (i == 1)
                note = new ExtendedNote(true, true);
            else
                note = new ExtendedNote(true, false);
            engine.noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            // Duration
            note.setDuration(new BigDecimal(engine.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(235, "", ByzClass.B, 0, 1, false);
        //System.out.println(engine.noteList);
        timeChar.accept(engine);
        timeChar = new TimeChar(234, "", ByzClass.B, 0, -1, false);
        //System.out.println(engine.noteList);
        timeChar.accept(engine);
        System.out.println(engine.noteList);
        assertAll(
                () -> assertEquals(1, engine.noteList.get(0).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(0).getType().getValue()),
                () -> assertEquals(3, engine.noteList.get(1).getDuration().intValue()),
                () -> assertEquals("quarter.", engine.noteList.get(1).getType().getValue()),
                () -> assertEquals(1, engine.noteList.get(1).getDot().size())
        );
    }

    @Test
    void testGorgonXronos2() {
        for (int i = 0; i < 2; i++) {
            // Note
            Note note;
            if (i == 1)
                note = new ExtendedNote(true, true);
            else
                note = new ExtendedNote(true, false);
            engine.noteList.add(note);

            // Pitch
            Pitch pitch = new Pitch();
            note.setPitch(pitch);
            pitch.setStep(Step.C);
            pitch.setOctave(4);

            // Duration
            note.setDuration(new BigDecimal(engine.division));

            // Type
            NoteType type = new NoteType();
            type.setValue("quarter");
            note.setType(type);
        }
        timeChar = new TimeChar(235, "", ByzClass.B, 0, 1, false);
        //System.out.println(engine.noteList);
        timeChar.accept(engine);
        timeChar = new TimeChar(57, "", ByzClass.B, 0, -2, false);
        //System.out.println(engine.noteList);
        timeChar.accept(engine);
        System.out.println(engine.noteList);
        assertAll(
                () -> assertEquals(1, engine.noteList.get(0).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(0).getType().getValue()),
                () -> assertEquals(4, engine.noteList.get(1).getDuration().intValue()),
                () -> assertEquals("half", engine.noteList.get(1).getType().getValue()),
                () -> assertEquals(1, engine.noteList.get(2).getDuration().intValue()),
                () -> assertEquals("eighth", engine.noteList.get(2).getType().getValue())
        );
    }

    @Test
    void testIfNullInNotations() {
        assertAll(
                () -> assertNotNull(new ExtendedNote(true, true).getNotations()),
                () -> assertNotNull(new Notations().getTiedOrSlurOrTuplet())
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