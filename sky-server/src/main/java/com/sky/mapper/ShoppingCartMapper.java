package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /**
     * 条件查找购物车内对应数据
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> findShoppingCartStuff(ShoppingCart shoppingCart);

    /**
     * 更新购物车
     * @param cart
     */
    @Update("update shopping_cart set number = #{number} where id = #{id} ")
    void updateByCartId(ShoppingCart cart);

    /**
     * 新增购物车条项
     * @param shoppingCart
     */
    @Insert("insert into shopping_cart(name, image, user_id, dish_id, setmeal_id, dish_flavor, amount, create_time, number) " +
            "VALUES (#{name}, #{image}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{amount}, #{createTime}, #{number})")
    void insert(ShoppingCart shoppingCart);

    /**
     * 查看购物车
     * @param shoppingCart
     * @return
     */
    @Select("select * from sky_take_out.shopping_cart where user_id = #{userId}")
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 清空购物车
     * @param userId
     */
    @Delete("delete from sky_take_out.shopping_cart where user_id = #{userId}")
    void deleteByUserId(Long userId);

    /**
     * 删除单条购物车数据
     * @param cart
     */
    @Delete("delete from sky_take_out.shopping_cart where id = #{id}")
    void deleteById(ShoppingCart cart);

    /**
     * 批量添加购物车
     * @param cartList
     */
    void insertBatch(List<ShoppingCart> cartList);
}
