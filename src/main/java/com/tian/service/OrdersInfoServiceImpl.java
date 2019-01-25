package com.tian.service;

import com.tian.dao.entity.OrdersInfo;
import com.tian.dao.mapper.OrdersInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tianxiong on 2019/1/11.
 */
@Service
public class OrdersInfoServiceImpl implements IOrdersInfoService {
    @Autowired
    private OrdersInfoMapper ordersInfoMapper;

    @Override
    public void insert(OrdersInfo ordersInfo) {
        ordersInfoMapper.insert(ordersInfo);
    }

    @Override
    public void updateById(OrdersInfo ordersInfo) {

    }
}
