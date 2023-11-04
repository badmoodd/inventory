package com.valoshka.inventory.controllers;

import com.valoshka.inventory.models.Equipment;
import com.valoshka.inventory.models.Storage;
import com.valoshka.inventory.services.EquipmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@AllArgsConstructor
@RequestMapping("/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("equipment", equipmentService.getAll());
        return "equipment/index";
    }

    @GetMapping("/new")
    public String newStorage(Model model) {
        model.addAttribute("equipment", new Equipment());
        return "equipment/new";
    }

    @PostMapping
    public String create(@ModelAttribute Equipment equipment) {
        equipmentService.save(equipment);
        return "redirect:/equipment";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        try {
            Equipment equipment = equipmentService.getById(id).orElseThrow();
            model.addAttribute("equipment", equipment);
            return "equipment/edit";
        } catch (NoSuchElementException e) {
            return "redirect:/equipment";
        }
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("storage") Equipment equipment, @PathVariable("id") int id) {
        equipmentService.update(id, equipment);
        return "redirect:/equipment";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        equipmentService.delete(id);
        return "redirect:/equipment";
    }
}
