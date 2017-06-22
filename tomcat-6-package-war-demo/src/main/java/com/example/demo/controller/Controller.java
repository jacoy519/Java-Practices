package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by medivh on 2017/6/22.
 */
@RestController
@RequestMapping( value = "/test")
public class Controller {

    @RequestMapping( method = RequestMethod.GET)
    public String test() {
        return "test";
    }
}
