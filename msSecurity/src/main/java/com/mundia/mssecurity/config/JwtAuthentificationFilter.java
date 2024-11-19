package com.mundia.mssecurity.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthentificationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; //Bean dans ApplictionConfig
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); // Authorization: header de jwt
        final String jwt;
        final String userEmail; //login ou username
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;// pour ne pas continuer l'execution
        }
        jwt = authHeader.substring(7);// jwt se trouve après 'Bearer '
        userEmail = jwtService.extractUsername(jwt);
        System.out.println("**************************************************");
        System.out.println(userEmail);
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){ // SecurityContextHolder.getContext().getAuthentication() == null càd l'utilisateur n'est pas authentifié
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            System.out.println("**************************************************");
            System.out.println(userDetails.toString());
            System.out.println(jwtService.isTokenValid(jwt,userDetails));
            System.out.println("**************************************************");
            if(jwtService.isTokenValid(jwt,userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            filterChain.doFilter(request,response);
        }
    }

}
