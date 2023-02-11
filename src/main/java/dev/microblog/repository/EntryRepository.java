package dev.microblog.repository;

import dev.microblog.entity.Entry;
import dev.microblog.entity.Person;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long>{
    Page<Entry> findAll(Pageable pageable);
    Optional<Entry> findByImageName(String imageName);
    List<Entry> findByTextContainingIgnoreCase(String text);
    Page<Entry> findByTextContainingIgnoreCase(String text, Pageable pageable);
    List<Entry> findByTags_Name(String name);
    Page<Entry> findByTags_Name(String name, Pageable pageable);
    Page<Entry> findByPerson(Person person, Pageable pageable);
}
