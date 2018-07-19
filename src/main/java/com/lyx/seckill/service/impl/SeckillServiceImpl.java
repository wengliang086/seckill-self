package com.lyx.seckill.service.impl;

import com.lyx.seckill.dao.SeckillDao;
import com.lyx.seckill.dao.SuccessKilledDao;
import com.lyx.seckill.dto.Exposer;
import com.lyx.seckill.dto.SeckillExecution;
import com.lyx.seckill.entity.Seckill;
import com.lyx.seckill.entity.SuccessKilled;
import com.lyx.seckill.enums.SeckillStatEnum;
import com.lyx.seckill.exception.RepeatKillException;
import com.lyx.seckill.exception.SeckillCloseException;
import com.lyx.seckill.exception.SeckillException;
import com.lyx.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SeckillDao seckillDao;

    @Resource
    private SuccessKilledDao successKilledDao;

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 100);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        Date now = new Date();
        if (seckill.getNumber() <= 0 || seckill.getStartTime().getTime() > now.getTime() || seckill.getEndTime().getTime() < now.getTime()) {
            return new Exposer(false, seckillId, now.getTime(), seckill.getStartTime().getTime(), seckill.getEndTime().getTime());
        }
        String md5 = getMd5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    private static final String slat = "fjowfmndasdfj!*@&#2131dskfsdf";

    private String getMd5(long seckillId) {
        String base = seckillId + "/" + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    /**
     * 使用注解来控制事务方法的优点：
     * 1、开发团队达成一致性，明确标注事务方法的编码风格
     * 2、保证事务方法的执行时间尽可能短，不用穿插其它网络操作RPC/HTTP请求，或者剥离到事务方法外部
     * 3、不是所有操作都需要事务，如只有一条修改操作，或者只读操作
     */
    @Transactional
    @Override
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException {
        try {
            if (md5 == null || !md5.equals(getMd5(seckillId))) {
                throw new SeckillException(SeckillStatEnum.DATA_REWRITE.getStateInfo());
            }
            Date now = new Date();
            int updateCount = seckillDao.reduceNumber(seckillId, now);
            if (updateCount <= 0) {
                throw new SeckillCloseException(SeckillStatEnum.END.getStateInfo());
            }
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if (insertCount <= 0) {
                throw new RepeatKillException(SeckillStatEnum.REPEAT_KILL.getStateInfo());
            }
            SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
            return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
        } catch (SeckillException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage() + e);
            throw new SeckillException("");
        }
    }
}
