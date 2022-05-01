package com.company.springsecurity_jwt_emailsending.security;

import com.company.springsecurity_jwt_emailsending.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private  JwtProvider jwtProvider;
    private  AuthService authService;
@Autowired
    public JwtFilter(JwtProvider jwtProvider, @Lazy AuthService authService) {
        this.jwtProvider = jwtProvider;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization.startsWith("Bearer")) {
            String email = jwtProvider.getEmailFromToken(authorization.substring(7));
            if (email!=null) {
                UserDetails userDetails = authService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }
filterChain.doFilter(request,response);
    }
}
