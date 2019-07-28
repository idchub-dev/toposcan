package com.sansec.mapper.oauth;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.sansec.module.oauth.OauthClientDetails;

public interface ClientDetailsMapper {

	@Select("SELECT * FROM `oauth_client_details` WHERE `client_id` = #{clientId}")
	@Results(id = "clientDetailsResult", value = { 
		@Result(column = "name", property = "name"), 
		@Result(column = "description", property = "description"),
		@Result(column = "client_id", property = "clientId"),
		@Result(column = "client_secret", property = "clientSecret"),
		@Result(column = "resource_ids", property = "resourceId"),
		@Result(column = "scope", property = "scope"),
		@Result(column = "authorized_grant_types", property = "authorizedGrantTypes"),
		@Result(column = "web_server_redirect_uri", property = "webServerRedirectUri"),
		@Result(column = "authorities", property = "authorities"),
		@Result(column = "access_token_validity", property = "accessTokenValidity"),
		@Result(column = "refresh_token_validity", property = "refreshTokenValidity"),
		@Result(column = "additional_information", property = "additionalInformation"),
		@Result(column = "autoapprove", property = "autoapprove"),
		@Result(column = "create_time", property = "createTime"),
		@Result(column = "update_time", property = "updateTime")
	})
	public OauthClientDetails queryByClientId(@Param("clientId") String clientId);
}
