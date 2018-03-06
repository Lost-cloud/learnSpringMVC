package com.king.service.serviceImpl;

import com.king.domain.RedPacket;
import com.king.domain.UserRedPacket;
import com.king.repository.RedPacketDao;
import com.king.repository.UserRedPacketDao;
import com.king.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRedPacketServiceImpl implements UserRedPacketService {


    private final UserRedPacketDao userRedPacketDao;
    private final RedPacketDao redPacketDao;
    private static final int FAILED = 0;

    @Autowired
    public UserRedPacketServiceImpl(UserRedPacketDao userRedPacketDao, RedPacketDao redPacketDao) {
        this.userRedPacketDao = userRedPacketDao;
        this.redPacketDao = redPacketDao;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public int grabRedPacket(Long redPacketId, Long userId) {
        //获取红包信息,同时记录version的初始值
        RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);
        //红包大于0
        if (redPacket.getStock() > 0) {
            //            判断version是否被改变
            int update = redPacketDao.decreaseRedPacketForVersion(redPacketId, redPacket.getVersion());
            //如果version改变了，证明被修改过，从而无法找到一开始version的数据
            //所以会返回0
            if (update == 0) {
                return FAILED;
            }
//            redPacketDao.decreaseRedPacket(redPacketId);
            UserRedPacket userRedPacket=new UserRedPacket();
            userRedPacket.setRedPacketId(redPacketId);
            userRedPacket.setUserId(userId);
            userRedPacket.setAmount(redPacket.getUnitAmount());
            userRedPacket.setNote("抢红包"+redPacketId);
            //插入抢红包信息
            return userRedPacketDao.grabRedPacket(userRedPacket);
        }
        //红包小于零返回0
        return FAILED;
    }
}
