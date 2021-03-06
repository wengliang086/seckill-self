-- 数据库初始化脚本

-- 创建数据库
create Database seckill;
-- 使用数据库
use seckill;
-- 创建表
create table seckill(
  `seckill_id` bigint NOT NULL AUTO_INCREMENT comment '商品库存id',
  `name` varchar(120) NOT NULL comment '商品名称',
  `number` int NOT NULL comment '库存数量',
  `start_time` timestamp NOT NULL comment '秒杀开始时间',
  `end_time` timestamp NOT NULL comment '秒杀结束时间',
  `create_time` timestamp NOT NULL default current_timestamp comment '创建时间',
  primary key (seckill_id),
  key idx_start_time(start_time),
  key idx_end_time(end_time),
  key idx_crate_time(create_time)
) ENGINE=InnoDB AUTO_INCREMENT=1000 default charset=utf8 comment = '秒杀库存表';

-- 插入测试数据
insert into
  seckill(name,number,start_time,end_time)
values
  ('1000元秒杀iphone6',100,'2018-7-18 00:00:00','2018-8-18 00:00:00'),
  ('500元秒杀ipad mini',200,'2018-7-18 00:00:00','2018-8-18 00:00:00'),
  ('2000元小米6',300,'2018-7-18 00:00:00','2018-8-18 00:00:00'),
  ('300元红米5',400,'2018-7-18 00:00:00','2018-8-18 00:00:00');

-- 秒杀成功明细表
--
create table success_killed (
  `seckill_id` bigint NOT NULL comment '商品库存id',
  `user_phone` bigint NOT NULL comment '手机号码',
  `state` tinyint NOT NULL default -1 comment '状态标识：-1：无效、0：成功、1：已付款',
  `create_time` timestamp NOT NULL comment '创建时间',
  PRIMARY KEY (seckill_id, user_phone),
  key idx_create_time(create_time)
) ENGINE=InnoDB default CHARSET=utf8 comment ='秒杀成功明细';

-- 连接数据库控制台
mysql -uroot -p

-- 为什么手写DDL
-- 记录每次上线的DDL修改
-- 上线版本v1.1
ALTER TABLE seckill
DROP Index idx_create_time,
ADD index idx_c_s(start_time, end_time);

-- 上线版本v1.2
-- DDL ...
