package dev.microblog.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Класс для описания информации о записи для HTML-формы
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EntryForm {
    // текст
    @NotEmpty(message = "Текст записи не может быть пустым")
    @Size(max = 200, message = "Текст записи не может содержать более 200 символов")
    private String text;
    // теги
    @NotEmpty(message = "Строка с тегами не может быть пустой")
    @Pattern(regexp = "(#[A-Za-z0-9]+\\s*)+", message = "Теги могут содержать только символы латиницы и цифры. "
            + "Теги должны записываться начиная с символа решетки и могут разделяться пробелом или записываться слитно")
    private String tags;
}
