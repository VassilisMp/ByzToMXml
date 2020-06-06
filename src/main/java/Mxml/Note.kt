package Mxml

import com.google.common.collect.ImmutableList
import org.audiveris.proxymusic.NoteType
import org.audiveris.proxymusic.Pitch
import org.audiveris.proxymusic.Rest
import org.audiveris.proxymusic.Step
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

    enum class NoteTypeEnum(var noteType: String) {
        MAXIMA("maxima"), LONG("long"), BREVE("breve"), WHOLE("whole"), HALF("half"), QUARTER("quarter"), EIGHTH("eighth"), SIXTEENTH("16th"), THIRTY_SECOND("32nd"), SIXTY_FOURTH("64th"), ONE_TWO_EIGHTH("128th"), TWO_FIVE_SIXTH("256th"), FIVE_TWELFTH("512th"), TEN_TWENTY_FOURTH("1024th");

    }

    companion object {
        private val steps = ImmutableList.of(Step.C, Step.D, Step.E, Step.F, Step.G, Step.A, Step.B)
        @JvmStatic
        fun createRest(duration: Int, noteTypeEnum: NoteTypeEnum): Note {
            return Note(duration, noteTypeEnum)
        }
    }
}