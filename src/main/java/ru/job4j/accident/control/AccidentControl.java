package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.repository.AccidentTypeMem;
import ru.job4j.accident.service.AccidentService;

import java.util.List;

@Controller
public class AccidentControl {
    private final AccidentService accidentService;
    private final AccidentTypeMem accidentTypes;

    public AccidentControl(AccidentService accidentService, AccidentTypeMem accidentTypes) {
        this.accidentService = accidentService;
        this.accidentTypes = accidentTypes;
    }

    @GetMapping("/create")
    public String create(Model model) {
        List<AccidentType> types = accidentTypes.getAccidentTypes();
        model.addAttribute("types", types);
        return "/accident/create";
    }

    @GetMapping("/update")
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("accident", accidentService.getById(id));
        List<AccidentType> types = accidentTypes.getAccidentTypes();
        model.addAttribute("types", types);
        return "/accident/update";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident) {
        accidentService.save(accident);
        return "redirect:/";
    }
}
