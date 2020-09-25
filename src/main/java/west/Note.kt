package west

import org.apache.commons.lang3.math.Fraction
import org.audiveris.proxymusic.*
import org.audiveris.proxymusic.Step.*

class Note(
        step: Step? = null,
        octave: Int? = null,
        duration: Int,
        noteType: NoteTypeEnum?,
        syllable: String?,
        rest: Boolean = false
) : org.audiveris.proxymusic.Note() {

    var rationalDuration: Fraction = Fraction.ONE

    var accidentalCommmas: Int? = null
        private set

    var lyricText: String?
        get() = lyric?.firstOrNull()?.elisionAndSyllabicAndText?.firstOrNull { it is TextElementData }?.cast<TextElementData>()?.value
        set(value) {
            lyric = listOf(Lyric().apply { elisionAndSyllabicAndText += Syllabic.SINGLE
                elisionAndSyllabicAndText += TextElementData().apply { this.value = value } })
        }

    var noteType: String?
        get() = type?.value
        set(value) { type = NoteType().apply { this.value = value } }

    var duration_: Int
        get() = duration.toInt()
        set(value) { duration = value.toBigDecimal() }

    var step: Step?
        get() = this.pitch?.step
        set(value) {
            if (pitch != null) pitch.step = value
            else pitch = Pitch().apply { step = value }
        }

    var octave: Int?
        get() = pitch?.octave
        set(value) {
            if (value != null) {
                if (pitch != null) pitch.octave = value
                else pitch = Pitch().apply { octave = value }
            }
        }

    val byzOctave: Int?
        get() = if (steps.indexOf(step) < 4) octave?.minus(5) else octave?.minus(4)

    private val notation: Notations
        get() = notations?.firstOrNull()?: Notations().also { notations = mutableListOf(it) }

    fun setTupletStart() = setTuplet(YesNo.YES, AboveBelow.ABOVE, StartStop.START)
    fun setTupletStop() = setTuplet(type = StartStop.STOP)

    private fun setTuplet(bracket: YesNo? = null, placement: AboveBelow? = null, type: StartStop) {
        notation.tiedOrSlurOrTuplet += Tuplet().also {tuplet ->
            if (bracket != null) tuplet.bracket = bracket
            if (placement != null) tuplet.placement = placement
            tuplet.type = type
        }
    }

    fun addTie(type: StartStop) {
        tie = mutableListOf(Tie().apply { this.type = type })
        notation.tiedOrSlurOrTuplet += Tied().also { it.type = StartStopContinue.valueOf(type.name) }
    }

    fun setTimeModification(actualNotes: Int, normalNotes: Int, normalType: String) {
        timeModification = TimeModification().apply {
            this.actualNotes = actualNotes.toBigInteger()
            this.normalNotes = normalNotes.toBigInteger()
            this.normalType = normalType
        }
    }

    fun setDots() {
        if (noteType != null) {
            dot = MutableList(noteType!!.count { it == '.' }) { EmptyPlacement() }
            /*mutableListOf<EmptyPlacement>().apply {
                val count = noteType!!.count { it == '.' }
                for (i in 0 until count) add(EmptyPlacement())
            }*/
        }
    }

    fun copy(): Note = Note(
            step = step,
            octave = octave,
            duration = duration_,
            noteType = noteType?.let { NoteTypeEnum.valueOf(it) },
            syllable = lyricText,
            rest = rest != null
    ).also {
        it.dot.addAll(this.dot.toList())
//        it.dot.plusAssign(mutableListOf<EmptyPlacement>().also { list -> for (i in 0..this.dot.size) list += EmptyPlacement() })
//    accidentalCommmas = note.accidentalCommmas
    }

    override fun toString(): String {
        return "Note{" +
                " pitch=${if (pitch != null) pitch.step.toString() + pitch.octave else "null"}" +
                ", duration=$duration" +
                ", type=${type.value}" +
                ", commas=$accidentalCommmas" +  //", tie=" + ((getTie().size()>0)?getTie().get(0).getType():"null") +
                '}'
    }

    enum class NoteTypeEnum(val noteType: String, val duration: Int, val dot: EmptyPlacement?, val dot2: EmptyPlacement?) {
        MAXIMADD("maxima", 5880, EmptyPlacement(), EmptyPlacement()),
        MAXIMAD("maxima", 5040, EmptyPlacement(), null),
        MAXIMA("maxima", 3360, null, null),
        LONGD("long", 2880, EmptyPlacement(), null),
        LONG("long", 1920, null, null),
        BREVEDD("breve", 1680, EmptyPlacement(), EmptyPlacement()),
        BREVED("breve", 1440, EmptyPlacement(), null),
        BREVE("breve", 960, null, null),
        WHOLEDD("whole", 840, EmptyPlacement(), EmptyPlacement()),
        WHOLED("whole", 720, EmptyPlacement(), null),
        WHOLE("whole", 480, null, null),
        HALFDD("half", 420, EmptyPlacement(), EmptyPlacement()),
        HALFD("half", 360, EmptyPlacement(), null),
        HALF("half", 240, null, null),
        QUARTERDD("quarter", 210, EmptyPlacement(), EmptyPlacement()),
        QUARTERD("quarter", 180, EmptyPlacement(), null),
        QUARTER("quarter", 105, null, null),
        EIGHTHDD("eighth", 90, EmptyPlacement(), EmptyPlacement()),
        EIGHTHD("eighth", 60, EmptyPlacement(), null),
        EIGHTH("eighth", 60, null, null),
        SIXTEENTHD("16th", 45, EmptyPlacement(), null),
        SIXTEENTH("16th", 30, null, null),
        THIRTY_SECOND("32nd", 15, null, null),
        SIXTY_FOURTH("64th", 0/*7.5F*/, null, null),
        ONE_TWO_EIGHTH("128th", 0/*3.75F*/, null, null),
        TWO_FIVE_SIXTH("256th", 0/*1.875F*/, null, null),
        FIVE_TWELFTH("512th", 0/*0.9375F*/, null, null),
        TEN_TWENTY_FOURTH("1024th", 0/*0.46875F*/, null, null);
    }

    init {
        if (step != null) this.step = step
        if (octave != null) this.octave = octave
        this.duration_ = duration
        if (noteType != null) this.noteType = noteType.noteType
        // add syllable if not null
        if (syllable != null) lyricText = syllable
        if (rest) this.rest = Rest()
    }

    companion object {
        val steps = listOf(C, D, E, F, G, A, B)
    }
}

/* Method to check if x is power of 2*/
/*fun Int.isPowerOfTwo(): Boolean {
    *//* First x in the below expression is
    for the case when x is 0 *//*
    return this != 0 && this and this - 1 == 0
}*/

fun newTimeModification(actualNotes: Int, normalNotes: Int, normalType: String) = TimeModification().apply {
    this.actualNotes = actualNotes.toBigInteger()
    this.normalNotes = normalNotes.toBigInteger()
    this.normalType = normalType
}