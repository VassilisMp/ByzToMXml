package west

import Byzantine.ByzStep
import Byzantine.ByzStep.*
import com.uchuhimo.collections.biMapOf
import org.apache.commons.lang3.math.Fraction
import org.apache.commons.lang3.math.Fraction.getFraction
import org.apache.commons.lang3.math.Fraction.getReducedFraction
import org.audiveris.proxymusic.*
import org.audiveris.proxymusic.Step.*

class Note(
        step: Step? = null,
        octave: Int? = null,
        duration: Int = 1,
        noteType: NoteTypeEnum? = null,
        syllable: String? = null,
        rest: Boolean = false
) : org.audiveris.proxymusic.Note() {

    var inTuplet: Boolean = false

    var rationalDuration: Fraction = Fraction.ONE
        set(value) {
            field = value
            noteType = if (inTuplet) teo(rationalDuration)
            else getNoteType(field)
            setDots()
        }

    fun setNoteTypeInTuplet() {
        noteType = teo(rationalDuration)
    }

    var accidentalCommas: Int? = null
        set(value) {
            field = value
            /*val accidentalValue = ACCIDENTALS_MAP[accidentalCommas]
            if (accidentalValue != null) {
                accidental = Accidental().apply {
                    this.value = accidentalValue
                }
            }*/
        }

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

    val byzStep: ByzStep? = STEPS_MAP.inverse[step]

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
        setNoteTypeInTuplet()
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

    fun equalsPitch(other: Note): Boolean = this.step == other.step && this.octave == other.octave

    fun copy(): Note = Note(
            step = step,
            octave = octave,
            duration = duration_,
            noteType = noteType?.let { NoteTypeEnum.valueOf(it) },
            syllable = lyricText,
            rest = rest != null
    ).also {
        it.dot = this.dot?.toList()
//        it.dot.plusAssign(mutableListOf<EmptyPlacement>().also { list -> for (i in 0..this.dot.size) list += EmptyPlacement() })
//    accidentalCommmas = note.accidentalCommmas
    }

    override fun toString(): String {
        return "Note{" +
                " pitch=${if (pitch != null) pitch.step.toString() + pitch.octave else "null"}" +
                ", duration=$rationalDuration" +
                ", type=${type?.value}" +
                ", commas=$accidentalCommas" +  //", tie=" + ((getTie().size()>0)?getTie().get(0).getType():"null") +
                ", rest=${rest != null}" +
                ", lyric=$lyricText" +
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
        private val STEPS_MAP = biMapOf(
                NH to G,
                PA to A,
                BOU to B,
                GA to C,
                DI to D,
                KE to E,
                ZW to F
        )
        /**
         * The best mapping from European to Byzantine hard-diatonic scale, without the need of turkish accidentals
         */
        private val STANDARD_MAP: Map<Step, ByzStep> = mapOf(
                C to NH,
                D to PA,
                E to BOU,
                F to GA,
                G to DI,
                A to KE,
                B to ZW
        )

        /**
         * The relative European step for Byzantine step NH of this engine converted to byzStep again,
         * via use of STEPS_MAP and STANDARD_MAP, use of STANDARD_MAP is required because everything is calculated using ByzScales.
         * e.g. STEPS_MAP: NH->G, STANDARD_MAP: G->DI
         */
        val relativeStandardStep: ByzStep = STANDARD_MAP.getValue(NH.toStep())

        fun ByzStep.toStep() = STEPS_MAP.getValue(this)
        fun Step.toByzStep() = STEPS_MAP.inverse[this]
//        fun Note.byzStep(): ByzStep? = STEPS_MAP.inverse[step]
        fun RestNote() = Note(rest= true)
    }
}

/* Method to check if x is power of 2*/
/*fun Int.isPowerOfTwo(): Boolean {
    *//* First x in the below expression is
    for the case when x is 0 *//*
    return this != 0 && this and this - 1 == 0
}*/

fun Int.toFraction(): Fraction = getFraction(this, 1)
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
        getFraction(3.5) to "half..",
        3.toFraction() to "half.",
        2.toFraction() to "half",
        getFraction(1.75) to "quarter..",
        getFraction(1.5) to "quarter.",
        1.toFraction() to "quarter",
        getFraction(1, 2) to "eighth",
        getFraction(0.75) to "eighth.",
        getFraction(1, 4) to "16th",
        getFraction(0.875) to "eighth..",
        getFraction(0.375) to "16th.",
        getFraction(1, 8) to "32nd",
        getFraction(0.4375) to "16th..",
        getFraction(0.1875) to "32nd.",
        getFraction(1, 16) to "64th",
        getFraction(0.21875) to "32nd..",
        getFraction(0.09375) to "64th.",
        getFraction(1, 32) to "128th",
        getFraction(0.109375) to "64th..",
        getFraction(1, 64) to "256th",
        getFraction(1, 128) to "512th",
        getFraction(1, 256) to "1024th"
)

fun getNoteType(fraction: Fraction): String? = fractionMap[fraction]
fun getNoteType(fraction: String): String? = getNoteType(getFraction(fraction))
fun getFraction(numerator: Int): Fraction = getFraction(numerator, 1)
private fun teo(fraction: Fraction): String? =
        if (fraction.denominator%2 == 0 || fraction.denominator == 1) getNoteType(fraction)
        else getNoteType(getReducedFraction(fraction.numerator, fraction.denominator - 1))