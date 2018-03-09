package com.king.service;

public interface RedisRedPacketService {
    /**
     * 保存redis抢红包列表
     * @param redPacketId 要抢的红包编号
     * @param unitAmount 红包金额
     */
    void saveUserRedPacketByRedis(Long redPacketId, Double unitAmount);
}
