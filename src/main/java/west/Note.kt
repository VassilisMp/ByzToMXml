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
        set(value) {
            field = value
            noteType = teo(field)
            setDots()
        }

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
        it.dot = this.dot.toList()
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

    enum class NoteTypeEnum(val noteType: String) {
        MAXIMA("maxima"),
        LONG("long"),
        BREVE("breve"),
        WHOLE("whole"),
        HALF("half"),
        QUARTER("quarter"),
        EIGHTH("eighth"),
        SIXTEENTH("16th"),
        THIRTY_SECOND("32nd"),
        SIXTY_FOURTH("64th"),
        ONE_TWO_EIGHTH("128th"),
        TWO_FIVE_SIXTH("256th"),
        FIVE_TWELFTH("512th"),
        TEN_TWENTY_FOURTH("1024th");
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

fun Int.toFraction(): Fraction = Fraction.getFraction(this, 1)
private val fractionMap = mapOf(
        49.toFraction() to "maxima..",
        42.toFraction() to "maxima.",
        28.toFraction() to "maxima",
        24.toFraction() to "long.",
        16.toFraction() to "long",
        14.toFraction() to "breve..",
        12.toFraction() to "breve.",
        8.toFraction() to "breve",
        7.toFraction() to "whole..",
        6.toFraction() to "whole.",
        4.toFraction() to "whole",
        Fraction.getFraction(3.5) to "half..",
        3.toFraction() to "half.",
        2.toFraction() to "half",
        Fraction.getFraction(1.75) to "quarter..",
        Fraction.getFraction(1.5) to "quarter.",
        1.toFraction() to "quarter",
        Fraction.getFraction(1, 2) to "eighth",
        Fraction.getFraction(0.75) to "eighth.",
        Fraction.getFraction(1, 4) to "16th",
        Fraction.getFraction(0.875) to "eighth..",
        Fraction.getFraction(0.375) to "16th.",
        Fraction.getFraction(1, 8) to "32nd",
        Fraction.getFraction(0.4375) to "16th..",
        Fraction.getFraction(0.1875) to "32nd.",
        Fraction.getFraction(1, 16) to "64th",
        Fraction.getFraction(0.21875) to "32nd..",
        Fraction.getFraction(0.09375) to "64th.",
        Fraction.getFraction(1, 32) to "128th",
        Fraction.getFraction(0.109375) to "64th..",
        Fraction.getFraction(1, 64) to "256th",
        Fraction.getFraction(1, 128) to "512th",
        Fraction.getFraction(1, 256) to "1024th"
)

fun getNoteType(fraction: Fraction): String? = fractionMap[fraction]
private fun teo(fraction: Fraction): String? =
        if (fraction.denominator%2 == 0 || fraction.denominator == 1) getNoteType(fraction)
        else getNoteType(Fraction.getReducedFraction(fraction.numerator, fraction.denominator - 1))