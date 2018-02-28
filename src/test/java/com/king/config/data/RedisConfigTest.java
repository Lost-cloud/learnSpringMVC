package com.king.config.data;

import com.king.config.RootConfig;
import com.king.domain.Employee;
import com.king.domain.builder.EmployeeBuilder;
import com.king.domain.builder.EmployeeBuilderImpl;
import com.king.enums.SexEnum;
import com.king.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
public class RedisConfigTest {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Before
    public void setUp() {
    }

    @Test
    public void redisTemplate() {
        EmployeeBuilder employeeBuilder = new EmployeeBuilderImpl(new Employee());
        Employee unsaved = employeeBuilder.buildRealName("jack")
                .buildBirthday(DateUtil.buildDate(1994, 10, 4))
                .buildMobile("18617164782")
                .buildEmail("lost0000@outlook.com")
                .buildSex(SexEnum.MALE)
                .buildNote("by converter")
                .buildPosition("广东深圳")
                .getEmployee();
        SessionCallback sessionCallback=new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.opsForValue().set("employee",unsaved);
                return redisOperations.opsForValue().get("employee");
            }
        };
        Employee saved=(Employee) redisTemplate.execute(sessionCallback);
        System.out.println(saved);
    }
}