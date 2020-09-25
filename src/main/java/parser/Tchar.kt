package parser

import org.apache.commons.lang3.math.Fraction
import org.apache.commons.lang3.math.Fraction.*
import org.audiveris.proxymusic.StartStop
import west.Note
import java.util.function.Consumer

class Tchar(private val dotPlace: Int, var division: Int, var argo: Boolean): Consumer<Engine> {
    /*companion object {
        private val tieStart = Tie().apply { type = StartStop.START }
        private val tieStop = Tie().apply { type = StartStop.STOP }

        private fun checkDots(note: Note) {
            note.noteType?.let { noteType ->
                note.dot.clear()
                for (i in 0 .. noteType.count { it == '.' }) note.dot += EmptyPlacement()
            }
        }
    }*/

    /*override fun accept(engine: Engine) {
        fun getNoteType(i: Int): String = engine.toNoteTypeDiv(if (i == dotPlace - 1) division - 1 else division + 1)
                        ?: throw NullPointerException("String noteType doesn't exist")
        val notes: MutableList<Any> = engine.noteList
        fun Note.index(): Int = notes.indexOf(this)
        val subList: List<Note> = notes.mySublist(this, division+1)
        if (division > 0) {
            var addedTime: Int
            if (dotPlace == 0) {
                if (engine.divisions % (division + 1) != 0) engine.changeDivision(division + 1)
            } else if (engine.divisions % (division + 2) != 0) engine.changeDivision(division + 2)
            var tieNote: Note? = null
            var tieNoteIndex = 0
            for (i in subList.indices) {
                addedTime = if (dotPlace == 0) engine.divisions / (division + 1) else engine.divisions / (division + 2)
                val note = subList[i]
                var noteType: String?
                if (note.duration_ > engine.divisions) {
                    var addedTime1 = addedTime
                    val producedDuration = note.duration_ - engine.divisions + addedTime1
                    // new added code
                    noteType = engine.noteTypeMap.inverse[producedDuration]
                    if (noteType != null) {
                        note.setDurType(producedDuration, noteType)
                        checkDots(note)
                        continue
                    }
                    if (i == dotPlace - 1) addedTime1 *= 2
                    note.duration_ = addedTime1
                    tieNote = note.copy()
                    tieNoteIndex = i
                    var tDuration = note.duration_ - engine.divisions //((duration / engine.division) * engine.division) - engine.division;
                    tieNote.noteType = engine.toNoteType(tDuration)
                    var tie: Tie
                    var tie2: Tie
                    if (dotPlace == 0) {
                        tie = tieStart
                        tie2 = tieStop
                        if (division % 2 == 0) {
                            noteType = engine.toNoteTypeDiv(division)
                            if (noteType == null) {
                                engine.changeDivision(division)
                                tDuration *= division
                                noteType = engine.noteTypeMap.inverse[engine.divisions / division]
                                if (noteType == null) throw NullPointerException("String noteType doesn't exist")
                            }
                            note.noteType = noteType
                            addTuplet(subList.size, i, note, 1, noteType)
                        } else note.type.value = engine.noteTypeMap.inverse[engine.divisions / (division + 1)]
                    } else {
                        if (i == 0) {
                            tie = tieStart
                            tie2 = tieStop
                        } else {
                            tie = tieStop
                            tie2 = tieStart
                        }
                        tieNote.addTied(StartStopContinue.valueOf(tie.type.value().toUpperCase()))
                        note.addTied(StartStopContinue.valueOf(tie2.type.value().toUpperCase()))
                        var divideWith = division + 1
                        if (i == dotPlace - 1) divideWith = division
                        noteType = engine.noteTypeMap.inverse[engine.divisions / divideWith]
                        if (division % 2 != 0) {
                            if (noteType == null) {
                                engine.changeDivision(division + 1)
                                tDuration *= division + 1
                                noteType = getNoteType(i)
                            }
                            addTuplet(subList.size, i, note, 2, noteType)
                        }
                        note.type.value = noteType
                    }
                    tieNote.addTie(tie)
                    note.addTie(tie2)
                    tieNote.duration_ = tDuration
                    checkDots(note)
                } else {
                    if (dotPlace == 0) {
                        note.duration_ = addedTime
                        if (division % 2 == 0) {
                            noteType = engine.toNoteType(engine.divisions / division)
                            if (noteType == null) {
                                engine.changeDivision(division)
                                noteType = engine.toNoteType(engine.divisions / division)
                            }
                            addTuplet(subList.size, i, note, 1, noteType)
                        } else noteType = engine.toNoteType(engine.divisions / (division + 1))
                        if (noteType == null) throw NullPointerException("String noteType doesn't exist")
                        note.type.value = noteType
                    } else {
                        var divideWith = division + 1
                        if (i == dotPlace - 1) {
                            divideWith = division
                            addedTime *= 2
                        }
                        note.duration_ = addedTime
                        if (division % 2 != 0) {
                            noteType = engine.toNoteTypeDiv(divideWith)
                            if (noteType == null) {
                                engine.changeDivision(division + 1)
                                if (tieNote != null) tieNote.duration_ *= division + 1
                                noteType = getNoteType(i)
                            }
                            note.noteType = noteType
                            addTuplet(subList.size, i, note, 2, noteType)
                        } else {
                            noteType = engine.toNoteTypeDiv(divideWith)
                            if (noteType == null) noteType = engine.toNoteType(addedTime)
                            note.noteType = noteType
                        }
                    }
                }
            }
            if (tieNote != null) {
                if (tieNoteIndex > 0) notes.add(subList.last().index() *//*+ tieNoteIndex*//*, tieNote)
                else notes.add(subList[0].index() - 1, tieNote)
            }
        }
        if (division < 0) {
            subList[0].run {
                duration_ *= abs(division) * engine.divisions
                noteType = engine.toNoteType(duration_)
            }
        }
    }*/

    /*private val noteTypeMap = biMapOf(
            420 to "half..",
            360 to "half.",
            240 to "half",
            180 to "quarter.",
            120 to "quarter",
            80 to "quarter", // 2/3
            60 to "eighth", // 1/2
            48 to "eighth", // 2/5
            40 to "eighth", // 1/3
            30 to "16th", // 1/4
            24 to "16th" // 1/5
    )

    private fun getNoteType(duration: Int): String? = noteTypeMap[duration]*/

    override fun accept(engine: Engine) {
        if (division < 0) return
        // quarter duration = 5! = 120
        fun hasDotplace() = if (dotPlace > 0) 1 else 0
        val notes: List<Note> = engine.noteList.mySublist(this, division)
        division += hasDotplace()
        engine.divisions = maxOf(engine.divisions, division)
        // time to be added
        val addedTime = getFraction(1, division)
        // remove a quarter duration and add the added time
        notes.forEach {
            it.rationalDuration += addedTime - ONE
            it.noteType = teo(it.rationalDuration)
            it.setDots()
        }
        // if gorgotita is parestigmeno, double the added time on the specific note
        if (dotPlace > 0) {
            with(notes[dotPlace-1]) {
                rationalDuration += addedTime
                noteType = teo(rationalDuration)
                setDots()
            }
        }
        // if division%2 != 0, it means the divider is odd, so we need a tuplet
        if (division%2 != 0) {
            // if parestigmeno, then the normal notes are 1 less than than the divider
            val normalNotes = if (dotPlace > 0) division-1 else division
            notes.forEach { it.setTimeModification(division, normalNotes, getNoteType(getFraction(1, division - 1))!!) }
            notes.first().setTupletStart()
            notes.last().setTupletStop()
        }
        notes.first().run {
            if (noteType == null) {
                val newAddedTime = if (dotPlace == 1) addedTime*2 else addedTime
                val tieNote = this.copy().also {
                    it.rationalDuration = this.rationalDuration - newAddedTime
                    it.noteType = teo(it.rationalDuration)
                    it.setDots()
                    it.addTie(StartStop.START)
                }
                // set fileds on the this note
                this.rationalDuration = newAddedTime
                this.noteType = teo(this.rationalDuration)
                this.setDots()
                this.addTie(StartStop.STOP)
                // add tieNote in noteList before this Note
                val index = engine.noteList.indexOf(this)
                engine.noteList.add(index, tieNote)
            }
        }
        notes.last().run {
            if (noteType == null) {
                val newAddedTime = if (dotPlace == 1) addedTime*2 else addedTime
                val tieNote = this.copy().also {
                    it.rationalDuration = this.rationalDuration - newAddedTime
                    it.noteType = teo(it.rationalDuration)
                    it.setDots()
                    it.addTie(StartStop.STOP)
                }
                // set fields on the this note
                this.rationalDuration = newAddedTime
                this.noteType = teo(this.rationalDuration)
                this.setDots()
                this.addTie(StartStop.START)
                // add tieNote in noteList before this Note
                val index = engine.noteList.indexOf(this)
                engine.noteList.add(index, tieNote)
            }
        }
//        if (quarterDuration()/division)
    }

    /*private fun addTuplet(subListSize: Int, i: Int, note: Note, num: Int, normalType: String?) {
        note.timeModification = TimeModification().apply {
            actualNotes = (division + num).toBigInteger()
            normalNotes = (division + (num - 1)).toBigInteger()
            this.normalType = normalType
        }
        if (i == 0) note.setTuplet(type = StartStop.STOP)
        if (i == subListSize - 1) note.setTuplet(YesNo.YES, AboveBelow.ABOVE, StartStop.START)
    }*/

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

fun List<Any>.mySublist(tChar: Tchar, notesNum: Int): List<Note> {
    return mutableListOf<Note>().apply {
        val index = this@mySublist.indexOf(tChar)
        addAll(this@mySublist.subList(0, index).filterIsInstance<Note>().asReversed().subList(0, 2).asReversed())
        if (notesNum-2 > 0)
            addAll(this@mySublist.subList(index+1, this@mySublist.size).filterIsInstance<Note>().subList(0, notesNum-2))
    }
}

private fun Int.toFraction() = getFraction(this, 1)

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

private fun getNoteType(fraction: Fraction): String? = fractionMap[fraction]
/** Subtracts the other value from this value. */
private operator fun Fraction.minus(other: Fraction) = subtract(other)
private operator fun Fraction.plus(other: Fraction) = add(other)
/** Multiplies this value by the other value. */
private operator fun Fraction.times(other: Fraction) = multiplyBy(other)
operator fun Fraction.times(other: Int) = multiplyBy(other.toFraction())
private fun teo(fraction: Fraction): String? =
        if (fraction.denominator%2 == 0) getNoteType(fraction)
        else getNoteType(getReducedFraction(fraction.numerator, fraction.denominator-1))