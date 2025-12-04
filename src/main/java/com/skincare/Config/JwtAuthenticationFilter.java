package com.skincare.Config;

import com.skincare.Repositories.UserRepository;
import com.skincare.Services.Impl.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Long userId = null;
        String jwtToken = null;

        final String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            jwtToken = authHeader.substring(7);
        }

        // if jwt token is null, need to check or fetch token from cookie
        if(jwtToken == null){
            Cookie[] cookies = request.getCookies();
            if(cookies != null){
                for (Cookie c: cookies){
                    if("JWT".equals(c.getName())){
                        jwtToken = c.getValue();
                        break;
                    }
                }
            }
        }

        // if no token found
        if(jwtToken == null){
            filterChain.doFilter(request,response);
            return;
        }

        // extract user id form jwt token
        userId = jwtService.extractUserId(jwtToken  );

        if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // fetch user details
            var userDetails = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));


            //  validate token
            if (jwtService.isTokenValid(jwtToken,userDetails )){
                List<SimpleGrantedAuthority> authorities = userDetails.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .toList();


                UsernamePasswordAuthenticationToken authenticationToken = new
                        UsernamePasswordAuthenticationToken(userDetails,null,authorities);

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }
        filterChain.doFilter(request,response);
    }
}
