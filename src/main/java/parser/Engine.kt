package parser

import com.google.common.math.IntMath.factorial
import org.apache.commons.lang3.math.Fraction.getFraction
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.audiveris.proxymusic.*
import org.audiveris.proxymusic.util.Marshalling
import parser.GorgotitesVisitor.Companion.gorgon
import parser.fthores.ByzScale
import parser.fthores.ByzScale.Companion.get2OctavesScale
import parser.fthores.Martyria
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
