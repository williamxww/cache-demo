package com.bow.redis.synchronize;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RedisUpdateToDataBase {

    public boolean excuteUpdate(List<String> list) {
        // 更新数据库
        return true;
    }
}
