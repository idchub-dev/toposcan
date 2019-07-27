package com.sansec.webapp.controller.user;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sansec.module.user.Employee;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping("/queryById")
	public Employee queryById(int id,HttpServletRequest request) {
		Enumeration<String> en = request.getHeaderNames();
		while(en.hasMoreElements()) {
			System.out.println(en.nextElement());
		}
		Employee emp = new Employee();
		emp.setId(id);
		if(id > 0) {
			throw new RuntimeException("111");
		}
		return emp;
	}
}
