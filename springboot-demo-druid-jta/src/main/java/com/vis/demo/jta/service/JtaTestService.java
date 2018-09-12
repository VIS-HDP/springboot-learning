package com.vis.demo.jta.service;

import com.vis.demo.jta.busmapper.JournalSecondMapper;
import com.vis.demo.jta.mapper.JournalMapper;
import com.vis.demo.jta.vo.JournalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JtaTestService {
    @Autowired
    public JournalMapper journalMapper;
    @Autowired
    public JournalSecondMapper journalSecondMapper;

    //@Transactional(transactionManager = "xatx" ,propagation = Propagation.REQUIRED,rollbackFor = {java.lang.RuntimeException.class})
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {java.lang.RuntimeException.class})
    public int jtaTest(){
        JournalVO vo = new JournalVO();
        vo.setJournal_id("test_001");
        vo.setJournal_code("test_001");
        vo.setJournal_name("测试云刊_一天test");
        int rows = journalMapper.updateOne(vo);
        vo.setJournal_name("测试云刊_sencond");
        String[] tt = new String[2];
        System.out.println(tt[3]);
        rows += journalSecondMapper.updateOne(vo);
        System.out.println("更新记录数:"+rows);
        return rows;
    }
}
