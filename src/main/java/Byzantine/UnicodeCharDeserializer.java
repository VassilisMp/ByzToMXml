package Byzantine;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class UnicodeCharDeserializer implements JsonDeserializer<UnicodeChar> {
    private Gson gson;
    private Map<String, Class<? extends UnicodeChar>> CharTypeRegistry;

    public UnicodeCharDeserializer() {
        this.gson = new Gson();
        this.CharTypeRegistry = new HashMap<>();
        CharTypeRegistry.put("ByzChar", ByzChar.class);
        CharTypeRegistry.put("FthoraChar", FthoraChar.class);
        CharTypeRegistry.put("MixedChar", MixedChar.class);
        CharTypeRegistry.put("QuantityChar", QuantityChar.class);
        CharTypeRegistry.put("TimeChar", TimeChar.class);
    }

    public UnicodeChar deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        JsonObject UnicodeCharObject = json.getAsJsonObject();
        JsonElement CharTypeElement = UnicodeCharObject.get("classType");

        Class<? extends UnicodeChar> CharType = CharTypeRegistry.get(CharTypeElement.getAsString());
        return gson.fromJson(UnicodeCharObject, CharType);
    }
}
