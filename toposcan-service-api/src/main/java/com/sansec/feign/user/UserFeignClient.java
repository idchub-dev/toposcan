package com.sansec.feign.user;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sansec.hystrix.user.UserHystrixFallBack;
import com.sansec.module.user.Employee;

@FeignClient(name="user",fallback = UserHystrixFallBack.class)
public interface UserFeignClient {
	
	@GetMapping("/user/queryById")
	public Employee queryById(@RequestParam("id") int id);
	
	@GetMapping("/user/query")
	public List<Employee> query(@RequestParam("keyWord") String keyWord,@RequestParam(name = "offset") int offset,
			@RequestParam(name = "limit") int limit);
	
	@GetMapping("/user/count")
	public int count(@RequestParam(name = "keyWord") String keyWord);
}
