package com.example.adapters.inbound.controller.config;

import com.example.adapters.inbound.controller.handler.ExceptionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private static final String MSG_UNAUTHORIZED = "No autorizado";
    private static final String MSG_INVALID_TOKEN = "El token no es v\u00E1lido";
    private static final String PAYLOAD_USERNAME = "username";
    public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";
    private static final String CHARACTER_ENCODING = "utf-8";

    public static final String PAYLOAD_ROLES = "roles";

    private static final String[] AUTH_WHITELIST_FILTER = {
        "/v2/api-docs",
        "/configuration/ui",
        "/swagger-resources",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars",
        "/health",
        "/refresh",
        "/api/v1.0/auth/login",
        "/api/v1.0/auth/validate",
        "/swagger-ui/index.html",
        "/swagger-ui/**",
        "/v3/api-docs/**"
    };

    private static final String secret = "S2FsbG9yIEJlZXIgc2VjcmV0IHNlY3JldCBzdHJpbmcgb2YgdGVzdGluZyBtZXNzYWdl";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if (existeJWTToken(request)) {
                Claims claims = validateToken(request);
                if (claims.get(PAYLOAD_ROLES) != null) {
                    setUpSpringAuthentication(claims);
                } else {
                    PrintWriter writer = response.getWriter();
                    SecurityContextHolder.clearContext();
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setCharacterEncoding(CHARACTER_ENCODING);
                    ExceptionResponse errorResponse = new ExceptionResponse();
                    errorResponse.setErrorMessage(MSG_UNAUTHORIZED);
                    errorResponse.setUrl(request.getRequestURI());
                    writer.write(convertObjectToJson(errorResponse));
                    writer.flush();
                    writer.close();
                }
            } else {
                PrintWriter writer = response.getWriter();
                SecurityContextHolder.clearContext();
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setCharacterEncoding(CHARACTER_ENCODING);
                ExceptionResponse errorResponse = new ExceptionResponse();
                errorResponse.setErrorMessage(MSG_UNAUTHORIZED);
                errorResponse.setUrl(request.getRequestURI());
                writer.write(convertObjectToJson(errorResponse));
                writer.flush();
                writer.close();
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            PrintWriter writer = response.getWriter();
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setCharacterEncoding(CHARACTER_ENCODING);
            ExceptionResponse errorResponse = new ExceptionResponse();
            errorResponse.setErrorMessage(MSG_INVALID_TOKEN);
            errorResponse.setUrl(request.getRequestURI());
            writer.write(convertObjectToJson(errorResponse));
            writer.flush();
            writer.close();
        }
    }

    private Claims validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER_AUTHORIZACION_KEY).replace(TOKEN_BEARER_PREFIX, "");
        try {
            return Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .build()
                    .parseSignedClaims(jwtToken)
                    .getPayload();
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    private void setUpSpringAuthentication(Claims claims) {
        @SuppressWarnings("unchecked")
        List<String> authorities = (List<String>) claims.get(PAYLOAD_ROLES);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                authorities.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Asegúrate de que roles estén correctamente formateados
                        .collect(Collectors.toList())
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private boolean existeJWTToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HEADER_AUTHORIZACION_KEY);
        return authenticationHeader != null && authenticationHeader.startsWith(TOKEN_BEARER_PREFIX);
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        return matcher(AUTH_WHITELIST_FILTER, request.getRequestURI());
    }

    private boolean matcher(String[] whiteList, String url) {
        AntPathMatcher matcher = new AntPathMatcher();
        return Arrays.stream(whiteList).anyMatch(pattern -> matcher.match(pattern, url));
    }


}

 /*  private void setUpSpringAuthentication(Claims claims) {
        @SuppressWarnings("unchecked")
        List<String> authorities = (List<String>) claims.get(PAYLOAD_ROLES);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                authorities.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList()));
        Object username = claims.get(PAYLOAD_USERNAME);
        if (username != null) {
            auth.setDetails(username);
        }
        SecurityContextHolder.getContext().setAuthentication(auth);
    }*/