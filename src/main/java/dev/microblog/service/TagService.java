package dev.microblog.service;

import dev.microblog.entity.Tag;
import dev.microblog.repository.TagRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    /**
     * Получение списка тегов
     */
    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    /**
     * Сохранение несуществующих тегов и получение списка всех тегов из БД, переданных в параметрах
     * @param tagNamesList - список наименований тегов
     */
    @Transactional
    public List<Tag> getTags(List<String> tagNamesList) {
        List<Tag> tagsList = new ArrayList<>();
        List<Tag> nonExistentTagsList = getNonExistentTagsList(tagNamesList);
        saveAllTags(nonExistentTagsList);
        tagNamesList.forEach(name -> {
            Tag tag = tagRepository.findByName(name).get();
            tagsList.add(tag);
        });
        
        return tagsList;
    }

    /**
     * Получение тега по наименованию
     */
    public Optional<Tag> getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    /**
     * Сохранение тега
     */
    @Transactional
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    /**
     * Сохранение списка тегов
     */
    @Transactional
    public List<Tag> saveAllTags(List<Tag> tags) {
        return tagRepository.saveAll(tags);
    }

    /**
     * Получение несуществующих тегов среди переданного списка наименований тегов
     */
    private List<Tag> getNonExistentTagsList(List<String> tagNamesList) {
        List<Tag> nonExistentTagsList = tagNamesList.stream()
                .filter(name -> tagRepository.findByName(name).isEmpty())
                .map(name -> new Tag(name))
                .toList();
        
        return nonExistentTagsList;
    }
}
