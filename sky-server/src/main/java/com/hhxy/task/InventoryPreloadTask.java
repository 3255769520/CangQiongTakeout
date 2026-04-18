package com.hhxy.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hhxy.entity.Dish;
import com.hhxy.mapper.DishMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Slf4j
public class InventoryPreloadTask {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private DishMapper dishMapper;

    @PostConstruct
    public void initInventory() {
        log.info("开始预热秒杀商品库存...");
        List<Dish> dishList = dishMapper.selectList(
            new LambdaQueryWrapper<Dish>().eq(Dish::getStatus, 1)
        );

        for (Dish dish : dishList) {
            String key = "seckill:stock:" + dish.getId();
            Integer stock = dish.getStock() != null ? dish.getStock() : 0;
            redisTemplate.opsForValue().set(key, stock);
        }
        log.info("库存预热完成，共预热 {} 款商品。", dishList.size());
    }
}
