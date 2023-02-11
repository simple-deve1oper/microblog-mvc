package dev.microblog.service;

import dev.microblog.entity.Person;
import dev.microblog.repository.PersonRepository;
import dev.microblog.repository.RoleRepository;
import dev.microblog.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RegistrationService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void register(Person person) {
        String password = person.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        person.setPassword(encodedPassword);
        Role role = roleRepository.findByName("USER").get();
        person.setRole(role);
        personRepository.save(person);
    }
}
