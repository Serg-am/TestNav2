package com.example.controller;

import com.example.model.DatabaseEntity;
import com.example.repositories.DatabaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/index")
@Slf4j
public class DatabaseController {
    private final DatabaseRepository databaseRepository;

    @Autowired
    public DatabaseController(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    @GetMapping()
    public String index(Model model) {
        List<DatabaseEntity> databases = databaseRepository.showAllDb();
        model.addAttribute("databases", databases);
        return "index";
    }

    @GetMapping("/new")
    public String newDataBase(@ModelAttribute("database") DatabaseEntity database) {
        return "new";
    }

    @PostMapping()
    public String createDatabase(@ModelAttribute("database") @Valid DatabaseEntity database,
                         BindingResult bindingResult) {
        try {
            databaseRepository.createDatabase(database.getDatabaseName());
            return "redirect:/index";
        } catch (Exception ex) {
            log.info("База данных с именем " + database.getDatabaseName() + " уже существует!");
            bindingResult.reject("databaseName", "База данных с таким именем уже существует");
            return "new";
        }
    }


}
