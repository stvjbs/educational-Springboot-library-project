package com.github.library.config.aspect.counter.issueCounter;

import com.github.library.config.actuator.counter.MetricOfIssue;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CounterIssueAspect {
    private final MetricOfIssue metricOfIssue;

    @AfterReturning("@annotation(com.github.library.config.aspect.counter.issueCounter.CounterIssue) || @within(com.github.library.config.aspect.counter.issueCounter.CounterIssue)")
    public void counterMethod() {
        metricOfIssue.increment();
    }
}
