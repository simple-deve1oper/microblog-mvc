package dev.microblog.controller;

import dev.microblog.entity.Entry;
import dev.microblog.entity.Person;
import dev.microblog.entity.Tag;
import dev.microblog.form.EntryForm;
import dev.microblog.repository.PersonRepository;
import dev.microblog.security.PersonDetails;
import dev.microblog.service.EntryService;
import dev.microblog.service.TagService;
import dev.microblog.util.DataTransfer;
import dev.microblog.util.FileProcessing;
import dev.microblog.util.Regexp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class EntryController {
    @Autowired
    private EntryService entryService;
    @Autowired
    private TagService tagService;
    @Autowired
    private PersonRepository personRepository;
    @Value("${directory.images}")
    private String directory;

    /**
     * Получение главной страницы с записями
     */
    @GetMapping
    public String main(
            @AuthenticationPrincipal PersonDetails personDetails,
            Model model,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page
    ) {
        String username = personDetails.getUsername();
        Person person = personRepository.findByUsername(username).get();
        Page<Entry> entries = entryService.findByPerson(person, PageRequest.of(page, 5,
                Sort.by("creationDate").descending()));
        model.addAttribute("entries", entries);
        model.addAttribute("numbers", IntStream.range(0, entries.getTotalPages()).toArray());
        
        return "entries/main";
    }

    /**
     * Получение страницы с формой добавления новой записи
     * @param entryForm - объект класса EntryForm
     */
    @GetMapping("/add")
    public String getFormAddEntry(@ModelAttribute(name = "entryForm") EntryForm entryForm) {
        return "entries/addEntry";
    }

    /**
     * Добавление записи в БД
     * @param image - принимаемый файл от пользователя (может быть пустым)
     * @param entryForm - объект класса EntryForm
     */
    @PostMapping("/add")
    public String addEntry(
            @AuthenticationPrincipal PersonDetails personDetails,
            @RequestParam(name = "image") MultipartFile image,
            @ModelAttribute(name = "entryForm") @Valid EntryForm entryForm,
            BindingResult bindingResult,
            Model model
    ) {
        String filename = "";
        if (!image.getOriginalFilename().isBlank()) {
            filename = image.getOriginalFilename();
        }
        List<String> errorMessages = new ArrayList<>();
        if (!filename.isBlank()) {
            filename = image.getOriginalFilename();
            Regexp.getErrorsForUploadedFile(filename, errorMessages);
        }
        
        if (bindingResult.hasErrors() || (!errorMessages.isEmpty())) {
            model.addAttribute("errorMessages", errorMessages);
            
            return "entries/addEntry";
        }
        
        try {
            if (!filename.isBlank()) {
                FileProcessing.createDirectory(directory);

                while (!entryService.getEntryByImage(filename).isEmpty()) {
                    String[] data = filename.split("\\.");
                    long number = Regexp.getLastNumberInName(filename);
                    filename = filename.replaceAll("\\d+", filename);
                    data = filename.split("\\.");
                    if (number == 0) {
                        filename = data[0] + 2;
                    } else {
                        filename = data[0] + (number + 1);
                    }
                    filename += String.format(".%s", data[1]);
                }

                String pathToFile = directory + File.separator + filename;
                FileProcessing.saveFile(image, pathToFile);
            }
            
            Entry entry;
            if (filename.isBlank()) {
                entry = DataTransfer.transferDataEntryFromFormToModel(entryForm);
            } else {
                entry = DataTransfer.transferDataEntryFromFormToModel(entryForm, filename);
            }
            List<String> tagNamesListFromEntityForm = DataTransfer.getTagNamesArrayFromEntryForm(entryForm);
            List<Tag> tagsList = tagService.getTags(tagNamesListFromEntityForm);
            entry.setTags(tagsList);
            tagsList.forEach(tag -> tag.setEntries(Collections.singletonList(entry)));
            String username = personDetails.getUsername();
            Person person = personRepository.findByUsername(username).get();
            entry.setPerson(person);
            if (person.getEntries().isEmpty()) {
                person.setEntries(Collections.singletonList(entry));
            } else {
                person.getEntries().add(entry);
            }
            entryService.saveEntry(entry);
        } catch (IOException e) {
            System.err.println("Ошибка добавления записи: " + e.getMessage());
        }
        
        return "redirect:/";
    }

    /**
     * Удаление записи
     * @param id - идентификатор записи, которую необходимо удалить
     */
    @DeleteMapping("/delete/{id}")
    public String deleteEntry(@PathVariable("id") Long id) {
        entryService.deleteEntry(id);

        return "redirect:/";
    }

    /**
     * Поиск записей по тегу или содержанию текста
     * @param q - текст запроса
     */
    @GetMapping("/find")
    public String findEntries(@RequestParam("q") String q, Model model,
                              @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        if (q.isBlank()) {
            model.addAttribute("message", "Ошибка запроса! Запрос пустой");
            return "entries/findEntries";
        } else {
            boolean flag = Regexp.checkText("#[A-Za-zА-Яа-яЁё0-9]+", q);
            if (flag) {
                String tagName = q.replaceAll("#", "");
                Page<Entry> entries = entryService.findByTags_Name(tagName, PageRequest.of(page, 5, Sort.by("creationDate").descending()));
                if (entries.isEmpty()) {
                    model.addAttribute("message", "Сообщения по тегу '" + tagName + "' не найдены");
                } else {
                    model.addAttribute("tagName", tagName);
                    model.addAttribute("entries", entries);
                    model.addAttribute("numbers", IntStream.range(0, entries.getTotalPages()).toArray());
                }
                return "entries/findEntries";
            }
            flag = Regexp.checkText("[^#]+", q);
            if (flag) {
                String text = q;
                Page<Entry> entries = entryService.findEntriesByTextContaining(text, PageRequest.of(page, 5, Sort.by("creationDate").descending()));
                if (entries.isEmpty()) {
                    model.addAttribute("message", "Сообщения с содержанием '" + text + "' не найдены");
                } else {
                    model.addAttribute("text", text);
                    model.addAttribute("entries", entries);
                    model.addAttribute("numbers", IntStream.range(0, entries.getTotalPages()).toArray());
                }
                return "entries/findEntries";
            }

            model.addAttribute("message", "Ошибка запроса! Что-то пошло не так");
            return "entries/findEntries";
        }
    }
}
