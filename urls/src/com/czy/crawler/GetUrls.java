package com.czy.crawler;

import java.io.IOException;
import java.util.List;

import com.czy.util.IsHe;
import com.czy.util.LinkQueue;
import com.czy.util.Queue;

public class GetUrls {
	String[] initUrls = { "http://news.ifeng.com/a/20141019/42241452_0.shtml",
			"http://news.ifeng.com/a/20141020/42243241_0.shtml", "http://gongyi.ifeng.com/a/20141019/40840609_0.shtml",
			"http://gongyi.ifeng.com/a/20141016/40838514_0.shtml",
			"http://blog.ifeng.com/article/34257368.html?touping", "http://bbs.ifeng.com/viewthread.php?tid=18354010",
			"http://blog.ifeng.com/article/34257368.html?touping", "http://bbs.ifeng.com/viewthread.php?tid=18307261",
			"http://bbs.ifeng.com/viewthread.php?tid=17110383", "http://news.qq.com/a/20141018/021384.htm",
			"http://news.qq.com/a/20141018/018180.htm", "http://mygd.qq.com/t-507582-1.htm",
			"http://mygd.qq.com/t-527542-1.htm", "http://gongyi.qq.com/a/20141017/016079.htm",
			"http://club.money.sohu.com/licai/thread/2ot41j98jtl",
			"http://bj.bbs.house.sina.com.cn/thread-5926510517546929667.html",
			"http://news.163.com/14/1017/14/A8P0AEIR0001124J.html",
			"http://gov.163.com/14/1016/23/A8NF9HNT00234IG8.html",
			"http://gongyi.163.com/14/1010/13/A86TPMCB009363EC.html",
			"http://blog.163.com/luweibing@yeah/blog/static/118817055201491605151225/?touping",
			"http://guanlizheshuyouhui.blog.163.com/blog/static/193048176201491795453995/?touping",
			"http://hujuezhao.blog.163.com/blog/static/170592566201491671958294/?touping",
			"http://bbs.news.163.com/bbs/society/463600587.html",
			"http://gd.sina.com.cn/news/ga/2014-10-20/0750134929.html",
			"http://gd.sina.com.cn/news/m/2014-10-19/0752134726.html",
			"http://eat.gd.sina.com.cn/ss/2014-10-17/09321557.html",
			"http://green.sina.com.cn/news/roll/2014-09-09/095530812664.shtml",
			"http://blog.sina.com.cn/s/blog_46cf47710102v7wm.html?tj=1",
			"http://club.eladies.sina.com.cn/thread-6047777-1-1.html",
			"http://forum.sports.sina.com.cn/thread-2147984-1-1.html",
			"http://pic.news.sohu.com/911196/911317/group-612361.shtml",
			"http://gongyi.sohu.com/20141017/n405199477.shtml", "http://suguojing.blog.sohu.com/306155616.html",
			"http://club.news.sohu.com/zz0082/thread/2p49m8w6wic", "http://gd.qq.com/a/20141020/004541.htm",
			"http://gongyi.qq.com/a/20141017/016662.htm", "http://mygd.qq.com/t-529186-1.htm",
			"http://bbs.news.qq.com/t-1867375-1.htm", "http://bbs.news.qq.com/t-2043486-1.htm",
			"http://club.auto.qq.com/t-782857-1.htm", "http://gongyi.163.com/14/1010/13/A86TPMCB009363EC.html",
			"http://bbs.news.163.com/bbs/society/462486837.html",
			"http://gongyi.ifeng.com/a/20141020/40841088_0.shtml",
			"http://blog.163.com/luweibing@yeah/blog/static/118817055201491605151225/?touping" };

	private Queue queue = new Queue();
	private LinkQueue linkQueue = new LinkQueue();
	private UrlParse parse = new UrlParse();

	/** 初始化待访问队列*/
	private void initQueue() {
		queue.clear();

		for (String url : initUrls) {
			queue.enQueue(url);
		}
	}

	@SuppressWarnings("unused")
	private void crawler() throws IOException {
		while (!queue.isEmpty()) {
			String url = queue.unQueue();

			List<String> us = parse.parse(url);
			linkQueue.add(url);

			//System.out.println("已爬取： " + url);

			for (String u : us) {
				if (!linkQueue.contains(u) && IsHe.isHe(u)) {
					queue.enQueue(u);
				}
			}
		}
	}

	private void crawler2() throws IOException {
		while (!queue.isEmpty()) {
			String url = queue.unQueue();

			@SuppressWarnings("unused")
			boolean dd = IsHe.isHe2(url);
			//System.out.println(dd);

		}
	}

	public static void main(String[] args) {
		GetUrls gu = new GetUrls();
		gu.initQueue();
		try {
			gu.crawler2();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//gu.printQueue();
	}
}
