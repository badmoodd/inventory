package com.valoshka.inventory.controllers;

import com.valoshka.inventory.models.EquipmentCard;
import com.valoshka.inventory.models.compositeKey.EquipmentCardKey;
import com.valoshka.inventory.services.EquipmentCardService;
import com.valoshka.inventory.services.StorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/records")
public class EquipmentCardRecordsController {

    EquipmentCardService equipmentCardService;
    StorageService storageService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("equipment_cards", equipmentCardService.getAll());
        return "equipment_records/index";
    }

    @GetMapping("/new")
    public String newRecord(Model model) {
        model.addAttribute("equipment_card", new EquipmentCard());
        return "equipment_records/new";
    }

    @PostMapping
    public String create(@ModelAttribute EquipmentCard equipmentCard) {
        try {
            var storageToSet = storageService
                    .getById(equipmentCard.getWaybill().getStorage().getId())
                    .orElseThrow();
            equipmentCard
                    .getWaybill()
                    .setDateTime(LocalDateTime.now());
            equipmentCard
                    .getWaybill()
                    .setStorage(storageToSet);
            equipmentCard.
                    setId(equipmentCard.getWaybill().getId(), equipmentCard.getEquipment().getId());
            equipmentCardService.save(equipmentCard);
        } catch (NoSuchElementException ex) {
            return "waybills/exceptions/noSuchStorage";
        }
        return "redirect:/records";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editRecord(@RequestParam("equipmentId") int equipmentId,
                             @RequestParam("waybillId") int waybillId,
                             Model model) {
        try {
            var cardToEdit = equipmentCardService
                    .getByCompositeKey(new EquipmentCardKey(equipmentId, waybillId))
                    .orElseThrow();
            model.addAttribute("card_to_edit", cardToEdit);
            return "equipment_records/edit";

        } catch (NoSuchElementException ex) {
            return "redirect:/records";
        }
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public String update(@RequestParam("equipmentId") int equipmentId,
                         @RequestParam("waybillId") int waybillId,
                         @ModelAttribute("card_to_edit") EquipmentCard editedCard) {

        var isStorageExist = storageService
                .getById(editedCard.getWaybill().getStorage().getId());

        if (isStorageExist.isPresent()) {
            equipmentCardService.update(new EquipmentCardKey(equipmentId, waybillId), editedCard);
            return "redirect:/records";
        } else {
            log.info("Attempt to set no exist storage");
            return "waybills/exceptions/noSuchStorage";
        }

    }

    @RequestMapping(method = RequestMethod.DELETE)
    public String delete(@RequestParam("equipmentId") int equipmentId,
                         @RequestParam("waybillId") int waybillId) {

        equipmentCardService.delete(new EquipmentCardKey(equipmentId, waybillId));
        return "redirect:/records";
    }


}
