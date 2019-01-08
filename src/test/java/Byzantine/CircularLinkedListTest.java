package Byzantine;

import org.audiveris.proxymusic.Step;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

class CircularLinkedListTest {
    static List<PitchEntry> C = Arrays.asList(
            new PitchEntry(9, Step.C),
            new PitchEntry(9, Step.D),
            new PitchEntry(4, Step.E),
            new PitchEntry(9, Step.F),
            new PitchEntry(9, Step.G),
            new PitchEntry(9, Step.A),
            new PitchEntry(4, Step.B)
    );
    static List<PitchEntry> NhDiatonic = Arrays.asList(
            new PitchEntry(9, Step.C),
            new PitchEntry(8, Step.D),
            new PitchEntry(5, Step.E),
            new PitchEntry(9, Step.F),
            new PitchEntry(9, Step.G),
            new PitchEntry(8, Step.A),
            new PitchEntry(5, Step.B)
    );
    static List<PitchEntry> PaHardChromatic = Arrays.asList(
            new PitchEntry(5, Step.D),
            new PitchEntry(12, Step.E),
            new PitchEntry(5, Step.F),
            new PitchEntry(9, Step.G),
            new PitchEntry(5, Step.A),
            new PitchEntry(12, Step.B),
            new PitchEntry(5, Step.C)
    );
    static List<PitchEntry> current;
    @Test
    void Test() throws Exception {
        List<PitchEntry> Cclone = Cloner.deepClone(C);
        List<PitchEntry> pitchEntries = PitchEntry.ListByStep(C, Step.D);
        PitchEntry.FthoraApply(C, PaHardChromatic);
        System.out.println(pitchEntries);
        System.out.println(C);

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