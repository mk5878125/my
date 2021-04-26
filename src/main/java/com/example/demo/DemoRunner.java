package com.example.demo;

import com.example.demo.service.ConnectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

public class DemoRunner implements ApplicationRunner {

    @Autowired
    private ConnectService connectService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        connectService.sendCommand("aaa");
    }
}
