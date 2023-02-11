package dev.microblog.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import dev.microblog.security.Role;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Класс-сущность для описания пользователя
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "people")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotEmpty(message = "Имя пользователя не может быть пустым")
    @Pattern(regexp = ".{2,60}", message = "Имя пользователя может содержать от 2 до 60 символов")
    @Pattern(regexp = "[A-Za-z]{1}[a-z0-9]{1,59}", message = "Имя пользователя может содержать только латинские символы и цифры. И также может начинаться с заглавной буквы")
    @Column(name = "username")
    private String username;
    @Pattern(regexp = ".{4,}", message = "Пароль должен содержать от 4 и более символов")
    @Column(name = "password")
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;
    @OneToMany(mappedBy = "person")
    private List<Entry> entries;
    public Person(String username) {
        this.username = username;
    }
}
