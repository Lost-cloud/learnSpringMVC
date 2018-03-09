package com.king.service.serviceImpl;

import com.king.domain.UserRedPacket;
import com.king.service.RedPacketService;
import com.king.service.RedisRedPacketService;
import com.king.service.UserRedPacketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Lazy
public class RedisRedPacketServiceImpl implements RedisRedPacketService {
    private static final String PREFIX = "red_packet_list_";
    private static final int TIME_SIZE = 1000;
    private final Logger logger = LogManager.getLogger();

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    @Qualifier("userRedPacketServiceImpl")
    private UserRedPacketService userRedPacketService=null;

    @Autowired
    private RedPacketService redPacketService;

    @Autowired
    private DataSource dataSource;

    @Override
    @Async
    public void saveUserRedPacketByRedis(Long redPacketId, Double unitAmount) {
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        logger.info("开始保存数据");
        long start = System.currentTimeMillis();
        BoundListOperations ops = redisTemplate.boundListOps(PREFIX + redPacketId);
        long size = ops.size();
        long times=size%TIME_SIZE==0?size/TIME_SIZE:(size/TIME_SIZE+1);
        int count=0;
        List<UserRedPacket> userRedPacketList=new ArrayList<>(TIME_SIZE);
        for (int i = 0; i < times; i++) {
            List userIdList;
            if (i == 0) {
                userIdList = ops.range(i * TIME_SIZE, (i + 1) * TIME_SIZE);
            } else {
                userIdList = ops.range(i * TIME_SIZE + 1, (i + 1) * TIME_SIZE);
            }
            userRedPacketList.clear();
            for (Object o : userIdList) {
                String args = (String) o;
                String[] arr=args.split("-");
                String userIdStr = arr[0];
                String timeStr = arr[1];

                UserRedPacket userRedPacket = new UserRedPacket();
                userRedPacket.setRedPacketId(redPacketId);
                userRedPacket.setUserId(Long.valueOf(userIdStr));
                userRedPacket.setAmount(unitAmount);
                userRedPacket.setGrabTime(new Timestamp(Long.valueOf(timeStr)));
                userRedPacket.setNote("抢红包" + redPacketId);
                userRedPacketList.add(userRedPacket);

            }
            //保存列表到数据库
            count += executeBatchByJdbc(userRedPacketList);
        }
        redisTemplate.delete(PREFIX + redPacketId);
        long end = System.currentTimeMillis();
        logger.info("保存数据结束，耗时 "+(end-start)+"毫秒,共"+count+" 条数据被保存");
    }

    private int executeBatch(List<UserRedPacket> userRedPacketList) {
        int count = 0;
        for (UserRedPacket userRedPacket : userRedPacketList) {
            redPacketService.decreaseRedPacket(userRedPacket.getId());
            userRedPacketService.grabRedPacket(userRedPacket.getRedPacketId(), userRedPacket.getUserId());
            count++;
        }
        return count;
    }

    private int executeBatchByJdbc(List<UserRedPacket> userRedPacketList) {
        Connection connection = null;
        Statement statement = null;
        int count[]=null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            for (UserRedPacket userRedPacket : userRedPacketList) {
                String sql1 = "UPDATE t_red_packet SET stock=stock-1 WHERE id="+userRedPacket.getRedPacketId();
                String sql2 = "INSERT INTO t_user_red_packet (red_packet_id, user_id, " +
                        "amount, grab_time, note) "+
                        "VALUES ("+userRedPacket.getRedPacketId()+","+
                        userRedPacket.getUserId()+","+
                        userRedPacket.getAmount()+","+
                        "'"+ userRedPacket.getGrabTime()+"' , "+
                        "'"+userRedPacket.getNote()+"')";
                statement.addBatch(sql1);
                statement.addBatch(sql2);
            }
            count = statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.getStackTrace();
        }finally {
            try {
                if (statement!=null&&!statement.isClosed())statement.close();
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        assert count != null;
        return count.length/2;
    }

}
