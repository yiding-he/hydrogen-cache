package com.hyd.cache.serialization;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hyd.cache.Element;

import static java.nio.charset.StandardCharsets.UTF_8;

public class JSONSerializer implements Serializer {

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
    public byte[] serialize(Object object) {
        String json = JSON.toJSONString(object, SerializerFeature.WriteClassName);
        return prependBytes(
                json.getBytes(UTF_8),
                PredefinedSerializeMethod.JSON.getTag()
        );
    }

    @Override
    public Object deserialize(byte[] bytes) {
        try {
            byte[] content = removeTag(bytes);
            return JSON.parseObject(new String(content, UTF_8));
        } catch (Exception e) {
            if (disposeOnFail) {
                return null;
            } else {
                throw e;
            }
        }
    }

    @Override
    public <T> Element<T> deserialize(byte[] bytes, Class<T> type) {
        byte[] content = removeTag(bytes);
        return JSON.parseObject(new String(content, UTF_8),
                new TypeReference<Element<T>>(type) {
                });
    }
}
