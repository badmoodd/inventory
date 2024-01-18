package com.valoshka.inventory.controllers;

import com.valoshka.inventory.models.Storage;
import com.valoshka.inventory.services.EquipmentService;
import com.valoshka.inventory.services.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

@Controller
@AllArgsConstructor
@RequestMapping("/storages")
public class StorageController {

    private final StorageService storageService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("storages", storageService.getAll());
        model.addAttribute("equipmentName", "");
        return "storages/index";
    }

    @GetMapping("/new")
    public String newStorage(Model model) {
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
            Storage storage = storageService.getById(id).orElseThrow();
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

    @GetMapping("/sort")
    public String showSortedStoragesByDate(@RequestParam(value = "date") String dateStr, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(dateStr, formatter);

        model.addAttribute("storages", storageService.getAllSortedByNameLessWaybillDate(date));
        return "storages/sortedByDate";
    }

    @GetMapping("/{id}/equipment")
    public String showOverallCountOfSpecificEquipmentByDate(@PathVariable("id") int id,
                                                            @RequestParam(value = "name") String equipmentName,
                                                            @RequestParam(value = "date") String dateStr,
                                                            Model model) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(dateStr, formatter);

        model.addAttribute("storageEquipmentCount", storageService.findEquipmentAndItsCountOnDate(id, equipmentName, date));
        model.addAttribute("dateToFilter", date);
        return "storages/storageEquipmentInfo";
    }


}
