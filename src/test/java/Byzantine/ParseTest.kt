package Byzantine

import org.apache.commons.lang3.StringUtils
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.junit.jupiter.api.Test
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import java.util.stream.Collectors

class ParseTest {
    private val docx = XWPFDocument(FileInputStream("elpiza.docx"))

    @Test
    fun testParse() {
        val regex = Regex("[α-ωΑ-Ω]+")
        val nonPrintable = "\\P{Print}"
        try {
            XWPFDocument().use { newDocument ->
                for (paragraph in docx.paragraphs) {
                    val newParagraph = newDocument.createParagraph()
                    for (run in paragraph.runs) {
                        val fontName = run.fontName
                        val isByzantine = Engine.byzClassMap.containsKey(fontName)
                        var text = run.text()
                        val match = regex.containsMatchIn(text)
                        text = if (isByzantine || match) StringUtils.stripAccents(text) else continue
                        /*if (!isByzantine) {
                       text = StringUtils.stripAccents(text.replace(" ", ""));
//                                .replaceAll(nonPrintable, "");
                       if (text.length() == 0) continue;
                    }*/
                        val newRun = newParagraph.createRun()
                        newRun.setText(text)
                        newRun.fontFamily = run.fontFamily
                        println("text = " + run.text() + ", font = " + run.fontFamily)
                        val codePointsStr = text.codePoints()
                                .mapToObj { i: Int -> i.toString() }
                                .collect(Collectors.joining(", "))
                        println("codePoints = $codePointsStr")
                        //                    }
                    }
                }
                newDocument.write(FileOutputStream("dynamisPhase1.docx"))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Test
    fun findCodepoints() {
        for (paragraph in docx.paragraphs) {
            for (run in paragraph.runs) {
                val codePointsStr = run.text().codePoints()
                        .mapToObj { i: Int -> i.toString() }
                        .collect(Collectors.joining(", "))
                println("text = " + run.text() + ", font = " + run.fontFamily + ", codePoints = " + codePointsStr)
            }
        }
    }

    @Test
    fun stage1Transform() {
        val joiner = StringJoiner(", ")
        XWPFDocument().use { newDocument ->
            for (paragraph in docx.paragraphs) {
                val newParagraph = newDocument.createParagraph()
                for (run in paragraph.runs) {
                    val fontName = run.fontName
                    val isByzantine = Engine.byzClassMap.containsKey(fontName)
                    // if is Byzantine font then keep only chars in unicode private use area
                    val text = if(isByzantine) run.text().filter { it.toInt() in 0xE000..0xF8FF }
                    // else keep only [α-ωΑ-Ω], which is greek text
                    else run.text().filter { it.toInt() in 0x0391..0x03A9 || it.toInt() in 0x03B1..0x03C9 }
                    if (text.isEmpty()) continue
                    val newRun = newParagraph.createRun()
                    newRun.setText(text)
                    newRun.fontFamily = run.fontFamily
//                    newRun.fontSize = run.fontSize
                    println("text = " + newRun.text() + ", font = " + newRun.fontFamily)
                    val codePointsStr = text.codePoints()
                            .mapToObj { i: Int -> i.toString() }
                            .collect(Collectors.joining(", "))
                    println("codePoints = $codePointsStr")
                    joiner.run {
                        if (!isByzantine) {
                            add(text.toCharArray().joinToString(separator = " "))
                        } else {
                            val byzClass: ByzClass = Engine.byzClassMap[fontName]!!
                            val str = text.toCharArray().toList().stream()
                                    .map { byzClass.toString() + String.format("%03d", (it.toInt() - 0xF000)) }
                                    .collect(Collectors.joining(" "))
                            add(str)
                        }
                    }
                }
            }
            newDocument.write(FileOutputStream("stage1.docx"))
            println(joiner.toString())
        }
    }

    @Test
    fun newTry() {
        val name = docx.document.body.newCursor().execQuery("w:r").name
        println(name)
    }

    @Test
    fun clearNonAscii() {
        val strValue = "Ã string çöntäining nön äsçii çhäräçters"

//        System.out.println( strValue.replaceAll( "[^\\x00-\\x7F]", "" ) );
        println(StringUtils.stripAccents(strValue))
    }
}