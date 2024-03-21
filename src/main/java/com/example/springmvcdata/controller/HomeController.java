package com.example.springmvcdata.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@SessionAttributes({"username", "isAuthenticated"})
public class HomeController {

    @GetMapping("/")
    public String index(final Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = !(authentication instanceof AnonymousAuthenticationToken);
        String username = null;
        if (isAuthenticated) {
            username = authentication.getName();
        }
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        List<String> authorityNames = authorities
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("username", username);
        model.addAttribute("authorities", authorityNames);
        return "home/index";
    }

}
