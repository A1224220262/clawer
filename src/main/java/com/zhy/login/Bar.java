package com.zhy.login;

import com.zhy.domain.Barrage;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Bar {

    public List<Barrage> getBarL(String BV) throws IOException {
        //弹幕 https://api.bilibili.com/x/player/pagelist?bvid=BV+信息
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault() ;
        HttpGet httpGet = new HttpGet("https://api.bilibili.com/x/player/pagelist?bvid=BV"+BV) ;//17b411u7mK

        CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpGet) ;
        HttpEntity httpEntity = httpResponse.getEntity() ;
        String en= EntityUtils.toString(httpEntity,"utf-8") ;
        //提取 cid
        //访问https://api.bilibili.com/x/v1/dm/list.so?oid=161951160

        //(?<=cid":).\d{1,} 161951160
        String con = "(?<=\"cid\":).\\d{1,}" ;
        Pattern ah = Pattern.compile(con);
        Matcher mr = ah.matcher(en);
        ArrayList<Barrage> barrageList = new ArrayList<Barrage>();
        while(mr.find()) {
            String id = mr.group() ;
            String newUrl = id.replace("cid=","") ;
            String x = newUrl.replace("&aid=","") ;
            //http://comment.bilibili.com/161951160.xml
            HttpGet httpGet1 = new HttpGet("http://comment.bilibili.com/"+x+".xml");//85152553
            CloseableHttpResponse httpResponse1 = closeableHttpClient.execute(httpGet1) ;
            HttpEntity httpEntity1 = httpResponse1.getEntity() ;
            String en1 = EntityUtils.toString(httpEntity1,"utf-8") ;
            System.out.println(en1);
            String c = "\">(.*?)<" ;
             Pattern a = Pattern.compile(c);
            Matcher m = a.matcher(en1);
            while(m.find()){
                String speak = m.group().replace("\">","") ;
                speak = speak.replace("<","") ;
                Barrage b = new Barrage();
                b.setStr(speak);
                barrageList.add(b);
                //检查弹幕
                System.out.println(speak);
                //返回
            }
        }
        return barrageList;
    }
}


