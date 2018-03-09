package com.king.repository;

import com.king.domain.UserRedPacket;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRedPacketDao {
    /**
     * 插入抢红包信息
     * @param userRedPacket 抢红包信息
     * @return 影响记录数
     */
    int grabRedPacket(UserRedPacket userRedPacket);
}
