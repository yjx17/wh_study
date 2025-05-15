package com.yjx.web.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学生信息表
 * </p>
 *
 * @author yjx
 * @since 2025-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("student_info")
public class StudentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID（关联user.id，唯一，一个学生对应一个用户）
     */
    private Integer userId;

    /**
     * 学生真实姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 年龄（0-127岁）
     */
    private Integer age;

    /**
     * 班级ID（关联class.id）
     */
    private Integer classId;

    /**
     * 联系方式（电话/微信）
     */
    private String contact;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
