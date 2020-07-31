package com.zhy.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pId;//人物id
    private String uname;//人名
    private Integer lv;//等级
    private String fav;//收藏
    private String sign;//签名

    public People() {
    }

    public People(Integer pId, String uname, Integer lv, String fav, String sign) {
        this.pId = pId;
        this.uname = uname;
        this.lv = lv;
        this.fav = fav;
        this.sign = sign;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Integer getLv() {
        return lv;
    }

    public void setLv(Integer lv) {
        this.lv = lv;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "people{" +
                "pId=" + pId +
                ", uname='" + uname + '\'' +
                ", lv=" + lv +
                ", fav='" + fav + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
