package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     * 根据菜品id统计关联套餐数
     * @param dishId
     * @return
     */
    Integer countByDishId(Long dishId);
}
