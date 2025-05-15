package com.yjx.web.domain.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UserVo {

    private String token;

    private String username;

    private String password;


    private String role;

    private String userPic;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
