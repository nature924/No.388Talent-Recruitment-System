package com.iurac.recruit;

import cn.hutool.core.util.IdUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class RecruitWebsiteApplicationTests {

    @Test
    void contextLoads() {
        for (int i = 0; i < 40; i++) {
            System.out.println(IdUtil.simpleUUID());
        }
    }
    @Test
    void test1() {
        Md5Hash md5Hash = new Md5Hash("123456","rmM!nKms",1024);
        System.out.println(md5Hash.toHex());
        System.out.println("e794bce41fd8646a7fe03e7affc3e3be");
    }

}
