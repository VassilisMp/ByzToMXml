import grammar.ByzLexer
import grammar.ByzParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.apache.commons.lang3.StringUtils.stripAccents
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFRun
import java.io.FileInputStream
import java.util.stream.Collectors

// input = "Ã string çöntäining nön äsçii çhäräçters"
fun String?.clearNonAscii(): String? = stripAccents(this)

fun XWPFDocument.findCodepoints(): StringBuilder {
    val string = StringBuilder()
    for (paragraph in this.paragraphs) {
        for (run in paragraph.runs) {
            val codePointsStr = run.text().codePoints()
                    .mapToObj { i: Int -> i.toString() }
                    .collect(Collectors.joining(", "))
            string.append("text = " + run.text() + ", font = " + run.fontFamily + ", codePoints = " + codePointsStr)
        }
    }
    return string
}

fun XWPFDocument.hasPrivateAreaChars(): Boolean = mutableListOf<XWPFRun>().apply {
    // add runs in list to iterate
    for (par in this@hasPrivateAreaChars.paragraphs) for (run in par.runs) add(run)
}.map { xwpfRun -> xwpfRun.text().none { it.toInt() in 0xE000..0xF8FF } }.firstOrNull { it } != null

fun MutableList<Any>.addAll(vararg elements: Any) {
    for (it in elements) {
        if(it is Iterable<*>) {
            it.forEach {iterableIt ->
                if (iterableIt != null) add(iterableIt)
            }
        }
        else add(it)
    }
}

fun makeArkt() {
    val str = "I096I055I045I091F070B115B067F125 I096I042I043 I096I042I061 I096B050B096B037F033 I096I042I093F033 I096I042I095 I096I041I092F072 I096I051I092 I096I057I092F072B104 I096I057I092F068 I096I054I045I080F074 I096I054I045I091F070 I096I054I045B104 I096B051B064I037I093 I096I054I045B104I061 I096I054I045I043 I096I054I045I080F036 I096I036I080F095 I096I126I055I091 I096I126I055I123I087 I096I126I055I091F070I123I087 I096I126I055I091F070L115F041 I096I126I056I095 I096I126I056B102F036 I096I126I056I061I048 I096I126I056B102I043 I096I126I056B103I123F064 I096I126I056I091F064I048 I096I126I056I112F033 I096I126I056L102I112F033 I096I040I092F041 I096I040I125F041 I096I040L102F041 I096I040I125 I096I040L102F076 I096B054B094L102 I096B054B094B103 I096B054B094L115 I096I126I054I112F068 I096I126I054B102F068 I096I126I054I092 I096I126I054I112L102F058 I096I126I054I112L102F036"
    val lexer = ByzLexer(CharStreams.fromString(str))
    val tokenStream = CommonTokenStream(lexer)
    val parser = ByzParser(tokenStream)
    parser.score()
    val namesList = tokenStream.tokens.joinToString(" ") {
        ByzLexer.VOCABULARY.getSymbolicName(it.type)
    }.replace("HXOS_WORD", "\nHXOS_WORD")
    println(namesList)
}

class Test{
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val docx = XWPFDocument(FileInputStream("arktikesMartyries.docx"))
            println(docx.hasPrivateAreaChars())
        }
    }
}