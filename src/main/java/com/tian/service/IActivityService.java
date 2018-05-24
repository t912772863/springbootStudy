package com.tian.service;


import com.tian.common.other.PageParam;
import com.tian.dao.entity.Activity;

/**
 * Created by Administrator on 2016/12/15 0015.
 */
public interface IActivityService {
    Activity queryById(Long id);

    boolean insertActivity(Activity activity);

    boolean deleteActivityById(Long id);

    boolean updateActivityById(Activity activity);

    /**
     * 分页查询活动
     * @param pageParam
     * @return
     */
    PageParam<Activity> queryActivityPage(PageParam<Activity> pageParam);
}
