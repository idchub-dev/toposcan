package com.sansec.hystrix.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sansec.feign.user.UserFeignClient;
import com.sansec.module.user.Employee;

/**
 * 熔断器 发生异常时自动调用 防止雪崩效应
 * @author zhigongzhang
 *
 */
@Component
public class UserHystrixFallBack implements UserFeignClient {

	private final Logger logger = LoggerFactory.getLogger(UserHystrixFallBack.class);
	
	@Override
	public Employee queryById(int id) {
		logger.info("根据ID查询用户发生异常");
		Employee emp = new Employee();
		emp.setId(-1);
		emp.setUsername("");
		emp.setName("");
		return emp;
	}

}
