package com.hhxy.result;

import lombok.Data;
import java.io.Serializable;

/**
 * 后端统一返回结果
 * @param <T>
 */
@Data // 重点：这个注解会自动生成 get/set 方法，但现在它会报红
public class Result<T> implements Serializable {    /*Serializable系列化 让对象可以压缩在网上传播 */
//    Serializable接口的作用
//    你看到的注释"让对象可以压缩在网上传播"解释得很好：
//    序列化：把Java对象转换成字节序列
//    反序列化：把字节序列恢复成Java对象
//    用途：网络传输、文件存储、分布式系统通信
//
//
    private Integer code; // 编码：1成功，0和其它数字为失败
    private String msg; // 错误信息
    private T data; // 数据

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 1;
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 1;
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result();
        result.msg = msg;
        result.code = 0;
        return result;
    }
}