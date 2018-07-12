package com.vis.demo.jta.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.UserTransaction;

/**
 *  druid multi datasource config
 */
//@Configuration
//@Profile("test")
public class DruidMultiDataSourceConfig {

/*    @Primary
    @Bean(name = "dataSourceSystemdb")
    @ConfigurationProperties("spring.datasource.druid.systemdb")
    public DataSource dataSourceSystemdb(){
        return DruidDataSourceBuilder.create().build();
    }
    @Bean(name = "dataSourceBusdb")
    @ConfigurationProperties("spring.datasource.druid.busdb")
    public DataSource dataSourceBusdb(){
        return DruidDataSourceBuilder.create().build();
    }*/

    //注入事物管理器
/*    @Bean(name = "xatx")
    public JtaTransactionManager regTransactionManager () {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransaction userTransaction = new UserTransactionImp();
        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }*/
}
