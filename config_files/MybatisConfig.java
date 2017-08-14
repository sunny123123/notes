package com.dji.dava.demo.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.dji.dava.data.mybatis.MybatisBatchSupport;
import com.dji.dava.data.mybatis.interceptor.PageIntercepts;

@Configuration
@EnableTransactionManagement
@AutoConfigureAfter({ DruidConfig.class })
@MapperScan({ "com.dji.dava.security.dao.mapper", "com.dji.dava.demo.dao.mapper" })
public class MybatisConfig implements TransactionManagementConfigurer {

	@Autowired
	private DataSource dataSource;

	private String typeAliasPackage = "com.dji.dava.demo.dao.entity";

	private static final String XML_RESOURCE_PATTERN = "classpath*:sqlmap/db/**/*Mapper.xml";
	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	/**
	 * 创建sqlSessionFactoryBean 实例 并且设置configtion 如驼峰命名.等等 设置mapper 映射路径
	 * 设置datasource数据源
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactoryBean createSqlSessionFactoryBean() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		
		// 1 Interceptors
		PageIntercepts pageHelper = new PageIntercepts("MYSQL");
		Properties p = new Properties();
		p.setProperty("dialect", "mysql");
		pageHelper.setProperties(p);
		
		org.apache.ibatis.plugin.Interceptor[] plugins = new org.apache.ibatis.plugin.Interceptor[1];
		plugins[0] = pageHelper;
		sqlSessionFactoryBean.setPlugins(plugins);
		
		
		//2  设置datasource
		sqlSessionFactoryBean.setDataSource(dataSource);
		
		/** 设置typeAlias 包扫描路径 */
		sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasPackage);
		//3  sql mapping 文件
		Resource[] mapperLocations = resourcePatternResolver.getResources(XML_RESOURCE_PATTERN);
		sqlSessionFactoryBean.setMapperLocations(mapperLocations);
		
		
		// 4 设置typehandlers
		sqlSessionFactoryBean.setTypeHandlersPackage("com.dji.dava.data.mybatis.typehandler");

		return sqlSessionFactoryBean;
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public MybatisBatchSupport mybatisBatchSupport(SqlSessionFactory sqlSessionFactory) {
		MybatisBatchSupport mybatisBatchSupport = new MybatisBatchSupport();
		mybatisBatchSupport.setSsf(sqlSessionFactory);

		return mybatisBatchSupport;
	}
}
