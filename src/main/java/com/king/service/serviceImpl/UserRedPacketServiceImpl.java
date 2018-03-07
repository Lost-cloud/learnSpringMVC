package com.king.service.serviceImpl;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.king.domain.RedPacket;
import com.king.domain.UserRedPacket;
import com.king.repository.RedPacketDao;
import com.king.repository.UserRedPacketDao;
import com.king.service.RedisRedPacketService;
import com.king.service.UserRedPacketService;
import com.king.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

@Service
public class UserRedPacketServiceImpl implements UserRedPacketService {

    @Autowired
    private  UserRedPacketDao userRedPacketDao;
    @Autowired
    private  RedPacketDao redPacketDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisRedPacketService redisRedPacketService=null;

    private static final int FAILED = 0;
    private String sha1=null;

    public UserRedPacketServiceImpl() {
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int grabRedPacket(Long redPacketId, Long userId) {
        long start = System.currentTimeMillis();
        while (true) {
            long end = System.currentTimeMillis();
            if (end - start > 100) return FAILED;
            //获取红包信息,同时记录version的初始值
            RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);
            //红包数大于0
            if (redPacket.getStock() > 0) {
                //判断version是否被改变
                int update = redPacketDao.decreaseRedPacketForVersion(redPacketId, redPacket.getVersion());
                //如果version改变了，证明被修改过，从而无法找到一开始version的数据
                //所以会返回0
                if (update == 0) {
                    continue;
                }
//            redPacketDao.decreaseRedPacket(redPacketId);
                UserRedPacket userRedPacket = new UserRedPacket();
                userRedPacket.setRedPacketId(redPacketId);
                userRedPacket.setUserId(userId);
                userRedPacket.setAmount(redPacket.getUnitAmount());
                userRedPacket.setNote("抢红包" + redPacketId);
                //插入抢红包信息
                return userRedPacketDao.grabRedPacket(userRedPacket);
            } else {
                //红包小于零返回0
                return FAILED;
            }
        }
    }

    @Override
    public Long grabRedPacketByRedis(Long redPacketId, Long userId) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("grabRedPacket.lua"));
        String args = userId + "-" + System.currentTimeMillis();
        Long result;
        Jedis jedis = (Jedis) redisTemplate.getConnectionFactory().getConnection().getNativeConnection();
        try {
            if(sha1==null) sha1 = jedis.scriptLoad(script.getScriptAsString());
            result = (Long) jedis.evalsha(sha1, 1, redPacketId + "", args);
            if (result == 2) {
                String unitAmountStr = jedis.hget("red_packet_" + redPacketId, "unit_amount");
                redisRedPacketService.saveUserRedPacketByRedis(redPacketId, Double.valueOf(unitAmountStr));
            }
        }finally {
            if (jedis != null && jedis.isConnected()) {
                jedis.close();
            }
        }
        return result;
    }
}
