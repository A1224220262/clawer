package com.zhy.service;

import com.zhy.domain.Barrage;
import com.zhy.domain.People;
import com.zhy.domain.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;


@Component
public class AllPipeline implements Pipeline {
@Autowired
private AllServiceImpl allService;
    final Integer PID = 2;
    @Override
    public void process(ResultItems resultItems, Task task) {
        //获取对象
        People people = resultItems.get("people");
        Video video = resultItems.get("video");
        List<Barrage> barrageList = resultItems.get("barrageList");
        //判断是否为空
        if(people != null){
            //不为空 保存
            people.setpId(PID);
            //System.out.println("-------------保存people");
            this.allService.saveP(people);
        }
        if(video != null){
            //video.setpId(this.allService.findPeopleByName(people.getUname()).getpId());
            //不为空 保存
            video.setPid(PID);
            //System.out.println("-------------保存video");
            this.allService.saveV(video);
        }
        if (barrageList!=null){
            for (int i = 0; i < barrageList.size(); i++) {
               // (ArrayList)barrageList.
                Barrage barrage = barrageList.get(i);
                barrage.setvId(video.getVid());
            }
            //System.out.println("-------------保存barrage");
            this.allService.saveB(barrageList);
        }

    }


}
