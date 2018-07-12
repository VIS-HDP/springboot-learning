package com.vis.demo.jta.config2;


import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties
@EnableAutoConfiguration
@MapperScan(basePackages = "com.vis.demo.jta.mapper", sqlSessionTemplateRef = "oneDsSqlSessionTemplate")
public class DataSourceXAOnedsConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.one-ds")
    public DataSource dataSourceOneDs(){
        return  new AtomikosDataSourceBean();
    }
    @Bean
    public SqlSessionFactory oneDsSqlSessionFactory(@Qualifier("dataSourceOneDs") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/*.xml"));
        bean.setTypeAliasesPackage("com.vis.demo.jta.vo");
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate oneDsSqlSessionTemplate(
            @Qualifier("oneDsSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
