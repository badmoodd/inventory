package com.valoshka.inventory.controllers;

import com.valoshka.inventory.models.Storage;
import com.valoshka.inventory.services.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/storage")
public class StorageController {

    private final StorageService storageService;

    @GetMapping
    public String get(Model model){
        model.addAttribute("storages", storageService.getAll());
        return "k";
    }

    @GetMapping("/new")
    public String fr(Model model){
        model.addAttribute("storage", new Storage());
        return "new";
    }

    @PostMapping
    public String h(@ModelAttribute Storage storage) {
        storageService.saveStorage(storage);
        return "redirect:/storage";
    }


}
