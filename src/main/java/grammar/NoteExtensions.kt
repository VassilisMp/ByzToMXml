package grammar

import org.audiveris.proxymusic.*
import java.math.BigDecimal

/* Method to check if x is power of 2*/
fun Int.isPowerOfTwo(): Boolean {
    /* First x in the below expression is
    for the case when x is 0 */
    return this != 0 && this and this - 1 == 0
}

fun Any.asNote(): Note = this as Note

fun Any.asTextElementData(): TextElementData = this as TextElementData

fun Note.copy(): Note = Note().also {
    it.pitch = this.pitch.copy()
    it.duration = BigDecimal(this.duration.toInt())
    it.type = this.type.copy()
    it.dot += mutableListOf<EmptyPlacement>().also { list -> for (i in 0..this.dot.size) list += EmptyPlacement() }
//    accidentalCommmas = note.accidentalCommmas
}

var Note.lyricText: String?
    get() = lyric?.firstOrNull()?.elisionAndSyllabicAndText?.firstOrNull { it is TextElementData }?.asTextElementData()?.value
    set(value) {
        lyric.clear()
        lyric += Lyric().apply { elisionAndSyllabicAndText += Syllabic.SINGLE
            elisionAndSyllabicAndText += TextElementData().apply { this.value = value } }
    }

var Note.noteType: String?
    get() = type?.value
    set(value) { type = NoteType().apply { this.value = value } }

fun Pitch.copy(): Pitch = Pitch().also { it.step = this.step; it.octave = this.octave }

fun NoteType.copy(): NoteType = NoteType().also { it.value = this.value }

fun noteTypeByDuration(duration: BigDecimal): Mxml.Note.NoteTypeEnum? =
        Mxml.Note.NoteTypeEnum.values().asList().find { it.duration == duration.toInt() }

var Note.durationInt: Int
    get() = duration.toInt()
    set(value) { duration += value.toBigDecimal() }

fun Note.setDuration(noteType: Mxml.Note.NoteTypeEnum) { durationInt = noteType.duration }
fun Note.setNoteType(noteType: Mxml.Note.NoteTypeEnum) { this.noteType = noteType.noteType }
fun Note.setType(noteType: Mxml.Note.NoteTypeEnum) {
    setDuration(noteType)
    setNoteType(noteType)
}
fun Note.setDurType(duration: Int, noteType: String) {
    durationInt = duration
    this.noteType = noteType
}

val Note.step: Step?
    get() = this.pitch?.step

val Note.octave: Int?
    get() = pitch?.octave

val Note.byzOctave: Int?
    get() = if (Mxml.Note.steps.indexOf(step) < 4) octave?.minus(5) else octave?.minus(4)

val Note.notation: Notations
    get() = notations.firstOrNull()?: Notations().also { notations += it }

fun Note.addTied(startStopContinue: StartStopContinue) {
    notation.tiedOrSlurOrTuplet += Tied().also { it.type = startStopContinue }
}

fun Note.setTuplet(bracket: YesNo? = null, placement: AboveBelow? = null, type: StartStop) {
    notation.tiedOrSlurOrTuplet += Tuplet().also {tuplet ->
        bracket?.let { tuplet.bracket = it }
        placement?.let { tuplet.placement = it }
        tuplet.type = type
    }
}

fun Note.addTie(tie: Tie) { this.tie += tie }

fun Note.updateDivision(multiplier: Int) { durationInt *= multiplier }