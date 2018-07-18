-- 数据库初始化脚本

-- 创建数据库
create Database seckill;
-- 使用数据库
use seckill;
-- 创建表
create table seckill(
  'seckill_id' bigint NOT NULL AUTO_INCREMENT COMMIT '商品库存id',
  'name' varchar(120) not null COMMIT '商品名称',
  'number' int not null commit '库存数量'
  'start_time' timestamp not null commit '秒杀开始时间',
  'end_time' timestamp not null commit '秒杀结束时间',
  'create_time' timestamp not null default current_timestamp commit '创建时间',
  primary key (seckill_id),
  key idx_start_time(start_time),
  key idx_end_time(end_time),
  key idx_crate_time(create_time)
) ENGINE=InnoDB AUTO_INCREMENT=1000 default charset=utf8 commit = '秒杀库存表';

-- 插入测试数据
insert into
  seckill(name,number,start_time,end_time)
values
  ('1000元秒杀iphone6',100,"2018-7-18 00:00:00","2018-8-18 00:00:00")
  ('500元秒杀ipad mini',100,"2018-7-18 00:00:00","2018-8-18 00:00:00")
  ('2000元小米6',300,"2018-7-18 00:00:00","2018-8-18 00:00:00")
  ('300元红米5',400,"2018-7-18 00:00:00","2018-8-18 00:00:00")

-- 秒杀成功明细表
--
create table success_killed (
  'seckill_id' bigint not null commit '商品库存id',
  'user_phone' bigint not null '手机号码',
  'state' tinyint not null default -1 commit '状态标识：-1：无效、0：成功、1：已付款'
  'create_time' timestamp not null commit '创建时间'
) ENGINE=InnoDB default CHARSET=utf8 commit ='秒杀成功明细';

-- 连接数据库控制台
mysql -uroot -p
