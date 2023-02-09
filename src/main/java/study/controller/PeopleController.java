package study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import study.dao.PersonDAO;
import study.model.Person;
import study.util.PersonValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private PersonDAO personDAO;
    private PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String allPeople(Model model) {
        model.addAttribute("people", personDAO.findAll());

        return "people/all-people";
    }

    @GetMapping("/{id}")
    public String personPage(Model model, @PathVariable int id) {
        model.addAttribute("person", personDAO.findById(id));
        model.addAttribute("books", personDAO.findBooksByPerson(id));

        return "people/person-page";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable int id) {
        personDAO.delete(id);

        return "redirect:/people";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new-person";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/new-person";
        }

        personDAO.create(person);

        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable int id) {
        model.addAttribute("person", personDAO.findById(id));

        return "people/edit-person";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") Person person, BindingResult bindingResult, @PathVariable int id) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/edit-person";
        }

        personDAO.update(id, person);

        return "redirect:/people";
    }
}
