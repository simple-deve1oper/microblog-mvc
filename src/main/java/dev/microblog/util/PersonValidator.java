package dev.microblog.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import dev.microblog.entity.Person;
import dev.microblog.service.PersonService;

@Component
public class PersonValidator implements Validator {
    @Autowired
    private PersonService personService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        
        if (personService.findByUsername(person.getUsername()).isPresent()) {
            errors.rejectValue("username", "", "Человек с таким именем пользователя уже существует");
        }
    }
}
