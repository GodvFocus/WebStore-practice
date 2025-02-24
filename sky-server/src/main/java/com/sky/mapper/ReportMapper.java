package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.Map;

@Mapper
public interface ReportMapper {
    /**
     * 根据日期统计营业额
     * @param map
     * @return
     */
    Double getByMap(Map map);
}
