package parser

import org.antlr.v4.runtime.InputMismatchException
import org.junit.jupiter.api.Test

// TODO fill assertions
internal class EngineTest {

    @Test
    fun makarios_anirTest() {
        val engine = Engine("makarios_anir_syntoma_fokaeos_simple.docx")
        try {
            engine.run()
        } catch (e: InputMismatchException) {
            println("caught")
        }
    }

    @Test
    fun doxologia_hxos_a_petrou_arghTest() {
        val engine = Engine("doxologia_hxos_a_petrou_argh.docx")
        try {
            engine.run()
        } catch (e: InputMismatchException) {
            println("caught")
        }
    }

    @Test
    fun dynamis_k_ioannidh_simpleTest() {
        val engine = Engine("dynamis_k_ioannidh_simple.docx")
        try {
            engine.run()
        } catch (e: InputMismatchException) {
            println("caught")
        }
    }
}