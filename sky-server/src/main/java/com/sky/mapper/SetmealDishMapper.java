package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 根据套餐id查询关联菜品
     * @param id
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> getBySetmealId(Long id);

    /**
     * 根据id删除套餐关联的所有菜品
     * @param id
     */
    @Delete("delete from setmeal_dish where setmeal_id = #{id}")
    void deleteBySetmealId(Long id);
}
