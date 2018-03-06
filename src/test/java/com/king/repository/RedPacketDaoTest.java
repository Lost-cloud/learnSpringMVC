package com.king.repository;

import com.king.config.RootConfig;
import com.king.domain.RedPacket;
import com.king.domain.UserRedPacket;
import com.king.service.RedPacketService;
import com.king.service.UserRedPacketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class})
public class RedPacketDaoTest {

    @Autowired
    private RedPacketDao redPacketDao;

    @Autowired
    private UserRedPacketDao userRedPacketDao;

    @Autowired
    private UserRedPacketService userRedPacketService;

    @Autowired
    private RedPacketService redPacketService;

    private Logger logger;

    @Before
    public void setUp() throws Exception {
        logger = LogManager.getLogger();
    }

    @Test
    public void testRedPacketGet() {
        logger.info(redPacketDao.getRedPacket(1L));
//        logger.info(userRedPacketDao.grabRedPacket(new UserRedPacket()));
    }

    @Test
    public void testGrabRedPacket() {
        //30000个用户开始抢红包，参数：抢红包Id，用户Id
        //抢到返回1，失败返回0
        for (int i = 1; i <= 30000; i++) {
            int finalI = i;
            RedPacket redPacket = redPacketService.getRedPacket(1L);
            Thread t = new Thread(() -> {
                if (redPacket.getStock() > 0) {
                    int update = redPacketDao.decreaseRedPacketForVersion(redPacket.getId(), redPacket.getVersion());
                    if (update == 0) {
                        return;
                    }
                    int result = userRedPacketService.grabRedPacket(1L, (long) finalI);
                    logger.info("抢红包 " + (result == 0 ? "失败" : "成功"));
                }
            });
            t.run();
        }
    }
}