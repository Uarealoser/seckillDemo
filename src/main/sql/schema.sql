--数据库初始化脚本
--创建数据库
create datebase seckill
--使用数据库
use seckill;
--创建秒杀库存表
create table seckill(
 seckill_id bigint not null AUTO_INCREMENT COMMENT '商品库存id',
 seckill_name varchar(150) not null COMMENT '商品名称',
seckill_number int not null COMMENT '库存数量',
  start_time timestamp not null COMMENT '秒杀开启时间',
  end_time timestamp not null COMMENT '秒杀结束时间',
  create_time TIMESTAMP  not null default current_timestamp COMMENT '创建时间',
  PRIMARY key (seckill_id),
  key idx_start_time(start_time),
  key idx_end_time(end_time),
  key idx_create_time(create_time)
)ENGING=InnoDB AUTO_INCREMENT=1000 default CHARSET=utf-8 COMMENT='秒杀库存表';
-- 初始化数据
insert into
seckill(seckill_name,seckill_number,start_time,end_time)
values
('100元秒杀电脑',10,'2019-5-7 00:00:00', '2019-5-8 00:00:00' ),
('10元秒杀牙刷',100,'2019-5-7 00:00:00', '2019-5-8 00:00:00' ),
('1000元秒杀别墅',1,'2019-5-7 00:00:00', '2019-5-8 00:00:00' );
-- 秒杀成功明细表
-- 用户登陆认证相关的信息
create table success_killed(
seckill_id bigint not null COMMENT '秒杀商品id',
user_phone bigint not null COMMENT '用户手机号',
state  tinyint not null default -1 COMMENT '状态显示：-1无效，0成功，1已付款 2已发货 3已收货',
create_time timestamp not null COMMENT '创建时间',
 primary key(seckill_id,user_phone),/*联合主键*/
  key idx_create_time(create_time)
)ENGING=InnoDB default CHARSET=utf-8 COMMENT='秒杀成功明细表';
-- 连接数据库的控制台
