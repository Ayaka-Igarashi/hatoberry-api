package com.hatoberry.api.date.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class DateController {
    @GetMapping({"/date", "/date/"})
    public String getDate() {
        LocalDateTime nowDate = LocalDateTime.now();
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
        return dtf1.format(nowDate);
    }
}
