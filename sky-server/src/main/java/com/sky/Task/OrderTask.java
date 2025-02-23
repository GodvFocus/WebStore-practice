package com.sky.Task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 处理超时未支付订单
     */
    @Scheduled(cron = "0 * * * * ?") // 每分钟触发一次
    public void scheduledTimeOutOrder(){
        log.info("处理超时未支付订单{}", LocalDateTime.now());
        // 获取超时订单的时间（当前时间-15分钟）
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
        List<Orders> ordersList = orderMapper.scheduleByStatus(Orders.PENDING_PAYMENT, time);

        if(ordersList != null && ordersList.size() > 0){
            for (Orders order : ordersList) {
                order.setCancelTime(LocalDateTime.now());
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("订单超时");
                orderMapper.update(order);
            }
        }
    }

    /**
     * 处理超时未完成订单
     */
    @Scheduled(cron = "0 0 1 * * ?") // 每天1点触发
    public void scheduledUnCompleteOrder(){
        log.info("处理超时未完成订单{}", LocalDateTime.now());
        // 获取超时订单的时间（当前时间-1小时）
        LocalDateTime time = LocalDateTime.now().plusHours(-1);
        List<Orders> ordersList = orderMapper.scheduleByStatus(Orders.DELIVERY_IN_PROGRESS, time);

        if(ordersList != null && ordersList.size() > 0){
            for (Orders order : ordersList) {
                order.setStatus(Orders.COMPLETED);
                orderMapper.update(order);
            }
        }
    }
}
