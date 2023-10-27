package com.valoshka.inventory.controllers;


import com.valoshka.inventory.models.Waybill;
import com.valoshka.inventory.services.StorageService;
import com.valoshka.inventory.services.WaybillService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;


@Controller
@AllArgsConstructor
@RequestMapping("/waybills")
public class WaybillController {

    private final WaybillService waybillService;
    private final StorageService storageService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("waybills", waybillService.getAll());
        return "waybills/index";
    }

    @GetMapping("/new")
    public String newWaybill(Model model) {
        model.addAttribute("waybill", new Waybill());
        return "waybills/new";
    }

    @PostMapping
    public String create(@ModelAttribute Waybill waybill) {
        try {
            var storageToSet = storageService.getById(waybill.getStorage().getId()).orElseThrow();
            waybill.setDateTime(LocalDateTime.now());
            waybill.setStorage(storageToSet);
            waybillService.save(waybill);
        } catch (NoSuchElementException e) {
            return "waybills/exceptions/noSuchStorage";
        }
        return "redirect:/waybills";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        try {
            var waybill = waybillService.getById(id).orElseThrow();
            model.addAttribute("waybill", waybill);
            return "waybills/edit";
        } catch (NoSuchElementException e) {
            return "redirect:/waybills";
        }
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("waybill") Waybill waybill, @PathVariable("id") int id) {
        try {
            var storageToSet = storageService.getById(waybill.getStorage().getId()).orElseThrow();
            waybill.setDateTime(LocalDateTime.now());
            waybill.setStorage(storageToSet);
            waybillService.update(id, waybill);
        } catch (NoSuchElementException e) {
            return "waybills/exceptions/noSuchStorage";
        }
        return "redirect:/waybills";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        Waybill waybill = waybillService.getById(id).orElseThrow();
        System.out.println(waybill.getEquipmentCardList());
        waybillService.delete(id);
        return "redirect:/waybills";
    }

}
