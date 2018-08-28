package com.test.service;

import java.util.HashMap;
import java.util.Map;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class SendService implements ISendService {

	
	private String EMAILCODE="SMS_141582437";//邮件code
	private String SCHEDULECODE="SMS_142385534";//日程code
	private String TASKCODE = "SMS_142380576";//新任务code
	private String NOTICECODE ="SMS_142385539";//新闻code
	private String MEETINGCODE ="SMS_14235540";//会议code
	private String SUBFLOWCODE = "SMS_142945395";//流程提交code
	private String BACKFLOWCODE="SMS_142945405";//流程退回code
	private String FUSINESEFLOWCODE="SMS_142950401";//流程委托提醒code
	private String COPYFLOWCODE="SMS_142950398";//流程抄送提醒code
	private String OVERFLOWCODE="SMS_142945398";//流程结束提醒code
	private String PRESSCODE="SMS_142950397";//流程催办提醒code
	public String SendMsg(String phone,String content) throws ClientException, InterruptedException {
		    String templateCode = "";
		    String signName = "江北新区行政审批局";
		    String paramString = "";
	        System.out.println("手机号："+phone);
	        System.out.println("内容："+content);
		    if(content.indexOf("邮件")!=-1){
		    	templateCode = EMAILCODE;
		    	paramString = "{\"emailContent\":\"'"+content+"'\"}";
		    }else if(content.indexOf("日程")!=-1){
		    	templateCode = SCHEDULECODE;
		    	paramString = "{\"scheduleContent\":\"'"+content+"'\"}";
		    }else if(content.indexOf("任务")!=-1){
		    	templateCode = TASKCODE;
		    	paramString = "{\"taskContent\":\"'"+content+"'\"}";
		    }else if(content.indexOf("新闻")!=-1){
		    	templateCode = NOTICECODE;
		    	paramString = "{\"noticeContent\":\"'"+content+"'\"}";
		    }else if(content.indexOf("会议")!=-1){
		    	templateCode = MEETINGCODE;
		    	paramString = "{\"meetingContent\":\"'"+content+"'\"}";
		    }else if(content.indexOf("提交")!=-1){
		    	templateCode = SUBFLOWCODE;
		    	paramString = "{\"subflow\":\"'"+content+"'\"}";
		    }else if(content.indexOf("催办")!=-1){
		    	templateCode = PRESSCODE;
		    	paramString = "{\"press\":\"'"+content+"'\"}";
		    }else if(content.indexOf("完毕")!=-1){
		    	templateCode = OVERFLOWCODE;
		    	paramString = "{\"overflow\":\"'"+content+"'\"}";
		    }else if(content.indexOf("抄送")!=-1){
		    	templateCode = COPYFLOWCODE;
		    	paramString = "{\"copyflow\":\"'"+content+"'\"}";
		    }else if(content.indexOf("委托")!=-1){
		    	templateCode = FUSINESEFLOWCODE;
		    	paramString = "{\"fusineseflow\":\"'"+content+"'\"}";
		    }else if(content.indexOf("退回")!=-1){
		    	templateCode = BACKFLOWCODE;
		    	paramString = "{\"backflow\":\"'"+content+"'\"}";
		    }
		    Map<String, String> map = new HashMap<String, String>();
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
