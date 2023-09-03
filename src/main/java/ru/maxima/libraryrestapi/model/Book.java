package ru.maxima.libraryrestapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
//@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer yearOfProduction;
    private String author;
    private String annotation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime removedAt;
    private String createdPerson;
    private String updatedPerson;
    private String removedPerson;
    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;




}
