package dev.microblog.config;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Класс для конфигурации отображения статического изображения на html-странице
 * @version 1.0
 */
@Configuration
@PropertySource("classpath:files.properties")
public class ImageConfig implements WebMvcConfigurer{
    @Value("${directory.images}")
    private String directory;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path path = Paths.get(directory);

        registry.addResourceHandler("/img/**")
            .addResourceLocations("file:" + File.separator + File.separator + File.separator + path.toAbsolutePath() + "/");
    }
    
}
