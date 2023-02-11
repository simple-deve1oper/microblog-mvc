
package dev.microblog.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.web.multipart.MultipartFile;

/**
 * Класс для обработки файлов средствами Java
 * @version 1.0
 */
public class FileProcessing {
    /**
     * Создание директории с переданным именем, если не существует такой
     * @param directoryName - название директории
     */
    public static boolean createDirectory(String directoryName) throws IOException{
        boolean flag = false;
        Path path = Paths.get(directoryName);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
            flag = true;
        }
        
        return flag;
    }

    /**
     * Сохранение файла по переданному пути
     * @param file - файл, полученный из формы
     * @param pathToFile - путь к файлу
     */
    public static void saveFile(MultipartFile file, String pathToFile) throws IOException {
        Path path = Paths.get(pathToFile);
        file.transferTo(path);
    }
}
