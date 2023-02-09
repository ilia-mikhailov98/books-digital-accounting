package study.model;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person {
    private int id;

    @Size(max = 40, message = "ФИО не должно превышать 40 символов")
    @Pattern(regexp = "[А-Я][а-я]+ [А-Я][а-я]+ [А-Я][а-я]+", message = "Введите ФИО, разделенные пробелами")
    private String fullName;

    @Min(value = 1, message = "Год рождения должен быть положительным")
    private int birthYear;
}
