package com.sansec.webapp.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class LoginController {

	@RequestMapping("/login")
	@ResponseBody
	public String login() {
		return "Hello! Wellcome to login page.";
	}
}
