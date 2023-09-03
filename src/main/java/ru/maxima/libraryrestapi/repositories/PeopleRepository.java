package ru.maxima.libraryrestapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maxima.libraryrestapi.model.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Long> {

    Person findByName(String name);

   Optional<Person> findByEmail(String email);
}