package com.auth.starter.model;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */
public class SecurityUser {

    /**
     * 登录名
     */
    private String loginName;
    /**
     * 用户信息
     */
    private String userInfo;
    /**
     * 用户权限集合
     */
    private List<String> permissionList;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public List<String> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<String> permissionList) {
        this.permissionList = permissionList;
    }
}
