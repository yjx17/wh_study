package com.yjx.web.controller;


import com.yjx.web.domain.ClassEntity;
import com.yjx.web.domain.StudentInfo;
import com.yjx.web.domain.User;
import com.yjx.web.domain.Vo.StudentVo;
import com.yjx.web.mapper.ClassMapper;
import com.yjx.web.mapper.StudentInfoMapper;
import com.yjx.web.mapper.UserMapper;
import com.yjx.web.service.IClassService;
import com.yjx.web.service.IStudentInfoService;
import com.yjx.web.service.IUserService;
import com.yjx.web.utils.BaseContext;
import com.yjx.web.utils.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 学生信息表 前端控制器
 * </p>
 *
 * @author yjx
 * @since 2025-05-09
 */
@RestController
@RequestMapping("/student-info")
public class StudentInfoController {
    @Autowired
    private IStudentInfoService studentInfoService;
    @Autowired
    private StudentInfoMapper studentInfoMapper;
    @Autowired
    private IClassService classService;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private UserMapper userMapper;

    // 管理员新增学生信息（注意必须存在 user，且角色为 student）
    @PostMapping("/add")
    @Transactional
    public Result addStudent(@RequestBody StudentInfo studentInfo) {
        boolean exists = studentInfoService.lambdaQuery()
                .eq(StudentInfo::getName, studentInfo.getName())
                .exists();
        if (exists) return Result.error("该用户学生信息已存在");

        // 创建并保存用户信息
        User user = new User();
        user.setUsername(studentInfo.getName());
        user.setPassword("123456"); // 默认密码
        user.setRole("student");
        userMapper.insert(user);
        // 设置 studentInfo 的 userId 字段
        studentInfo.setUserId(user.getId());
        studentInfo.setClassId(studentInfo.getClassId());
        studentInfo.setGender(studentInfo.getGender());
        studentInfo.setAge(studentInfo.getAge());
        studentInfo.setContact(studentInfo.getContact());

        // 保存学生信息
        studentInfoMapper.insert(studentInfo);
        return Result.success("添加成功，初始密码为123456");
    }


    // 修改学生信息（管理员或学生本人）
    @PutMapping("/update")
    public Result updateStudent(@RequestBody StudentInfo studentInfo) {
        studentInfoService.updateById(studentInfo);
        return Result.success();
    }

    // 删除学生信息（管理员）
    @DeleteMapping("/{id}")
    public Result deleteStudent(@PathVariable Integer id) {
        studentInfoService.removeById(id);
        return Result.success();
    }

    // 查询所有学生（管理员）
    @GetMapping("/all")
    public Result listAll(@RequestParam(required = false) String name) {
        List<StudentVo> list = studentInfoMapper.selectStudentWithClass(name);
        return Result.success(list);
    }

    // 学生查看自己信息
    @GetMapping("/myInfo")
    public Result getMyInfo() {
        String id = BaseContext.getCurrentId();
        StudentInfo info = studentInfoService.lambdaQuery()
                .eq(StudentInfo::getUserId, id)
                .one();
        ClassEntity entity = classService.getById(info.getClassId());
        StudentVo vo = new StudentVo();
        BeanUtils.copyProperties(info, vo);
        vo.setClassName(entity.getClassName());
        return Result.success(vo);
    }
}
