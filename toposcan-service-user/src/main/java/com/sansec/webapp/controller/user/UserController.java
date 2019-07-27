package com.sansec.webapp.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sansec.module.user.Employee;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping("/queryById")
	public Employee queryById(int id) {
		Employee emp = new Employee();
		emp.setId(id);
		if(id > 0) {
			throw new RuntimeException("111");
		}
		return emp;
	}
}
