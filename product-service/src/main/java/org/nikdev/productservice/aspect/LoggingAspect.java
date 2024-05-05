package org.nikdev.productservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {



    @Around("execution(* org.nikdev.productservice.service.*.*(..))")
    public Object productAroundAdvice(ProceedingJoinPoint joinPoint) throws Exception {
        String method = joinPoint.getSignature().getName();
        Object proceed;
        try {
            log.info("Началось выполнение метода {}", method);
            proceed = joinPoint.proceed();
            log.info("Метод {} успешно выполнен", method);

        } catch (Throwable e) {
            log.warn("Выполнение метода {} завершилось ошибкой", method);
            throw new Exception(e.getMessage());
        }
        return proceed;
    }
}


