package com.auth.starter;

import com.auth.starter.annotation.LoginType;
import com.auth.starter.exception.ConnectErrorException;
import com.auth.starter.exception.LoginNameNULLException;
import com.auth.starter.exception.LoginNamePasswordErrorException;
import com.auth.starter.exception.OutOfDateException;
import com.auth.starter.model.SecurityUser;
import com.auth.starter.util.*;

/**
 * 负责用户登录，身份、权限的认证
 */
public class Subject {

    private Subject() {}

    /**
     * 登录认证
     * @param securityUser
     * @return
     * @throws Exception
     */
    public static String login(SecurityUser securityUser) throws LoginNamePasswordErrorException, LoginNameNULLException, ConnectErrorException {
        if (securityUser == null) {
            throw new LoginNamePasswordErrorException("用户名或密码错误");
        }
        String loginName = securityUser.getLoginName();
        if (loginName == null || loginName.trim().length() == 0) {
            throw new LoginNameNULLException("登录名不能为空");
        }
        String token = IDUtil.getID();
        /**
         * 判断用户能否同时在多个客户端登录，如果不能，则踢掉前一个登录的客户端
         **/
        if (ConfigurationManagement.getLoginType().equals(LoginType.ONE)) {
            String old_token = StorageMediumEntrance.get(loginName);
            if (old_token != null) {
                StorageMediumEntrance.del(old_token);
            }
            StorageMediumEntrance.add(loginName, token);
        }
        /**存储登录用户信息**/
        StorageMediumEntrance.add(token, JsonUtil.toJson(securityUser));
        return token;
    }

    /**
     * 退出登录
     * @return
     * @throws Exception
     */
    public static void logout() throws ConnectErrorException {
        String token;
        try {
            token = AuthenticationInformation.getToken();
        } catch (OutOfDateException e) {
            return;
        }
        String json = StorageMediumEntrance.get(token);
        if (json != null) {
            SecurityUser securityUser = JsonUtil.toConnectedObject(json, SecurityUser.class);
            StorageMediumEntrance.del(securityUser.getLoginName());
            StorageMediumEntrance.del(token);
        }
    }

    /**
     * 获取登录用户
     * @return
     * @throws ConnectErrorException
     */
    public static SecurityUser getUser() throws ConnectErrorException, OutOfDateException {
        String token = AuthenticationInformation.getToken();
        String json = StorageMediumEntrance.get(token);
        if (json == null) {
            throw new OutOfDateException("token已过期");
        }
        return JsonUtil.toConnectedObject(json, SecurityUser.class);
    }
}
