package com.yjx.web.controller;


import com.yjx.web.domain.User;
import com.yjx.web.domain.Vo.UserVo;
import com.yjx.web.service.IUserService;
import com.yjx.web.utils.BaseContext;
import com.yjx.web.utils.Result;
import com.yjx.web.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 用户表（管理员/学生通用） 前端控制器
 * </p>
 *
 * @author yjx
 * @since 2025-05-09
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        boolean exists = userService.lambdaQuery().eq(User::getUsername, user.getUsername()).exists();
        if (exists) return Result.error("用户名已存在");
        userService.save(user);
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        User dbUser = userService.lambdaQuery()
                .eq(User::getUsername, user.getUsername())
                .eq(User::getPassword, user.getPassword())
                .one();
        if (dbUser == null) return Result.error("用户名或密码错误");
        String token = TokenUtils.generateToken(String.valueOf(dbUser.getId()), dbUser.getRole());
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(dbUser, userVo);
        userVo.setToken(token);
        return Result.success(userVo);
    }

    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody Map<String, String> params) {
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");

        String userId = BaseContext.getCurrentId();

        // 查询数据库中的用户
        User user = userService.getById(userId);
        if (user == null) return Result.error("用户不存在");

        if (!user.getPassword().equals(oldPassword)) {
            return Result.error("原密码错误");
        }
        user.setPassword(newPassword);
        userService.updateById(user);
        return Result.success("密码修改成功");
    }

    @PostMapping("/admin/resetPassword/{id}")
    public Result resetPassword(@PathVariable String id) {
        User user = userService.lambdaQuery().eq(User::getId, id).one();
        if (user == null) return Result.error("用户不存在");
        user.setPassword("123456");
        userService.updateById(user);
        return Result.success("密码重置成功");
    }

}
