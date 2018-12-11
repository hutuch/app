package liu.chi.app.gateway.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * aspect具有以下几种 before、around、afterReturn、afterThrowing、after(Finally)
 *
 * @author：liuchi
 * @date： 2018/01/28 11:14
 */
@Slf4j
@Aspect
@Configuration
public class AopLog {

    @Pointcut("execution(* liu.chi.app.gateway..*.*(..))")
    public void loginPointCut() {
    }

    /**
     * 日志aop不消化任何异常
     */
    @Around("loginPointCut()")
    public Object exec(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("进入{}.{}方法，参数是：{}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));

        try {
            Object result = joinPoint.proceed();
            log.debug("离开{}.{}方法，结果是：{}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), result);

            return result;
        } catch (IllegalArgumentException e) {
            log.error("{}.{}的非法参数：{}，", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));

            /**
             * Aop 不消化任何异常
             */
            throw e;
        }
    }

    /**
     * @AfterThrowing优先@ControllerAdvice
     */
    @AfterThrowing(value = "loginPointCut()", throwing = "e")
    public void exec2(Exception e) {
        log.error("@AfterThrowing 捕获控制器异常:" + e.getClass().getName() + "--" + e.getMessage());
    }
}
