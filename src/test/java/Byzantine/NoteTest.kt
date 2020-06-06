package Byzantine

import org.audiveris.proxymusic.Step
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals

class NoteTest {
    @Test
    fun noteToByzStep() {
        val note = Mxml.Note(true, true, Step.G, 4, 2, Mxml.Note.NoteTypeEnum.QUARTER.noteType)
        assertAll({ assertEquals(-1, Mxml.Note(note).apply { pitch.step = Step.B; pitch.octave = 3 }.byzOctave)},
                { assertEquals(-1, Mxml.Note(note).apply { pitch.step = Step.C }.byzOctave)},
                { assertEquals(-1, Mxml.Note(note).apply { pitch.step = Step.D }.byzOctave)},
                { assertEquals(-1, Mxml.Note(note).apply { pitch.step = Step.E }.byzOctave)},
                { assertEquals(-1, Mxml.Note(note).apply { pitch.step = Step.F }.byzOctave)},
                {assertEquals(0, note.byzOctave)},
                { assertEquals(0, Mxml.Note(note).apply { pitch.step = Step.A }.byzOctave)},
                { assertEquals(0, Mxml.Note(note).apply { pitch.step = Step.B }.byzOctave)},
                { assertEquals(0, Mxml.Note(note).apply { pitch.step = Step.C; pitch.octave = 5 }.byzOctave)},
                { assertEquals(0, Mxml.Note(note).apply { pitch.step = Step.D; pitch.octave = 5 }.byzOctave)},
                { assertEquals(0, Mxml.Note(note).apply { pitch.step = Step.E; pitch.octave = 5 }.byzOctave)},
                { assertEquals(0, Mxml.Note(note).apply { pitch.step = Step.F; pitch.octave = 5 }.byzOctave)},
                { assertEquals(1, Mxml.Note(note).apply { pitch.octave = 5 }.byzOctave)},
                { assertEquals(1, Mxml.Note(note).apply { pitch.step = Step.A; pitch.octave = 5 }.byzOctave)},
                { assertEquals(1, Mxml.Note(note).apply { pitch.step = Step.B; pitch.octave = 5 }.byzOctave)},
                { assertEquals(1, Mxml.Note(note).apply { pitch.step = Step.C; pitch.octave = 6 }.byzOctave)},
                { assertEquals(1, Mxml.Note(note).apply { pitch.step = Step.D; pitch.octave = 6 }.byzOctave)},
                { assertEquals(1, Mxml.Note(note).apply { pitch.step = Step.E; pitch.octave = 6 }.byzOctave)})
    }
}