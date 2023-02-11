package dev.microblog.service;

import dev.microblog.entity.Entry;
import dev.microblog.entity.Person;
import dev.microblog.repository.EntryRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EntryService {
    @Autowired
    private EntryRepository entryRepository;

    /**
     * Получение списка записей
     */
    public List<Entry> getEntries() {
        return entryRepository.findAll();
    }

    /**
     * Получение страницы записей
     */
    public Page<Entry> getEntries(Pageable pageable) {
        return entryRepository.findAll(pageable);
    }

    public Page<Entry> findByPerson(Person person, Pageable pageable) {
        return entryRepository.findByPerson(person, pageable);
    }

    /**
     * Получение всех записей по наименованию определенного тега
     * @param name - наименование тега
     */
    public List<Entry> findByTags_Name(String name) {
        return entryRepository.findByTags_Name(name);
    }

    /**
     * Получение страницы записей по наименованию определенного тега
     * @param name - наименование тега
     */
    public Page<Entry> findByTags_Name(String name, Pageable pageable) {
        return entryRepository.findByTags_Name(name, pageable);
    }

    /**
     * Получение объекта записи по наименованию изображения
     * @param image - наименование изображения
     */
    public Optional<Entry> getEntryByImage(String image) {
        return entryRepository.findByImageName(image);
    }

    /**
     * Сохранение записи
     * @param entry - объект типа Entry
     */
    @Transactional
    public Entry saveEntry(Entry entry) {
        return entryRepository.save(entry);
    }

    /**
     * Удаление записи
     * @param id - идентификатор записи
     */
    @Transactional
    public void deleteEntry(Long id) {
        entryRepository.deleteById(id);
    }

    /**
     * Получение записей по содержанию текста, не учитывая регистр символов
     * @param text - содержимое текста
     */
    public List<Entry> findEntriesByTextContaining(String text) {
        return entryRepository.findByTextContainingIgnoreCase(text);
    }

    /**
     * Получение страницы записей по содержанию текста, не учитывая регистр символов
     * @param text - содержимое текста
     */
    public Page<Entry> findEntriesByTextContaining(String text, Pageable pageable) {
        return entryRepository.findByTextContainingIgnoreCase(text, pageable);
    }
}
