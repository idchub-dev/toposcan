package com.sansec.service.user;

import java.util.List;

import com.sansec.module.user.Employee;

public interface UserService {

	public Employee queryById(int id);
	
	public List<Employee> query(String keyWord,int offset,int limit);
	
	public int count(String keyWord);
}
