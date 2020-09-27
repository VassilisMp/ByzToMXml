package parser

import org.apache.commons.lang3.math.Fraction
import org.apache.commons.lang3.math.Fraction.getFraction
import org.audiveris.proxymusic.StartStop
import west.Note
import west.getNoteType
import west.toFraction
import java.util.function.Consumer
import kotlin.math.abs

class Tchar(private val dotPlace: Int, var division: Int, var argo: Boolean): Consumer<Engine> {

    override fun accept(engine: Engine) {
        fun Note.checkTieNote(tieNoteTie: StartStop, thisNoteTie: StartStop, newAddedTime: Fraction) {
            if ((rationalDuration - newAddedTime).numerator == 0) return
            if (noteType == null) {
                val tieNote = this.copy().also {
                    it.rationalDuration = this.rationalDuration - newAddedTime
                    it.noteType ?: throw NullPointerException("${this.rationalDuration} ${it.rationalDuration}")
                    it.addTie(tieNoteTie)
                    if (this.lyricText != null) {
                        val syllable = InMusicSyllable(this.lyricText)
                        it.lyricText = syllable.start
                        this.lyricText = syllable.end
                    }
                }
                // set fields on the this note
                rationalDuration = newAddedTime
                noteType!!
                addTie(thisNoteTie)
                // add tieNote in noteList before this Note
                val index = engine.noteList.indexOf(this)
                engine.noteList.add(index, tieNote)
            }
        }
        if (division < 0) {
            with(engine.noteList.mySublist(this, 1).last()) {
                val addedTime = getFraction(abs(division), 1)
                rationalDuration += addedTime
                checkTieNote(StartStop.START, StartStop.STOP, addedTime)
            }
            return
        }
        val notes: List<Note> = engine.noteList.mySublist(this, division)
        fun hasDotplace() = if (dotPlace > 0) 1 else 0
        division += hasDotplace()
        engine.divisions = maxOf(engine.divisions, division)
        // time to be added
        val addedTime = getFraction(1, division)
        // remove a quarter duration and add the added time
        notes.forEach { it.rationalDuration += addedTime - 1 }
        // if gorgotita is parestigmeno, double the added time on the specific note
        if (dotPlace > 0) notes[dotPlace-1].rationalDuration += addedTime
        if (division%2 != 0) notes.forEach { it.inTuplet = true }
        // if first is the dot Note, use double time
        notes.first().checkTieNote(StartStop.START, StartStop.STOP, if (dotPlace == 1) addedTime*2 else addedTime)
        // if last is the dot Note, use double time
        notes.last().checkTieNote(StartStop.STOP, StartStop.START, if (dotPlace == notes.size) addedTime*2 else addedTime)
        // if division%2 != 0, it means the divider is odd, so we need a tuplet
        if (division%2 != 0) {
            // if parestigmeno, then the normal notes are 1 less than than the divider
//            val normalNotes = /*if (dotPlace > 0) division-1 else */division-1
            notes.forEach { it.setTimeModification(division, division-1, getNoteType("1/${division-1}")!!) }
            notes.first().setTupletStart()
            notes.last().setTupletStop()
        }
    }

    override fun toString(): String = when {
        dotPlace == 0 && division == 1 -> "gorgon"
        dotPlace == 1 && division == 1 -> "GorgonParestigmenoAristera"
        dotPlace == 2 && division == 1 -> "GorgonParestigmenoDexia"
        dotPlace == 0 && division == 2 -> "digorgon"
        dotPlace == 1 && division == 2 -> "DigorgonParestigmenoAristeraKato"
        dotPlace == 2 && division == 2 -> "DigorgonParestigmenoAristeraAno"
        dotPlace == 3 && division == 2 -> "DigorgonParestigmenoDexia"
        dotPlace == 0 && division == 3 -> "trigorgon"
        dotPlace == 1 && division == 3 -> "TrigorgonParestigmenoAristeraKato"
        dotPlace == 3 && division == 3 -> "TrigorgonParestigmenoAristeraPano"
        dotPlace == 4 && division == 3 -> "TrigorgonParestigmenoDexia"
        dotPlace == 0 && division == -1 && argo -> "argon"
        dotPlace == 0 && division == -2 && argo -> "imiDiargon"
        dotPlace == 0 && division == -3 && argo -> "diargon"
        dotPlace == 0 && division == -1 -> "apli"
        dotPlace == 0 && division == -2 -> "dipli"
        dotPlace == 0 && division == -3 -> "tripli"
        else -> super.toString()
    }
}

fun List<Any>.mySublist(tChar: Tchar, notesNum: Int): List<Note> = mutableListOf<Note>().apply {
    val index = this@mySublist.indexOf(tChar)
    addAll(this@mySublist.subList(0, index).filterIsInstance<Note>().asReversed().subList(0, 2).asReversed())
    if (notesNum-2 > 0)
        addAll(this@mySublist.subList(index+1, this@mySublist.size).filterIsInstance<Note>().subList(0, notesNum-2))
}

/** Subtracts the other value from this value. */
private operator fun Fraction.minus(other: Fraction) = subtract(other)
private operator fun Fraction.minus(other: Int) = subtract(other.toFraction())
private operator fun Fraction.plus(other: Fraction) = add(other)
/** Multiplies this value by the other value. */
private operator fun Fraction.times(other: Fraction) = multiplyBy(other)
operator fun Fraction.times(other: Int): Fraction = multiplyBy(other.toFraction())