package com.tian.service;

import com.tian.dao.entity.CompanyInfo;

/**
 * Created by tianxiong on 2019/1/11.
 */
public interface ICompanyInfoService {
    void insert(CompanyInfo companyInfo);

    void updateById(CompanyInfo companyInfo);
}
