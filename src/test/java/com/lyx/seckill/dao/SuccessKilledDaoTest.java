package com.lyx.seckill.dao;

import com.lyx.seckill.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() {
        long seckillId = 1001;
        long userPhone = 13811112222L;
        successKilledDao.insertSuccessKilled(seckillId, userPhone);
    }

    @Test
    public void queryByIdWithSeckill() {
        long seckillId = 1000;
        long userPhone = 13811112222L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
        System.out.println(successKilled);
    }
}