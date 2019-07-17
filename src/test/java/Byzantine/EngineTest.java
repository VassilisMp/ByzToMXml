package Byzantine;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class EngineTest {

    @Test
    void run() throws IOException {
        Engine engine = new Engine("dynamis_k_ioannidh.docx").setTimeBeats(0);
        engine.run();
    }
}