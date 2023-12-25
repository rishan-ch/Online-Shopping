package com.bway.BroadwayProject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustpmerRestController {

    @GetMapping("api/customer/user")
    public String getUser(){
        return "";
    }
    
}
