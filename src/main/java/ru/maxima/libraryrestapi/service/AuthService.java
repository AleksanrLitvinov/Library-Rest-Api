package ru.maxima.libraryrestapi.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maxima.libraryrestapi.model.Person;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public String authentication(Person person) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(person.getEmail(),person.getPassword());

            authenticationManager.authenticate(authenticationToken);
            return jwtService.getToken(person);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
