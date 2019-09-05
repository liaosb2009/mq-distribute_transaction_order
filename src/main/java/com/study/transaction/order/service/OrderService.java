package com.study.transaction.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

@Service
public class OrderService {

	@Autowired
	OrderDatabaseService orderDatabaseService;

	@Autowired
	MQService mQService;

	/**
	 * 创建订单
	 */
	// @Transactional
	public void createOrder(JSONObject orderInfo) throws Exception {
		// 1. 订单信息 - 插入订单系统，订单数据库（事务-1）
		// 消息发送的时机： 订单创建成功之后
		orderDatabaseService.saveOrder(orderInfo);
		// TODO 此处省略一万行代码

		// 2. 通过http接口发送订单信息到 运单系统
		// String result = callDispatchHttpApi(orderInfo);
		// if (!"ok".equals(result)) {
		// throw new Exception("订单创建失败，原因[运单接口调用失败]");
		// }

		this.mQService.sendMsg(orderInfo);
	}

	/**
	 * 通过http接口发送 运单系统，将订单号传过去
	 *
	 * @return 接口调用结果
	 */
	public String callDispatchHttpApi(JSONObject orderInfo) {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		// 链接超时时间 > 3秒
		requestFactory.setConnectTimeout(3000);
		// 处理超时时间 > 2 秒
		requestFactory.setReadTimeout(2000);

		RestTemplate restTemplate = new RestTemplate(requestFactory);
		String httpUrl = "http://127.0.0.1:8080/dispatch-api/dispatch?orderId=" + orderInfo.getString("orderId");
		String result = restTemplate.getForObject(httpUrl, String.class);

		return result;
	}
}
