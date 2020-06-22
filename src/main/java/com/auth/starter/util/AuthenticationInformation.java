package com.auth.starter.util;

import com.auth.starter.annotation.CollectionType;
import com.auth.starter.annotation.NotAuth;
import com.auth.starter.annotation.RequiresPermissions;
import com.auth.starter.exception.OutOfDateException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;

/**
 * Created by Administrator on 2017/8/23.
 */
public class AuthenticationInformation {

    /**
     * 获取注解类的权限值
     * @param joinPoint
     * @return
     * @throws Exception
     */
    public static String[] getValue(JoinPoint joinPoint) {
        String value[] = null;
        RequiresPermissions requiresPermissions = getRequiresPermissions(joinPoint);
        if (requiresPermissions != null) {
            value = requiresPermissions.value();
        }
        return value;
    }

    /**
     * 获取token值
     * @return
     * @throws Exception
     */
    public static String getToken() throws OutOfDateException {
        HttpServletRequest request = HttpUtil.getHttpServletRequest();
        String token = request.getHeader(Constants.TOKEN);
        if (token != null && !token.isEmpty()) {
            return token;
        }
        Enumeration enumeration = request.getParameterNames();
        while(enumeration.hasMoreElements()){
            String key = (String)enumeration.nextElement();
            if (Constants.TOKEN.equals(key)) {
                token = request.getParameter(key);
            }
        }
        if (token == null || token.isEmpty()) {
            throw new OutOfDateException("token已过期");
        }
        return token;
    }

    /**
     * 获取注解类的type值
     * @param joinPoint
     * @return
     * @throws Exception
     */
    public static CollectionType getCollectionType(JoinPoint joinPoint) {
        RequiresPermissions requiresPermissions = getRequiresPermissions(joinPoint);
        if (requiresPermissions == null) {
            return null;
        }
        return requiresPermissions.type();
    }

    /**
     * 获取注入权限对象
     * @param joinPoint
     * @return
     * @throws Exception
     */
    private static RequiresPermissions getRequiresPermissions(JoinPoint joinPoint) {
        RequiresPermissions requiresPermissions = null;
        Method method = getMethod(joinPoint);
        if (method != null) {
            requiresPermissions = method.getAnnotation(RequiresPermissions.class);
        }
        return requiresPermissions;
    }

    /**
     * 判断是否需要认证
     * @param joinPoint
     * @return
     */
    public static boolean notAuth(JoinPoint joinPoint) {
        NotAuth notAuth = null;
        Method method = getMethod(joinPoint);
        if (method != null) {
            notAuth = method.getAnnotation(NotAuth.class);
        }
        return notAuth != null ? true : false;
    }

    /**
     * 获取拦截方法
     * @param joinPoint
     * @return
     */
    private static Method getMethod(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Method method = null;
        try {
            Class targetClass = Class.forName(className);
            method = targetClass.getMethod(methodName, methodSignature.getParameterTypes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return method;
    }
}
