package com.auth.starter.util;

import java.util.UUID;

/**
 * Created by Administrator on 2017/4/18.
 */
public class IDUtil {

    public static String getID() {
        String ID = UUID.randomUUID().toString();
        return ID.replaceAll("-", "");
    }
}
