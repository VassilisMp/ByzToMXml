package parser

import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.FileInputStream

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        /*val docx = XWPFDocument(FileInputStream("elpiza.docx"))
        val name = docx.document.body.newCursor().execQuery("w:r").name
        println(name)*/
//        -classpath /home/vassilis/IdeaProjects/ByzToWes/libs/SaxonHE10-1J/*

        /*val grammarName = "generatedGrammar"

        val s = (0x1D046..0x1D0F5).joinToString(separator = "\n") {
            val name = Character.getName(it)
                    .replaceFirst("BYZANTINE MUSICAL SYMBOL ", "")
                    .replace(' ', '_')
            val char = String(Character.toChars(it))
            "$name : \'$char\' ;"
        }
        println(s)
        File("$grammarName.g4").writeText("lexer grammar $grammarName;\n\n$s")*/
        //    private val title = "elpiza"
        //    private val title = "dynamis_k_ioannidh_simple"
        val title = "makarios_anir_syntoma_fokaeos_simple"
        val parser = Parser(XWPFDocument(FileInputStream("$title.docx")))
        parser.parse()
    }
}