package Mxml

import com.google.common.collect.ImmutableList
import org.audiveris.proxymusic.*
import org.jetbrains.annotations.TestOnly
import java.math.BigDecimal

class Note : org.audiveris.proxymusic.Note, Cloneable {
    private val canGetLyric: Boolean
    val isRest: Boolean
    private var time: Boolean
    var accidentalCommmas: Int? = null
        private set

    constructor(lyric: Boolean, time: Boolean, step: Step?, octave: Int, duration: Int, noteType: String?) : this(lyric, time) {
        // Pitch
        val pitch = Pitch()
        pitch.step = step
        pitch.octave = octave
        setPitch(pitch)
        // Duration
        setDuration(BigDecimal(duration))
        // Type
        val type = NoteType()
        type.value = noteType
        setType(type)
    }

    constructor(note: Note) {
        this.canGetLyric = note.canGetLyric
        time = note.time
        isRest = note.isRest
        pitch = Pitch()
        pitch.step = Step.valueOf(note.pitch.step.toString())
        pitch.octave = note.pitch.octave
        duration = BigDecimal(note.duration.toInt())
        type = NoteType()
        type.value = note.type.value
        accidentalCommmas = note.accidentalCommmas
    }

    @TestOnly
    constructor(lyric: Boolean, time: Boolean) {
        this.canGetLyric = lyric
        this.time = time
        isRest = false
    }

    // Rest constructor
    private constructor(duration: Int, noteTypeEnum: NoteTypeEnum) {
        this.canGetLyric = false
        time = true
        isRest = true
        setDuration(BigDecimal.valueOf(duration.toLong()))
        val noteType = NoteType()
        noteType.value = noteTypeEnum.noteType
        setType(noteType)
        setRest(Rest())
    }

    fun canGetLyric(): Boolean {
        return canGetLyric
    }

    fun canGetTime(): Boolean {
        return time
    }

    fun setTime(time: Boolean) {
        this.time = time
    }

    fun setAccidentalCommmas(accidentalCommmas: Int) {
        this.accidentalCommmas = accidentalCommmas
    }

    val step: Step?
        get() = if (isRest) null else pitch.step

    val octave: Int?
        get() = if (isRest) null else getPitch().octave

    fun setOctave(octave: Int) {
        if (!isRest) getPitch().octave = octave
    }

    val byzOctave: Int?
        get() {
            if (isRest) return null
            var octave = octave!! - 4
            if (steps!!.indexOf(step) < 4) --octave
            return octave
        }

    fun updateDivision(multiplier: Int) {
        setDuration(getDuration().multiply(BigDecimal.valueOf(multiplier.toLong())))
    }

    override fun toString(): String {
        return "Note{" +
                "lyric=" + canGetLyric +
                ", time=" + time +
                ", pitch=" + (if (pitch != null) pitch.step.toString() + "" + pitch.octave else "null") +
                ", duration=" + duration +
                ", type=" + type.value +
                ", commas=" + accidentalCommmas +  //", tie=" + ((getTie().size()>0)?getTie().get(0).getType():"null") +
                '}'
    }

    public enum class NoteTypeEnum(val noteType: String, val duration: Int, val dot: EmptyPlacement?, val dot2: EmptyPlacement?) {
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
        QUARTER("quarter", 120,null, null),
        EIGHTHDD("eighth", 105, EmptyPlacement(), EmptyPlacement()),
        EIGHTHD("eighth", 90, EmptyPlacement(), null),
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
        val steps = ImmutableList.of(Step.C, Step.D, Step.E, Step.F, Step.G, Step.A, Step.B)
        @JvmStatic
        fun createRest(duration: Int, noteTypeEnum: NoteTypeEnum): Note {
            return Note(duration, noteTypeEnum)
        }
    }
}