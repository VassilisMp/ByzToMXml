package Byzantine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ByzCharTest {

    @Test
    void getCodePointClass() {
        ByzChar test = new ByzChar(80, "", ByzClass.B);
        System.out.println(test.getClass().getSimpleName());
        assertEquals("B080", test.getCodePointClass());
    }

    @Test
    void test() {
        QuantityChar quantityChar = new QuantityChar(1, "", ByzClass.B);
        System.out.print(quantityChar);
    }
}