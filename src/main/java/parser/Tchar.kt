package parser

import org.audiveris.proxymusic.*
import west.cast
import java.util.function.Consumer
import kotlin.math.abs

class Tchar(private val dotPlace: Int, var division: Int, val argo: Boolean): Consumer<Engine> {
    companion object {
        private val tieStart = Tie().apply { type = StartStop.START }
        private val tieStop = Tie().apply { type = StartStop.STOP }

        private fun checkDots(note: Note) {
            note.noteType?.let { noteType ->
                note.dot.clear()
                for (i in 0 .. noteType.count { it == '.' }) note.dot += EmptyPlacement()
            }
        }
    }

    override fun accept(engine: Engine) {
        fun getNoteType(i: Int): String = engine.toNoteTypeDiv(if (i == dotPlace - 1) division - 1 else division + 1)
                        ?: throw NullPointerException("String noteType doesn't exist")
        val notes: MutableList<Any> = engine.noteList
        fun Note.index(): Int = notes.indexOf(this)
        val subList: List<Note> = notes.mySublist(this, division)
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
                if (tieNoteIndex > 0) notes.add(subList.last().index() /*+ tieNoteIndex*/, tieNote)
                else notes.add(subList[0].index() - 1, tieNote)
            }
        }
        if (division < 0) {
            val note = if (argo) subList[1] else subList[0]
            val duration = note.duration_ + abs(division) * engine.divisions
            val noteTypeS = engine.toNoteType(duration)
            if (noteTypeS != null) {
                note.setDurType(duration, noteTypeS)
                checkDots(note)
            } else {
                note.addTied(StartStopContinue.STOP)
                Byzantine.Cloner.deepClone(note).run {
                    val newDuration = duration / engine.divisions * engine.divisions
                    val newNoteType = engine.toNoteType(newDuration)
//                            ?: throw NullPointerException("String noteType doesn't exist")
                    if (newNoteType == null) {
                        println(newDuration.toString() + "  ")
                        engine.noteTypeMap.forEach {
                            println(it)
                        }
//                        throw NullPointerException("String noteType doesn't exist")
                    }
                    setDurType(newDuration, "newNoteType")
                    addTied(StartStopContinue.START)
                    notes.add(notes.indexOf(note), this)
                }
            }
        }
    }

    private fun addTuplet(subListSize: Int, i: Int, note: Note, num: Int, normalType: String?) {
        note.timeModification = TimeModification().apply {
            actualNotes = (division + num).toBigInteger()
            normalNotes = (division + (num - 1)).toBigInteger()
            this.normalType = normalType
        }
        if (i == 0) note.setTuplet(YesNo.YES, AboveBelow.ABOVE, StartStop.START)
        if (i == subListSize - 1) note.setTuplet(type = StartStop.STOP)
    }

    override fun toString(): String {
        return when {
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
}

fun List<Any>.mySublist(tChar: Tchar, notesNum: Int): List<Note> {
    return mutableListOf<Note>().apply {
        val clone = this@mySublist.toMutableList()
        val tCharIndex = clone.indexOf(tChar)
        add(clone.subList(0, tCharIndex).asReversed().first { it is Note }.cast()!!)
        val nextList = clone.subList(tCharIndex + 1, clone.size)
        var nextNote = nextList.first { it is Note }.cast<Note>()!!
        add(nextNote)
        for (i in 1 until notesNum) {
            nextNote = nextList.subList(nextList.indexOf(nextNote)+1, nextList.size).first { it is Note }.cast<Note>()!!
            add(nextNote)
//            clone.remove(nextNote)
        }
    }
}