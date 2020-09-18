package parser

import Byzantine.ByzScale
import Byzantine.ByzStep
import Byzantine.ByzStep.*
import Byzantine.Martyria
import com.uchuhimo.collections.MutableBiMap
import com.uchuhimo.collections.biMapOf
import com.uchuhimo.collections.mutableBiMapOf
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.audiveris.proxymusic.*
import org.audiveris.proxymusic.Step.*
import org.audiveris.proxymusic.util.Marshalling
import west.newPart
import west.newScorePartWise
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.xml.bind.Marshaller

class Engine(filePath: String) {
    private val docx: XWPFDocument
    private val fileName: String
    val currentByzScale: ByzScale = ByzScale.get2OctavesScale()

    // measure division must be at least 2, or else I 'll have to implement the case of division change, in the argo case as well..
    // division must be <= 16383
    var divisions: Int = 1
        set(value) {
            field = value
            mapValuesUpdate()
        }
    lateinit var noteList: MutableList<Any>
    var noteTypeMap: MutableBiMap<String, Int> = mutableBiMapOf(
            "maxima" to 28,
            "long" to 16,
            "breve" to 8,
            "whole" to 4,
            "half" to 2,
            "quarter" to 1
    )
    fun toNoteType(duration: Int) = noteTypeMap.inverse[duration]
    fun toNoteTypeDiv(num: Int) = noteTypeMap.inverse[divisions / num]

    init {
        val matcher: Matcher = Pattern.compile("(.*/)*(.*)(\\.docx?)").matcher(filePath)
        if (matcher.find()) fileName = matcher.group(2) else throw FileNotFoundException("couldn't match filename")
        docx = XWPFDocument(FileInputStream(filePath))
        initAccidentalCommas()
        // TODO
//        putFthoraScale(ByzScale(currentByzScale), 0)
    }

    fun initAccidentalCommas() {
        // T0DO
//        ByzScale.initAccidentalCommas(currentByzScale, relativeStandardStep)
    }

    @Throws(Exception::class)
    fun run() {
        val parser = Parser(docx)
        noteList = parser.parse()
//        noteList.filterIsInstance<Tchar>().forEach { it.accept(this) }
        noteList.filterIsInstance<Note>().forEach { it.noteType?.run { it.noteType = it.noteType!!.replace(".", "") } }
        val list = noteList.filterIsInstance<Note>().apply { forEach { it.noteType?.run { it.noteType = it.noteType!!.replace(".", "") } } }
        try {
            FileOutputStream("$fileName.xml").use { fileOutputStream ->
                val part = newPart("P1", "Voice", divisions, list)
                val scorePartwise: org.audiveris.proxymusic.ScorePartwise = newScorePartWise(part)
                val marshaller: Marshaller = Marshalling.getContext(org.audiveris.proxymusic.ScorePartwise::class.java).createMarshaller()
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
                marshaller.marshal(scorePartwise, fileOutputStream)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun mapValuesUpdate() {
//        noteTypeMap.clear();
        // 1024th, 512th, 256th, 128th, 64th, 32nd, 16th, eighth, quarter, half, whole, breve, long, and maxima
        noteTypeMap["maxima.."] = divisions * 49
        noteTypeMap["maxima."] = divisions * 42
        noteTypeMap["maxima"] = divisions * 28
        //noteTypeMap.put("long..", division * 28);
        noteTypeMap["long."] = divisions * 24
        noteTypeMap["long"] = divisions * 16
        noteTypeMap["breve.."] = divisions * 14
        noteTypeMap["breve."] = divisions * 12
        noteTypeMap["breve"] = divisions * 8
        noteTypeMap["whole.."] = divisions * 7
        noteTypeMap["whole."] = divisions * 6
        noteTypeMap["whole"] = divisions * 4
        if ((divisions * 2) % 4 == 0) noteTypeMap["half.."] = (3.5 * divisions).toInt()
        noteTypeMap["half."] = divisions * 3
        noteTypeMap["half"] = divisions * 2
        noteTypeMap["quarter"] = divisions
        if (divisions % 2 == 0) {
            noteTypeMap["quarter."] = (divisions * 1.5).toInt()
            noteTypeMap["eighth"] = divisions / 2
        }
        if (divisions % 4 == 0) {
            noteTypeMap["quarter.."] = (divisions * 1.75).toInt()
            noteTypeMap["eighth."] = (divisions * 0.75).toInt()
            noteTypeMap["16th"] = divisions / 4
        }
        if (divisions % 8 == 0) {
            noteTypeMap["eighth.."] = (divisions * 0.875).toInt()
            noteTypeMap["16th."] = (divisions * 0.375).toInt()
            noteTypeMap["32nd"] = divisions / 8
        }
        if (divisions % 16 == 0) {
            noteTypeMap["16th.."] = (divisions * 0.4375).toInt()
            noteTypeMap["32nd."] = (divisions * 0.1875).toInt()
            noteTypeMap["64th"] = divisions / 16
        }
        if (divisions % 32 == 0) {
            noteTypeMap["32nd.."] = (divisions * 0.21875).toInt()
            noteTypeMap["64th."] = (divisions * 0.09375).toInt()
            noteTypeMap["128th"] = divisions / 32 // TODO continue to 128 dotted
        }
        if (divisions % 64 == 0) {
            noteTypeMap["64th.."] = (divisions * 0.109375).toInt()
            noteTypeMap["256th"] = divisions / 64
        }
        if (divisions % 128 == 0) {
            noteTypeMap["512th"] = divisions / 128
        }
        if (divisions % 256 == 0) noteTypeMap["1024th"] = divisions / 256
    }

    fun changeDivision(multiplier: Int) {
        divisions *= multiplier
        // change the duration of all notes according to the new corresponding to the new division value
        noteList.filterIsInstance<Note>().forEach { note -> note.updateDivision(multiplier) }
        // reInsert the values in the map to add those supported by the new measure division
        mapValuesUpdate()
    }

    private fun commasToAccidental(commas: Int): Accidental? {
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
    }

    companion object {
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
        private val relativeStandardStep: ByzStep = STANDARD_MAP.getValue(NH.toStep())

        fun ByzStep.toStep() = STEPS_MAP.getValue(this)
        fun Step.toByzStep() = STEPS_MAP.inverse[this]
        fun Note.byzStep(): ByzStep? = STEPS_MAP.inverse[step]
    }
}

/*
@Suppress("UNCHECKED_CAST")
fun <K, V> BiMap<in K, in V>.with(vararg pairs: Pair<K, V>): BiMap<K, V> {
    for ((key, value) in pairs) {
        put(key, value)
    }
    return this as BiMap<K, V>
}*/
