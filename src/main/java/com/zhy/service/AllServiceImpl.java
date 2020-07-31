package com.zhy.service;

import com.zhy.dao.BarrageDao;
import com.zhy.dao.PeopleDao;
import com.zhy.dao.VideoDao;
import com.zhy.domain.Barrage;
import com.zhy.domain.People;
import com.zhy.domain.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
public class AllServiceImpl implements AllService {


    @Autowired
    private PeopleDao peopleDao;
    @Autowired
    private VideoDao videoDao;
    @Autowired
    private BarrageDao barrageDao;

    @Override
    @Transactional//事务管理
    public void saveP(People people) {
        //先从数据库查询数据查重
        People p = people;
        List<People> list = this.findPeople(p);
        if (list.size()==0){
            this.peopleDao.saveAndFlush(people);
        }
    }
    @Override
    @Transactional
    public void saveV(Video video) {
        //先从数据库查询数据查重
        Video v = video;
        List<Video> list = this.findVideo(v);
        if (list.size()==0){
            this.videoDao.saveAndFlush(video);
        }
    }
    @Override
    @Transactional
    public void saveB(List<Barrage> barrage) {
        this.barrageDao.saveAll(barrage);
    }

    @Override
    public List<People> findPeople(People people) {
        Example example = Example.of(people);
        List<People> list = this.peopleDao.findAll(example);
        return  list;
    }


    @Override
    public List<Video> findVideo(Video video) {
        Example example = Example.of(video);
        List<Video> list = this.videoDao.findAll(example);
        return  list;
    }

    @Override
    public List<Barrage> findBarrage(Barrage barrage) {
        Example example = Example.of(barrage);
        List<Barrage> list = this.barrageDao.findAll(example);
        return  list;
    }

    @Override
    public People findPeopleByName(String Puname) {

        return this.peopleDao.findPeople(Puname);
    }

    @Override
    public Video findVideoByName(Video video) {
        return null;
    }

    @Override
    public Barrage findBarrageID(Barrage barrage) {
        return null;
    }

}
