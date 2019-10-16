package Byzantine;

import com.google.common.base.Charsets;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ByzCharDeSerializeTest {

    @Test
    void fromJson() throws IOException {
        String json = FileUtils.readFileToString(new File(Engine.JSON_CHARS_FILE), Charsets.UTF_8);

        ByzCharDeSerialize inOut= new ByzCharDeSerialize();
        List<ByzChar> charList = inOut.fromJson(json);
    }

    @Test
    void toJson() throws IOException {
        String json = FileUtils.readFileToString(new File(Engine.JSON_CHARS_FILE), Charsets.UTF_8);

        ByzCharDeSerialize inOut= new ByzCharDeSerialize();
        List<ByzChar> charList = inOut.fromJson(json);
    }
}