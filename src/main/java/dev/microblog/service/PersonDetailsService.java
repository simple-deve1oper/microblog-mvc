package dev.microblog.service;

import java.util.Optional;

import dev.microblog.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.microblog.entity.Person;
import dev.microblog.security.PersonDetails;

@Service
public class PersonDetailsService implements UserDetailsService {
    @Autowired
    private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> optionalPerson = personRepository.findByUsername(username);

        if (optionalPerson.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь с таким именем не найден");
        }

        return new PersonDetails(optionalPerson.get());
    }
    
}
