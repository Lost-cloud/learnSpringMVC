package com.king.repository;

import com.king.domain.RedPacket;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 根据版本号扣减红包数
     * @param id 红包id
     * @param version 版本
     * @return 返回更新记录条数
     */
    int decreaseRedPacketForVersion(@Param("id") Long id, @Param("version") Integer version);
}

