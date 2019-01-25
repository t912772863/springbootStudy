package com.tian.dao.mapper;

import com.tian.dao.entity.OrdersInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by tianxiong on 2019/1/10.
 */
@Mapper
public interface OrdersInfoMapper {
    void insert(OrdersInfo ordersInfo);

    void updateById(OrdersInfo ordersInfo);
}
