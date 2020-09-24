package com.github.zy.netty.common.serialize;


/**
 * @version 1.0 created by zy on 2020/4/23 14:32
 */
public interface Serializer {
    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
