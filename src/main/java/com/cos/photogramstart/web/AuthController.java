package com.cos.photogramstart.web;


import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.domain.user.Users;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor //final 필드를 DI할때 사용 
@Log4j2
@Controller // 1. Ioc 2. 파일을 리턴하는 컨트롤러
public class AuthController {
	
	
	private final AuthService authService;
	
//	public AuthController(AuthService authSevice) {
//		this.authService = authService;
//	}
	
	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}
	
	// 회원가입 기능
	@PostMapping("/auth/signup")
	public String signup(@Valid SignupDto signupDto,BindingResult bindingResult) {
		
	   if(bindingResult.hasErrors()) {
		   Map<String,String> errorMap = new HashMap<>();
		   
		   for(FieldError error : bindingResult.getFieldErrors()) {
			   errorMap.put(error.getField(), error.getDefaultMessage());
			   System.out.println("========================");
			   System.out.println(error.getDefaultMessage());
			   System.out.println("========================");
		  }//for
		   
	   }//if
		
		//User <- SignupDto
		Users user = signupDto.toEntity();
//		log.info(user.toString());
		Users userEntity = authService.회원가입(user);
		System.out.println(userEntity);
		return "auth/signin"; // 회원가입이 완료될시 로그인 페이지로 이동
	}
	
	
	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}
}
