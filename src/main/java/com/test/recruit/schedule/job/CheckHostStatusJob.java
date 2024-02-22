package com.test.recruit.schedule.job;

import com.test.recruit.service.HostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckHostStatusJob extends QuartzJobBean {

    private final HostService hostService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("Start CheckHostStatusJob");
        // TODO ACTION
        log.info("End CheckHostStatusJob");
    }
}
