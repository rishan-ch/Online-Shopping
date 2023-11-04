package com.bway.BroadwayProject.controller;


import com.bway.BroadwayProject.model.Admin;
import com.bway.BroadwayProject.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class AdminController {

    @Autowired
    private AdminRepository admnRepo;

    @GetMapping("/admin")
    public String getAdmin(){
        return "adminHome";
    }

    @GetMapping("/adminLogin")
    public String getAdminLogin(){
        return "adminLogin";
    }

    @PostMapping("/admin")
    public String postAdminLogin(@ModelAttribute Admin admin){
        if(admnRepo.findByUsernameAndPassword(admin.getUsername(), admin.getPassword())!=null){
            return "adminHome";
        }
        return"adminLogin";
    }

    @GetMapping("/adminSignup")
    public String getAdminSignup(){
        return "adminSignup";
    }

    @PostMapping("/adminLogin")
    public String postAdminSignup(@ModelAttribute Admin admin){
        admnRepo.save(admin);
        return "adminLogin";
    }

    @GetMapping("/adminLogout")
    public String getAdminLogout(){
        return "adminLogin";
    }
    
}
