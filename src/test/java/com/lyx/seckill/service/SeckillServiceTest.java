package com.lyx.seckill.service;

import com.lyx.seckill.dto.Exposer;
import com.lyx.seckill.dto.SeckillExecution;
import com.lyx.seckill.entity.Seckill;
import com.lyx.seckill.exception.SeckillException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class SeckillServiceTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() {
        List<Seckill> seckillList = seckillService.getSeckillList();
        System.out.println(seckillList);
    }

    @Test
    public void getById() {
        Seckill byId = seckillService.getById(1000L);
        System.out.println(byId);
        logger.info("Seckill={}", byId.toString());

    }

    @Test
    public void exportSeckillUrl() {
        long id = 1000L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        System.out.println(exposer);
        if (exposer.isExposed()) {
            logger.info("exposer", exposer);
            executeSeckill(exposer.getSeckillId(), exposer.getMd5());
        } else {
            logger.error("exposer", exposer);
        }
    }

    //    @Test
    public void executeSeckill(long id, String md5) {
        long userPhone = 12322223335L;
        try {
            SeckillExecution execution = seckillService.executeSeckill(id, userPhone, md5);
            logger.info("SeckillExecution={}", execution);
        } catch (SeckillException e) {
            logger.error(e.getMessage());
        }
    }
}