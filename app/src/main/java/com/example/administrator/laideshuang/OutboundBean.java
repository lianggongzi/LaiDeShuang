package com.example.administrator.laideshuang;

/**
 * Created by Administrator on 2018\11\15 0015.
 */

public class OutboundBean {
    String waibuNumber;
    String aoshiNumber;
    String fenyunNumber;
    String time;

    public String getWaibuNumber() {
        return waibuNumber;
    }

    public void setWaibuNumber(String waibuNumber) {
        this.waibuNumber = waibuNumber;
    }

    public String getAoshiNumber() {
        return aoshiNumber;
    }

    public void setAoshiNumber(String aoshiNumber) {
        this.aoshiNumber = aoshiNumber;
    }

    public String getFenyunNumber() {
        return fenyunNumber;
    }

    public void setFenyunNumber(String fenyunNumber) {
        this.fenyunNumber = fenyunNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "OutboundBean{" +
                "waibuNumber='" + waibuNumber + '\'' +
                ", aoshiNumber='" + aoshiNumber + '\'' +
                ", fenyunNumber='" + fenyunNumber + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
