package com.hhxy.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.hhxy.context.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始公共字段自动填充[INSERT]...");

        // 1. 获取当前登录用户的 ID (从我们的 ThreadLocal 储物柜拿)
        Long currentId = BaseContext.getCurrentId();

        // 2. 填充字段
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createUser", Long.class, currentId);
        this.strictInsertFill(metaObject, "updateUser", Long.class, currentId);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始公共字段自动填充[UPDATE]...");

        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "updateUser", Long.class, BaseContext.getCurrentId());
    }
}