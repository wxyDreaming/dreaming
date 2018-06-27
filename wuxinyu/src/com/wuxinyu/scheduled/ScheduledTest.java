package com.wuxinyu.scheduled;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author wxy
 * @Async 异步任务 属性value = 配置线程池名字
 * @Scheduled 定时任务
 */
@Service
public class ScheduledTest {

	@Async(value = "asyncExecutor")
	public void sayHello(int i) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("the " + i + " time to say " + "userHello");
	}

//	@Scheduled(fixedDelay = 1000)
	public void sayHelloEverySecondes() {
		System.out.println("time:" + System.currentTimeMillis() + " userHello");
	}
}
