-- 订单库的建表脚本 
create table table_order (
	order_id CHAR(36) primary key, 
	user_id	VARCHAR(36), 
	order_content VARCHAR(100), 
	create_time datetime);

create table tb_distributed_message (
	unique_id CHAR(36) primary key,  
	msg_content VARCHAR(500), 
	msg_status SMALLINT, 
	create_time datetime);


-- 派单库的建表脚本
create table table_dispatch (
	dispatch_seq CHAR(36) primary KEY, 
	order_id CHAR(36),
	dispatch_content VARCHAR(500));