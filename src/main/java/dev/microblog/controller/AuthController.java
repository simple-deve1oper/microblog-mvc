package dev.microblog.controller;

import javax.validation.Valid;

import dev.microblog.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.microblog.entity.Person;
import dev.microblog.service.RegistrationService;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private PersonValidator personValidator;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute(name = "person") Person person) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(
        @ModelAttribute("person") @Valid Person person,
        BindingResult bindingResult
    ) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }

        registrationService.register(person);

        return "redirect:/";
    }
}
