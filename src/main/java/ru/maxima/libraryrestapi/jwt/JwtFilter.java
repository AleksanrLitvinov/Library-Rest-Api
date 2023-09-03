package ru.maxima.libraryrestapi.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ru.maxima.libraryrestapi.model.Person;
import ru.maxima.libraryrestapi.model.PersonDetails;
import ru.maxima.libraryrestapi.service.JwtService;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    //но гарантированно вызывается только один раз для каждого запроса в одном потоке запросов.

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.contains("Bearer ")) {
            String token = authHeader.substring(7);
            if(!token.isBlank()) {
                DecodedJWT decodedJWT =  JWT.require(Algorithm.HMAC256(JwtService.SECRET))
                        .build()
                        .verify(token);

                Person person = Person.builder()
                        .email(decodedJWT.getClaim("Email").asString())
                        .role(decodedJWT.getClaim("Role").asString())
                        .name(decodedJWT.getClaim("Name").asString())
                        .id(decodedJWT.getClaim("Id").asLong())

                        .build();
                PersonDetails personDetails = new PersonDetails(person);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(person, null, personDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}