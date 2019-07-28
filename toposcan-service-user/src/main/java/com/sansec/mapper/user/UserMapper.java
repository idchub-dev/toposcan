package com.sansec.mapper.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.sansec.module.user.Employee;

public interface UserMapper {

	@Select("select e.*,r.name as role_name from employee e left join role r on e.role_id = r.id where e.id = #{id}")
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
		@Result(column="role_name",property="roleName"),
		@Result(column="create_time",property="createTime"),
		@Result(column="update_time",property="updateTime")
	})
	public Employee queryById(int id);
	
	@Select("<script>select e.*,r.name as role_name from employee e left join role r on e.role_id = r.id"
			+ "<where>"
			+ "<if test=\"keyWord!=null and keyWord!=''\"><![CDATA["
			+ "and (e.name like concat('%',#{keyWord},'%')"
			+ "or e.username like concat('%',#{keyWord},'%')"
			+ "or r.name like concat('%',#{keyWord},'%')"
			+ ")"
			+ "]]></if>"
			+ "</where>"
			+ "<if test=\"limit > 0\"><![CDATA["
			+ "limit #{offset},#{limit}"
			+ "]]></if>"
			+ "</script>")
	@ResultMap(value = "userResult")
	public List<Employee> query(@Param("keyWord")String keyWord,@Param("offset")int offset,@Param("limit")int limit);
	
	@Select("<script>select count(e.id) from employee e left join role r on e.role_id = r.id"
			+ "<where>"
			+ "<if test=\"keyWord!=null and keyWord!=''\"><![CDATA["
			+ "and (e.name like concat('%',#{keyWord},'%')"
			+ "or e.username like concat('%',#{keyWord},'%')"
			+ "or r.name like concat('%',#{keyWord},'%')"
			+ ")"
			+ "]]></if>"
			+ "</where>"
			+ "</script>")
	public int count(@Param("keyWord") String keyWord);
}
