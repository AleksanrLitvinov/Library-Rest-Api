package ru.maxima.libraryrestapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.maxima.libraryrestapi.model.Person;
import ru.maxima.libraryrestapi.service.AuthService;
import ru.maxima.libraryrestapi.service.PersonDetailsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final PersonDetailsService personDetailsService;

    @GetMapping("/get-token")
    public String authentication(@RequestBody Person person) {
        return authService.authentication(person);
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginPerson(@RequestBody Person person)  {
            UserDetails personDetails = personDetailsService.loadUserByUsername(person.getEmail());
            if (!personDetails.isEnabled()) return ResponseEntity.status(HttpStatus.LOCKED).body("Person is blocked!");
            if (passwordEncoder.matches(person.getPassword(), personDetails.getPassword())) {
                return ResponseEntity.status(HttpStatus.OK).body("Person logged successfully \n Token: " +  authService.authentication(person));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person failed to login");

    }
}