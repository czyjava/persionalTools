package com.czy.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.czy.crawler.TitleExtract;

public class IsHe {
	static Map<String, Integer> uti = new HashMap<String, Integer>();

	static String[] regs = { "^http://([\\w-]*\\.)*gd.sina.com.cn/", "^http://([\\w-]*\\.)*gongyi.sina.com.cn/",
			"^http://([\\w-]*\\.)*green.sina.com.cn/", "^http://([\\w-]*\\.)*blog.sina.com.cn/",
			"^http://([\\w-]*\\.)*club.([\\w-]*\\.)*sina.com.cn/",
			"^http://([\\w-]*\\.)*bbs.([\\w-]*\\.)*sina.com.cn/",
			"^http://([\\w-]*\\.)*forum.([\\w-]*\\.)*sina.com.cn/", "^http://([\\w-]*\\.)*news.sina.com.cn/",
			"^http://([\\w-]*\\.)*quan.sohu.com/ ", "^http://([\\w-]*\\.)*pic.news.sohu.com/",
			"^http://([\\w-]*\\.)*club.([\\w-]*\\.)*sohu.com/", "^http://([\\w-]*\\.)*gongyi.sohu.com/",
			"^http://([\\w-]*\\.)*blog.sohu.com/", "^http://([\\w-]*\\.)*news.sohu.com/",
			"^http://([\\w-]*\\.)*gd.qq.com/", "^http://([\\w-]*\\.)*gongyi.qq.com/",
			"^http://([\\w-]*\\.)*club.([\\w-]*\\.)*qq.com/", "^http://([\\w-]*\\.)*bbs.([\\w-]*\\.)*qq.com/",
			"^http://([\\w-]*\\.)*mygd.qq.com/", "^http://([\\w-]*\\.)*news.qq.com/",
			"^http://([\\w-]*\\.)*view.163.com/", "^http://([\\w-]*\\.)*gov.163.com/",
			"^http://([\\w-]*\\.)*gongyi.163.com/", "^http://([\\w-]*\\.)*blog.163.com/",
			"^http://([\\w-]*\\.)*bbs.([\\w-]*\\.)*163.com/", "^http://([\\w-]*\\.)*news.163.com/",
			"^http://([\\w-]*\\.)*gongyi.ifeng.com/", "^http://([\\w-]*\\.)*blog.ifeng.com/",
			"^http://([\\w-]*\\.)*bbs.ifeng.com/", "^http://([\\w-]*\\.)*news.ifeng.com/" };

	static Pattern[] ps = new Pattern[regs.length];

	static {
		for (int i = 0; i < regs.length; i++) {
			ps[i] = Pattern.compile(regs[i]);
		}
	}

	public static boolean isHe(String url) throws IOException {
		TitleExtract t = new TitleExtract(url);
		for (Pattern p : ps) {
			Matcher m = p.matcher(url);
			if (m.find()) {
				String gz = ConfigUtil.getGzByPattern(p.pattern());

				Integer index = uti.get(p.pattern());
				if (index == null) {
					uti.put(p.pattern(), 1);
				} else {
					uti.put(p.pattern(), ++index);
				}

				if (gz != null && !gz.trim().equals("") && (index == null || index < 10)) {
					String[] items = gz.split("\\+");
					int length = items.length;

					if (length == 2) {
						String key = items[0];
						String value = items[1];
						t.extractTitle(key, value);
						if (t.getTitle() != null)
							System.out.println(url + "===" + gz + " === " + t.getTitle());
					} else if (length == 3) {

						String key = items[0];
						String value = items[1];
						String tagName = items[2];
						t.getFirst(key, value, tagName);
						if (t.getTitle() != null)
							System.out.println(url + "===" + gz + " === " + t.getTitle());
					}
				}

				return true;
			}
		}

		return false;
	}

	public static boolean isHe2(String url) throws IOException {
		TitleExtract t = new TitleExtract(url);
		for (Pattern p : ps) {
			Matcher m = p.matcher(url);
			if (m.find()) {
				String gz = ConfigUtil.getGzByPattern(p.pattern());

				Integer index = uti.get(p.pattern());
				if (index == null) {
					uti.put(p.pattern(), 1);
				} else {
					uti.put(p.pattern(), ++index);
				}

				if (gz != null && !gz.trim().equals("") && (index == null || index < 10)) {
					String[] items = gz.split("\\+");
					int length = items.length;

					if (length == 2) {
						String key = items[0];
						String value = items[1];
						t.extractTitle(key, value);
						System.out.println(url + "===" + gz + " === " + t.getTitle());
					} else if (length == 3) {

						String key = items[0];
						String value = items[1];
						String tagName = items[2];
						t.getFirst(key, value, tagName);
						System.out.println(url + "===" + gz + " === " + t.getTitle());
					} else if (length == 4) {

						String key = items[0];
						String value = items[1];
						String tagName = items[2];
						t.getLast(key, value, tagName);
						System.out.println(url + "===" + gz + " === " + t.getTitle());
					} else {
						System.out.println("不住地咋了");
					}
				} else {
					System.out.println(url + "木有找到");
				}

				return true;
			}
		}

		return false;
	}
}
