package com.king.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class UserRedPacket implements Serializable {
    private Long id;
    private Long redPacketId;
    private Long userId;
    private Double amount;
    private Timestamp grabTime;
    private String  note;

    @Override
    public String toString() {
        return "UserRedPacket{" +
                "id=" + id +
                ", redPacketId=" + redPacketId +
                ", userId=" + userId +
                ", amount=" + amount +
                ", grabTime=" + grabTime +
                ", note='" + note + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRedPacket that = (UserRedPacket) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRedPacketId() {
        return redPacketId;
    }

    public void setRedPacketId(Long redPacketId) {
        this.redPacketId = redPacketId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getGrabTime() {
        return grabTime;
    }

    public void setGrabTime(Timestamp grabTime) {
        this.grabTime = grabTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
