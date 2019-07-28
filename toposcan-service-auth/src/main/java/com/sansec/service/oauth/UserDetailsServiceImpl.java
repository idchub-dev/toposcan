package com.sansec.service.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sansec.mapper.role.RoleMapper;
import com.sansec.mapper.user.UserMapper;
import com.sansec.module.role.Role;
import com.sansec.module.user.Employee;
import com.sansec.service.user.UserService;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserService,UserDetailsService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee emp = userMapper.queryByUserName(username);
		if(emp == null) {
			throw new UsernameNotFoundException("用户名密码错误");
		}
		int roleId = emp.getRoleId();
		if(roleId == 0) {
			throw new RuntimeException("用户未授权");
		}
		Role role = roleMapper.queryById(roleId);
		if(role == null) {
			throw new RuntimeException("用户未授权");
		}
		emp.setRole(role);
		return emp;
	}

	@Override
	public Employee login(String username, String password) {
		return userMapper.login(username, password);
	}

}
