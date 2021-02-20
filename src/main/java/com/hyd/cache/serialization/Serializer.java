package com.hyd.cache.serialization;

/**
 * 将对象序列化/反序列化的类
 */
public interface Serializer {

    /**
     * 序列化对象，结果的第一个字节为标志位
     *
     * @param object 被序列化的对象
     *
     * @return 序列化结果
     */
    byte[] serialize(Object object) throws Exception;

    /**
     * 反序列化，bytes 的第一个字节为标志位，需要从内容中排除
     *
     * @param bytes 序列化的内容，第一个字节为标志位
     *
     * @return 反序列化结果
     */
    Object deserialize(byte[] bytes) throws Exception;

    void setDisposeOnFail(boolean disposeOnFail);

    boolean isDisposeOnFail();

    default byte[] prependBytes(byte[] bytes, byte tag) {
        byte[] result = new byte[bytes.length + 1];
        result[0] = tag;
        System.arraycopy(bytes, 0, result, 1, bytes.length);
        return result;
    }

    default byte[] removeTag(byte[] bytes) {
        byte[] result = new byte[bytes.length - 1];
        System.arraycopy(bytes, 1, result, 0, result.length);
        return result;
    }
}
