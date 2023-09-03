package ru.maxima.libraryrestapi.service;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maxima.libraryrestapi.model.Person;
import ru.maxima.libraryrestapi.repositories.PeopleRepository;

import com.auth0.jwt.JWT;
@Service
@RequiredArgsConstructor
public class JwtService {

    public static final String SECRET = "231123333";
    private final PeopleRepository peopleRepository;


    public String getToken(Person person) {
        Person personFromDB = peopleRepository
                .findByEmail(person.getEmail()).orElseThrow();

        return JWT.create()
                .withClaim("Email", personFromDB.getEmail())
                .withClaim("Role", personFromDB.getRole())
                .withClaim("Name", personFromDB.getName())
                .withClaim("Id", personFromDB.getId())
                .sign(Algorithm.HMAC256(SECRET));
    }

    public Person getUserNameFromToken() {
        return ((Person) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());
    }
}
