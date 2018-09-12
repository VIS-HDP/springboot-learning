package com.vis.jta.testcase;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DruidMultiDataSourceTest {

    @Resource
    DruidDataSource dataSourceSystemdb ;

    @Resource
    DruidDataSource dataSourceBusdb ;

    @Test
    public void testSystemDB(){
        //assertThat(dataSourceSystemdb.getUrl()).isEqualTo("jdbc:h2:file:./demo-db");
        assertThat(dataSourceSystemdb.getUsername()).isEqualTo("bcp_user");
        assertThat(dataSourceSystemdb.getPassword()).isEqualTo("eteng");
        assertThat(dataSourceSystemdb.getDriverClassName()).isEqualTo("com.mysql.jdbc.Driver");
        assertThat(dataSourceSystemdb.getInitialSize()).isEqualTo(5);
        assertThat(dataSourceSystemdb.getMaxActive()).isEqualTo(20);
        assertThat(dataSourceSystemdb.getMaxWait()).isEqualTo(60000);
        System.out.println(dataSourceSystemdb.getName());
    }

    @Test
    public void testBusDB(){
        System.out.println(dataSourceBusdb.getUrl());
        System.out.println(dataSourceBusdb.getName());
        assertThat(dataSourceBusdb.getUsername()).isEqualTo("bcp_user");
        assertThat(dataSourceBusdb.getPassword()).isEqualTo("eteng");
        assertThat(dataSourceBusdb.getDriverClassName()).isEqualTo("com.mysql.jdbc.Driver");
        assertThat(dataSourceBusdb.getInitialSize()).isEqualTo(4);
        assertThat(dataSourceBusdb.getMaxActive()).isEqualTo(19);
        assertThat(dataSourceBusdb.getMaxWait()).isEqualTo(50000);
    }
}
