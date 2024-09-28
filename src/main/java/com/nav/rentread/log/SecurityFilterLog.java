package com.nav.rentread.log;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@RequiredArgsConstructor
@Log4j2
public class SecurityFilterLog extends OncePerRequestFilter {


    private final String prefix;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("{}: {} {}", prefix, request.getRequestURI(),request.getHeader(HttpHeaders.AUTHORIZATION));
        filterChain.doFilter(request, response);
    }
}
