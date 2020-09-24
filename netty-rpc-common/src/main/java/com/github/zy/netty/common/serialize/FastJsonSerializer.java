package com.github.zy.netty.common.serialize;

import com.alibaba.fastjson.JSON;

/**
 * @version 1.0 created by zy on 2020/4/23 14:34
 */
public enum FastJsonSerializer implements Serializer {
    INSTANCE;

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
