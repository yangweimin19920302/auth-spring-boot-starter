package com.auth.starter;

import com.auth.starter.exception.ConnectErrorException;
import com.auth.starter.exception.NoPermissionException;
import com.auth.starter.exception.OutOfDateException;
import com.auth.starter.util.AuthenticationInformation;
import com.auth.starter.util.Authorization;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 切面控制器
 */
@Aspect
@Component
public class AuthorizationAttributeSourceAdvisor {

    /**
     * aop的拦截方式
     *
     * execution：用于匹配方法执行的连接点；
     * within：用于匹配指定类型内的方法执行；
     * this：用于匹配当前AOP代理对象类型的执行方法；注意是AOP代理对象的类型匹配，这样就可能包括引入接口也类型匹配；
     * target：用于匹配当前目标对象类型的执行方法；注意是目标对象的类型匹配，这样就不包括引入接口也类型匹配；
     * args：用于匹配当前执行的方法传入的参数为指定类型的执行方法；
     * @within：用于匹配所以持有指定注解类的类下所有方法；
     * @target：用于匹配当前目标对象类型的执行方法，其中目标对象持有指定的注解；
     * @args：用于匹配当前执行的方法传入的参数持有指定注解的执行；
     * @annotation：用于匹配当前执行方法持有指定注解的方法；
     *
     * bean：Spring AOP扩展的，AspectJ没有对于指示符，用于匹配特定名称的Bean对象的执行方法；
     * reference pointcut：表示引用其他命名切入点，只有@ApectJ风格支持，Schema风格不支持。
     * @param joinPoint
     * @throws OutOfDateException
     * @throws NoPermissionException
     * @throws ConnectErrorException
     */
    @Before("@within(com.auth.starter.annotation.Auth) || @annotation(com.auth.starter.annotation.Auth) || @annotation(com.auth.starter.annotation.RequiresPermissions)")
    public void auth(JoinPoint joinPoint) throws OutOfDateException, NoPermissionException, ConnectErrorException {
        if (!AuthenticationInformation.notAuth(joinPoint)) {
            Authorization.auth(joinPoint);
        }
    }
}
