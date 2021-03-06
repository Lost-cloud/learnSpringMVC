package com.king.service;

import com.king.domain.RedPacket;

public interface RedPacketService {
    /**
     * 获取红包
     * @param id 编号
     * @return 红包信息
     */
    RedPacket getRedPacket(Long id);

    /**
     * 扣减红包
     * @param id 编号
     * @return 影响条数
     */
    int decreaseRedPacket(Long id);

    int decreaseRedPacketForVersion(Long id, Integer version);
}
