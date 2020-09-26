package parser

import Byzantine.ByzScale
import Byzantine.ByzStep
import Byzantine.ByzStep.*
import Byzantine.Martyria
import com.google.common.math.IntMath.factorial
import com.uchuhimo.collections.biMapOf
import org.apache.commons.lang3.math.Fraction.getFraction
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.audiveris.proxymusic.*
import org.audiveris.proxymusic.Step.*
import org.audiveris.proxymusic.util.Marshalling
import parser.GorgotitesVisitor.Companion.gorgon
import west.Note
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
    lateinit var noteList: MutableList<Any>

    init {
        val matcher: Matcher = Pattern.compile("(.*/)*(.*)(\\.docx?)").matcher(filePath)
        if (matcher.find()) fileName = matcher.group(2) else throw FileNotFoundException("couldn't match filename")
        docx = XWPFDocument(FileInputStream(filePath))
        initAccidentalCommas()
        // TODO
//        putFthoraScale(ByzScale(currentByzScale), 0)
    }

    fun initAccidentalCommas() {
        // TODO
//        ByzScale.initAccidentalCommas(currentByzScale, relativeStandardStep)
    }

    @Throws(Exception::class)
    fun run() {
        val parser = Parser(docx)
        noteList = parser.parse()
        // convert argo to argia, gorgo
        noteList.filterIsInstance<Tchar>().filter { it.argo }.forEach {
            it.argo = false
            noteList.add(noteList.indexOf(it)+1, gorgon())
        }
        noteList.filterIsInstance<Tchar>().forEach { it.accept(this) }
        divisions = factorial(divisions)
        val list = noteList.filterIsInstance<Note>().map {
            it.noteType?.run { it.noteType = it.noteType!!.replace(".", "") }
            it.duration_ = (it.rationalDuration*divisions).toInt()
            it
        }
        FileOutputStream("$fileName.xml").use { fileOutputStream ->
            val part = newPart("P1", "Voice", divisions, list)
            val scorePartwise: ScorePartwise = newScorePartWise(part)
            val marshaller: Marshaller = Marshalling.getContext(ScorePartwise::class.java).createMarshaller()
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
            marshaller.marshal(scorePartwise, fileOutputStream)
        }
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

private fun String.toFraction() = getFraction(this)
