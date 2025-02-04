package study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import study.dao.BookDAO;
import study.dao.PersonDAO;
import study.model.Book;
import study.model.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String allBooks(Model model) {
        model.addAttribute("books", bookDAO.findAll());

        return "books/all-books";
    }

    @GetMapping("/{id}")
    public String bookPage(Model model, @PathVariable int id) {
        model.addAttribute("book", bookDAO.findById(id));
        model.addAttribute("person", bookDAO.findPersonByBookId(id));
        model.addAttribute("people", personDAO.findAll());

        System.out.println(bookDAO.findPersonByBookId(id));
        System.out.println(personDAO.findAll());

        return "books/book-page";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {
        bookDAO.delete(id);

        return "redirect:/books";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new-book";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new-book";
        }

        bookDAO.create(book);

        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable int id) {
        model.addAttribute("book", bookDAO.findById(id));

        return "books/edit-book";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") Book book, BindingResult bindingResult, @PathVariable int id) {
        if (bindingResult.hasErrors()) {
            return "books/edit-book";
        }

        bookDAO.update(id, book);

        return "redirect:/books";
    }

    @PatchMapping("/{id}/free")
    public String freeBook(@PathVariable int id) {
        bookDAO.freeBookById(id);

        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign-person")
    public String assignPerson(@PathVariable int id, @ModelAttribute("person") Person person) {
        bookDAO.assignPersonToBook(person.getId(), id);

        return "redirect:/books";
    }
}
