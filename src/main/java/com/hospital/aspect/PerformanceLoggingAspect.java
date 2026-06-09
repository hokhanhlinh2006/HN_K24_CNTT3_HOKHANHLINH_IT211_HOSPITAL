package com.hospital.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceLoggingAspect {

    @Around(
            "execution(* com.hospital.service.impl.*.*(..))"
    )
    public Object measureExecutionTime(
            ProceedingJoinPoint joinPoint)
            throws Throwable {

        long startTime =
                System.currentTimeMillis();

        Object result =
                joinPoint.proceed();

        long endTime =
                System.currentTimeMillis();

        long executionTime =
                endTime - startTime;

        log.info(
                "{} executed in {} ms",
                joinPoint.getSignature()
                        .getName(),
                executionTime
        );

        return result;
    }
}