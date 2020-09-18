package parser

import org.audiveris.proxymusic.*
import west.cast
import java.math.BigDecimal

/* Method to check if x is power of 2*/
fun Int.isPowerOfTwo(): Boolean {
    /* First x in the below expression is
    for the case when x is 0 */
    return this != 0 && this and this - 1 == 0
}

fun Note.copy(): Note = Note().also {
    it.pitch = this.pitch.copy()
    it.duration_ = this.duration_
    it.noteType = this.noteType
    it.dot += mutableListOf<EmptyPlacement>().also { list -> for (i in 0..this.dot.size) list += EmptyPlacement() }
//    accidentalCommmas = note.accidentalCommmas
}

var Note.lyricText: String?
    get() = lyric?.firstOrNull()?.elisionAndSyllabicAndText?.firstOrNull { it is TextElementData }?.cast<TextElementData>()?.value
    set(value) {
        lyric.clear()
        lyric += Lyric().apply { elisionAndSyllabicAndText += Syllabic.SINGLE
            elisionAndSyllabicAndText += TextElementData().apply { this.value = value } }
    }

var Note.noteType: String?
    get() = type?.value
    set(value) { type = NoteType().apply { this.value = value } }

fun Pitch.copy(): Pitch = Pitch().also { it.step = this.step; it.octave = this.octave }

fun noteTypeByDuration(duration: BigDecimal): Mxml.Note.NoteTypeEnum? =
        Mxml.Note.NoteTypeEnum.values().asList().find { it.duration == duration.toInt() }

var Note.duration_: Int
    get() = duration.toInt()
    set(value) { duration = value.toBigDecimal() }

fun Note.setDuration(noteType: Mxml.Note.NoteTypeEnum) { duration_ = noteType.duration }
fun Note.setNoteType(noteType: Mxml.Note.NoteTypeEnum) { this.noteType = noteType.noteType }
fun Note.setType(noteType: Mxml.Note.NoteTypeEnum) {
    setDuration(noteType)
    setNoteType(noteType)
}
fun Note.setDurType(duration: Int, noteType: String) {
    duration_ = duration
    this.noteType = noteType
}

var Note.step: Step?
    get() = this.pitch?.step
    set(value) {
        if (pitch != null) pitch.step = value
        else pitch = Pitch().apply { step = value }
    }

var Note.octave: Int?
    get() = pitch?.octave
    set(value) {
        if (value != null) {
            if (pitch != null) pitch.octave = value
            else pitch = Pitch().apply { octave = value }
        }
    }

val Note.byzOctave: Int?
    get() = if (Mxml.Note.steps.indexOf(step) < 4) octave?.minus(5) else octave?.minus(4)

val Note.notation: Notations
    get() = notations.firstOrNull()?: Notations().also { notations += it }

fun Note.addTied(startStopContinue: StartStopContinue) {
    notation.tiedOrSlurOrTuplet += Tied().also { it.type = startStopContinue }
}

fun Note.setTuplet(bracket: YesNo? = null, placement: AboveBelow? = null, type: StartStop) {
    notation.tiedOrSlurOrTuplet += Tuplet().also {tuplet ->
        if (bracket != null) tuplet.bracket = bracket
        if (placement != null) tuplet.placement = placement
        tuplet.type = type
    }
}

fun Note.addTie(tie: Tie) { this.tie += tie }

fun Note.updateDivision(multiplier: Int) { duration_ *= multiplier }

fun newPitch(step: Step, octave: Int): Pitch = Pitch().apply {
    this.step = step
    this.octave = octave
}

fun newNote(
        step: Step,
        octave: Int,
        duration: Int,
        noteType: Mxml.Note.NoteTypeEnum,
        syllable: String?
): Note = Note().apply {
    this.step = step
    this.octave = octave
    this.duration_ = duration
    setNoteType(noteType)
    // add syllable if not null
    if (syllable != null) lyricText = syllable
}