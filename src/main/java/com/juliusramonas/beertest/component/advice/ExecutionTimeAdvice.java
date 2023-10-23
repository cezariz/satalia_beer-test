package com.juliusramonas.beertest.component.advice;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ExecutionTimeAdvice {

    private final MethodExecutionDurations methodExecutionDurations;

    @Around("@annotation(com.juliusramonas.beertest.component.advice.TrackExecutionTime)")
    public Object executionTime(final ProceedingJoinPoint point) throws Throwable {
        final StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        try {
            stopWatch.start(point.getSignature()
                    .getName());
            return point.proceed();
        } finally {
            stopWatch.stop();
            methodExecutionDurations.getMethodDurations()
                    .put(point.getSignature().getName(), stopWatch.getLastTaskTimeMillis());
            log.info(stopWatch.prettyPrint());
        }
    }

}
