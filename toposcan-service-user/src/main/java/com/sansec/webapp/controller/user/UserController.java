package com.sansec.webapp.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sansec.module.user.Employee;
import com.sansec.service.user.UserService;
import com.util.StringUtil;


@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/queryById")
	public Employee queryById(int id) {
		return userService.queryById(id);
	}
	
	@GetMapping("/query")
	public List<Employee> query(String keyWord,int offset,int limit){
		return userService.query(keyWord, offset, limit);
	}
	
	@GetMapping("/count")
	public int count(String keyWord) {
		return userService.count(keyWord);
	}
}
