package com.czy.util;

import java.util.HashSet;
import java.util.Set;

/**
 * 已访问队列
 * @author czy
 */
public class LinkQueue {
	//待访问URL
	//private Queue queue = new Queue();
	//已访问队列
	Set<String> visied = new HashSet<String>();
	
	//已访问队列是否为空
	public boolean isEmpty(){
		return visied.isEmpty();
	}
	
	//入队列
	public void add(String url){
		visied.add(url);
	}
	
	//出队列
	public void remove(String url){
		visied.remove(url);
	}
	
	//是否包含
	public boolean contains(String url){
		return visied.contains(url);
	}
}
