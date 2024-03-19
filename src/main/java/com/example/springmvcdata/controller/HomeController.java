package com.example.springmvcdata.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = !(authentication instanceof AnonymousAuthenticationToken);
        String username = null;
        if (isAuthenticated) {
            username = authentication.getName();
        }
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("username", username);
        model.addAttribute("authorities", authorities);
        return "home/index";
    }

}
