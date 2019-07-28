package com.sansec.webapp.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.sansec.mapper.*")
public class DataSourceConfig {

	@Bean("dataSource")
	@ConfigurationProperties(prefix="spring.user.server.datasource")
	public DataSource getDataSource() {
		return DataSourceBuilder.create(BasicDataSource.class.getClassLoader()).build();
	}
	
	@Bean("sqlSessionFactory")
	@Resource(name="dataSource")
	public SqlSessionFactory getSqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sf = new SqlSessionFactoryBean();
		sf.setDataSource(dataSource);
		return sf.getObject();
	}
}
