package com.czy.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 待访问的URL
 * @author czy
 */
public class Queue {
	List<String> unVisied = new ArrayList<String>();
	
	//待访问队列是否为空
	public boolean isEmpty(){
		return unVisied.isEmpty();
	}
	
	//是否包含某链接
	public boolean contains(String url){
		return unVisied.contains(url);
	}
	
	//入队列
	public void enQueue(String url){
		if(!contains(url))
			unVisied.add(url);
	}
	
	//出队列
	public String unQueue(){
		return unVisied.remove(0);
	}
	
	//清空队列
	public void clear(){
		unVisied.clear();
	}
	
	//打印队列
	public void print(){
		for(String url : unVisied){
			System.out.println(url);
		}
	}
}
