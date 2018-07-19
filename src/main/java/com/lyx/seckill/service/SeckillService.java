package com.lyx.seckill.service;

import com.lyx.seckill.dto.Exposer;
import com.lyx.seckill.dto.SeckillExecution;
import com.lyx.seckill.entity.Seckill;
import com.lyx.seckill.exception.RepeatKillException;
import com.lyx.seckill.exception.SeckillCloseException;
import com.lyx.seckill.exception.SeckillException;

import java.util.List;

/**
 * 业务接口：站在“使用者”的角度设计接口
 * 三个方面考虑：方法定义的粒度、参数、返回类型（return、异常）
 */
public interface SeckillService {

    /**
     * 查询所有秒杀记录
     *
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     *
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 导出秒杀URL
     *
     * @param seckillId
     * @return
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, SeckillCloseException, RepeatKillException;
}
