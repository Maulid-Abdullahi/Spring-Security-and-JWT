package com.project.Bookshop.controller;

import com.project.Bookshop.entity.AuthRequest;
import com.project.Bookshop.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String Welcome(){
        return "Welcome to BookShop ||";
    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception{
        try{
        //Validate Username and Password
        authenticationManager.authenticate (
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword())
        );
        }catch (Exception ex){
            throw new Exception("Invalid username and Password");
        }
        return jwtUtil.generateToken(authRequest.getUsername());
    }

}
