package com.userfront.ressource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

//	@Autowired
//	private FacebookService facebookService;
//
//	@GetMapping("/createFacebookAuthorization")
//	public String createFacebookAuthorization() {
//		return facebookService.createFacebookAuthorizationURL();
//	}
//
//	@GetMapping("/facebook")
//	public void createFacebookAccessToken(@RequestParam("code") String code) {
//		facebookService.createFacebookAccessToken(code);
//	}
//
//	@GetMapping("/getName")
//	public String getNameResponse() {
//		return facebookService.getName();
//	}
}
