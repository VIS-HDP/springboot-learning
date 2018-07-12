package com.vis.demo.jta.mapper;


import com.vis.demo.jta.vo.JournalVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JournalMapper {

    List<JournalVO> getAll();
    JournalVO getOne(String id);
    void insertOne(@Param("journal") JournalVO journalVO);
    int updateOne (@Param("journal")JournalVO journalVO);


}
