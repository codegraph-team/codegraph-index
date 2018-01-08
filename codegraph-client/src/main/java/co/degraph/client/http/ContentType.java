package co.degraph.client.http;

import co.degraph.client.http.serializers.EntitySerializer;
import co.degraph.client.http.serializers.JsonSerializer;

public enum ContentType {

    JSON(new JsonSerializer(), org.apache.http.entity.ContentType.APPLICATION_JSON);

    private EntitySerializer serializer;
    private org.apache.http.entity.ContentType type;

    ContentType(EntitySerializer serializer, org.apache.http.entity.ContentType type) {
        this.serializer = serializer;
        this.type = type;
    }

    public EntitySerializer getSerializer() {
        return serializer;
    }

    public org.apache.http.entity.ContentType getType() {
        return type;
    }
}
