package dev.microblog.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для проверки текста регулярным выражением
 * @version 1.0
 */
public class Regexp {
    /**
     * Валидация наименования файла с расширением
     * @param filename - наименование файла
     * @param errorMessages - список ошибок
     */
    public static void getErrorsForUploadedFile(String filename, List<String> errorMessages) {
        if (!checkText("[A-Za-z]+\\.[a-z]+", filename)) {
            errorMessages.add("Наименование файла может содержать только символы латиницы, например: 'file.jpg'");
        }
        if (!checkText(".{1,50}", filename)) {
            errorMessages.add("Наименование файла (с расширением) должно содержать не более 50 символов");
        }
        if (!checkText("jpeg|jpg|png", filename.split("\\.")[1])) {
            errorMessages.add("Расширения файлов доступны только следующие: jpeg, jpg и png");
        }
    }

    /**
     * Метод для проверки текста регулярным выражением
     * @param regexp - регулярное выражение
     * @param text - текст
     */
    public static boolean checkText(String regexp, String text) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    /**
     * Получение числа из имени файла
     * @param filename - наименование файла
     */
    public static long getLastNumberInName(String filename) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(filename);
        long number = 0;
        while (matcher.find()) {
            String temp = matcher.group();
            number = Long.valueOf(temp);
        }

        return number;
    }
}
