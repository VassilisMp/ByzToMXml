package parser

import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File
import java.io.FileInputStream

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        //    private val title = "elpiza"
        //    private val title = "dynamis_k_ioannidh_simple"
        val title = "makarios_anir_syntoma_fokaeos_simple"
        val parser = Parser(XWPFDocument(FileInputStream("$title.docx")))
        parser.parse()
    }

    @Deprecated("One time used only")
    fun generateByzMusicSymbolsGrammar(grammarName: String = "generatedGrammar") {
        val s = (0x1D046..0x1D0F5).joinToString(separator = "\n") {
            val name = Character.getName(it)
                    .replaceFirst("BYZANTINE MUSICAL SYMBOL ", "")
                    .replace(' ', '_')
            val char = String(Character.toChars(it))
            "$name : \'$char\' ;"
        }
        println(s)
        File("$grammarName.g4").writeText("lexer grammar $grammarName;\n\n$s")
    }
}