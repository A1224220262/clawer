package com.zhy.login;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class loginTest{

    @Test
    public void getcookies() throws Exception {
        String url = "https://space.bilibili.com/34403770/favlist";
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get(url); //
        // 等待浏览器加载完毕
        //System.out.println("--------------需要完成登录");
        Thread.sleep(30*1000);
        //webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        //中间完成登录操作
        //System.out.println("--------------登录");
        Set<Cookie> cookies = webDriver.manage().getCookies();

        baocun(cookies);
    }

    //把登录后得到的cookie序列化到硬盘中
    public static void baocun(Set<Cookie> cookies) throws Exception {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("d:\\4.txt"));
        objectOutputStream.writeObject(cookies);
    }

    //反序列化登录
    public WebDriver login (String url)throws Exception {
        //System.out.println("------------驱动开始");
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.default_directory", "d:\\");
        options.setExperimentalOption("prefs", prefs);
        WebDriver webDriver = new ChromeDriver();
        //System.out.println("------------获取cookies");
        //loginTest.getcookies();
        Set<Cookie> read = loginTest.read();

        //System.out.println("------------写入cookies");
        //String url = "https://space.bilibili.com/34403770/favlist";
        webDriver.get(url); //
        webDriver.manage().deleteAllCookies();
        //System.out.println("------------逐个写入cookies");
        for (Cookie cookie : read) {
            webDriver.manage().addCookie((Cookie) cookie);
        }
        // 等待浏览器加载完毕
        Thread.sleep(3*1000);
        //webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.navigate().refresh();
        // 等待浏览器加载完毕
        Thread.sleep(3*1000);
        return webDriver;//.getPageSource();
    }

    //读取硬盘cookie的操作
    public static Set<Cookie> read() throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("d:\\4.txt"));
        HashSet<Cookie> cookies = (HashSet<Cookie>) objectInputStream.readObject();
        return cookies;
    }

    //获取下一页
    public WebDriver getNpage (WebDriver webDriver){
        WebDriver webDriver1 = webDriver;
                if (webDriver1.findElement(By.cssSelector("div.fav-content.section ul.be-pager li.be-pager-next a")).getText().equals("下一页")) {
                    webDriver1.findElement(By.cssSelector("div.fav-content.section ul.be-pager li.be-pager-next a")).click();
                }else{
                    return null;
                }
        return webDriver1;
    }
}




