package com.bway.BroadwayProject.controller;


import com.bway.BroadwayProject.model.Admin;
import com.bway.BroadwayProject.repository.AdminRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class AdminController {

    @Autowired
    private AdminRepository admnRepo;

    @GetMapping("/admin")
    public String getAdmin(HttpSession session){

        if(session.getAttribute("validAdmin")==null){
            return "adminLogin";
        }

        return "adminHome";
    }

    @GetMapping("/adminLogin")
    public String getAdminLogin(){
        return "adminLogin";
    }

    @PostMapping("/admin")
    public String postAdminLogin(@ModelAttribute Admin admin, HttpSession session){

        Admin validAdmin = admnRepo.findByUsernameAndPassword(admin.getUsername(), admin.getPassword());

        session.setAttribute("validAdmin",validAdmin);
        session.setMaxInactiveInterval(200);

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
        //password encryption
        admin.setPassword(DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()));
        admnRepo.save(admin);
        return "adminLogin";
    }

    @GetMapping("/adminLogout")
    public String getAdminLogout(HttpSession session){

        session.invalidate();

        return "adminLogin";
    }
    
}
