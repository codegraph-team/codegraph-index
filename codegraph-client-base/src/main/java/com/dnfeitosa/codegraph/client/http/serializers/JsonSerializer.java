package com.dnfeitosa.codegraph.client.http.serializers;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;

import java.lang.reflect.Type;
import java.util.List;

public class JsonSerializer implements EntitySerializer {

    public String serialize(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(List.class, getListAdapter());
        return gsonBuilder.setPrettyPrinting().create().toJson(object);
    }

    private com.google.gson.JsonSerializer<List<?>> getListAdapter() {
        return new com.google.gson.JsonSerializer<List<?>>() {
            @Override
            public JsonElement serialize(List<?> src, Type typeOfSrc, JsonSerializationContext context) {
                if (src == null || src.isEmpty()) {
                    return null;
                }

                JsonArray array = new JsonArray();
                for (Object item : src) {
                    array.add(context.serialize(item));
                }
                return array;
            }
        };
    }
}
