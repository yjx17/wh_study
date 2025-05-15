package com.yjx.web.mapper;

import com.yjx.web.domain.StudentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjx.web.domain.Vo.StudentVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 学生信息表 Mapper 接口
 * </p>
 *
 * @author yjx
 * @since 2025-05-09
 */
public interface StudentInfoMapper extends BaseMapper<StudentInfo> {

    List<StudentVo> selectStudentWithClass(@Param("name") String name);

}
