package com.vis.demo.jta.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

// http://www.cnblogs.com/ityouknow/p/6102399.html
@Configuration
@MapperScan(basePackages = "com.vis.demo.jta.mapper",sqlSessionTemplateRef = "sytemdbSqlSessionTemplate")
public class DataSourceSystemdbConfig {
    @Primary
    @Bean(name = "dataSourceSystemdb")
    @ConfigurationProperties("spring.datasource.druid.systemdb")
    public DataSource dataSourceSystemdb(){
        //return DruidDataSourceBuilder.create().build();

        DruidDataSource ds = DruidDataSourceBuilder.create().build();
        System.out.println("systemdb.url="+ds.getUrl());
        System.out.println(ds.getProperties());
        System.out.println(ds.getDbType());
/*        AtomikosDataSourceBean ads = new AtomikosDataSourceBean();
        Properties prop  = ds.getConnectProperties();
        System.out.println(prop.toString());
        ads.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        ads.setUniqueResourceName("systemdb");
        ads.setPoolSize(5);
        ads.setXaProperties(prop);*/
        return  ds ;
    }
    @Bean(name = "sytemdbSqlSessionFactory")
    @Primary
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("dataSourceSystemdb") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/*.xml"));
        return bean.getObject();
    }
    @Bean(name = "sytemdbSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("sytemdbSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "sytemdbTransactionManager")
    @Primary
    public DataSourceTransactionManager testTransactionManager(@Qualifier("dataSourceSystemdb") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


}
