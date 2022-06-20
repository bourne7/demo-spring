package com.pbr.controller;

import com.pbr.dao.mongo.entity.MongoFruit;
import com.pbr.dao.mongo.repository.KsBinSpaceUpdateRecordRepository;
import com.pbr.dao.mongo.repository.MongoFruitRepository;
import com.pbr.dao.mysql.entity.MysqlFruit;
import com.pbr.dao.mysql.repository.MysqlFruitRepository;
import com.pbr.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @Autowired
    private KsBinSpaceUpdateRecordRepository ksBinSpaceUpdateRecordRepository;


    @RequestMapping(value = "get1", method = RequestMethod.GET)
    public Object get1() {
        System.out.println(1);
        return new Date() + " - get1";
    }

    @RequestMapping(value = "get2", method = RequestMethod.GET)
    public Object get2() {
        return new Date() + " - get2";
    }

    @RequestMapping(value = "post1", method = RequestMethod.POST)
    public Object post1() {
        return new Date() + " - post1";
    }

    @RequestMapping(value = "post2", method = RequestMethod.POST)
    public Object post2() {
        return new Date() + " - post2";
    }

    @Autowired
    MongoFruitRepository mongoFruitRepository;

    @RequestMapping(value = "mongoFruit", method = RequestMethod.GET)
    public Object mongoFruit(@RequestParam(required = false) String name) {

        mongoFruitRepository.save(new MongoFruit(name));

        return mongoFruitRepository.findAll();
    }


    @Autowired
    MysqlFruitRepository mysqlFruitRepository;

    @Autowired
    EntityManager entityManager;

    @RequestMapping(value = "mysqlFruit", method = RequestMethod.GET)
    public Object mysqlFruit(@RequestParam String name) {

        MysqlFruit mysqlFruit = new MysqlFruit();
        mysqlFruit.setName(name);

        mysqlFruitRepository.save(mysqlFruit);

        return mysqlFruitRepository.findAll();
    }

    @RequestMapping(value = "deleteFruit", method = RequestMethod.GET)
    public Object deleteFruit(@RequestParam String name) {

        demoService.deleteFruit(name);

        return LocalDateTime.now();
    }

    @RequestMapping(value = "updateFruitByName", method = RequestMethod.GET)
    public Object updateFruitByName(@RequestParam String name) {

        demoService.updateFruit(name);

        return LocalDateTime.now();
    }

    @RequestMapping(value = "updateFruitEntity", method = RequestMethod.POST)
    public Object updateFruitEntity(@RequestBody MysqlFruit mysqlFruit) {

        demoService.updateFruitEntity(mysqlFruit);

        return LocalDateTime.now();
    }

}
