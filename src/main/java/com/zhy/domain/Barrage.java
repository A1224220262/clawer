package com.zhy.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Barrage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bId;//弹幕id
    private String vId;//视频id
    private String str;//弹幕内容

    @Override
    public String toString() {
        return "Barrage{" +
                "bId=" + bId +
                ", vId='" + vId + '\'' +
                ", str='" + str + '\'' +
                '}';
    }

    public Barrage(Integer bId, String vId, String str) {
        this.bId = bId;
        this.vId = vId;
        this.str = str;
    }

    public Barrage() {
    }

    public Integer getbId() {
        return bId;
    }

    public void setbId(Integer bId) {
        this.bId = bId;
    }

    public String getvId() {
        return vId;
    }

    public void setvId(String vId) {
        this.vId = vId;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
