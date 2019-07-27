package com.sansec.feign.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sansec.hystrix.user.UserHystrixFallBack;
import com.sansec.module.user.Employee;

@FeignClient(name="user",fallback = UserHystrixFallBack.class)
public interface UserFeignClient {
	
	@RequestMapping("/user/queryById")
	public Employee queryById(@RequestParam("id") int id);
}
