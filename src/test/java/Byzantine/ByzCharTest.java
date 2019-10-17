package Byzantine;

import org.audiveris.proxymusic.NoteType;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ByzCharTest {

    @Test
    void testClone() throws CloneNotSupportedException {
        FthoraChar fthoraChar = new FthoraChar(121, ByzClass.B, Type.S_D, ByzStep.DI, 0);
        TimeChar timeChar = new TimeChar(80, ByzClass.B, 0, 1, false);
        QuantityChar quantityChar = new QuantityChar(1, ByzClass.B, new Move(1, false, true));
        MixedChar mixedChar = new MixedChar(110, ByzClass.B, Arrays.asList(
                timeChar,
                quantityChar)
        );
        MixedChar mClone = (MixedChar) mixedChar.clone();
        TimeChar tClone = (TimeChar) timeChar.clone();
        System.out.println(tClone);
        QuantityChar qClone = (QuantityChar) quantityChar.clone();
        FthoraChar fClone = (FthoraChar) fthoraChar.clone();
        assertAll(
                () -> assertNotSame(fthoraChar, fClone),
                () -> assertNotSame(timeChar, tClone),
                () -> assertNotSame(quantityChar, qClone),
                () -> assertNotSame(mixedChar, mClone),
                () -> assertNotSame(mixedChar.getChars(), mClone.getChars())
        );
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