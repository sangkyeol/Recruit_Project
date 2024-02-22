package com.test.recruit.schedule;


import com.test.recruit.constant.Constant;
import com.test.recruit.exception.BatchException;
import com.test.recruit.schedule.job.CheckHostStatusJob;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import static org.quartz.JobBuilder.newJob;

@Service
@Slf4j
@RequiredArgsConstructor
public class BatchService {
    private final Scheduler scheduler;

    @PostConstruct
    public void runSchedule() {
        //==== CHECK_HOST_STATUS =========
        JobDetail checkHostStatusJob = newJob(CheckHostStatusJob.class)
                .withIdentity(Constant.Q_JOB_CHECK_HOST_STATUS)
                .build();
        try {
            //10초마다 지속 체크
            scheduler.scheduleJob(checkHostStatusJob, runJobTrigger("*/10 * * * * ?"));
        } catch (SchedulerException e) {
            throw new BatchException("checkHostStatusJob/" + e.getMessage());
        }
        //==== END_CHECK_HOST_STATUS =========
    }

    public Trigger runJobTrigger(String scheduleExp) {
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
    }

}
