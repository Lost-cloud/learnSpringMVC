package com.king.repository;

import com.king.config.RootConfig;
import com.king.config.data.RedisConfig;
import com.king.domain.RedPacket;
import com.king.domain.UserRedPacket;
import com.king.service.RedPacketService;
import com.king.service.RedisRedPacketService;
import com.king.service.UserRedPacketService;
import com.king.service.serviceImpl.RedisRedPacketServiceImpl;
import com.king.service.serviceImpl.UserRedPacketServiceImpl;
import com.king.util.DateUtil;
import com.king.web.WebConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import redis.clients.jedis.Jedis;

import javax.sql.DataSource;
import java.sql.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class,WebConfig.class})
public class RedPacketDaoTest {

    @Autowired
    private RedPacketDao redPacketDao;

    @Autowired
    private UserRedPacketService userRedPacketService;

    @Autowired
    private RedPacketService redPacketService;

    @Autowired
    private RedisRedPacketService service;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DataSource dataSource;

    private static final String PREFIX = "red_packet_list_";
    private static final int TIME_SIZE = 1000;

    private Logger logger;

    @Before
    public void setUp() throws Exception {
        logger = LogManager.getLogger();
    }

    @Test
    public void testRedPacketGet() {
        logger.info(redPacketDao.getRedPacket(1L));
    }

    @Test
    public void testGrabRedPacket() {
        //30000个用户开始抢红包，参数：抢红包Id，用户Id
        //抢到返回1，失败返回0
        RedPacket redPacket = redPacketService.getRedPacket(1L);
        for (int i = 1; i <= 30000; i++) {
            int finalI = i;
            Thread t = new Thread(() -> {
                    long result = userRedPacketService.grabRedPacketByRedis(redPacket.getId(), (long) finalI);
                    logger.info("抢红包 " + (result == 0 ? "失败" : "成功"));
            });
            t.run();
        }
    }

    @Before
    public void initRedisData() {
        DefaultRedisScript defaultRedisScript=new DefaultRedisScript();
        defaultRedisScript.setLocation(new ClassPathResource("initData.lua"));
        Jedis jedis = (Jedis) redisTemplate.getConnectionFactory().getConnection().getNativeConnection();
        String script = jedis.scriptLoad(defaultRedisScript.getScriptAsString());
        jedis.evalsha(script);
    }

    @Test
    public void testSaveDataBase() {
        service.saveUserRedPacketByRedis(1L,10d);
    }

    @Test
    public void testLua() {
        DefaultRedisScript redisScript=new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource("grabRedPacket.lua"));
        String script = redisScript.getScriptAsString();
        Jedis jedis = (Jedis) redisTemplate.getConnectionFactory().getConnection().getNativeConnection();
        String sha1 = jedis.scriptLoad(script);
        Object object = jedis.evalsha(sha1);
        logger.info("the info is "+object);
    }

    @Test
    public void testJdbc() {
        initBatch();
    }

    private void initBatch() {
        UserRedPacket userRedPacket=new UserRedPacket();
        userRedPacket.setRedPacketId( 1l);
        userRedPacket.setNote("test");
        userRedPacket.setUserId((long) 1);
        userRedPacket.setAmount(10.0);
        userRedPacket.setGrabTime(new Timestamp(System.currentTimeMillis()));
        testBatch(userRedPacket);
    }

    private void testBatch(UserRedPacket userRedPacket) {
        String sql1 = "UPDATE t_red_packet SET stock=stock-1 WHERE id="+userRedPacket.getRedPacketId();
        String sql2 = "INSERT INTO t_user_red_packet (red_packet_id, user_id, " +
                "amount, grab_time, note) "+
                "VALUES ("+userRedPacket.getRedPacketId()+","+
                userRedPacket.getUserId()+","+
                userRedPacket.getAmount()+","+
                "'"+ userRedPacket.getGrabTime()+"' , "+
                "'"+userRedPacket.getNote()+"')";
        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            for (int i = 0; i < 20000; i++) {
                statement.addBatch(sql1);
                statement.addBatch(sql2);
            }
            statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(statement!=null&&!statement.isClosed()) statement.close();
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}