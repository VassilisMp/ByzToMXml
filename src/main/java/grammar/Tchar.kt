package grammar

import Byzantine.Engine
import org.audiveris.proxymusic.*
import java.math.BigDecimal
import java.util.function.Consumer
import kotlin.math.abs

class Tchar(private val dotPlace: Int, var divisions: Int, val argo: Boolean): Consumer<Engine> {
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
        val notes: MutableList<Note> = engine.noteList.toMutableList()
        if (divisions > 0) {
            val index = engine.index
            val subList: List<Note> = notes.subList(index - 1, index + divisions)
            var addedTime: Int
            if (dotPlace == 0) {
                if (engine.division % (divisions + 1) != 0) engine.changeDivision(divisions + 1)
            } else if (engine.division % (divisions + 2) != 0) engine.changeDivision(divisions + 2)
            var tieNote: Note? = null
            var tieNoteIndex = 0
            for (i in subList.indices) {
                addedTime = if (dotPlace == 0) engine.division / (divisions + 1) else engine.division / (divisions + 2)
                val note = subList[i]
                val duration = note.duration.toInt()
                var noteType: String?
                if (duration > engine.division) {
                    var addedTime1 = addedTime
                    val producedDuration = duration - engine.division + addedTime1
                    // new added code
                    noteType = engine.noteTypeMap.inverse()[producedDuration]
                    if (noteType != null) {
                        note.setDurType(producedDuration, noteType)
                        checkDots(note)
                        continue
                    }
                    if (i == dotPlace - 1) addedTime1 *= 2
                    note.durationInt = addedTime1
                    tieNote = note.copy()
                    tieNoteIndex = i
                    var tDuration = duration - engine.division //((duration / engine.division) * engine.division) - engine.division;
                    tieNote.type.value = engine.noteTypeMap.inverse()[tDuration]
                    var tie: Tie
                    var tie2: Tie
                    if (dotPlace == 0) {
                        tie = tieStart
                        tie2 = tieStop
                        if (divisions % 2 == 0) {
                            noteType = engine.noteTypeMap.inverse()[engine.division / divisions]
                            if (noteType == null) {
                                engine.changeDivision(divisions)
                                tDuration *= divisions
                                noteType = engine.noteTypeMap.inverse()[engine.division / divisions]
                                if (noteType == null) throw NullPointerException("String noteType doesn't exist")
                            }
                            note.type.value = noteType
                            addTuplet(subList.size, i, note, 1, noteType)
                        } else note.type.value = engine.noteTypeMap.inverse()[engine.division / (divisions + 1)]
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
                        var divideWith = divisions + 1
                        if (i == dotPlace - 1) divideWith = divisions
                        noteType = engine.noteTypeMap.inverse()[engine.division / divideWith]
                        if (divisions % 2 != 0) {
                            if (noteType == null) {
                                engine.changeDivision(divisions + 1)
                                tDuration *= divisions + 1
                                noteType = getNoteType(i, engine)
                            }
                            addTuplet(subList.size, i, note, 2, noteType)
                        }
                        note.type.value = noteType
                    }
                    tieNote.addTie(tie)
                    note.addTie(tie2)
                    tieNote.durationInt = tDuration
                    checkDots(note)
                } else {
                    if (dotPlace == 0) {
                        note.duration = BigDecimal(addedTime)
                        if (divisions % 2 == 0) {
                            noteType = engine.noteTypeMap.inverse()[engine.division / divisions]
                            if (noteType == null) {
                                engine.changeDivision(divisions)
                                noteType = engine.noteTypeMap.inverse()[engine.division / divisions]
                            }
                            addTuplet(subList.size, i, note, 1, noteType)
                        } else noteType = engine.noteTypeMap.inverse()[engine.division / (divisions + 1)]
                        if (noteType == null) throw NullPointerException("String noteType doesn't exist")
                        note.type.value = noteType
                    } else {
                        var divideWith = divisions + 1
                        if (i == dotPlace - 1) {
                            divideWith = divisions
                            addedTime *= 2
                        }
                        note.duration = BigDecimal(addedTime)
                        if (divisions % 2 != 0) {
                            noteType = engine.noteTypeMap.inverse()[engine.division / divideWith]
                            if (noteType == null) {
                                engine.changeDivision(divisions + 1)
                                if (tieNote != null) {
                                    val tieNoteDur = tieNote.duration.toInt()
                                    tieNote.duration = BigDecimal.valueOf(tieNoteDur * (divisions + 1).toLong())
                                }
                                noteType = getNoteType(i, engine)
                            }
                            note.type.value = noteType
                            addTuplet(subList.size, i, note, 2, noteType)
                        } else {
                            noteType = engine.noteTypeMap.inverse()[engine.division / divideWith]
                            if (noteType == null) noteType = engine.noteTypeMap.inverse()[addedTime]
                            note.type.value = noteType
                        }
                    }
                }
            }
            if (tieNote != null) {
                if (tieNoteIndex > 0) notes.add(index + tieNoteIndex, tieNote) else notes.add(index - 1 + tieNoteIndex, tieNote)
            }
        }
        if (divisions < 0) {
            val note = if (argo) notes[engine.index + 1] else notes[engine.index]
            val duration = note.durationInt + abs(divisions) * engine.division
            val noteTypeS = engine.noteTypeMap.inverse()[duration]
            if (noteTypeS != null) {
                note.setDurType(duration, noteTypeS)
                checkDots(note)
            } else {
                note.addTied(StartStopContinue.STOP)
                Byzantine.Cloner.deepClone(note).run {
                    val newDuration = duration / engine.division * engine.division
                    val newNoteType = engine.noteTypeMap.inverse()[newDuration]
                            ?: throw NullPointerException("String noteType doesn't exist")
                    setDurType(newDuration, newNoteType)
                    addTied(StartStopContinue.START)
                    notes.add(notes.indexOf(note), this)
                }
            }
        }
    }

    private fun getNoteType(i: Int, engine: Engine): String {
        val divideWith: Int = if (i == dotPlace - 1) divisions - 1 else divisions + 1
        return engine.noteTypeMap.inverse()[engine.division / divideWith] ?: throw NullPointerException("String noteType doesn't exist")
    }

    private fun addTuplet(subListSize: Int, i: Int, note: Note, num: Int, normalType: String?) {
        note.timeModification = TimeModification().apply {
            actualNotes = (divisions + num).toBigInteger()
            normalNotes = (divisions + (num - 1)).toBigInteger()
            this.normalType = normalType
        }
        if (i == 0) note.setTuplet(YesNo.YES, AboveBelow.ABOVE, StartStop.START)
        if (i == subListSize - 1) note.setTuplet(type = StartStop.STOP)
    }

    override fun toString(): String {
        return when {
            dotPlace == 0 && divisions == 1 -> "gorgon"
            dotPlace == 1 && divisions == 1 -> "GorgonParestigmenoAristera"
            dotPlace == 2 && divisions == 1 -> "GorgonParestigmenoDexia"
            dotPlace == 0 && divisions == 2 -> "digorgon"
            dotPlace == 1 && divisions == 2 -> "DigorgonParestigmenoAristeraKato"
            dotPlace == 2 && divisions == 2 -> "DigorgonParestigmenoAristeraAno"
            dotPlace == 3 && divisions == 2 -> "DigorgonParestigmenoDexia"
            dotPlace == 0 && divisions == 3 -> "trigorgon"
            dotPlace == 1 && divisions == 3 -> "TrigorgonParestigmenoAristeraKato"
            dotPlace == 3 && divisions == 3 -> "TrigorgonParestigmenoAristeraPano"
            dotPlace == 4 && divisions == 3 -> "TrigorgonParestigmenoDexia"
            dotPlace == 0 && divisions == -1 && argo -> "argon"
            dotPlace == 0 && divisions == -2 && argo -> "imiDiargon"
            dotPlace == 0 && divisions == -3 && argo -> "diargon"
            dotPlace == 0 && divisions == -1 -> "apli"
            dotPlace == 0 && divisions == -2 -> "dipli"
            dotPlace == 0 && divisions == -3 -> "tripli"
            else -> super.toString()
        }
    }
}