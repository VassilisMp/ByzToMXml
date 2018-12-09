package Byzantine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class ByzClassTest {

    @Test
    void PAnnotationTest() throws NotSupportedException {
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
    }

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