package com.pbr.service.impl;

import com.pbr.dao.mysql.entity.MysqlFruit;
import com.pbr.dao.mysql.repository.MysqlFruitRepository;
import com.pbr.service.DemoService;
import com.pbr.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author pbr
 */
@Service
@Slf4j
public class DemoServiceImpl implements DemoService {

    @Autowired
    MysqlFruitRepository mysqlFruitRepository;

    @Autowired
    EntityManager entityManager;

    @Override
    public void test() {

        // TODO write your code here

        log.info("testing...");

        // Read test

        // analysis data

        // read mysql

        List<MysqlFruit> fruits = mysqlFruitRepository.findAll();

        log.info(JsonUtils.obj2StringPretty(fruits));

        // export to local json file

        // print master2

        // web1

    }

    @Transactional
    @Override
    public void deleteFruit(String name) {

        mysqlFruitRepository.deleteByName(name);

        log.info(JsonUtils.obj2String(mysqlFruitRepository.findAllByName(name)));

        entityManager.flush();

        log.info(JsonUtils.obj2String(mysqlFruitRepository.findAllByName(name)));

        // throw new RuntimeException();

    }

    private static final AtomicInteger atomInteger = new AtomicInteger();

    /**
     * Test JPA
     *
     * @param name name
     */
    @Transactional(transactionManager = "mysqlTransactionManager", timeout = 36000, rollbackFor = Exception.class)
    @Override
    public void updateFruit(String name) {

        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            throw new RuntimeException("No transaction!");
        }

        MysqlFruit fruit = mysqlFruitRepository.findAllByName(name);

        log.info("before update: fruit {}", JsonUtils.obj2String(fruit));

        mysqlFruitRepository.updateFruit(atomInteger.getAndIncrement(), new Date(), name);

        log.info("after update: fruit {}", JsonUtils.obj2String(fruit));

        MysqlFruit fruit2 = mysqlFruitRepository.findAllByName(name);

        log.info("after update and select again: fruit {}", JsonUtils.obj2String(fruit));

        log.info("after update and select again: fruit2 {}", JsonUtils.obj2String(fruit2));

        fruit.setName("a1");

//        fruit2.setName("a2");

    }

    @Transactional(transactionManager = "mysqlTransactionManager", timeout = 36000, rollbackFor = Exception.class)
    @Override
    public void updateFruitEntity(MysqlFruit mysqlFruit) {

        mysqlFruitRepository.save(mysqlFruit);

        // 如果没有这一句的话, 则不会即时刷新到 数据库, 那么报错只会发生在事务提交的一瞬间. 如果加了这一句, 那么就会在过程中报错.
//        entityManager.flush();

        log.info("mysqlFruit {}", JsonUtils.obj2String(mysqlFruit));

        // 注意这里不会让 mysqlFruit 被 entityManager 管理
        mysqlFruit.setName("after saving");

    }
}
