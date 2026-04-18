package com.hhxy.controller.admin;

import com.hhxy.dto.SetmealDTO;
import com.hhxy.dto.SetmealPageQueryDTO;
import com.hhxy.result.PageResult;
import com.hhxy.result.Result;
import com.hhxy.service.SetmealService;
import com.hhxy.vo.SetmealVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Tag(name = "套餐相关接口")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @PostMapping
    @Operation(summary = "新增套餐")
    public Result<String> save(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐：{}", setmealDTO);
        setmealService.saveWithDish(setmealDTO);
        return Result.success("新增套餐成功");
    }

    @PostMapping("/status/{status}")
    @Operation(summary = "套餐起售停售")
    public Result<String> startOrStop(@PathVariable Integer status, Long id) {
        log.info("套餐起售停售：status={}, id={}", status, id);
        setmealService.startOrStop(status, id);
        return Result.success();
    }

    @GetMapping("/page")
    @Operation(summary = "套餐分页查询")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("套餐分页查询：{}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @PutMapping
    @Operation(summary = "修改套餐")
    public Result<String> update(@RequestBody SetmealDTO setmealDTO) {
        log.info("修改套餐：{}", setmealDTO);
        setmealService.updateWithDish(setmealDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据id查询套餐")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        log.info("根据id查询套餐：{}", id);
        SetmealVO setmealVO = setmealService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }

    @DeleteMapping
    @Operation(summary = "批量删除套餐")
    public Result<String> delete(@RequestParam List<Long> ids) {
        log.info("批量删除套餐：{}", ids);
        setmealService.deleteBatch(ids);
        return Result.success();
    }
}
