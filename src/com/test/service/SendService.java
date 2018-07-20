package com.test.service;

import java.util.HashMap;
import java.util.Map;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class SendService implements ISendService {

	public String SendMsg(String phone,String content) throws ClientException, InterruptedException {
		
		  Map<String, String> map = new HashMap<String, String>();
	        String templateCode = "SMS_125115886";
	        String signName = "江北新区行政审批局";
	        String paramString = "{\"vcode\":\"7788\"}";
	        
	        map.put("telphone", phone);
	        map.put("templateCode", templateCode);
	        map.put("signName", signName);
	        map.put("paramString", paramString);
	        //发短信
	        SendSmsResponse response = MessageUtil.sendSms(map);

	        System.out.println("BizId=" + response.getBizId());
	        Thread.sleep(3000L);
	        if(response.getCode() != null && response.getCode().equals("OK")) {
	        	System.out.println("请求成功");
	            QuerySendDetailsResponse querySendDetailsResponse = MessageUtil.querySendDetails(response.getBizId(),phone);
	            System.out.println("短信明细查询接口返回数据----------------");
	            System.out.println("Code=" + querySendDetailsResponse.getCode());
	            System.out.println("Message=" + querySendDetailsResponse.getMessage());
	            int i = 0;
	            System.out.println(querySendDetailsResponse.getSmsSendDetailDTOs());
	            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
	            {
	                System.out.println("SmsSendDetailDTO["+i+"]:");
	                System.out.println("Content=" + smsSendDetailDTO.getContent());
	                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
	                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
	                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
	                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
	                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
	                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
	                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
	            }
	            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
	            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());

	        }
		return content;
	}

	
	
	public Msg getMsg(Msg msg) {
		Msg _msg = new Msg();
		_msg.setPhone(msg.getPhone());
		_msg.setMsg(msg.getMsg());
 		return _msg;
	}

	
}
