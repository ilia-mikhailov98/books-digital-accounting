package study.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    int id;
    Integer personId;

    @NotEmpty
    String name;

    @NotEmpty
    String author;

    @Min(value = 1, message = "Год рождения должен быть положительным")
    int year;
}
