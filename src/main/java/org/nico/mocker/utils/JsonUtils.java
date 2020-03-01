package org.nico.mocker.utils;

import com.alibaba.fastjson.JSON;

public class JsonUtils {

    public static String toString(Object o) {
        return JSON.toJSONString(o);
    }
    
    public static <T> T toObject(String str, Class<T> c) {
        return JSON.parseObject(str, c);
    }
}
