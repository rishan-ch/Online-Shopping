package com.bway.BroadwayProject.controller;

import com.bway.BroadwayProject.model.User;
import com.bway.BroadwayProject.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/login")
	public String getLogin() {

		return "login";
	}

	@PostMapping("/login")
	public String postLogin(@ModelAttribute User user, Model model, HttpSession session){

		User usr = userRepo.findByEmailAndPassword(user.getEmail(),user.getPassword());

		session.setAttribute("validUser",usr);
		session.setMaxInactiveInterval(200);
		if(usr!=null){
			return "home";
		}
		model.addAttribute("loginFailed","User not found!");
		return "login";
	}

	@GetMapping("/signup")
	public String getSignup(){

		return "signup";
	}

	@PostMapping("/signup")
	public String postSignup(@ModelAttribute User user, Model model){

		//password encryption
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));

			userRepo.save(user);
			return "login";
	}

	@GetMapping("/logout")
	public String getUserLogout(HttpSession session){

		session.invalidate();
		return "login";
	}

}
