package com.reggie.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一结果返回类
 *
 * @param <T>
 * @author NewAdmin
 */
@Data
public class Result<T> {
    /**
     * 返回的状态码
     * 1表示成功，其余为失败
     */
    private Integer code;
    /**
     * 返回的描述信息
     */
    private String msg;
    /**
     * 返回的数据
     */
    private T data;
    /**
     * 用来存储额外的数据
     */
    private Map<String, Object> map = new HashMap<>();

    public static <T> Result<T> success(T object) {
        Result<T> r = new Result<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> r = new Result<>();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public Result<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
