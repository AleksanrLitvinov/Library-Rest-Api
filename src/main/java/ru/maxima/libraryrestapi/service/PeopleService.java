package ru.maxima.libraryrestapi.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maxima.libraryrestapi.jwt.Authorities;
import ru.maxima.libraryrestapi.model.Person;
import ru.maxima.libraryrestapi.repositories.PeopleRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PeopleService {

       private final PeopleRepository peopleRepository;
       private final JwtService jwtService;
       private final PasswordEncoder passwordEncoder;

       public List<Person> getAllPerson() {
           return peopleRepository.findAll();
       }

       public Person creatPerson(Person person) {
           Person personForSave = Person.builder()
                   .name(person.getName())
                   .createdPerson(jwtService.getUserNameFromToken().getName())
                   .password(passwordEncoder.encode(person.getPassword()))
                   .email(person.getEmail())
                   .role(Authorities.ROLE_USER)
                   .createdAt(LocalDateTime.now())
                   .build();
         return peopleRepository.save(personForSave);
       }

       public Person updatePerson(Person person) {
           Person personForUpdate = peopleRepository.getReferenceById(person.getId());

           personForUpdate.setName(person.getName());
           personForUpdate.setAge(person.getAge());
           personForUpdate.setPhoneNumber(person.getPhoneNumber());
           personForUpdate.setPassword(passwordEncoder.encode(person.getPassword()));
           personForUpdate.setRole(person.getRole());

           return peopleRepository.save(personForUpdate);
       }

       public void removePerson(Person person){
         peopleRepository.getReferenceById(person.getId()).setRemovedPerson(jwtService.getUserNameFromToken().getName());
         peopleRepository.getReferenceById(person.getId()).setRemovedAt(LocalDateTime.now());
       }

       public Person getPerson(Long id) {
           return peopleRepository.findById(id).orElseThrow();
       }
}
