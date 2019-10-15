package Byzantine;

import com.google.common.base.Charsets;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ByzCharDeserializerTest {

    @Test
    void fromJson() throws IOException {
        String json = FileUtils.readFileToString(new File(Engine.JSON_CHARS_FILE), Charsets.UTF_8);
        //System.out.println(json);
        ByzCharDeserializer deserializer = new ByzCharDeserializer();

        List<ByzChar> charList = deserializer.fromJson(json);
        System.out.println(charList);
        System.out.println(charList.size());
    }
}