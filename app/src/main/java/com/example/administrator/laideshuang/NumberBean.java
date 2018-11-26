package com.example.administrator.laideshuang;

/**
 * Created by Administrator on 2018\11\14 0014.
 */

public class NumberBean {
    String waibuNumber;
    String aoshiNumber;
    String fenyunNumber;

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

    @Override
    public String toString() {
        return "NumberBean{" +
                "waibuNumber='" + waibuNumber + '\'' +
                ", aoshiNumber='" + aoshiNumber + '\'' +
                ", fenyunNumber='" + fenyunNumber + '\'' +
                '}';
    }
}
