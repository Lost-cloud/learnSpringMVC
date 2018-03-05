package com.king.service;

import com.king.domain.UserRedPacket;

public interface UserRedPacketService {
    /**
     * 保存抢红包信息
     * @param redPacketId 红包编号
     * @param userId 抢红包用户编号
     * @return 影响条数
     */
    int grabRedPacket(Long redPacketId,Long userId);
}
