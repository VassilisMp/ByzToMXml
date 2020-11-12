package parser

import Byzantine.ByzClass
import grammar.ByzLexer
import grammar.ByzParser
import org.antlr.v4.runtime.*
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFRun
import org.audiveris.proxymusic.Key
import parser.visitors.ScoreVisitor

class Parser(private val docx: XWPFDocument, private val inPrivateArea: Boolean = true) {
    val byzCharsStr: String = docToString()
    var key: Key? = null

    // TODO parse per paragraph, so that I return error places more precise
    private fun docToString(): String = mutableListOf<XWPFRun>().apply {
        // add runs in list to iterate
        for (par in docx.paragraphs) for (run in par.runs) add(run)
    }.joinToString(separator = "") { run ->
        val byzClass = fontToByzClass[run.fontName]
        run.text().run {
            when {
                // translate special byzantine letters
                byzClass == ByzClass.N -> when(this) {
                    "u" -> "\uD834\uDCE7"//"ου"
                    "s" -> "\uD834\uDCE8"//"στ"
                    "n" -> "ν"
                    "z" -> "ζ" // TODO this is the wrong letter translation
                    // this character is used to delimit character clusters
                    "_" -> "_"
                    else -> ""
                }
                // Arxigramma case
                byzClass == ByzClass.A -> "@$this".replace(".", "")
                byzClass != null && byzClass != ByzClass.T -> {
                    // if is Byzantine font then keep only chars in unicode private use area
                    if (inPrivateArea) {
                        filter { it.toInt() in 0xE000..0xF8FF }
                                .map {
                                    val codePoint = (it.toInt() - 0xF000)
                                    if (byzClass == ByzClass.B && codePoint == 32) ""
                                    else "$byzClass${String.format("%03d", codePoint)}"
                                }.joinToString(separator = "") { it }
                    } else {
                        // if is Byzantine font but not in private area, only just convert chars to integers
                        map {
                            val codePoint = it.toInt()
                            if (byzClass == ByzClass.B && codePoint == 32) ""
                            else "$byzClass${String.format("%03d", codePoint)}"
                        }.joinToString(separator = "") { it }
                    }
                }
                // else keep only [α-ωΑ-Ω], which is greek text
                else -> this.replace(".", "")
            }
        }
    }

    @Throws(InputMismatchException::class)
    fun parse(str: String = byzCharsStr): MutableList<Any> {
        println(str)
        val lexer = ByzLexer(CharStreams.fromString(str))
        val tokenStream = CommonTokenStream(lexer)
        val parser = ByzParser(tokenStream)
        // add error Listener
        parser.removeErrorListeners()
        val errorListener = ErrorListener()
        parser.addErrorListener(errorListener)
        val scoreTree = parser.score()
        val visitor = ScoreVisitor(parser)
        visitor.visit(scoreTree)
        println(visitor.elements)
        key = visitor.key
        return visitor.elements
    }

    private class ErrorListener: BaseErrorListener() {
        var exception: InputMismatchException? = null
        override fun syntaxError(
                recognizer: Recognizer<*, *>?,
                offendingSymbol: Any?,
                line: Int,
                charPositionInLine: Int,
                msg: String?,
                e: RecognitionException?
        ) {
            if (line == 1 && e is InputMismatchException) exception = e
            super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e)
        }
    }
}