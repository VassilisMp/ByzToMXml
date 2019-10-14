package Byzantine;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
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
        if (CharType == MixedChar.class) {
//            System.out.println("error");

//            System.out.println(UnicodeCharObject.get("chars"));
            MixedChar mixedChar = (MixedChar)gson.fromJson(UnicodeCharObject, CharType);
            Gson gsonM = new GsonBuilder()
                    .registerTypeAdapter(UnicodeChar.class, this)
                    .create();
            List<UnicodeChar> outList = gsonM.fromJson(UnicodeCharObject.get("chars"), new TypeToken<List<UnicodeChar>>(){}.getType());
            mixedChar.setChars(outList.toArray(new ByzChar[0]));
//            System.out.println(outList);
            return mixedChar;
            //new MixedChar(codePoint, ByzClass.B, )
        }
        return gson.fromJson(UnicodeCharObject, CharType);
    }
}
