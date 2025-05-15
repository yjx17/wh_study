package com.yjx.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yjx.web.utils.Result;
import com.yjx.web.domain.ClassEntity; // ✅ 你的实体类
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yjx.web.service.IClassService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 班级表 前端控制器
 * </p>
 *
 * @author yjx
 * @since 2025-05-09
 */
@RestController
@RequestMapping("/class")
public class ClassController {
    @Autowired
    private IClassService classService;

    // 管理员创建班级
    @PostMapping("/add")
    public Result addClass(@RequestBody ClassEntity classEntity) {
        LambdaQueryWrapper<ClassEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClassEntity::getClassName, classEntity.getClassName());
        boolean exists = classService.count(wrapper) > 0;
        if (exists) {
            return Result.error("班级名称已存在");
        }
        classService.save(classEntity);
        return Result.success();
    }

    // 修改班级
    @PutMapping("/update")
    public Result updateClass(@RequestBody ClassEntity classEntity) {
        classService.updateById(classEntity);
        return Result.success();
    }

    // 删除班级
    @DeleteMapping("/{id}")
    public Result deleteClass(@PathVariable Integer id) {
        classService.removeById(id);
        return Result.success();
    }

    // 查询所有班级
    @GetMapping("/list")
    public Result listAll() {
        return Result.success(classService.list());
    }
}
