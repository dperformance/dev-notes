package com.example.devnotes.tobyspringboot;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
@RestController
@RequestMapping("/hello")
public class HelloController {

    private final HelloService helloService;
    private final ApplicationContext applicationContext;

    public HelloController(HelloService helloService, ApplicationContext applicationContext) {
        this.helloService = helloService;
        this.applicationContext = applicationContext;
    }

    @GetMapping
    public String hello(String name) {
        return helloService.sayHello(Objects.requireNonNull(name));
    }


    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(applicationContext);
    }
}
