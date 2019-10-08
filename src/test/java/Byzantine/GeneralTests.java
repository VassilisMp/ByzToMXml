package Byzantine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Type;
import java.util.*;

public class GeneralTests {
    @Test
    void GsonTest() {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        List<UnicodeChar> charList;
        try {
            FileInputStream fileIn = new FileInputStream("lis.obj");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            charList = (List<UnicodeChar>) in.readObject();
            in.close();
            fileIn.close();
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        charList.forEach(Char -> Char.classType = Char.getClass().getSimpleName());

        String json = gson.toJson(charList);
        System.out.println(json);

        UnicodeCharDeserializer deserializer = new UnicodeCharDeserializer();
        gson = new GsonBuilder()
                .registerTypeAdapter(UnicodeChar.class, deserializer)
                .create();

        List<UnicodeChar> outList = gson.fromJson(json, new TypeToken<List<UnicodeChar>>(){}.getType());
        System.out.println(outList);
        /*ByzChar byzChar = new ByzChar(120, "Arial", ByzClass.B);
        System.out.println(gson.toJson(byzChar));
        List<UnicodeChar> unicodeChars = new ArrayList<>(Arrays.asList(byzChar));
        QuantityChar quantityChar = new QuantityChar(225, "", ByzClass.B, Collections.singletonList(
                new Move(2, false, true)
        ));
        unicodeChars.add(quantityChar);
        TimeChar timeChar = new TimeChar(234, "", ByzClass.B, 0, 1, false);
        unicodeChars.add(timeChar);
        List<ByzChar> byzChars = new ArrayList<>(Arrays.asList(quantityChar, timeChar));
        MixedChar mixedChar = new MixedChar(235, "", ByzClass.B, byzChars);
        unicodeChars.add(mixedChar);
        FthoraChar fthoraChar = new FthoraChar(112, "", ByzClass.B, FthoraChar.Type.S_D, FthoraChar.ByzStep.KE, 9);
        unicodeChars.add(fthoraChar);
        String json = gson.toJson(unicodeChars);
        System.out.println(json);
        // Deserialization
        Type collectionType = new TypeToken<List<UnicodeChar>>(){}.getType();
        List<UnicodeChar> chars = gson.fromJson(json, collectionType);
        System.out.println(chars.get(2).getClass());*/
    }
    // https://github.com/google/gson/blob/master/UserGuide.md
    // https://www.baeldung.com/gson-list
    // http://www.javacreed.com/gson-annotations-example/

    @Test
    void GsonTest2() {
        System.out.println(ByzChar.class.getSimpleName());
    }
}
