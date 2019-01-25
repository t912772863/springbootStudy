package com.tian.service;

import com.tian.common.other.BusinessException;
import com.tian.dao.entity.CompanyInfo;
import com.tian.dao.mapper.CompanyInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by tianxiong on 2019/1/11.
 */
@Service
public class CompanyInfoServiceImpl implements ICompanyInfoService {
    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Override
    public void insert(CompanyInfo companyInfo) {
        Date date = new Date();
        companyInfo.setCreateTime(date);
        companyInfo.setModifyTime(date);
        companyInfoMapper.insert(companyInfo);
    }

    @Override
    public void updateById(CompanyInfo companyInfo) {
        if(companyInfo.getId() == null){
            throw new BusinessException(500, "while do update, id can not be null");
        }
        companyInfo.setModifyTime(new Date());
        companyInfoMapper.updateById(companyInfo);
    }
}
