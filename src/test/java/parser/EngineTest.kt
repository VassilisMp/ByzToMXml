package parser

import org.antlr.v4.runtime.InputMismatchException
import org.apache.commons.io.FilenameUtils
import org.audiveris.proxymusic.ScorePartwise
import org.audiveris.proxymusic.util.Marshalling
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.io.File
import java.io.FileOutputStream
import javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT

// TODO fill assertions
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class EngineTest {

    @BeforeAll
    fun init() {
        File(savePath).mkdir()
    }

    @ParameterizedTest
    @ValueSource(strings = ["dynamis_k_ioannidh_simple.docx", "doxologia_hxos_a_petrou_argh.docx"
        , "makarios_anir_syntoma_fokaeos_simple.docx", "oimoi.docx"])
    fun testSamples(fileName: String) {
        val engine = Engine("$filePath${File.separator}$fileName")
        try {
            val scorePartwise = engine.run()
            FileOutputStream("$savePath${File.separator}${fileName.removeExtension()}.xml").use {
                val marshaller = Marshalling.getContext(ScorePartwise::class.java).createMarshaller();
                marshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(scorePartwise, it)
            }
        } catch (e: InputMismatchException) {
            println("caught")
        }
    }

    companion object {
        const val savePath = "sample_tests_output"
        const val filePath = "sample_docs"
        private fun String.removeExtension() = FilenameUtils.removeExtension(this)
    }
}