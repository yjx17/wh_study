package com.yjx.web.utils;
import lombok.Data;

@Data
public class Result {
    private Integer code;
    private String msg;
    private Object data;

    public static Result success(Object data) {
        Result r = new Result();
        r.setCode(200);
        r.setData(data);
        r.setMsg("成功");
        return r;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(String msg) {
        Result r = new Result();
        r.setCode(400);
        r.setMsg(msg);
        return r;
    }
}
