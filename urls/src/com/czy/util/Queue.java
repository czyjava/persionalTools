package com.czy.util;

import java.util.ArrayList;
import java.util.List;

/**
 * �����ʵ�URL
 * @author czy
 */
public class Queue {
	List<String> unVisied = new ArrayList<String>();
	
	//�����ʶ����Ƿ�Ϊ��
	public boolean isEmpty(){
		return unVisied.isEmpty();
	}
	
	//�Ƿ����ĳ����
	public boolean contains(String url){
		return unVisied.contains(url);
	}
	
	//�����
	public void enQueue(String url){
		if(!contains(url))
			unVisied.add(url);
	}
	
	//������
	public String unQueue(){
		return unVisied.remove(0);
	}
	
	//��ն���
	public void clear(){
		unVisied.clear();
	}
	
	//��ӡ����
	public void print(){
		for(String url : unVisied){
			System.out.println(url);
		}
	}
}
