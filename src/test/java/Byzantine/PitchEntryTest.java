package Byzantine;

import org.audiveris.proxymusic.Step;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PitchEntryTest {

    @Test
    void testClone() {
        PitchEntry pitchEntry = new PitchEntry(10, Step.A, 0, FthoraChar.ByzStep.KE);
        PitchEntry clone = new PitchEntry(pitchEntry);
        System.out.println("pitchEntry before" + pitchEntry);
        System.out.println("clone before" + clone);
        clone.byzStep = FthoraChar.ByzStep.DI;
        System.out.println("pitchEntry after" + pitchEntry);
        System.out.println("clone after" + clone);
    }
}