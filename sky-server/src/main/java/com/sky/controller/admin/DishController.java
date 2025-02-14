package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增菜品")
    public Result addDish(@RequestBody DishDTO dishDTO){
        log.info("新增菜品：{}",dishDTO);
        dishService.addDish(dishDTO);
        return Result.success();
    }

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询：{}",dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 删除菜品
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("菜品删除")
    public Result deleteDish(@RequestParam List<Long> ids){
        log.info("删除{}菜品",ids);
        dishService.deleteDish(ids);
        return Result.success();
    }

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> findDishById(@PathVariable Long id){
        log.info("根据id查询菜品:{}", id);
        DishVO dishVO = dishService.findDishById(id);
        return Result.success(dishVO);
    }

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> findByCategoryId(Long categoryId){
        log.info("根据分类id查询菜品{}",categoryId);
        List<Dish> dishList = dishService.findByCategoryId(categoryId);
        return Result.success(dishList);
    }

    /**
     * 修改菜品信息
     * @param dishDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改菜品信息")
    public Result updateDish(@RequestBody DishDTO dishDTO){
        log.info("修改菜品信息：{}", dishDTO);
        dishService.updateWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * 启售停售菜品
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启售停售菜品")
    public Result StartOrStopDish(@PathVariable Integer status, Long id){
        log.info("改变菜品id:{}为状态:{}", id, status);
        dishService.StartOrStopDish(id, status);
        return Result.success();
    }
}
