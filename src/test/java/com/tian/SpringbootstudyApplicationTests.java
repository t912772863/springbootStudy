package com.tian;

import com.tian.controller.LoginController;
import com.tian.dao.entity.OrdersInfo;
import com.tian.service.IActivityService;
import com.tian.service.IOrdersInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootstudyApplicationTests {

	@Autowired
	private LoginController loginController;
	@Autowired
	private IActivityService activityService;
	@Autowired
	private IOrdersInfoService ordersInfoService;

	@Test
	public void tempTest() {
		Random random = new Random(100);
		for(int i=1;i< 1000;i++){
			OrdersInfo ordersInfo = new OrdersInfo();

			ordersInfo.setId(1351027L+i);
			ordersInfo.setActivityId(i+0L);
			ordersInfo.setOrderAmount(random.nextInt(100));

			ordersInfoService.insert(ordersInfo);


//			Activity activity = new Activity();
//			activity.setId(i+0L);
//			activity.setAddress("activity"+i);
//			activity.setName("name"+i);
//			activityService.insertActivity(activity);
		}

	}

}
