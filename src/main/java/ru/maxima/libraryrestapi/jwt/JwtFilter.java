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

    // осуществляет аутентификацию
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException, IOException {

        String authHeader = request.getHeader("Authorization");
//        достаем из HTTP запроса значение заголовка Authorization
        if (authHeader != null && authHeader.contains("Bearer ")) {
//            значение этого заголовка не null и токен валидный
            String token = authHeader.substring(7);
            if(!token.isBlank()) {
//                Класс, представляющий веб-токен Json, декодированный из его строкового представления.
                DecodedJWT decodedJWT =  JWT.require(Algorithm.HMAC256(JwtService.SECRET))
                        .build()
                        .verify(token);

//                Получите  по по ее названию.
//                Получите это утверждение как строку.
                Person person = Person.builder()
                        .email(decodedJWT.getClaim("Email").asString())
                        .role(decodedJWT.getClaim("Role").asString())
                        .name(decodedJWT.getClaim("Name").asString())
                        .id(decodedJWT.getClaim("Id").asLong())
                        .build();

                PersonDetails personDetails = new PersonDetails(person);
//                UsernamePasswordAuthenticationToken(экземпляр интерфейса Authentication)
//                после чего он передается экземпляру AuthenticationManager для проверки.
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(person, null, personDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}