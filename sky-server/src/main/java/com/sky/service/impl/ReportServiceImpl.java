package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ReportMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 营业额统计
     * @param begin
     * @param end
     * @return
     */
    @Override
    public TurnoverReportVO turnoverReport(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = getLocalDates(begin, end);

//        select sum(amount) from orders where status = 5 and order_time > ? and order_time < ?
        List<Double> amountList = new ArrayList<>();
        for (LocalDate date : dateList) {
            // 获取xx日00点00分00秒
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            // 获取xx日23点59分59秒
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map map = new HashMap();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover = reportMapper.getByMap(map);
            turnover = turnover == null ? 0.0 : turnover;
            amountList.add(turnover);
        }

        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateList,","))
                .turnoverList(StringUtils.join(amountList,","))
                .build();
    }

    private static List<LocalDate> getLocalDates(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        // 获取日期区间的日期
        dateList.add(begin);
        while (!begin.equals(end)){
            begin = begin.plusDays(1);//日期计算，获得指定日期后1天的日期
            dateList.add(begin);
        }
        return dateList;
    }

    /**
     * 用户统计
     * @param begin
     * @param end
     * @return
     */
    @Override
    public UserReportVO userReport(LocalDate begin, LocalDate end) {
        // 获取指定区间内的日期
        List<LocalDate> dateList = getLocalDates(begin, end);

        List<Integer> totalUserList = new ArrayList<>();
        List<Integer> newUserList = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map map = new HashMap();
            map.put("end", endTime);
            // select count(id) from user where create_time <= endTime
            totalUserList.add(userMapper.countUserByTime(map));
            // select count(id) from user where create_time <= endTime and create_time >= beginTime
            map.put("begin", beginTime);
            newUserList.add(userMapper.countUserByTime(map));
        }
        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .build();
    }

    /**
     * 用户统计
     * @param begin
     * @param end
     * @return
     */
    @Override
    public OrderReportVO orderReport(LocalDate begin, LocalDate end) {
        // 获取指定区间内的日期
        List<LocalDate> dateList = getLocalDates(begin, end);

        // 每日订单数
        List<Integer> dailyOrderList = new ArrayList<>();
        // 每日有效订单数
        List<Integer> validDailyOrderList = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map map = new HashMap();
            map.put("begin", beginTime);
            map.put("end", endTime);
            // select count(id) from orders where order_time <= endTime and create_time >= beginTime
            dailyOrderList.add(orderMapper.countOrderByCondition(map));
            map.put("status", Orders.COMPLETED);
            // select count(id) from orders where order_time <= endTime and create_time >= beginTime and status = 5
            validDailyOrderList.add(orderMapper.countOrderByCondition(map));
        }
        Integer totalOrder = dailyOrderList.stream().reduce(Integer::sum).get();
        Integer vaildOrder = validDailyOrderList.stream().reduce(Integer::sum).get();
        Double orderCompletionRate = 0.0;
        if(totalOrder != 0){
            orderCompletionRate = vaildOrder.doubleValue() / totalOrder;
        }
        return OrderReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .orderCountList(StringUtils.join(dailyOrderList, ","))
                .validOrderCountList(StringUtils.join(validDailyOrderList, ","))
                .totalOrderCount(totalOrder)
                .validOrderCount(vaildOrder)
                .orderCompletionRate(orderCompletionRate)
                .build();

    }

    /**
     * 查询销量排名top10
     * @param begin
     * @param end
     * @return
     */
    @Override
    public SalesTop10ReportVO top10Report(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        List<GoodsSalesDTO> sales = orderMapper.getSalesByTime(beginTime, endTime);
        // 分别从数据中获取name和number各成一个list
        List<String> nameList = sales.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
        List<Integer> numberList = sales.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());
        return SalesTop10ReportVO.builder()
                .nameList(StringUtils.join(nameList, ","))
                .numberList(StringUtils.join(numberList, ","))
                .build();
    }
}
