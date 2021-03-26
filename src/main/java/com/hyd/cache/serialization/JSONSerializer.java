package com.hyd.cache.serialization;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;

import static java.nio.charset.StandardCharsets.UTF_8;

public class JSONSerializer implements Serializer {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new JsonMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        PolymorphicTypeValidator validator = objectMapper.getPolymorphicTypeValidator();
        objectMapper.activateDefaultTyping(validator, ObjectMapper.DefaultTyping.NON_FINAL);
    }

    private boolean disposeOnFail;

    @Override
    public boolean isDisposeOnFail() {
        return disposeOnFail;
    }

    @Override
    public void setDisposeOnFail(boolean disposeOnFail) {
        this.disposeOnFail = disposeOnFail;
    }

    @Override
    public byte[] serialize(Object object) throws Exception {
        String json = objectMapper.writeValueAsString(object);
        return prependBytes(
                json.getBytes(UTF_8),
                PredefinedSerializeMethod.JSON.getTag()
        );
    }

    @Override
    public Object deserialize(byte[] bytes) throws Exception {
        try {
            byte[] content = removeTag(bytes);
            return objectMapper.readValue(content, Object.class);
        } catch (Exception e) {
            if (disposeOnFail) {
                return null;
            } else {
                throw e;
            }
        }
    }

}
