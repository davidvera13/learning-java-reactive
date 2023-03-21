package org.example.rsocket.commons.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.rsocket.Payload;
import io.rsocket.util.DefaultPayload;

public class Utils {
    public static Payload toPayload(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            byte[] bytes = objectMapper.writeValueAsBytes(obj);
            return DefaultPayload.create(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // we pass the class we expect as return in the parameters and we set the same class T as output result
    public static <T> T toObject(Payload payload, Class<T> type) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            byte[] bytes = payload.getData().array();
            return objectMapper.readValue(bytes, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
