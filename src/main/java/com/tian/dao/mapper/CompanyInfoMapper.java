package com.tian.dao.mapper;

import com.tian.dao.entity.CompanyInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by tianxiong on 2019/1/10.
 */
@Mapper
public interface CompanyInfoMapper {
    void insert(CompanyInfo companyInfo);

    void updateById(CompanyInfo companyInfo);
}
