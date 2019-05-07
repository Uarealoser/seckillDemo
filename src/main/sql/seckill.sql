--秒杀执行的存储过程
-- ROW_COUNT()返回上一条修改类型sql影响的行数
-- ROW_COUNT() 0:未修改数据 >0表示修改的行数 <0:sql错误/未执行
DELIMITER $$
CREATE PROCEDURE seckill.execute_seckill
(in v_seckill_id bigint,in v_phone bigint,in v_kill_time TIMESTAMP,out r_result int)
BEGIN
DECLARE insert_count int DEFAULT 0;
START TRANSACTION;
insert ignore into success_killed
(seckill_id,user_phone,create_time)
VALUES
(v_seckill_id,v_phone,v_kill_time);
SELECT ROW_COUNT() into insert_count;
if(insert_count=0) THEN
ROLLBACK;
SET r_result=-1;
ELSEIF(insert_count<0)THEN
ROLLBACK;
SET r_result=-2;
ELSE
UPDATE seckill
SET seckill_number=seckill_number-1
where seckill_id=v_seckill_id
and end_time>v_kill_time
and start_time<v_kill_time
and seckill_number>0;
SELECT ROW_COUNT() into insert_count;
if( insert_count=0)THEN
ROLLBACK;
set r_result=0;
ELSEIF (insert_count<0) THEN
ROLLBACK;
SET r_result=-2;
ELSE
COMMIT;
set r_result=1;
END IF;
END IF;
End;
$$
--执行存储过程
set @r_result=-3
call execute_seckill(1000,12314553,now(),@r_result);
--获取结果
SELECT @r_result;



