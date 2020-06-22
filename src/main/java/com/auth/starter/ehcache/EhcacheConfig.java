package com.auth.starter.ehcache;

import java.net.URL;

/**
 * Created by Administrator on 2017/8/22.
 */
public class EhcacheConfig {

    /**
     * 文件的地址
     */
    private String path = "/acquiescence-ehcache.xml";

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public URL getUrl(String fileUrl) {
        return getClass().getResource(fileUrl);
    }
}
