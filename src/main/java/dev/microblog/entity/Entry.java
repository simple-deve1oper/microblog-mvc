package dev.microblog.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Класс-сущность для описания записи
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "entries")
public class Entry {
    // идентификатор
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    // текст
    @Column(name = "text")
    private String text;
    // наименование изображения с расширением
    @Column(name = "image")
    private String imageName;
    // дата создания
    @Column(name = "creation_date", columnDefinition = "TEMPORAL")
    private LocalDateTime creationDate;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;
    
    // список тегов
    @ManyToMany
    @JoinTable(
            name = "entries_tags",
            joinColumns = @JoinColumn(name = "entry_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    public Entry(String text) {
        this.text = text;
        this.creationDate = LocalDateTime.now();
    }
    public Entry(String text, String imageName) {
        this.text = text;
        this.imageName = imageName;
        this.creationDate = LocalDateTime.now();
    }
}
