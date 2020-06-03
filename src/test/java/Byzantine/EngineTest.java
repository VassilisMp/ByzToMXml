package Byzantine;

import org.audiveris.proxymusic.Step;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EngineTest {
    private Engine engine;

    @Test
    void runA() throws Exception {
        engine = new Engine("a.docx", Step.G).setTimeBeats(0);
        engine.run();
        System.out.println(engine.getDurationSum());
        assertAll(
                () -> assertEquals(BigDecimal.valueOf(4296), engine.getDurationSum()),
                () -> assertEquals(engine.getInitialStep(), engine.getLastNoteStep())
        );
    }

    @Test
    void runElpiza() throws Exception {
        engine = new Engine("elpiza.docx", Step.A).setTimeBeats(4);
        engine.run();
        System.out.println(engine.getDurationSum());
        assertAll(
                () -> assertEquals(BigDecimal.valueOf(768), engine.getDurationSum()),
                () -> assertEquals(Step.D, engine.getLastNoteStep())
        );
    }

    @Test
    void runDynamis() throws Exception {
        engine = new Engine("dynamis_k_ioannidh.docx", Step.A).setTimeBeats(0);
        engine.run();
        System.out.println(engine.getDurationSum());
        assertAll(
                () -> assertEquals(BigDecimal.valueOf(1488), engine.getDurationSum()),
                () -> assertEquals(Step.A, engine.getLastNoteStep())
        );
    }

    @Test
    void runMakarios() throws Exception {
        engine = new Engine("makarios_anir_syntoma_fokaeos.docx", Step.G).setTimeBeats(0);
        engine.run();
        System.out.println(engine.getDurationSum());
        assertAll(
                () -> assertEquals(BigDecimal.valueOf(3048), engine.getDurationSum()),
                () -> assertEquals(Step.G, engine.getLastNoteStep())
        );
    }
}