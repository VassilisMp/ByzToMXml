package parser

import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.io.FileInputStream

internal class ParserTest {

    @Test
    fun parseNotInPrivateArea() {
        val parser = Parser(XWPFDocument(FileInputStream("arktikesMartyries.docx")), false)
        val parser2 = Parser(XWPFDocument(FileInputStream("arktikesMartyries.docx")), true)
        assertAll(
                { assertTrue(parser.byzCharsStr.isNotEmpty()) },
                { assertTrue(parser2.byzCharsStr.isEmpty()) }
        )
    }
}