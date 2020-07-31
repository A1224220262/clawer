package com.zhy.service;


import com.zhy.domain.Barrage;
import com.zhy.domain.People;
import com.zhy.domain.Video;

import java.util.List;

public interface AllService {
    /**
     * 保存
     * @param people video barrage
     */
    public void saveP(People people);
    public void saveV(Video video);
    public void saveB(List<Barrage> barrage);

    /**
     * 按照条件查询
     * @param people video barrage
     * @return
     */
    public List<People> findPeople(People people);
    public List<Video> findVideo(Video video);
    public List<Barrage> findBarrage(Barrage barrage);

    /**
     * 查询 实体
     *
     * @param people video barrage
     * @return
     */


    public People findPeopleByName(String Puname);
    public Video findVideoByName(Video video);
    public Barrage findBarrageID(Barrage barrage);




}
