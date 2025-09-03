package com.iurac.recruit;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.iurac.recruit.mapper")
public class RecruitWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecruitWebsiteApplication.class, args);
    }

}
