package study.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import study.model.Book;
import study.model.Person;

import java.util.List;

@Component
public class BookDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Person findPersonByBookId(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id = (SELECT PersonId FROM Book WHERE id = ?)", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(new Person());
    }

    public void create(Book book) {
        jdbcTemplate.update("INSERT INTO Book (Name, Author, Year) VALUES (?, ?, ?)", book.getName(), book.getAuthor(), book.getYear());
    }

    public Book findById(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET Name = ?, Author = ?, Year = ?  WHERE id = ?", book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id = ?", id);
    }

    public void freeBookById(int id) {
        jdbcTemplate.update("UPDATE Book SET PersonId = NULL WHERE id = ?", id);
    }

    public void assignPersonToBook(int personId, int bookId) {
        jdbcTemplate.update("UPDATE Book SET PersonId = ? WHERE id = ?", personId, bookId);
    }
}
