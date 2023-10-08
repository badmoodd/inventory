package com.valoshka.inventory.controllers;

import com.valoshka.inventory.models.Storage;
import com.valoshka.inventory.services.EquipmentCardService;
import com.valoshka.inventory.services.EquipmentService;
import com.valoshka.inventory.services.StorageService;
import com.valoshka.inventory.services.WaybillService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@AllArgsConstructor
@RequestMapping("/storages")
public class StorageController {

    private final StorageService storageService;

    @GetMapping
    public String index(Model model){
        model.addAttribute("storages", storageService.getAll());
        return "storages/index";
    }

    @GetMapping("/new")
    public String newStorage(Model model){
        model.addAttribute("storage", new Storage());
        return "storages/new";
    }
    @PostMapping
    public String create(@ModelAttribute Storage storage) {
        storageService.save(storage);
        return "redirect:/storages";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        try {
            var storage = storageService.getById(id).orElseThrow();
            model.addAttribute("storage", storage);
            return "storages/edit";
        } catch (NoSuchElementException e) {
            return "redirect:/storages";
        }
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("storage") Storage storage, @PathVariable("id") int id) {
        storageService.update(id, storage);
        return "redirect:/storages";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        storageService.delete(id);
        return "redirect:/storages";
    }




}
