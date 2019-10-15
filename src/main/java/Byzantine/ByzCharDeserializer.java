package Byzantine;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ByzCharDeserializer implements JsonDeserializer<ByzChar> {
    private Gson gson;
    private Map<String, Class<? extends ByzChar>> CharTypeRegistry;

    ByzCharDeserializer() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(ByzChar.class, this)
                .create();
        this.CharTypeRegistry = new HashMap<>();
        CharTypeRegistry.put("FthoraChar", FthoraChar.class);
        CharTypeRegistry.put("MixedChar", MixedChar.class);
        CharTypeRegistry.put("QuantityChar", QuantityChar.class);
        CharTypeRegistry.put("TimeChar", TimeChar.class);
    }

    public ByzChar deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        JsonObject ByzCharObject = json.getAsJsonObject();
        JsonElement CharTypeElement = ByzCharObject.get("classType");

        Class<? extends ByzChar> CharType = CharTypeRegistry.get(CharTypeElement.getAsString());
        if (CharType == MixedChar.class) {
            ByzClass byzClass = ByzClass.valueOf(ByzCharObject.get("byzClass").getAsString());
            int codePoint = ByzCharObject.get("codePoint").getAsInt();
            List<ByzChar> chars = gson.fromJson(ByzCharObject.get("chars"), new TypeToken<List<ByzChar>>(){}.getType());

            return new MixedChar(codePoint, byzClass, chars);
        }
        return gson.fromJson(ByzCharObject, CharType);
    }

    List<ByzChar> fromJson(String json) {
        return gson.fromJson(json, new TypeToken<List<ByzChar>>(){}.getType());
    }
}
