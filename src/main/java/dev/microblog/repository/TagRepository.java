package dev.microblog.repository;

import dev.microblog.entity.Tag;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{
    Optional<Tag> findByName(String name);
}
