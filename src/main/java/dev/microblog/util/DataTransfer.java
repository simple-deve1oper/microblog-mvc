package dev.microblog.util;

import dev.microblog.entity.Entry;
import dev.microblog.form.EntryForm;

import java.util.Arrays;
import java.util.List;

/**
 * Класс для перевода данных из модели в форму и наоборот
 * @version 1.0
 */
public class DataTransfer {
    /**
     * Перевод данных из объекта типа EntryForm в объект типа Entry (с изображением)
     * @param entryForm - объект типа EntryForm
     * @param imageName - наименование изображения
     */
    public static Entry transferDataEntryFromFormToModel(EntryForm entryForm, String imageName) {
        Entry entry = transferDataEntryFromFormToModel(entryForm);
        entry.setImageName(imageName);
        
        return entry;
    }

    /**
     * Перевод данных из объекта типа EntryForm в объект типа Entry (без изображения)
     * @param entryForm - объект типа EntryForm
     */
    public static Entry transferDataEntryFromFormToModel(EntryForm entryForm) {
        String text = entryForm.getText();
        Entry entry = new Entry(text);
        
        return entry;
    }

    /**
     * Получение списка наименований тегов из объекта типа EntryForm
     * @param entryForm - объект типа EntryForm
     * @return
     */
    public static List<String> getTagNamesArrayFromEntryForm(EntryForm entryForm) {
        String tags = entryForm.getTags();
        List<String> tagNamesList = Arrays.stream(tags.split("#|\\s#"))
                .filter(name -> !name.isBlank()).toList();
        
        return tagNamesList;
    }
}
