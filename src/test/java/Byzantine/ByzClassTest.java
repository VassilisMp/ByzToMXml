package Byzantine;

import Byzantine.Annotations.NotSupported;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ByzClassTest {

    /*@Test
    void PAnnotationTest() {
        ByzClass byzClass = ByzClass.P;
        boolean annotationPresent = false;
        try {
            annotationPresent = ByzClass.class.getField(byzClass.toString()).isAnnotationPresent(NotSupported.class);
            System.out.println(byzClass + " annotation isPresent: " + annotationPresent);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        boolean finalAnnotationPresent = annotationPresent;
        Executable executable = () -> {
            if (finalAnnotationPresent) {
                throw new NotSupportedException("ByzClass." + byzClass + " is not supported");
            }
        };

        assertThrows(NotSupportedException.class, executable);
    }*/

    @Test
    void BAnnotationTest() {
        ByzClass byzClass = ByzClass.B;
        try {
            boolean annotationPresent = ByzClass.class.getField(byzClass.toString()).isAnnotationPresent(NotSupported.class);
            System.out.println(byzClass + "annotation isPresent: " + annotationPresent);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}