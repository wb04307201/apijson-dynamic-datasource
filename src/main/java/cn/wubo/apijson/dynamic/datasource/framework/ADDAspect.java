package cn.wubo.apijson.dynamic.datasource.framework;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * ADDAspect
 * 用于事务管理一致性的提交和回滚
 *
 * @author 吴博
 * @version 1.0
 * @date 2022.09.05
 */
@Aspect
@Component
public class ADDAspect {

    private static final Logger logger = LoggerFactory.getLogger(ADDAspect.class);

    @Pointcut("execution(* cn.wubo.apijson.dynamic.datasource..*.*(..)) ")
    private void logPoinCut() {
        // Just a PoinCut
    }

    @Around("logPoinCut()")
    public Object process(ProceedingJoinPoint point) throws Throwable {
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) point.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();
        ADDTransactional addTransactional = getZCTAnnotation(method);
        RestController restController = getRCAnnotation(method);
        Service service = getSAnnotation(method);
        if (addTransactional != null && (restController != null || service != null)) {
            logger.info("use custom db pool control connect");
            String id = UUID.randomUUID().toString();
            MDC.put(ADDConstant.APIJSON_DYNAMIC_DATASOURCE_TRACE_ID, id);
            try {
                Object returnValue = point.proceed();
                logger.info("use custom db pool onCommit");
                ADDConnectionPool.onCommit(id);
                return returnValue;
            } catch (Throwable e) {
                ADDConnectionPool.onRollback(id);
                throw e;
            }
        } else if (restController != null || service != null) {
            MDC.remove(ADDConstant.APIJSON_DYNAMIC_DATASOURCE_TRACE_ID);
            return point.proceed();
        } else {
            return point.proceed();
        }
    }

    private ADDTransactional getZCTAnnotation(Method method) {
        if (method.isAnnotationPresent(ADDTransactional.class)) {
            return method.getAnnotation(ADDTransactional.class);
        }
        if (method.getDeclaringClass().isAnnotationPresent(ADDTransactional.class)) {
            return method.getDeclaringClass().getAnnotation(ADDTransactional.class);
        }
        return null;
    }

    private RestController getRCAnnotation(Method method) {
        if (method.getDeclaringClass().isAnnotationPresent(RestController.class)) {
            return method.getDeclaringClass().getAnnotation(RestController.class);
        }
        return null;
    }

    private Service getSAnnotation(Method method) {
        if (method.getDeclaringClass().isAnnotationPresent(Service.class)) {
            return method.getDeclaringClass().getAnnotation(Service.class);
        }
        return null;
    }
}
