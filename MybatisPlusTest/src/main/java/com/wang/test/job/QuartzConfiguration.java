package com.wang.test.job;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: wangcan
 * @Date: 2020/11/20 15:19
 */
@Configuration
public class QuartzConfiguration {
    /**
     * 包装job
     * @return
     */
    @Bean
    public JobDetail myCronJobDetail() {
        return JobBuilder.newJob(TestJob.class).withIdentity("couponTimeOutJob").storeDurably().build();
    }

    /**
     * 注册Cron
     * @return
     */
    @Bean
    public Trigger CronJobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/2 * * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(myCronJobDetail())
                .withIdentity("CouponTimeOutJobTrigger")
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}