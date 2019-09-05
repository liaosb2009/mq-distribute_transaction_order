package com.study.transaction.order.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

// 兜底 --- 计划任务去检查~~针对长时间没有发送成功的消息~~然后重试
@Component
public class ScheduleTask {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 3000)
    public void reportCurrentTime() {
        System.out.println("当前时间：" + dateFormat.format(new Date()));
        // 检查消息记录表
    }
}