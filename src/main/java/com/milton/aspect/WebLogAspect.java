package com.milton.aspect;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author milton.zhang
 * @description 实现web访问log处理
 * @date 2017-05-31 13:39
 */
@Aspect
@Component
public class WebLogAspect {
    Logger logger = LogManager.getLogger(this.getClass());

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    //定义切入点 该包下的所有函数
    @Pointcut("execution(public * com.milton.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("AOP URL : " + request.getRequestURL().toString());
        logger.info("AOP HTTP_METHOD : " + request.getMethod());
        logger.info("AOP IP : " + request.getRemoteAddr());
        logger.info("AOP CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("AOP ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("AOP RESPONSE : " + JSON.toJSONString(ret));
        logger.info("AOP SPEND TIME : {}ms", (System.currentTimeMillis() - startTime.get()));
    }
}
