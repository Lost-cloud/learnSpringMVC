package com.king.repository;

import com.king.config.RootConfig;
import com.king.domain.UserRedPacket;
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

    private Logger logger;

    @Before
    public void setUp() throws Exception {
        logger = LogManager.getLogger();
    }

    @Test
    public void testRedPacketGet() {
        logger.info(redPacketDao.getRedPacket(1L));
        logger.info(userRedPacketDao.grabRedPacket(new UserRedPacket()));
    }
}