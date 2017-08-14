package com.yjy.quartz.job;

import org.quartz.*;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Administrator
 * Date: 2017-04-10
 * Time: 22:54
 */
@Component
public class DumbJob implements Job {
    private static Logger logger = LoggerFactory.getLogger(HelloJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("HelloJob start... ...");

        JobDetail job = jobExecutionContext.getJobDetail();
        logger.info("job: " + job);

        logger.info("HelloJob end... ...");
    }
}
