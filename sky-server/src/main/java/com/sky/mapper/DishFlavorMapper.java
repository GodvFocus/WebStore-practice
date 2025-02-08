package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * 批量插入菜品口味数据
     * @param dishFlavors
     */
    void insertBatch(List<DishFlavor> dishFlavors);

    /**
     * 根据菜品id删除口味
     * @param id
     */
    @Delete("delete from sky_take_out.dish_flavor where dish_id = #{id}")
    void deleteById(Long id);

    /**
     * 根据菜品id获取口味
     * @param id
     * @return
     */
    @Select("select * from sky_take_out.dish_flavor where dish_id = #{id}")
    List<DishFlavor> getByDishId(Long id);

    /**
     * 根据菜品id批量删除口味
     * @param ids
     */
    void deleteByDishIds(List<Long> ids);
}
