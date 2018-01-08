package co.degraph.client.http.serializers;

import com.google.gson.GsonBuilder;

public class JsonSerializer implements EntitySerializer {

    public String serialize(Object object) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(object);
    }
}
