package com.study.transaction.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

/**
 * 数据库操作相关的service
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderDatabaseService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * 1.保存订单记录
	 *
	 * @param orderInfo
	 *            订单内容
	 * @throws Exception
	 *             抛个异常
	 */
	public void saveOrder(JSONObject orderInfo) throws Exception {
		String sql = "insert into table_order (order_id, user_id, order_content, create_time) values (?, ?, ?,now())";
		// 1. 添加订单记录
		jdbcTemplate.update(sql, orderInfo.get("orderId"), orderInfo.get("userId"), orderInfo.get("orderContent"));

		this.saveLocalMessage(orderInfo);
	}

	/**
	 * 2 保存本地消息表信息
	 *
	 * @param orderInfo
	 *            订单内容
	 * @throws Exception
	 */
	public void saveLocalMessage(JSONObject orderInfo) throws Exception {
		String sql = "insert into tb_distributed_message (unique_id, msg_content, msg_status, create_time) values (?, ?, ?, now())";
		jdbcTemplate.update(sql, orderInfo.get("orderId"), orderInfo.toJSONString(), 0);
	}
}
