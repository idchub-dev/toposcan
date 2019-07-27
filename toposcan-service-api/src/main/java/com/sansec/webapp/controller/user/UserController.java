package com.sansec.webapp.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sansec.feign.user.UserFeignClient;
import com.sansec.module.user.Employee;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserFeignClient userFeignClient;
	
	@GetMapping("queryById")
	public Employee queryById(int id) {
		return userFeignClient.queryById(id);
	}
}
