package Byzantine;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.util.List;

class ByzCharDeSerialize {
    private Gson gson;

    @SuppressWarnings("unchecked")
    public ByzCharDeSerialize() {
        JsonSerializer<ByzChar> serializer =
                (byzChar, type, jsonSerializationContext) -> {
                    JsonObject jsonObject = gson.toJsonTree(byzChar)
                            .getAsJsonObject();
                    jsonObject.addProperty("class", byzChar.getClass().getName());
                    return jsonObject;
                };
        JsonDeserializer<ByzChar> deserializer =
                (jsonElement, type, jsonDeserializationContext) -> {
                    JsonObject ByzCharObject = jsonElement.getAsJsonObject();
                    JsonElement CharTypeElement = ByzCharObject.get("class");

                    Class<? extends ByzChar> CharType = null;
                    try {
                        CharType = (Class<? extends ByzChar>) Class.forName(CharTypeElement.getAsString());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        System.exit(-1);
                    }
                    return gson.fromJson(ByzCharObject, CharType);
                };

        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .registerTypeAdapter(ByzChar.class, serializer)
                .registerTypeAdapter(ByzChar.class, deserializer)
                .create();
    }

    List<ByzChar> fromJson(String json) {
        return gson.fromJson(json, new TypeToken<List<ByzChar>>(){}.getType());
    }
    String toJson(List<ByzChar> lis) {
        return gson.toJson(lis, new TypeToken<List<ByzChar>>(){}.getType());
    }
}
