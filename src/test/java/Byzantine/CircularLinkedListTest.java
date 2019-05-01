package Byzantine;

import org.audiveris.proxymusic.Step;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static Byzantine.FthoraChar.HARD_DIATONIC;
import static Byzantine.FthoraChar.HARD_CHROMATIC;

class CircularLinkedListTest {
    @Test
    void Test() throws Exception {
        List<PitchEntry> Cclone = Cloner.deepClone(HARD_DIATONIC);
        List<PitchEntry> pitchEntries = PitchEntry.ListByStep(HARD_DIATONIC, Step.D);
        PitchEntry.FthoraApply(HARD_DIATONIC, HARD_CHROMATIC);
        System.out.println(pitchEntries);
        System.out.println(HARD_DIATONIC);

    }

    @Test
    void Test3() throws IOException {
        try (BufferedReader r = Files.newBufferedReader(Paths.get("LinkedList.java"), StandardCharsets.UTF_8)) {
            r.lines().filter(s -> !s.contains("*")).forEach(System.out::println);
        }
        //String s = FileUtils.readFileToString(new File("LinkedList.java"), StandardCharsets.UTF_8);
        //System.out.println(s.replaceAll("/\\*(?:.|[\\n\\r])*?\\*/",""));
    }

}