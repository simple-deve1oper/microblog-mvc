package dev.microblog.service;

import java.util.Optional;

import dev.microblog.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.microblog.entity.Person;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    
    public Optional<Person> findByUsername(String username) {
        return personRepository.findByUsername(username);
    }
}
