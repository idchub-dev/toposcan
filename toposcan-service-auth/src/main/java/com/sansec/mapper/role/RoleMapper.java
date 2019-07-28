package com.sansec.mapper.role;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.sansec.module.role.Role;

public interface RoleMapper {

	@Select("SELECT * FROM `role` WHERE `id` = #{roleId}")
	@Results(id="roleResult", value = {
		@Result(column="id",property="id"),
		@Result(column="name",property="name"),
		@Result(column="type",property="type"),
		@Result(column="description",property="description"),
		@Result(column="granted_authority",property="grantedAuthority"),
		@Result(column="create_time",property="createTime"),
		@Result(column="update_time",property="updateTime"),
	})
	public Role queryById(int roleId);
}
