package com.github.library.config.aspect.timer;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TimerAspect {
    @Around("@annotation(com.github.library.config.aspect.timer.TimerCustomAnnotation) || @within(com.github.library.config.aspect.timer.TimerCustomAnnotation)")
    public Object timerMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;
        log.info("Class {}, method {} #(running time: {} milliseconds)",
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(), executionTime);
        return proceed;
    }
}
