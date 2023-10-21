package com.juliusramonas.beertest.component.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
@ConditionalOnExpression("${aspect.enabled:true}")
public class ExecutionTimeAdvice {

    @Around("@annotation(com.juliusramonas.beertest.component.advice.TrackExecutionTime)")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        final StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        try {
            stopWatch.start(point.getSignature()
                                 .getName());
            return point.proceed();
        } finally {
            stopWatch.stop();
            log.info(stopWatch.prettyPrint());
        }
    }

}
