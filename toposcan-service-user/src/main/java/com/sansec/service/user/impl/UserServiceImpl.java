package com.sansec.service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sansec.mapper.user.UserMapper;
import com.sansec.module.user.Employee;
import com.sansec.service.user.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public Employee queryById(int id) {
		return userMapper.queryById(id);
	}

	@Override
	public List<Employee> query(String keyWord, int offset, int limit) {
		return userMapper.query(keyWord, offset, limit);
	}

	@Override
	public int count(String keyWord) {
		return userMapper.count(keyWord);
	}

}
