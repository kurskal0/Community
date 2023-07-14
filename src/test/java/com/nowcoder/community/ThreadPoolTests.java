package com.nowcoder.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class ThreadPoolTests {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolTests.class);

    //JDK普通线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    // JDK可执行定时任务线程池
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    // spring普通线程池
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    // spring可执行定时任务线程池
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExecutorService(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                logger.debug("Hello ExecutorService!");
            }
        };
        for (int i = 0; i < 10; i++) {
            executorService.submit(runnable);
        }

        sleep(10000);
    }

    @Test
    public void testScheduleExecutorService(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                logger.debug("Hello ExecutorService!");
            }
        };

        scheduledExecutorService.scheduleAtFixedRate(runnable, 10000, 1000, TimeUnit.MILLISECONDS);

        sleep(30000);
    }

    @Test
    public void testThreadPoolTaskExecutor(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                logger.debug("Hello ExecutorService!");
            }
        };
        for (int i = 0; i < 10; i++) {
            threadPoolTaskExecutor.submit(runnable);
        }

        sleep(10000);
    }
}
