package com.sansec.service.user;

import com.sansec.module.user.Employee;

public interface UserService {
	
	public Employee login(String username,String password);
}
