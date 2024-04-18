package com.github.library.config.aspect.counter.issueRefuseCounter;

import com.github.library.config.actuator.counter.MetricOfIssueRefuse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CounterRefuseIssueAspect {
    private final MetricOfIssueRefuse metricOfIssueRefuse;

    @AfterThrowing("@annotation(com.github.library.config.aspect.counter.issueRefuseCounter.CounterRefuseIssue) || @within(com.github.library.config.aspect.counter.issueRefuseCounter.CounterRefuseIssue)")
    public void counterMethod() {
            metricOfIssueRefuse.increment();
    }
}
