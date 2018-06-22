package com.tian.controller;

import com.tian.common.other.PageParam;
import com.tian.common.other.ResponseData;
import com.tian.common.validation.Number;
import com.tian.dao.entity.Activity;
import com.tian.service.IActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.tian.common.other.ResponseData.successData;

/**
 * 活动控制层
 * Created by Administrator on 2016/12/15 0015.
 */
@RestController
@RequestMapping("activity")
public class ActivityController {
    @Autowired
    private IActivityService activityService;

    private static final Logger log = LoggerFactory.getLogger(ActivityController.class);

    /**
     * 新增一个活动
     * @param activity
     */
    @RequestMapping("insert_activity")
    public ResponseData insertActivity(Activity activity){
        activityService.insertActivity(activity);
        return ResponseData.successData;
    }

    /**
     * 分页查询活动
     * @param pageParam
     * @param request
     * @return
     */
    @RequestMapping("query_activity_page")
    public ResponseData queryActivityPage(PageParam<Activity> pageParam, HttpServletRequest request){
        pageParam.getParams().put("startTime",request.getParameter("startTime"));
        pageParam.getParams().put("endTime",request.getParameter("endTime"));
        pageParam.getParams().put("name",request.getParameter("name"));
        pageParam.getParams().put("status",request.getParameter("status"));
        activityService.queryActivityPage(pageParam);
        return successData.setData(pageParam);
    }

    /**
     * 根据id查询单个活动详情
    * @param id
     * @return
     */
    @RequestMapping("query_activity_by_id")
    public ResponseData queryActivityById(@Number Long id){
        long startTime = System.currentTimeMillis();
        ResponseData responseData =  successData.setData(activityService.queryById(id));
        log.info("=====>use time: "+(System.currentTimeMillis() - startTime));
        return responseData;
    }

    /**
     * 根据id更新活动信息
     * @param activity
     * @return
     */
    @RequestMapping("update_activity_by_id")
    public ResponseData updateActivityById(Activity activity){
        activityService.updateActivityById(activity);
        return ResponseData.successData;
    }


}
