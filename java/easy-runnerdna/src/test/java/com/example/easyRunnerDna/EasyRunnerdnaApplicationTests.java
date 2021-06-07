package com.example.easyRunnerDna;

import com.example.easyRunnerDna.entity.SportInfo;
import com.example.easyRunnerDna.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EasyRunnerdnaApplicationTests {

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void hello() {
        SportInfo sportInfo= new SportInfo();
        sportInfo.setAccelerate("123");
        redisUtil.lSet("sportInfo",sportInfo);
        System.out.println(redisUtil.lGet("sportInfo", 0, -1));
    }

}
