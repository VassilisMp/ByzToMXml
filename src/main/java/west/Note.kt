package west

import org.audiveris.proxymusic.*
import org.audiveris.proxymusic.Step.*
import java.math.BigDecimal

class Note(step: Step?, octave: Int, duration: Int, noteType: String?) : org.audiveris.proxymusic.Note() {
    var isRest: Boolean
        get() = rest != null
        set(value) {
            rest = if (value) Rest() else null
        }
    var accidentalCommmas: Int? = null
        private set

    var step: Step?
        get() = pitch?.step
        set(value) {

        }

    val octave: Int?
        get() = if (isRest) null else getPitch().octave

    val byzOctave: Int?
        get() {
            if (isRest) return null
            var octave = octave!! - 4
            if (steps.indexOf(step) < 4) --octave
            return octave
        }

    fun updateDivision(multiplier: Int) {
        setDuration(getDuration().multiply(BigDecimal.valueOf(multiplier.toLong())))
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

    companion object {
        val steps = listOf(C, D, E, F, G, A, B)
    }

    init {
        val pitch = Pitch()
        pitch.step = step
        pitch.octave = octave
        setPitch(pitch)
        setDuration(BigDecimal(duration))
        val type = NoteType()
        type.value = noteType
        setType(type)
    }
}