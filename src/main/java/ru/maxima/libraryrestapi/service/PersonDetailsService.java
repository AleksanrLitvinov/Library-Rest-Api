package ru.maxima.libraryrestapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.maxima.libraryrestapi.model.Person;
import ru.maxima.libraryrestapi.model.PersonDetails;
import ru.maxima.libraryrestapi.repositories.PeopleRepository;


//       UserDetailsService используется для получения имени пользователя, пароля и других атрибутов для аутентификации
//        с использованием имени пользователя и пароля
@Service
@RequiredArgsConstructor
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository personRepo;
    @Override
    public UserDetails loadUserByUsername(String email) {

        Person person = personRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found? ", email)));

        return new PersonDetails(person);
    }
}