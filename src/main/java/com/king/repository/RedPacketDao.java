package com.king.repository;

import com.king.domain.RedPacket;
import org.springframework.stereotype.Repository;

@Repository
public interface RedPacketDao {

    /**
     * 获取红包信息
     * @param id 红包Id
     * @return RedPacket对象
     */
    RedPacket getRedPacket(Long id);

    /**
     * 扣减红包数
     * @param id --红包
     * @return 更新记录的条数
     */
    int decreaseRedPacket(Long id);
}

