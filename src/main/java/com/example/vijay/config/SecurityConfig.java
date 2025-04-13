package com.example.vijay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, ClientRegistrationRepository clientRegistrationRepository) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/public/**").permitAll() // Public endpoints
                        .anyRequest().authenticated() // All other endpoints require authentication
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/home", true) // Redirect to /home after successful login
                       /* .userInfoEndpoint(userInfo -> userInfo
                                .userAuthoritiesMapper(this::mapAuthorities) // Map authorities from OAuth2 user
                        )*/
                )
                .logout(logout -> logout
                        .logoutSuccessHandler(oidcLogoutSuccessHandler(clientRegistrationRepository)) // OIDC logout
                        .invalidateHttpSession(true) // Invalidate session on logout
                        .clearAuthentication(true) // Clear authentication on logout
                        .deleteCookies("JSESSIONID") // Delete cookies on logout
                );
        return http.build();
    }

    /**
     * Maps authorities from the OAuth2 user to Spring Security's GrantedAuthority.
     */
    private Collection<GrantedAuthority> mapAuthorities(OAuth2User oauth2User) {
        // Extract authorities from the OAuth2User object
        return oauth2User.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority())) // Map each authority
                .collect(Collectors.toList());
    }

    /**
     * Configures OIDC logout to redirect to the OAuth2 provider's logout endpoint.
     */
    private LogoutSuccessHandler oidcLogoutSuccessHandler(ClientRegistrationRepository clientRegistrationRepository) {
        OidcClientInitiatedLogoutSuccessHandler successHandler =
                new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        successHandler.setPostLogoutRedirectUri(String.valueOf(URI.create("http://localhost:8080/home"))); // Redirect to home after logout
        return successHandler;
    }
}