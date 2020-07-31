package com.zhy.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="video")
public class Video {

    @Id
    private String vid;//视频ID

    private Integer pid;//

    private String vname;

    private String palyNum;

    private Integer NumBar;

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getPalyNum() {
        return palyNum;
    }

    public void setPalyNum(String palyNum) {
        this.palyNum = palyNum;
    }

    public Integer getNumBar() {
        return NumBar;
    }

    public void setNumBar(Integer numBar) {
        NumBar = numBar;
    }

    @Override
    public String toString() {
        return "Video{" +
                "vid='" + vid + '\'' +
                ", pid=" + pid +
                ", vname='" + vname + '\'' +
                ", palyNum='" + palyNum + '\'' +
                ", NumBar=" + NumBar +
                '}';
    }

    public Video() {
    }

    public Video(String vid, Integer pid, String vname, String palyNum, Integer numBar) {
        this.vid = vid;
        this.pid = pid;
        this.vname = vname;
        this.palyNum = palyNum;
        NumBar = numBar;
    }
}
