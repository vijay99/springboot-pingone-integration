package com.example.vijay.controller;


import org.springframework.ui.Model;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model,
                       @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                       OAuth2AuthenticationToken authentication) {

        OAuth2User user = authentication.getPrincipal();
        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();

        model.addAttribute("userName", user.getName());
        model.addAttribute("attributes", user.getAttributes());
        model.addAttribute("accessToken", accessToken.getTokenValue());
        model.addAttribute("expiresAt", accessToken.getExpiresAt());

        return "home";
    }
}
