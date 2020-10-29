package com.project.Bookshop.filter;

import com.project.Bookshop.service.CustomUserDetailService;
import com.project.Bookshop.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
       String authorizationHeader = httpServletRequest.getHeader("Authorization");
       String token = null;
       String username = null;
       //Bearer
        // eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNYXVsaWQiLCJleHAiOjE2MDQwMTEzNDEsImlhdCI6MTYwMzk3NTM0MX0.AsRQkfVH26u6Q7znulQgKfn1ludZ4ZF0YLDpY8SVdlU
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer")){
            token = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails =  customUserDetailService.loadUserByUsername(username);

            if (jwtUtil.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
