// Copyright (c) 2003-2013, LogMeIn, Inc. All rights reserved.
// This is part of Xively4J library, it is under the BSD 3-Clause license.
package com.auth.starter.util;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.List;

/**
 * Helper class for parsing to and from json for API calls
 * json转换工具
 *
 * @author s0pau
 */
public class JsonUtil {

    /**
     * 将对象转换为json
     */
    public static String toJson(Object model) {
        String json = JSON.toJSONString(model);
        return json;
    }

    /**
     * 将json格式转换为数组对象
     *
     * @param body
     * @param clazz
     * @throws IOException
     */
    public static <T> List<T> toConnectedObjects(String body, Class<T> clazz) {
        List<T> objs = JSON.parseArray(body, clazz);
        return objs;
    }

    /**
     * 将json转换为对象
     *
     * @param body
     * @param clazz
     * @return
     */
    public static <T> T toConnectedObject(String body, Class<T> clazz) {
        if (body == null || body.trim().length() == 0) {
            return null;
        }
        T t = JSON.parseObject(body, clazz);
        return t;
    }
}
