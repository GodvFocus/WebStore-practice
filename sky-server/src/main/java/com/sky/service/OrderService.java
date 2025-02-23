package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {
    /**
     * 用户下单
     * @param ordersSubmitDTO
     * @return
     */
    OrderSubmitVO orderSubmit(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    /**
     * 历史订单查询
     * @param ordersPageQueryDTO
     * @return
     */
    PageResult getHistoryOrders(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    OrderVO getOrderDetail(Long id);

    /**
     * 取消订单
     * @param id
     */
    void cancelOrder(Long id) throws Exception;

    /**
     * 再来一单
     * @param id
     */
    void repetitionOrder(Long id);

    /**
     * 订单搜索
     * @param ordersPageQueryDTO
     * @return
     */
    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 统计各个状态的订单数量
     * @return
     */
    OrderStatisticsVO OrderStatistics();

    /**
     * 接单
     * @param id
     */
    void ConfirmOrder(Long id);

    /**
     * 拒单
     * @param ordersRejectionDTO
     */
    void rejectionOrder(OrdersRejectionDTO ordersRejectionDTO) throws Exception;

    /**
     * 取消订单
     * @param ordersCancelDTO
     */
    void adminCancelOrder(OrdersCancelDTO ordersCancelDTO);

    /**
     * 派送订单
     * @param id
     */
    void deliveryOrder(Long id);

    /**
     * 完成订单
     * @param id
     */
    void complete(Long id);

    /**
     * 用户催单
     * @param id
     */
    void reminderOrder(Long id);
}
