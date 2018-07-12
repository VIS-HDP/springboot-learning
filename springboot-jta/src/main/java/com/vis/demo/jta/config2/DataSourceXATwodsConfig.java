package com.vis.demo.jta.config2;


import com.alibaba.druid.pool.xa.DruidXADataSource;
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
@MapperScan(basePackages = "com.vis.demo.jta.busmapper", sqlSessionTemplateRef = "twoDsSqlSessionTemplate")
public class DataSourceXATwodsConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.two-ds")
    public DataSource dataSourceTwoDs(){
       // new DruidXADataSource().setm
        return  new AtomikosDataSourceBean();
    }

    @Bean
    public SqlSessionFactory twoDsSqlSessionFactory(@Qualifier("dataSourceTwoDs") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper2/*.xml"));
        bean.setTypeAliasesPackage("com.vis.demo.jta.vo");
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate twoDsSqlSessionTemplate(
            @Qualifier("twoDsSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
