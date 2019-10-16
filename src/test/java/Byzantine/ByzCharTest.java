package Byzantine;

import org.audiveris.proxymusic.NoteType;
import org.audiveris.proxymusic.StartStop;
import org.audiveris.proxymusic.StartStopContinue;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ByzCharTest {

    @Test
    void testClone() throws CloneNotSupportedException {
        MixedChar mixedChar = new MixedChar(110, ByzClass.B, Arrays.asList(
                new TimeChar(80, ByzClass.B, 0, 1, false),
                new QuantityChar(1, ByzClass.B, new Move(1, false, true))
        ));
        MixedChar clone = (MixedChar) mixedChar.clone();
        System.out.println(clone);
    }

    @Test
    void getCodePointClass() throws CloneNotSupportedException {
        ByzChar test = new TimeChar(80, ByzClass.B, 0, 1, false);
        ByzChar test2 = test.clone();
        System.out.println(test.getClass().getSimpleName());
        assertEquals("B080", test.getCodePointClass());
    }

    @Test
    void test() {
        QuantityChar quantityChar = new QuantityChar(1, ByzClass.B);
        System.out.println(quantityChar);
        NoteType noteType = new NoteType();
        System.out.println(noteType.getValue());
    }
}