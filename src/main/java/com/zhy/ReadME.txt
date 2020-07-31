--建表语句
CREATE TABLE IF NOT EXISTS `video`(
   `v_id` INT UNSIGNED AUTO_INCREMENT,
   `p_id` INT NOT NULL,
   `vname` VARCHAR(40) NOT NULL,
	 `palynum` INT NOT NULL,
   `numbar` INT NOT NULL,
   PRIMARY KEY ( `v_id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
--另外两表类似 详见domain包对应实体类

功能
爬取bilibili个人信息  以及 收藏视频 收藏视屏弹幕

结构
webmagic + selenium +spring boot

applicable 为程序入口
webmagic 管理爬虫任务
使用selenium登录 lioginTest类
bar 获取弹幕

扩展
使用登录网站获取视频  可获取更多个人有效信息 （会限制爬虫效率）
爬取内容可以更多 例如视频评论 标签等

个人实力时间有限
预期做获取弹幕，评论+视频标签
使用分词 获取经常出现词汇
对词汇分类（聚类） 评分
可以从侧面描绘出账号使用者 喜好的网络碎片信息
得出使用者某一方面的性格（例如：学习视频多 弹幕 评论关于学习的词汇量会很大~~）

