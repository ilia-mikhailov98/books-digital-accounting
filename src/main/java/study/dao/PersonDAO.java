package study.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import study.model.Book;
import study.model.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // BeanPropertyRowMapper maps column names to corresponding (eponymous) fields of class
    public List<Person> findAll() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public List<Book> findBooksByPerson(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE PersonId = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void create(Person person) {
        jdbcTemplate.update("INSERT INTO Person (FullName, BirthYear) VALUES (?, ?)", person.getFullName(), person.getBirthYear());
    }

    public Person findById(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE Person SET FullName = ?, BirthYear = ?  WHERE id = ?", person.getFullName(), person.getBirthYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id = ?", id);
    }

    public Optional<Person> findByFullName(String fullName) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE FullName = ?", new Object[]{fullName}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
}
