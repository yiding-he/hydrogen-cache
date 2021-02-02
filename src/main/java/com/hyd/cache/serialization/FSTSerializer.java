package com.hyd.cache.serialization;

import org.nustaq.serialization.FSTConfiguration;

public class FSTSerializer implements Serializer {

    private static final FSTConfiguration FST;

    static {
        FST = FSTConfiguration.createDefaultConfiguration();
        FST.setForceSerializable(true);
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
    public byte[] serialize(Object object) {
        byte[] content = FST.asByteArray(object);
        return prependBytes(content, (byte) 0);
    }

    @Override
    public Object deserialize(byte[] bytes) {
        try {
            byte[] content = removeTag(bytes);
            return FST.asObject(content);
        } catch (Exception e) {
            if (disposeOnFail) {
                return null;
            } else {
                throw e;
            }
        }
    }
}
