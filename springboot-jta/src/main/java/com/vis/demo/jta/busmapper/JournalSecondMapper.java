package com.vis.demo.jta.busmapper;

import com.vis.demo.jta.vo.JournalVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface JournalSecondMapper {
    List<JournalVO> getAll();
    JournalVO getOne(String id);
    void insertOne(@Param("journal") JournalVO journalVO);
    int updateOne(@Param("journal") JournalVO journalVO);
}
