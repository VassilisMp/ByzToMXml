package parser

import com.google.common.math.IntMath.factorial
import org.antlr.v4.runtime.InputMismatchException
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.audiveris.proxymusic.ClefSign
import org.audiveris.proxymusic.ScorePartwise
import org.audiveris.proxymusic.ScorePartwise.Part.Measure
import org.audiveris.proxymusic.util.Marshalling
import parser.GorgotitesVisitor.Companion.gorgon
import parser.fthores.ByzScale
import parser.fthores.ByzScale.Companion.get2OctavesScale
import west.*
import west.Note.Companion.relativeStandardStep
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.xml.bind.Marshaller
import javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT

class Engine(filePath: String) {
    private val docx: XWPFDocument
    private val fileName: String
    val currentByzScale: ByzScale = get2OctavesScale()

    // measure division must be at least 2, or else I 'll have to implement the case of division change, in the argo case as well..
    // division must be <= 16383
    var divisions: Int = 1
    lateinit var noteList: MutableList<Any>

    init {
        val matcher: Matcher = Pattern.compile("(.*/)*(.*)(\\.docx?)").matcher(filePath)
        if (matcher.find()) fileName = matcher.group(2) else throw FileNotFoundException("couldn't match filename")
        docx = XWPFDocument(FileInputStream(filePath))
        initAccidentalCommas()
    }

    fun initAccidentalCommas() {
        // TODO
//        ByzScale.initAccidentalCommas(currentByzScale, relativeStandardStep)
    }

    @Throws(InputMismatchException::class)
    fun run() {
        val parser = Parser(docx)
        noteList = parser.parse()
        // convert argo to gorgo, argia
        noteList.filterIsInstance<Tchar>().filter { it.argo }.forEach {
            // get argo index
            val argoIndex = noteList.indexOf(it)
            // replace argo with gorgo
            noteList[argoIndex] = gorgon()
            // add the argia to the note
            val nextNoteIndex = noteList.nextNoteIndex(argoIndex, 1)
            noteList.add(nextNoteIndex + 1, it.apply { argo = false })
        }
        // run timeChars
        for (it in noteList.filterIsInstance<Tchar>()) {
            it.accept(this)
        }
        // set divisions based on the the greatest denominator used in dividing times
        divisions = factorial(divisions)
        // remove dots from noteType strings
        // set notes duration based on divisions
        val list = noteList.filterIsInstance<Note>().map {
            it.noteType?.run { it.noteType = it.noteType!!.replace(".", "") }
            it.duration_ = (it.rationalDuration*divisions).toInt()
            it
        }
        // capitalize first note lyric
        list.first().apply { if (lyricText != null) { lyricText = lyricText!!.capitalize() } }
        val measures = toMeasures(list)
        val byzScale = get2OctavesScale()
        byzScale.initAccidentalCommas(relativeStandardStep)
        // get commas from the key of the score
        /*fun getCommasFromKey(note: Note): Int {
            println("${note.byzStep} ${note.byzOctave}")
            return byzScale.getMartyria(note.byzStep, note.byzOctave!!)!!.accidentalCommas
        }
        fun getAccidental(commas: Int) = ACCIDENTALS_MAP[commas]
        measures.forEach { measure ->
            val measureNotes = measure.noteOrBackupOrForward.filter { it is Note && it.rest == null }.cast<List<Note>>()!!
            measureNotes.forEachIndexed { index, note ->
                val lastNote = measureNotes.subList(0, index).findLast { it.equalsPitch(note) }
                val stepCommas = if (lastNote == null) getCommasFromKey(note) else lastNote.accidentalCommas!!
                if (note.accidentalCommas == null) note.accidentalCommas = stepCommas
                val diff = note.accidentalCommas!! - stepCommas
                val accidentalValue = getAccidental(diff)
                if (accidentalValue != null) note.accidental = Accidental().apply { value = accidentalValue }
            }
        }*/
        // TODO add Key
        if (parser.key != null)
            measures.first().key = parser.key
//        measures.first().key = ByzScale.NEANES_KEY
        val part = newPart("P1", "Voice", measures)
        newScorePartWise(part).toXml(fileName)
    }

    private fun ScorePartwise.toXml(filename: String) = FileOutputStream("$filename.xml").use {
        val marshaller: Marshaller = Marshalling.getContext(this::class.java).createMarshaller()
        marshaller.setProperty(JAXB_FORMATTED_OUTPUT, true)
        marshaller.marshal(this, it)
    }

    private fun toMeasures(list: List<Any>, timeBeats: Int? = null): List<Measure> {
        val measures = ArrayList<Measure>()
        var i = 0
        var index = i
        var durations = 0
        var measureNum = 1
        if (timeBeats != null) {
            while (i < list.size) {
                val note: Note = list[i].cast() ?: continue
                durations += note.duration_
                if (durations == divisions * timeBeats) {
                    measures += newMeasure(divisions, measureNum++, noteList = list.subList(index, i + 1))
                    index = i + 1
                    durations = 0
                } else if (durations > divisions * timeBeats) throw Exception("error in noteLists")
                i++
            }
            // set Time Signature on the first measure
            measures.first().timeSignature = arrayOf(timeBeats, 4)
        } else {
            var lastNominator = 0
            while (i < list.size) {
                val note: Note = list[i].cast() ?: continue
                durations += note.duration_
                // time signatures allowed range 2/4 to 12/4
                val j = durations/divisions
                if (j in 2..12) {
                    measures += newMeasure(
                            divisions,
                            measureNum++,
                            timeSignature = if (j == lastNominator) null else arrayOf(j, 4).also { lastNominator = j },
                            noteList = list.subList(index, i + 1)
                    )
                    index = i + 1
                    durations = 0
                }
                if (durations > divisions * 12) throw Exception("error in dividing measures, i=$i, durations=$durations")
                i++
            }
        }
        measures.first().clef = newClef(ClefSign.G, 2)
        return measures
    }

    /*private fun commasToAccidental(commas: Int): Accidental? {
        val accidentalValue: AccidentalValue = Martyria.ACCIDENTALS_MAP[commas] ?: return null
        val accidental = Accidental()
        accidental.value = accidentalValue
        return accidental
    }

    private fun keyToMap(key: Key): Map<Step, AccidentalValue?> {
        // create Step to AccidentalValue map
        val stepToAccidental: MutableMap<Step, AccidentalValue?> = HashMap(7)
        // put all Steps as keys in map
        Step.values().forEach { step: Step -> stepToAccidental[step] = null }
        // get
        val nonTraditionalKey: List<Any> = key.nonTraditionalKey
        var i: Int = 0
        while (i < nonTraditionalKey.size) {
            val step: Step = nonTraditionalKey[i] as Step
            val accidentalValue: AccidentalValue = nonTraditionalKey[i + 2] as AccidentalValue
            stepToAccidental[step] = accidentalValue
            i += 3
        }
        return stepToAccidental
    }*/
}

/*
@Suppress("UNCHECKED_CAST")
fun <K, V> BiMap<in K, in V>.with(vararg pairs: Pair<K, V>): BiMap<K, V> {
    for ((key, value) in pairs) {
        put(key, value)
    }
    return this as BiMap<K, V>
}*/

private fun List<Any>.nextNoteIndex(startIndex: Int, noteNum: Int = 1): Int {
    var noteNum1 = 0
    for (index in startIndex..this.size) {
        if (this[index] is Note) {
            noteNum1++
            if (noteNum1 == noteNum) return index
        }
    }
    return -1
    /*return subList(startIndex+1, this.size)
            .filterIsInstance<Note>()
            .firstOrNull()?.let { indexOf(this) }*/
}
