package com.sansec.mapper.oauth;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.sansec.module.oauth.OauthAccessToken;
import com.sansec.module.oauth.OauthRefreshToken;

public interface TokenMapper {
	
	@Select("SELECT * FROM `oauth_access_token` WHERE `token_id` = #{tokenId}")
	@Results(id = "accessTokenResult",value = {
		@Result(column = "tokenId", property = "token_id"), 
		@Result(column = "token", property = "token"),
		@Result(column = "authentication_id", property = "authenticationId"),
		@Result(column = "user_name", property = "userName"),
		@Result(column = "client_id", property = "clientId"),
		@Result(column = "authentication", property = "authentication"),
		@Result(column = "refresh_token", property = "refreshToken")
	})
	public OauthAccessToken queryByTokenId(@Param("tokenId") String tokenId);
	
	@Select("SELECT * FROM `oauth_access_token` WHERE `authentication_id` = #{authenticationId}")
	@ResultMap(value = "accessTokenResult")
	public OauthAccessToken queryByAuthenticationId(@Param("authenticationId") String authenticationId);
	
	@Select("SELECT * FROM `oauth_access_token` WHERE `client_id` = #{clientId} AND `user_name` = #{userName}")
	@ResultMap(value = "accessTokenResult")
	public List<OauthAccessToken> queryByClientIdAndUserName(@Param("clientId") String clientId,
			@Param("userName") String username);
	
	@Select("SELECT * FROM `oauth_access_token` WHERE `client_id` = #{clientId}")
	@ResultMap(value = "accessTokenResult")
	public List<OauthAccessToken> queryByClientId(@Param("clientId") String clientId);
	
	@Delete("DELETE FROM `oauth_access_token` WHERE `token_id` = #{tokenId}")
	public void deleteByTokenId(@Param("tokenId") String tokenId);
	
	@Delete("DELETE FROM `oauth_access_token` WHERE `refresh_token` = #{refreshToken}")
	public void deleteByRefreshToken(@Param("refreshToken") String refreshToken);
	
	@Insert("INSERT INTO `oauth_access_token` ("
			+ "`token_id`," + 
			"`token`," + 
			"`authentication_id`," + 
			"`user_name`," + 
			"`client_id`," + 
			"`authentication`," + 
			"`refresh_token`)VALUES("
			+ "#{tokenId}," + 
			"#{token}," + 
			"#{authenticationId}," + 
			"#{userName}," + 
			"#{clientId}," + 
			"#{authentication}," + 
			"#{refreshToken})")
	public void insertAccessToken(OauthAccessToken accessToken);
	
	@Insert("INSERT INTO `oauth_refresh_token` (`token_id`,`token`,`authentication`) VALUES (#{tokenId},#{token},#{authentication})")
	public void insertRefreshToken(OauthRefreshToken refreshToken);
	
	@Select("SELECT * FROM `oauth_refresh_token` WHERE `token_id` = #{tokenId}")
	@Results(id = "refreshTokenResult",value = {
		@Result(column = "tokenId", property = "token_id"),
		@Result(column = "token", property = "token"),
		@Result(column = "authentication", property = "authentication")
	})
	public OauthRefreshToken queryRefreshTokenByTokenId(@Param("tokenId") String tokenId);
	
	@Delete("DELETE FROM `oauth_refresh_token` WHERE `token_id` = #{tokenId}")
	public void deleteRefreshByTokenId(@Param("tokenId") String tokenId);
}
