package com.tian.service;

import com.tian.dao.entity.OrdersInfo;

/**
 * Created by tianxiong on 2019/1/11.
 */
public interface IOrdersInfoService {
    void insert(OrdersInfo ordersInfo);

    void updateById(OrdersInfo ordersInfo);
}
