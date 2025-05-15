package com.yjx.web.domain.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentVo {
    private Integer id;

    private Integer userId;

    private String name;

    private String gender;

    private Integer age;

    private Integer classId;

    private String className;

    private String contact;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
