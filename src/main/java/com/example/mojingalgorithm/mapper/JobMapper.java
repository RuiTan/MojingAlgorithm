package com.example.mojingalgorithm.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mojingalgorithm.pojo.Job;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * @author Administrator
 */
@Mapper
public interface JobMapper extends BaseMapper<Job> {
    @Select("select max(_oa) as _oa, max(_pre) as _pre, max(_recall) as _recall from job ${ew.customSqlSegment}")
    Job selectMaxMetrics(@Param("ew") Wrapper wrapper);
}
