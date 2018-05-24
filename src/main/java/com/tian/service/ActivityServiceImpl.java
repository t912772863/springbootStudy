package com.tian.service;

import com.tian.common.other.PageParam;
import com.tian.dao.entity.Activity;
import com.tian.dao.mapper.ActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/12/15 0015.
 */
@Service
public class ActivityServiceImpl implements IActivityService {
    @Autowired
    private ActivityMapper activityMapper;

    public Activity queryById(Long id){
        return activityMapper.selectByPrimaryKey(id);
    }

    public boolean insertActivity(Activity activity) {
        activity.setCreateTime(new Date());
        activity.setStatus(1);
        activity.setActivityStatus(1);
        activityMapper.insertSelective(activity);
        return true;
    }

    public boolean deleteActivityById(Long id) {
        activityMapper.deleteByPrimaryKey(id);
        return true;
    }

    public boolean updateActivityById(Activity activity) {
        activity.setUpdateTime(new Date());
        activityMapper.updateByPrimaryKeySelective(activity);
        return true;
    }


    public PageParam<Activity> queryActivityPage(PageParam<Activity> pageParam) {
        List<Activity> list = activityMapper.queryByPage(pageParam);
        pageParam.setResult(list);
        return pageParam;
    }
}
