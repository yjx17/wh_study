package com.yjx.web.service.impl;

import com.yjx.web.domain.User;
import com.yjx.web.mapper.UserMapper;
import com.yjx.web.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表（管理员/学生通用） 服务实现类
 * </p>
 *
 * @author yjx
 * @since 2025-05-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
