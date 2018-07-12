package com.vis.demo.jta.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.atomikos.icatch.jta.UserTransactionImp;
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
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import java.util.Properties;

// http://www.cnblogs.com/ityouknow/p/6102399.html
//@Configuration
//@Profile("test")
//@MapperScan(basePackages = "com.vis.demo.jta.busmapper",sqlSessionTemplateRef = "busdbSqlSessionTemplate")
public class DataSourceBusdbConfig {

   /* @Bean(name = "dataSourceBusdb")
    @ConfigurationProperties("spring.datasource.druid.busdb")
    public DataSource dataSourceSystemdb(){
        new DruidXADataSource()

        //DruidXADataSource dsxa = DruidDataSourceBuilder.create().build();

        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        //Properties prop = build(env, "spring.datasource.druid.systemDB.");
        //ds.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        ds.setUniqueResourceName("systemDB");
        ds.setPoolSize(5);
        ds.setXaDataSource(dsxa);
        //ds.setXaProperties(prop);

        return ds ;
    }
    @Bean(name = "busdbSqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("dataSourceBusdb") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper2/*.xml"));
        return bean.getObject();
    }
    @Bean(name = "busdbSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("busdbSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

  @Bean(name = "busdbTransactionManager")
    public DataSourceTransactionManager busdbTransactionManager(@Qualifier("dataSourceBusdb") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

*/
}
