package parser

import Byzantine.ByzClass
import grammar.ByzLexer
import grammar.ByzParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CodePointCharStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFRun

class Parser(private val docx: XWPFDocument) {
    // = XWPFDocument(FileInputStream("$title.docx"))
    //    private val title = "elpiza"
//        private val title = "dynamis_k_ioannidh_simple"
//    private val title = "makarios_anir_syntoma_fokaeos_simple"
    private val byzCharsStr: String = docToString()
//    private val byzCharsStr: String = """(I096I055I045I091F070)(B097ΑνεB097εB101)(B120ε)(B115ΔυB118υB216)(B118υB216)(B107υB104ναB117)(B039αB208)(B115αB101)(B106α)(B097μιςB117α)(B102B073)(B095αB114)(B107α)(B099αB101)(B106α)(B106α)(B106α)(B099αB101)(B106αB104γιB057)(B106ι)(B099ιB087)(B106ι)(B099ιB087)(B106ι)(B099ιB101)(B106ι)(B115ι)(B099ιB101)(B115ιB101)(B106ι)(B039ιB208)(B106ιB082)(B100αB117)(B118αB110)(B092B097α)(B039αL165B110)(B118αB216)(B106α)(B106αB110)(B099αB101)(B106α)(B106α)(B106α)(B115Α)(B097γι)(B107οB117)(B115οB101)(B106ο)(B106ος)(B045ο)(B107ΘεB117)(B102ο)(B039οL165)(B118οB216)(B106οB105)(B115οB069)(B095νοB114)(B106ο)(B106οB114)(B115ο)(B039οL170)(B106οB082)(B115ο)(B092B097οB097οB101)(B097οςB057Β)(B102ΧΟΡΟΣαB073)(B095αB114)(B107α)(B099αB101)(B106α)(B106α)(B106α)(B099αB101)(B106αB104γιB057)(B106ι)(B099ιB087)(B106ι)(B099ιB087)(B106ι)(B099ιB101)(B106ι)(B115ι)(B099ιB101)(B115ιB101)(B106ι)(B039ιB208)(B106ιB082)(B162ΑB117)(B118α)(B092B097α)(B039αL165B110)(B118αB216)(B106α)(B106αB110)(B099αB101)(B106α)(B106α)(B106α)(B115α)(B097γι)(B107οB117)(B115οB101)(B106ο)(B106ος)(B045Ι)(B107σχυB117)(B102ρο)(B039οL165)(B118οB216)(B106οB105)(B115οB069)(B095νοB114)(B106ο)(B106οB114)(B115ο)(B039οL170)(B106οB082)(B115ο)(B092B097οB097οB101)(B097οςB057Α)(B102ΧΟΡΟΣα)(B120α)(B095αB114B089)(B106αB104να)(B115αB073B101)(B115αB069)(B092B107α)(B106α)(B115αB117)(B083α)(B106α)(B106αB114)(B097γι)(B115ι)(B039ιL170)(B099ι)(B115ι)(B107ιB117)(B115ιB069)(B097νιB117)(B045ι)(B092B097Α)(B106αB114B095αB114)(B092B106γι)(B106ιB114B042)(B097ος)(B097Α)(B111L102θαB073)(B106αB105)(B115αB069)(B097α)(B106αB105)(B099αB101)(B106α)(B106α)(B106α)(B115αB117)(B045α)(L112αB114)(L112αB114)(B107α)(B099αB119)(B106α)(B099αB101)(B115αB101)(B106α)(B106α)(B106α)(B106α)(B083να)(B045αB114)(B106α)(B106α)(B106χα)(B092B097αB097αB101)(B097αB057Β)(B097ΧΟΡΟΣεB069)(B111B102λε)(B120εB095εB114B089)(B106εB104νε)(B115εB073B101)(B106ε)(B106ε)(B115εB117)(B115η)(B106η)(B106ηB114)(B097η)(B115η)(B039ηB208)(B099η)(B120η)(B107ηB117)(B120ηB082)(B097νηB117)(B106η)(B115Ε)(B092B097λε)(B106εB114B095εB114)(B092B106η)(B106ηB114B042)(B097σο)(B120ον)(B118η)(B092B097μα)(B106αB114B042)(B106α)(B092B115αB097αB101)(B106αB105)(B092B106α)(B106α)(B115α)(B092B097αB097αB101)(B097αςB057)"""

    private fun docToString(): String = mutableListOf<XWPFRun>().apply {
        // add runs in list to iterate
        for (par in docx.paragraphs) for (run in par.runs) add(run)
    }.joinToString(separator = "") { run ->
        val byzClass = fontToByzClass[run.fontName]
        run.text().run {
            when {
                // translate special byzantine letters
                byzClass == ByzClass.N -> when(this) {
                    "u" -> "ου"
                    "s" -> "στ"
                    "n" -> "ν"
                    "z" -> "ζ" // TODO this is the wrong letter translation
                    else -> ""
                }
                // Arxigramma case
                byzClass == ByzClass.A -> "@$this".replace(".", "")
                // if is Byzantine font then keep only chars in unicode private use area
                byzClass != null && byzClass != ByzClass.T ->
                    filter { it.toInt() in 0xE000..0xF8FF }
                    .map {
                        val codePoint = (it.toInt() - 0xF000)
                        if (byzClass == ByzClass.B && codePoint == 32) ""
                        else "$byzClass${String.format("%03d", codePoint)}"
                    }.joinToString(separator = "") { it }
                // else keep only [α-ωΑ-Ω], which is greek text
                else -> this.replace(".", "")
            }
        }
    }.splitToSequence(' ')
        .filter { it.isNotEmpty() }
        .map { "($it)" }
        .joinToString(separator = "")

    fun parse(): MutableList<Any> {
        val noParentheses = stage1()
        return stage2(noParentheses)
    }

    private fun stage1(): String {
        println(byzCharsStr)
        val lexer = ByzLexer(byzCharsStr.toCodePointCharStream())
        val tokenStream = CommonTokenStream(lexer)
        val parser = ByzParser(tokenStream)
        val tree = parser.score()
        val walker = ParseTreeWalker()
        // fix the syllables places, and rewrite the stream
        val listener = RewriterListener(tokenStream)
        walker.walk(listener, tree)
        println(tree.toStringTree(parser))
        println(listener.rewriter.text)
        // remove parentheses for the next parse stage
        val noParentheses = listener.rewriter.text.replace(Regex("[()]"), "")
        println(noParentheses)
        return noParentheses
    }

    fun stage2(str: String): MutableList<Any> {
        val lexer = ByzLexer(str.toCodePointCharStream())
        val tokenStream = CommonTokenStream(lexer)
        val parser = ByzParser(tokenStream)
        val score2Tree = parser.score2()
        val visitor = ScoreVisitor()
        visitor.visit(score2Tree)
        println(visitor.elements)
        return visitor.elements
    }

    private fun String.toCodePointCharStream(): CodePointCharStream = CharStreams.fromString(this)
}