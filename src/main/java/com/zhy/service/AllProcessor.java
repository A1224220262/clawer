package com.zhy.service;

import com.zhy.domain.Barrage;
import com.zhy.domain.People;
import com.zhy.domain.Video;
import com.zhy.login.Bar;
import com.zhy.login.loginTest;
import org.jsoup.Jsoup;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AllProcessor implements PageProcessor {
    //我的收藏页
    private String url = "https://space.bilibili.com/34403770/favlist";
    private int pageNum = 0;
    private WebDriver webDriver = null;
    private People people = new People();

    private Site site = Site
            .me()
            .setCharset("utf-8")
            .setRetryTimes(3)
            .setSleepTime(1000)
            .setTimeOut(10000);

    // 用来存储cookie信息
    private Set<Cookie> cookies;

    public AllProcessor() throws Exception {
        //获取本地cookies
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("d:\\4.txt"));
        cookies = (HashSet<Cookie>) objectInputStream.readObject();
    }

    @Override
    public void process(Page page) {
        Html html = null;
        loginTest lt  = new loginTest();

        try {
            webDriver = lt.login(page.getUrl().toString());
        } catch (Exception e) {
            System.out.println("------------没有获取到webDriver");
        }

        //获取html
        html = new Html(webDriver.getPageSource());
        page.setHtml(html);
        //System.out.println("------------html获取成功");
        //System.out.println("------------没有获取到html");
        //控制台查看html页面
        //System.out.println(html);

        //获取解析页面  得到视频url
        List<Selectable> list =  html.css("div.fav-main div.fav-content.section ul.fav-video-list.clearfix.content li.small-item a").nodes();

        //System.out.println(list.size()+"--------------------------------------------");
        //判断url页面或者抓取页面
        if(list.size()==0){
            //数据页 解析获取弹幕 获取video信息
            this.saveVideo(page,people);
            //视屏信息获取玩
            webDriver.close();
        }else{
            //url页
            //解析页面 获取people url 加入任务队列
            this.savePeople(page,people);
            for(Selectable selectable:list){
                //url地址
                String vedio = selectable.links().toString();
                //System.out.println("---------------"+vedio);
                //加入任务队列
                page.addTargetRequest(vedio);
                //System.out.println("--------------任务已添加");
            }
            boolean flag = true;
            //循环解析 url 加入队列 翻页获取链接
            while (flag){
                if(webDriver.findElement(By.cssSelector("div.fav-content.section ul.be-pager li.be-pager-next a")).getText().equals("下一页")){
                    try {
                        webDriver = lt.getNpage(webDriver);
                        Thread.sleep(1000);
                        html = new Html(webDriver.getPageSource());
                        page.setHtml(html);
                    } catch (Exception e) {
                        System.out.println("-------------webDrive异常");
                    }
                    list =  html.css("div.fav-main div.fav-content.section ul.fav-video-list.clearfix.content li.small-item").nodes();
                    //获取video信息 url 加入任务队列
                    for(Selectable selectable:list){
                        //url地址
                        String vedio = selectable.links().toString();
                        //加入任务队列
                        page.addTargetRequest(vedio);
                        //System.out.println("-------------"+vedio);
                        //System.out.println("-------------下一页任务已添加");
                    }
                }else {
                    flag = false;
                }
            }
            webDriver.close();
        }
    }

    //保存弹幕
    private void saveBarrage(Page page){
        //创建视频对象
        Bar bar = new Bar();
        List<Barrage> barrageList = new ArrayList<Barrage>();
        String BV = page.getUrl().toString().split("BV")[1];
        try {
            barrageList = bar.getBarL(BV);
        }catch (IOException io){
            System.out.println("-------------没有弹幕");
        }
        page.putField("barrageList",barrageList);

        //解析页面
        //System.out.println("--------------弹幕页面"+html);

    }
    //保存用户
    private void savePeople(Page page,People people){
        //创建用户对象
        Html html = page.getHtml();
        //解析页面
        people.setUname(Jsoup.parse(html.css("span#h-name").toString()).text());
        //转为数字
        people.setLv(Integer.valueOf(html.css("div.h-basic a.h-level.m-level","lvl").toString()));
        people.setFav(Jsoup.parse(html.css("div.fav-item.cur span.num").toString()).text());
        people.setSign(html.css("div.h-basic-spacing h4.h-sign","title").toString());

        //保存结果
        page.putField("people",people);

        //存入数据库

        //System.out.println("--------------弹幕页面"+html);
        //System.out.println("people = " + people.toString());
        //System.out.println("--------------------------------------------------");

    }
    //保存视屏
    @Autowired
    private AllServiceImpl allService;
    private void saveVideo(Page page,People people){
        //创建视频对象
        Html html = page.getHtml();
        Video video = new Video();
        //解析页面
        video.setVname(Jsoup.parse(html.css("div#viewbox_report h1.video-title span.tit").toString()).text());
        video.setNumBar(Integer.valueOf(Jsoup.parse(html.css("div.bilibili-player-video-info div.bilibili-player-video-info-danmaku.player-tooltips-trigger span.bilibili-player-video-info-danmaku-number").toString()).text()));
        video.setPalyNum(html.css("div#viewbox_report div.video-data span.view","title").toString());
        video.setVid(page.getUrl().toString().split("BV")[1]);

        page.putField("video",video);

        //保存视屏弹幕
        this.saveBarrage(page);
        //System.out.println("video.toString() = " + video.toString());
    }
    @Override
    public Site getSite() {
        // 将获取到的cookie信息添加到webmagic中
        for (Cookie cookie : cookies) {
            site.addCookie(cookie.getName().toString(), cookie.getValue()
                    .toString());
        }
        return site;
    }

    @Autowired
    AllPipeline allPipeline;
    //任务启动后1秒开始执行 每隔100秒执行一次
    @Scheduled(initialDelay = 1000, fixedDelay = 1000 * 100000000)
    public void process() throws Exception {
        Spider.create(new AllProcessor())
                .addUrl(url)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(1000)))
                .thread(1)
                .addPipeline(this.allPipeline)
                .run();
    }
}
