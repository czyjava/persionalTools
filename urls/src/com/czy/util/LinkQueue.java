package com.czy.util;

import java.util.HashSet;
import java.util.Set;

/**
 * �ѷ��ʶ���
 * @author czy
 */
public class LinkQueue {
	//������URL
	//private Queue queue = new Queue();
	//�ѷ��ʶ���
	Set<String> visied = new HashSet<String>();
	
	//�ѷ��ʶ����Ƿ�Ϊ��
	public boolean isEmpty(){
		return visied.isEmpty();
	}
	
	//�����
	public void add(String url){
		visied.add(url);
	}
	
	//������
	public void remove(String url){
		visied.remove(url);
	}
	
	//�Ƿ����
	public boolean contains(String url){
		return visied.contains(url);
	}
}
