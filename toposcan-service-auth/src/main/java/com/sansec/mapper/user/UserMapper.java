package com.sansec.mapper.user;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.sansec.module.user.Employee;

public interface UserMapper {
	
	@Select("select * from employee where id = #{id}")
	@Results(id = "userResult",value = {
		@Result(column="id",property="id"),
		@Result(column="name",property="name"),
		@Result(column="username",property="username"),
		@Result(column="password",property="password"),
		@Result(column="email",property="email"),
		@Result(column="type",property="type"),
		@Result(column="status",property="status"),
		@Result(column="phone_number",property="phoneNumber"),
		@Result(column="role_id",property="roleId"),
		@Result(column="create_time",property="createTime"),
		@Result(column="update_time",property="updateTime")
	})
	public Employee queryById(int id);
	
	@Select("select * from employee where username = #{username} limit 1")
	@ResultMap(value = "userResult")
	public Employee queryByUserName(String username);
	
	@Insert("insert into `employee` (`name`,`username`,`password`,`email`,`type`,`status`,`phone_number`" +
			",`role_id`,`create_time`)" +
			"values(#{name},#{username},#{password},#{email},#{type},#{status}"
			+ ",#{phoneNumber},#{roleId},#{createTime})")
	@Options(useGeneratedKeys = true,keyProperty = "id", keyColumn="id")
	public int insert(Employee emp);
	
	@Select("select * from employee where username = #{username} and password = #{password}")
	@ResultMap(value = "userResult")
	public Employee login(String username,String password);
}
