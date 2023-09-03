package ru.maxima.libraryrestapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.maxima.libraryrestapi.model.Person;
import ru.maxima.libraryrestapi.service.PeopleService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private final PeopleService personService;

    @PostMapping()
    @PreAuthorize("hasAnyAuthority(@authorities.ROLE_ADMIN)")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        Person personCreated = personService.creatPerson(person);
        return new ResponseEntity<>(personCreated, HttpStatus.OK);

    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority(@authorities.ROLE_ADMIN)")
    public List<Person> getAllPerson() {
        return personService.getAllPerson();
    }

    @GetMapping("{personId}")
    @PreAuthorize("hasAnyAuthority(@authorities.ROLE_ADMIN)")
    public Optional<Person> getPerson(@PathVariable Long personId) {
        return Optional.ofNullable(personService.getPerson(personId));
    }

    @PostMapping("/remove")
    @PreAuthorize("hasAnyAuthority(@authorities.ROLE_ADMIN)")
    public void removePerson(@RequestBody Person person) {
       personService.removePerson(person);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority(@authorities.ROLE_ADMIN)")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        Person personEdited = personService.updatePerson(person);
        return new ResponseEntity<>(personEdited, HttpStatus.OK);
    }

}