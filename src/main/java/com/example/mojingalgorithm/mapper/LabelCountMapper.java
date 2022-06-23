package com.example.mojingalgorithm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mojingalgorithm.pojo.LabelCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface LabelCountMapper extends BaseMapper<LabelCount> {
    @Update("update label_count set label='${new_label}' where label='${label}'")
    void updateLabel(@Param("new_label") String newLabel, @Param("label") String label);
}
