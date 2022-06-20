package com.pbr.service;

import com.pbr.dao.mysql.entity.MysqlFruit;

/**
 * @author pbr
 */
public interface DemoService {

    /**
     * test
     */
    void test();

    /**
     * @param name name
     */
    void deleteFruit(String name);


    /**
     * @param name name
     */
    void updateFruit(String name);


    void updateFruitEntity(MysqlFruit mysqlFruit);
}
