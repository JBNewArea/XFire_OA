package com.test.service;

import com.aliyuncs.exceptions.ClientException;

public interface ISendService {
	
	public String SendMsg(String phone,String content)throws ClientException,InterruptedException;
	
	public Msg getMsg(Msg msg); 
}
