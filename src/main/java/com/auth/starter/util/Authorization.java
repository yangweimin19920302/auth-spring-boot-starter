package com.auth.starter.util;

import com.auth.starter.Subject;
import com.auth.starter.annotation.CollectionType;
import com.auth.starter.exception.ConnectErrorException;
import com.auth.starter.exception.NoPermissionException;
import com.auth.starter.exception.OutOfDateException;
import com.auth.starter.model.SecurityUser;
import org.aspectj.lang.JoinPoint;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * token和权限认证
 */
public class Authorization {
    /**
     * token和权限认证
     * @param joinPoint
     * @throws OutOfDateException
     * @throws NoPermissionException
     * @throws ConnectErrorException
     */
    public static void auth(JoinPoint joinPoint) throws OutOfDateException, NoPermissionException, ConnectErrorException {
        /**获取权限控制类型**/
        CollectionType collectionType = AuthenticationInformation.getCollectionType(joinPoint);
        /**获取当前方法所需的权限**/
        String values [] = AuthenticationInformation.getValue(joinPoint);
        /**获取当前用户信息**/
        SecurityUser securityUser = Subject.getUser();
        if (values == null || values.length == 0) {
            return;
        }
        /**获取当前用户所具有的权限**/
        List<String> permissionList = securityUser.getPermissionList();
        if (permissionList == null || permissionList.size() == 0) {
            throw new NoPermissionException("无操作权限");
        }
        /**获取两个list的交集**/
        List<String> intersection = permissionList.stream().filter(item -> Arrays.asList(values).contains(item)).collect(toList());
        if (collectionType.equals(CollectionType.AND) && intersection.size() != values.length) {
            throw new NoPermissionException("无操作权限");
        }
        if (collectionType.equals(CollectionType.OR) && intersection.size() == 0) {
            throw new NoPermissionException("无操作权限");
        }
    }
}
