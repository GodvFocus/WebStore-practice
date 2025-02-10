package com.sky.mapper;

import com.sky.entity.SetmealDish;
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

    /**
     * 新增套餐内菜品
     * @param setmealDishList
     */
    void insertSetmealDish(List<SetmealDish> setmealDishList);

    /**
     * 批量删除套餐菜品关系
     * @param ids
     */
    void deleteByIds(List<Long> ids);
}
