package com.yjx.web.service.impl;

import com.yjx.web.domain.StudentInfo;
import com.yjx.web.mapper.StudentInfoMapper;
import com.yjx.web.service.IStudentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生信息表 服务实现类
 * </p>
 *
 * @author yjx
 * @since 2025-05-09
 */
@Service
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoMapper, StudentInfo> implements IStudentInfoService {

}
