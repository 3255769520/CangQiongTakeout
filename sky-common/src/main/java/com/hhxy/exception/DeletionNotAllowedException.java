package com.hhxy.exception;

/**
 * 业务异常：删除失败异常
 */
public class DeletionNotAllowedException extends BaseException {
    public DeletionNotAllowedException(String msg) {
        super(msg);
    }
}